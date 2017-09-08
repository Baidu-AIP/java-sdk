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
package com.baidu.aip.nlp;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.http.*;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AipNlp extends BaseClient {



    public AipNlp(String appId, String aipKey, String aipToken) {
        super(appId, aipKey, aipToken);
    }

    // wordseg interfaces
    // @Deprecated
    public JSONObject wordseg(String query) {
        return wordseg(query, NlpLangId.LANG_CHINESE);
    }

    // @Deprecated
    public JSONObject wordseg(String query, int langId) {
        AipRequest request = new AipRequest();
        preOperation(request);
        // request.setHttpMethod(HttpMethodName.POST);
        // 添加内容到request
        request.addBody("query", query);
        request.addBody("lang_id", langId);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setUri(NlpConsts.NLP_WORDSEG_URL);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);

    }

    // 词性标注 wordpos interfaces
    // @Deprecated
    public JSONObject wordpos(String query) {
        AipRequest request = new AipRequest();
        preOperation(request);
        // 添加内容到request
        request.addBody("query", query);
        request.setUri(NlpConsts.NLP_WORDPOS_URL);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);
    }

    // 词相似度接口
    public JSONObject wordSimEmbedding(String word1, String word2) {
        return wordSimEmbedding(word1, word2, null);
    }

    public JSONObject wordSimEmbedding(String word1, String word2, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("word_1", word1);
        request.addBody("word_2", word2);
        if (options != null) {
            for (Map.Entry<String, Object> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(NlpConsts.NLP_WORD_SIM_EMBEDDING_URL);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);
    }

    // 返回词向量
    public JSONObject wordEmbedding(String word) {
        return wordEmbedding(word, null);
    }

    public JSONObject wordEmbedding(String word, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("word", word);
        if (options != null) {
            for (Map.Entry<String, Object> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(NlpConsts.NLP_WORDEMBEDDING_URL);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);
    }

    // 中文dnn语言模型
    public JSONObject dnnlmCn(String text) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("text", text);
        request.setUri(NlpConsts.NLP_DNNLM_URL);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);
    }

    // 短文本相似度
    public JSONObject simnet(String text1, String text2, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);

        request.addBody("text_1", text1);
        request.addBody("text_2", text2);

        if (options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(NlpConsts.NLP_SIMNET_URL);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);
    }

    // 情感观点挖掘
    public JSONObject commentTag(String text, ESimnetType type) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("text", text);
        request.addBody("type", type.ordinal());
        request.setUri(NlpConsts.NLP_COMMENT_TAG_URL);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);
    }

    // 词法分析接口
    public JSONObject lexer(String text) {
        AipRequest request = new AipRequest();

        preOperation(request);
        request.addBody("text", text);
        request.setUri(NlpConsts.NLP_LEXER_URL);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);
    }

    // 情感分析接口
    public JSONObject sentimentClassify(String text) {
        AipRequest request = new AipRequest();

        preOperation(request);
        request.addBody("text", text);
        request.setUri(NlpConsts.NLP_SENTIMENT_URL);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);
    }

    public JSONObject depParser(String text, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();

        preOperation(request);
        request.addBody("text", text);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.NLP_DEPPARSER_URL);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);

        return requestServer(request);
    }

}
