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

import com.baidu.aip.http.AipHttpClient;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.AipResponse;
import com.baidu.aip.util.AipClientConfiguration;
import com.baidu.aip.util.AipClientConst;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class DevAuth {

    /**
     * get access_token from openapi
     * @param apiKey API key from console
     * @param secretKey Secret Key from console
     * @param config network config settings
     * @return JsonObject of response from OAuth server
     */
    public static JSONObject oauth(String apiKey, String secretKey, AipClientConfiguration config) {
        try {
            AipRequest request = new AipRequest();
            request.setUri(new URI(AipClientConst.OAUTH_URL));
            request.addBody("grant_type", "client_credentials");
            request.addBody("client_id", apiKey);
            request.addBody("client_secret", secretKey);
            request.setConfig(config);
            AipResponse response = AipHttpClient.post(request);
            String res = response.getBodyStr();
            int statusCode = response.getStatus();
            if (res != null && !res.equals("")) {
                return new JSONObject(res);
            }
            else {
                return Util.getGeneralError(statusCode, "Server response code: " + statusCode);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
