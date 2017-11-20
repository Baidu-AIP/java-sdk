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

import com.baidu.aip.util.AipClientConfiguration;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AipRequest {
    private HashMap<String, String> headers;
    private HashMap<String, String> params;
    private HashMap<String, Object> body;
    private URI uri;
    private HttpMethodName httpMethod;
    private EBodyFormat bodyFormat;
    private String contentEncoding;
    private AipClientConfiguration config;


    public AipRequest() {
        headers = new HashMap<String, String>();
        params = new HashMap<String, String>();
        body = new HashMap<String, Object>();
        httpMethod = HttpMethodName.POST;
        bodyFormat = EBodyFormat.FORM_KV;
        contentEncoding = HttpCharacterEncoding.DEFAULT_ENCODING;
        config = null;
    }

    public AipRequest(HashMap<String, String> header, HashMap<String, String> bodyParams) {
        headers = header;
        params = bodyParams;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public EBodyFormat getBodyFormat() {
        return bodyFormat;
    }

    public void setBodyFormat(EBodyFormat bodyFormat) {
        this.bodyFormat = bodyFormat;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
        if (key.equals(Headers.CONTENT_ENCODING)) {
            this.contentEncoding = value;
        }
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void addBody(String key, Object value) {
        body.put(key, value);
    }

    public void addBody(HashMap other) {
        if (other != null) {
            body.putAll(other);
        }
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    /**
     * get body content depending on bodyFormat
     * @return body content as String
     */
    public String getBodyStr() {
        ArrayList<String> arr = new ArrayList<String>();
        if (bodyFormat.equals(EBodyFormat.FORM_KV)) {
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                if (entry.getValue() == null || entry.getValue().equals("")) {
                    arr.add(Util.uriEncode(entry.getKey(), true));
                } else {
                    arr.add(String.format("%s=%s", Util.uriEncode(entry.getKey(), true),
                            Util.uriEncode(entry.getValue().toString(), true)));
                }
            }
            return Util.mkString(arr.iterator(), '&');
        }
        else if (bodyFormat.equals(EBodyFormat.RAW_JSON)) {
            JSONObject json = new JSONObject();
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
            return json.toString();
        }
        return "";
    }

    public String getParamStr() {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            buffer.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }

    public HashMap<String, Object> getBody() {
        return body;
    }

    public void setBody(HashMap<String, Object> body) {
        this.body = body;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setUri(String url) {
        try {
            this.uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public HttpMethodName getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethodName httpMethod) {
        this.httpMethod = httpMethod;
    }

    public AipClientConfiguration getConfig() {
        return config;
    }

    public void setConfig(AipClientConfiguration config) {
        this.config = config;
    }
}
