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
package com.baidu.aip.http;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class AipHttpClient {

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * post方式请求服务器(https协议)
     *
     * @param request 请求内容
     * @return AipResponse
     */
    public static AipResponse post(AipRequest request) {
        String url;
        String charset = request.getContentEncoding();
        String content = request.getBodyStr();
        HashMap<String, String> header = request.getHeaders();
        AipResponse response = new AipResponse();
        try {
            if (request.getParams().isEmpty()) {
                url = request.getUri().toString();
            }
            else {
                url = String.format("%s?%s", request.getUri().toString(), request.getParamStr());
            }

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                    new java.security.SecureRandom());

            URL console = new URL(url);
            Proxy proxy = request.getConfig() == null ? Proxy.NO_PROXY : request.getConfig().getProxy();
            HttpURLConnection conn = (HttpURLConnection) console.openConnection(proxy);
            if (url.contains("https")) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                httpsConn.setSSLSocketFactory(sc.getSocketFactory());
                httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            }

            // set timeout
            if (request.getConfig() != null) {
                conn.setConnectTimeout(request.getConfig().getConnectionTimeoutMillis());
                conn.setReadTimeout(request.getConfig().getSocketTimeoutMillis());
            }
            conn.setDoOutput(true);
            // 添加header
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(content.getBytes(charset));
            out.flush();
            out.close();
            int statusCode = conn.getResponseCode();
            response.setHeader(conn.getHeaderFields());
            response.setStatus(statusCode);
            response.setCharset(charset);
            if (statusCode != 200) {
                return response;
            }

            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                response.setBody(outStream.toByteArray());
            }
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return response;
    }

}