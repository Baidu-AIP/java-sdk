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
package com.baidu.aip.speech;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.error.AipError;
import com.baidu.aip.http.*;
import com.baidu.aip.util.AipClientConst;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.SignUtil;
import com.baidu.aip.util.Util;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AipSpeech extends BaseClient {

    public AipSpeech(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    public JSONObject asr(String path, String format, int rate, HashMap<String, Object> options) {
        try {
            byte[] imgData = Util.readFileByBytes(path);
            return asr(imgData, format, rate, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    public JSONObject asr(byte[] data, String format, int rate, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();

        preOperation(request);  // get access token
        if (this.isBceKey.get()) {
            // get access token failed!
            return Util.getGeneralError(
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE,
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_MSG);
        }
        // state.setState(EAuthState.STATE_TRUE_AIP_USER);
        String base64Content = Base64Util.encode(data);
        request.addBody("speech", base64Content);
        request.addBody("format", format);
        request.addBody("rate", rate);
        request.addBody("channel", 1);
        String cuid = SignUtil.md5(accessToken, "UTF-8");
        request.addBody("cuid", cuid);
        request.addBody("token", accessToken);
        request.addBody("len", data.length);
        if (options != null) {
            request.addBody(options);
        }
        // no post operation

        request.setUri(SpeechConsts.SPEECH_ASR_URL);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);

        return requestServer(request);
    }

    public JSONObject asr(String url, String callback, String format, int rate, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();

        preOperation(request);  // get access token
        if (this.isBceKey.get()) {
            // get access token failed!
            return Util.getGeneralError(
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE,
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_MSG);
        }
        request.addBody("url", url);
        request.addBody("format", format);
        request.addBody("rate", rate);
        request.addBody("channel", 1);
        String cuid = SignUtil.md5(accessToken, "UTF-8");
        request.addBody("cuid", cuid);
        request.addBody("token", accessToken);
        request.addBody("callback", callback);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(SpeechConsts.SPEECH_ASR_URL);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);

        return requestServer(request);
    }

    public TtsResponse synthesis(String text, String lang, int ctp, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        if (this.isBceKey.get()) {
            // get access token failed!
            TtsResponse response = new TtsResponse();
            JSONObject msg = Util.getGeneralError(AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE,
                    AipClientConst.OPENAPI_NO_ACCESS_ERROR_MSG);
            response.setResult(msg);
            return response;
        }
        request.addBody("tex", text);
        request.addBody("lan", lang);
        request.addBody("tok", accessToken);
        request.addBody("ctp", ctp);
        String cuid = SignUtil.md5(accessToken, "UTF-8");
        request.addBody("cuid", cuid);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(SpeechConsts.SPEECH_TTS_URL);

        TtsResponse response = new TtsResponse();
        AipResponse res = AipHttpClient.post(request);
        if (res == null) {
            response.setResult(Util.getGeneralError(-1,
                    "null response from server"));
            return response;
        }
        Map<String, List<String>> header = res.getHeader();
        if (header.containsKey("content-type")) {
            String contentType = res.getHeader().get("content-type").get(0);
            if (contentType.contains("json")) {
                String data = res.getBodyStr();
                JSONObject json = new JSONObject(data);
                response.setResult(json);
            }
            else {
                byte[] binData = res.getBody();
                response.setData(binData);
            }
        }
        else {
            LOGGER.error("synthesis get no content-type in header: " + header);
            LOGGER.info("synthesis response status: " + res.getStatus());
            try {
                JSONObject json = new JSONObject(res.getBodyStr());
                response.setResult(json);
            } catch (JSONException e) {
                response.setData(res.getBody());
            }
        }
        return response;
    }
}
