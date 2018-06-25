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
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;
import com.baidu.aip.http.Headers;
import com.baidu.aip.http.HttpCharacterEncoding;
import com.baidu.aip.http.HttpContentType;
import org.json.JSONObject;

import java.util.HashMap;

public class AipNlp extends BaseClient {

    public AipNlp(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * 词法分析接口
     * 词法分析接口向用户提供分词、词性标注、专名识别三大功能；能够识别出文本串中的基本词汇（分词），对这些词汇进行重组、标注组合后词汇的词性，并进一步识别出命名实体。
     *
     * @param text - 待分析文本（目前仅支持GBK编码），长度不超过65536字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject lexer(String text, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("text", text);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.LEXER);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 词法分析（定制版）接口
     * 词法分析接口向用户提供分词、词性标注、专名识别三大功能；能够识别出文本串中的基本词汇（分词），对这些词汇进行重组、标注组合后词汇的词性，并进一步识别出命名实体。
     *
     * @param text - 待分析文本（目前仅支持GBK编码），长度不超过65536字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject lexerCustom(String text, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("text", text);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.LEXER_CUSTOM);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 依存句法分析接口
     * 依存句法分析接口可自动分析文本中的依存句法结构信息，利用句子中词与词之间的依存关系来表示词语的句法结构信息（如“主谓”、“动宾”、“定中”等结构关系），并用树状结构来表示整句的结构（如“主谓宾”、“定状补”等）。
     *
     * @param text - 待分析文本（目前仅支持GBK编码），长度不超过256字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   mode 模型选择。默认值为0，可选值mode=0（对应web模型）；mode=1（对应query模型）
     * @return JSONObject
     */
    public JSONObject depParser(String text, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("text", text);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.DEP_PARSER);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 词向量表示接口
     * 词向量表示接口提供中文词向量的查询功能。
     *
     * @param word - 文本内容（GBK编码），最大64字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject wordEmbedding(String word, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("word", word);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.WORD_EMBEDDING);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * DNN语言模型接口
     * 中文DNN语言模型接口用于输出切词结果并给出每个词在句子中的概率值,判断一句话是否符合语言表达习惯。
     *
     * @param text - 文本内容（GBK编码），最大512字节，不需要切词
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject dnnlmCn(String text, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("text", text);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.DNNLM_CN);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 词义相似度接口
     * 输入两个词，得到两个词的相似度结果。
     *
     * @param word1 - 词1（GBK编码），最大64字节*
     * @param word2 - 词1（GBK编码），最大64字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   mode 预留字段，可选择不同的词义相似度模型。默认值为0，目前仅支持mode=0
     * @return JSONObject
     */
    public JSONObject wordSimEmbedding(String word1, String word2, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("word_1", word1);
        
        request.addBody("word_2", word2);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.WORD_SIM_EMBEDDING);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 短文本相似度接口
     * 短文本相似度接口用来判断两个文本的相似度得分。
     *
     * @param text1 - 待比较文本1（GBK编码），最大512字节*
     * @param text2 - 待比较文本2（GBK编码），最大512字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   model 默认为"BOW"，可选"BOW"、"CNN"与"GRNN"
     * @return JSONObject
     */
    public JSONObject simnet(String text1, String text2, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("text_1", text1);
        
        request.addBody("text_2", text2);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.SIMNET);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 评论观点抽取接口
     * 评论观点抽取接口用来提取一条评论句子的关注点和评论观点，并输出评论观点标签及评论观点极性。
     *
     * @param text - 评论内容（GBK编码），最大10240字节
     * @param type - ESimnetType枚举类型，选择识别的垂类
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   type 评论行业类型，默认为4（餐饮美食）
     * @return JSONObject
     */
    public JSONObject commentTag(String text, ESimnetType type, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("text", text);
        request.addBody("type", type.ordinal());
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.COMMENT_TAG);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 情感倾向分析接口
     * 对包含主观观点信息的文本进行情感极性类别（积极、消极、中性）的判断，并给出相应的置信度。
     *
     * @param text - 文本内容（GBK编码），最大102400字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject sentimentClassify(String text, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("text", text);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.SENTIMENT_CLASSIFY);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 文章标签接口
     * 文章标签服务能够针对网络各类媒体文章进行快速的内容理解，根据输入含有标题的文章，输出多个内容标签以及对应的置信度，用于个性化推荐、相似文章聚合、文本内容分析等场景。
     *
     * @param title - 篇章的标题，最大80字节*
     * @param content - 篇章的正文，最大65535字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject keyword(String title, String content, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("title", title);
        
        request.addBody("content", content);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.KEYWORD);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 文章分类接口
     * 对文章按照内容类型进行自动分类，首批支持娱乐、体育、科技等26个主流内容类型，为文章聚类、文本内容分析等应用提供基础技术支持。
     *
     * @param title - 篇章的标题，最大80字节*
     * @param content - 篇章的正文，最大65535字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject topic(String title, String content, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("title", title);
        
        request.addBody("content", content);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.TOPIC);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 文本纠错接口
     * 识别输入文本中有错误的片段，提示错误并给出正确的文本结果。支持短文本、长文本、语音等内容的错误识别，纠错是搜索引擎、语音识别、内容审查等功能更好运行的基础模块之一。
     *
     * @param text - 待纠错文本，输入限制511字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject ecnet(String text, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("text", text);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.ECNET);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 对话情绪识别接口接口
     * 针对用户日常沟通文本背后所蕴含情绪的一种直观检测，可自动识别出当前会话者所表现出的情绪类别及其置信度，可以帮助企业更全面地把握产品服务质量、监控客户服务质量
     *
     * @param text - 待识别情感文本，输入限制512字节
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   scene default（默认项-不区分场景），talk（闲聊对话-如度秘聊天等），task（任务型对话-如导航对话等），customer_service（客服对话-如电信/银行客服等）
     * @return JSONObject
     */
    public JSONObject emotion(String text, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("text", text);
        
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(NlpConsts.EMOTION);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_GBK);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

}