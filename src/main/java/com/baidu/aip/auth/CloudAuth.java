/*
 * Copyright 2017 Baidu, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.baidu.aip.auth;

import com.baidu.aip.http.AipRequest;
import com.baidu.aip.util.AipClientConst;
import com.baidu.aip.util.SignUtil;
import com.baidu.aip.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class CloudAuth {

    /**
     * @param request AipRequest Object
     * @param ak access key ID
     * @param sk secret access key
     * @param timestamp UTC timestamp
     * @return signed authorization header for cloud auth
     */
    public static String sign(AipRequest request, String ak, String sk, String timestamp) {
        HashMap<String, String> headers = request.getHeaders();
        HashMap<String, String> params = request.getParams();
        String httpMethod = request.getHttpMethod().toString();
        String path = request.getUri().getPath();
        // 1. 生成signingKey
        //  1.1 authString,格式为：bce-auth-v1/{accessKeyId}/{timestamp}/{expirationPeriodInSeconds}
        String authStringPrefix = String.format("bce-auth-v1/%s/%s/%d",
                ak, timestamp, AipClientConst.BCE_AUTH_EXPIRE_IN_SECONDS);

        try {
            // 1.2.使用authStringPrefix加上SK，用SHA-256生成sign key
            String signingKey = SignUtil.hmacSha256(sk, authStringPrefix);

            // 2. 生成规范化uri
            String canonicalUri = getCanonicalUri(path);

            // 3. 生成规范化query string
            String canonicalQuery = getCanonicalQuery(params);

            // 4. 生成规范化headers
            String canonicalHeaders = getCanonicalHeaders(headers);

            ArrayList<String> canonicalRequest = new ArrayList<String>();
            canonicalRequest.add(httpMethod);
            canonicalRequest.add(canonicalUri);
            canonicalRequest.add(canonicalQuery);
            canonicalRequest.add(canonicalHeaders);

            String signature = SignUtil.hmacSha256(signingKey, Util.mkString(canonicalRequest.iterator(), '\n'));

            return String.format("%s/%s/%s", authStringPrefix, "", signature);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getCanonicalUri(String path) {
        if (!path.startsWith("/")) {
            path = String.format("/%s", path);
        }
        return Util.uriEncode(path, false);
    }

    private static String getCanonicalQuery(HashMap<String, String> params) {
        if (params.isEmpty()) {
            return "";
        }

        TreeSet<String> querySet = new TreeSet<String>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!entry.getKey().toLowerCase().equals("authorization")) {
                querySet.add(String.format("%s=%s",
                        Util.uriEncode(entry.getKey(), true),
                        Util.uriEncode(entry.getValue(), true)));
            }
        }

        return Util.mkString(querySet.iterator(), '&');

    }

    /*
     *   对部分header名称及参数进行编码，默认使用：
     *   1. host
     *   2. content-md5
     *   3. content-length
     *   4. content-type
     *   5. 所有以x-bce-开头的header项
     */
    private static String getCanonicalHeaders(HashMap<String, String> headers) {
        if (headers.isEmpty()) {
            return "";
        }
        TreeSet<String> headerSet = new TreeSet<String>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey().trim().toLowerCase();
            if (key.startsWith(AipClientConst.BCE_PREFIX)
                    || AipClientConst.BCE_HEADER_TO_SIGN.contains(key)) {
                headerSet.add(String.format("%s:%s", Util.uriEncode(key, true),
                        Util.uriEncode(entry.getValue().trim(), true)));
            }
        }

        return Util.mkString(headerSet.iterator(), '\n');
    }

}
