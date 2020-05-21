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

package com.baidu.aip.ocr;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.error.AipError;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Calendar;

public class AipOcr extends BaseClient {

    public AipOcr(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * 通用文字识别接口   
     * 用户向服务请求识别某张图中的所有文字
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   language_type 识别语言类型，默认为CHN_ENG。可选值包括：<br>- CHN_ENG：中英文混合；<br>- ENG：英文；<br>- POR：葡萄牙语；<br>- FRE：法语；<br>- GER：德语；<br>- ITA：意大利语；<br>- SPA：西班牙语；<br>- RUS：俄语；<br>- JAP：日语；<br>- KOR：韩语；
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject basicGeneral(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.GENERAL_BASIC);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 通用文字识别接口
     * 用户向服务请求识别某张图中的所有文字
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   language_type 识别语言类型，默认为CHN_ENG。可选值包括：<br>- CHN_ENG：中英文混合；<br>- ENG：英文；<br>- POR：葡萄牙语；<br>- FRE：法语；<br>- GER：德语；<br>- ITA：意大利语；<br>- SPA：西班牙语；<br>- RUS：俄语；<br>- JAP：日语；<br>- KOR：韩语；
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject basicGeneral(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return basicGeneral(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 通用文字识别接口   
     * 用户向服务请求识别某张图中的所有文字
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   language_type 识别语言类型，默认为CHN_ENG。可选值包括：<br>- CHN_ENG：中英文混合；<br>- ENG：英文；<br>- POR：葡萄牙语；<br>- FRE：法语；<br>- GER：德语；<br>- ITA：意大利语；<br>- SPA：西班牙语；<br>- RUS：俄语；<br>- JAP：日语；<br>- KOR：韩语；
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject basicGeneralUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.GENERAL_BASIC);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 通用文字识别（高精度版）接口   
     * 用户向服务请求识别某张图中的所有文字，相对于通用文字识别该产品精度更高，但是识别耗时会稍长。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject basicAccurateGeneral(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.ACCURATE_BASIC);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 通用文字识别（高精度版）接口
     * 用户向服务请求识别某张图中的所有文字，相对于通用文字识别该产品精度更高，但是识别耗时会稍长。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject basicAccurateGeneral(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return basicAccurateGeneral(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 通用文字识别（含位置信息版）接口   
     * 用户向服务请求识别某张图中的所有文字，并返回文字在图中的位置信息。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   language_type 识别语言类型，默认为CHN_ENG。可选值包括：<br>- CHN_ENG：中英文混合；<br>- ENG：英文；<br>- POR：葡萄牙语；<br>- FRE：法语；<br>- GER：德语；<br>- ITA：意大利语；<br>- SPA：西班牙语；<br>- RUS：俄语；<br>- JAP：日语；<br>- KOR：韩语；
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     *   vertexes_location 是否返回文字外接多边形顶点位置，不支持单字位置。默认为false
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject general(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.GENERAL);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 通用文字识别（含位置信息版）接口
     * 用户向服务请求识别某张图中的所有文字，并返回文字在图中的位置信息。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   language_type 识别语言类型，默认为CHN_ENG。可选值包括：<br>- CHN_ENG：中英文混合；<br>- ENG：英文；<br>- POR：葡萄牙语；<br>- FRE：法语；<br>- GER：德语；<br>- ITA：意大利语；<br>- SPA：西班牙语；<br>- RUS：俄语；<br>- JAP：日语；<br>- KOR：韩语；
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     *   vertexes_location 是否返回文字外接多边形顶点位置，不支持单字位置。默认为false
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject general(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return general(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 通用文字识别（含位置信息版）接口   
     * 用户向服务请求识别某张图中的所有文字，并返回文字在图中的位置信息。
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   language_type 识别语言类型，默认为CHN_ENG。可选值包括：<br>- CHN_ENG：中英文混合；<br>- ENG：英文；<br>- POR：葡萄牙语；<br>- FRE：法语；<br>- GER：德语；<br>- ITA：意大利语；<br>- SPA：西班牙语；<br>- RUS：俄语；<br>- JAP：日语；<br>- KOR：韩语；
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     *   vertexes_location 是否返回文字外接多边形顶点位置，不支持单字位置。默认为false
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject generalUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.GENERAL);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 通用文字识别（含位置高精度版）接口   
     * 用户向服务请求识别某张图中的所有文字，并返回文字在图片中的坐标信息，相对于通用文字识别（含位置信息版）该产品精度更高，但是识别耗时会稍长。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   vertexes_location 是否返回文字外接多边形顶点位置，不支持单字位置。默认为false
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject accurateGeneral(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.ACCURATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 通用文字识别（含位置高精度版）接口
     * 用户向服务请求识别某张图中的所有文字，并返回文字在图片中的坐标信息，相对于通用文字识别（含位置信息版）该产品精度更高，但是识别耗时会稍长。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   vertexes_location 是否返回文字外接多边形顶点位置，不支持单字位置。默认为false
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject accurateGeneral(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return accurateGeneral(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 通用文字识别（含生僻字版）接口   
     * 某些场景中，图片中的中文不光有常用字，还包含了生僻字，这时用户需要对该图进行文字识别，应使用通用文字识别（含生僻字版）。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   language_type 识别语言类型，默认为CHN_ENG。可选值包括：<br>- CHN_ENG：中英文混合；<br>- ENG：英文；<br>- POR：葡萄牙语；<br>- FRE：法语；<br>- GER：德语；<br>- ITA：意大利语；<br>- SPA：西班牙语；<br>- RUS：俄语；<br>- JAP：日语；<br>- KOR：韩语；
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject enhancedGeneral(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.GENERAL_ENHANCED);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 通用文字识别（含生僻字版）接口
     * 某些场景中，图片中的中文不光有常用字，还包含了生僻字，这时用户需要对该图进行文字识别，应使用通用文字识别（含生僻字版）。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   language_type 识别语言类型，默认为CHN_ENG。可选值包括：<br>- CHN_ENG：中英文混合；<br>- ENG：英文；<br>- POR：葡萄牙语；<br>- FRE：法语；<br>- GER：德语；<br>- ITA：意大利语；<br>- SPA：西班牙语；<br>- RUS：俄语；<br>- JAP：日语；<br>- KOR：韩语；
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject enhancedGeneral(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return enhancedGeneral(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 通用文字识别（含生僻字版）接口   
     * 某些场景中，图片中的中文不光有常用字，还包含了生僻字，这时用户需要对该图进行文字识别，应使用通用文字识别（含生僻字版）。
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   language_type 识别语言类型，默认为CHN_ENG。可选值包括：<br>- CHN_ENG：中英文混合；<br>- ENG：英文；<br>- POR：葡萄牙语；<br>- FRE：法语；<br>- GER：德语；<br>- ITA：意大利语；<br>- SPA：西班牙语；<br>- RUS：俄语；<br>- JAP：日语；<br>- KOR：韩语；
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     *   probability 是否返回识别结果中每一行的置信度
     * @return JSONObject
     */
    public JSONObject enhancedGeneralUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.GENERAL_ENHANCED);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 网络图片文字识别接口   
     * 用户向服务请求识别一些网络上背景复杂，特殊字体的文字。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     * @return JSONObject
     */
    public JSONObject webImage(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.WEB_IMAGE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 网络图片文字识别接口
     * 用户向服务请求识别一些网络上背景复杂，特殊字体的文字。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     * @return JSONObject
     */
    public JSONObject webImage(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return webImage(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 网络图片文字识别接口   
     * 用户向服务请求识别一些网络上背景复杂，特殊字体的文字。
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_language 是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
     * @return JSONObject
     */
    public JSONObject webImageUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.WEB_IMAGE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 身份证识别接口   
     * 用户向服务请求识别身份证，身份证识别包括正面和背面。
     *
     * @param image - 二进制图像数据
     * @param idCardSide - front：身份证含照片的一面；back：身份证带国徽的一面
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_risk 是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能，默认不开启，即：false。可选值:true-开启；false-不开启
     * @return JSONObject
     */
    public JSONObject idcard(byte[] image, String idCardSide, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        
        request.addBody("id_card_side", idCardSide);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.IDCARD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 身份证识别接口
     * 用户向服务请求识别身份证，身份证识别包括正面和背面。
     *
     * @param image - 本地图片路径
     * @param idCardSide - front：身份证含照片的一面；back：身份证带国徽的一面
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   detect_risk 是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能，默认不开启，即：false。可选值:true-开启；false-不开启
     * @return JSONObject
     */
    public JSONObject idcard(String image, String idCardSide, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return idcard(data, idCardSide, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 银行卡识别接口   
     * 识别银行卡并返回卡号和发卡行。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject bankcard(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.BANKCARD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 银行卡识别接口
     * 识别银行卡并返回卡号和发卡行。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject bankcard(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return bankcard(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 驾驶证识别接口   
     * 对机动车驾驶证所有关键字段进行识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     * @return JSONObject
     */
    public JSONObject drivingLicense(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.DRIVING_LICENSE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 驾驶证识别接口
     * 对机动车驾驶证所有关键字段进行识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     * @return JSONObject
     */
    public JSONObject drivingLicense(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return drivingLicense(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 行驶证识别接口   
     * 对机动车行驶证正本所有关键字段进行识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   accuracy normal 使用快速服务，1200ms左右时延；缺省或其它值使用高精度服务，1600ms左右时延
     * @return JSONObject
     */
    public JSONObject vehicleLicense(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.VEHICLE_LICENSE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 行驶证识别接口
     * 对机动车行驶证正本所有关键字段进行识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   accuracy normal 使用快速服务，1200ms左右时延；缺省或其它值使用高精度服务，1600ms左右时延
     * @return JSONObject
     */
    public JSONObject vehicleLicense(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return vehicleLicense(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 车牌识别接口   
     * 识别机动车车牌，并返回签发地和号牌。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   multi_detect 是否检测多张车牌，默认为false，当置为true的时候可以对一张图片内的多张车牌进行识别
     * @return JSONObject
     */
    public JSONObject plateLicense(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.LICENSE_PLATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 车牌识别接口
     * 识别机动车车牌，并返回签发地和号牌。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   multi_detect 是否检测多张车牌，默认为false，当置为true的时候可以对一张图片内的多张车牌进行识别
     * @return JSONObject
     */
    public JSONObject plateLicense(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return plateLicense(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 营业执照识别接口   
     * 识别营业执照，并返回关键字段的值，包括单位名称、法人、地址、有效期、证件编号、社会信用代码等。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject businessLicense(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.BUSINESS_LICENSE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 营业执照识别接口
     * 识别营业执照，并返回关键字段的值，包括单位名称、法人、地址、有效期、证件编号、社会信用代码等。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject businessLicense(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return businessLicense(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 通用票据识别接口   
     * 用户向服务请求识别医疗票据、发票、的士票、保险保单等票据类图片中的所有文字，并返回文字在图中的位置信息。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   probability 是否返回识别结果中每一行的置信度
     *   accuracy normal 使用快速服务，1200ms左右时延；缺省或其它值使用高精度服务，1600ms左右时延
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     * @return JSONObject
     */
    public JSONObject receipt(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.RECEIPT);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 通用票据识别接口
     * 用户向服务请求识别医疗票据、发票、的士票、保险保单等票据类图片中的所有文字，并返回文字在图中的位置信息。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   probability 是否返回识别结果中每一行的置信度
     *   accuracy normal 使用快速服务，1200ms左右时延；缺省或其它值使用高精度服务，1600ms左右时延
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     * @return JSONObject
     */
    public JSONObject receipt(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return receipt(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 火车票识别接口   
     * 支持对大陆火车票的车票号、始发站、目的站、车次、日期、票价、席别、姓名进行结构化识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject trainTicket(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.TRAIN_TICKET);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 火车票识别接口
     * 支持对大陆火车票的车票号、始发站、目的站、车次、日期、票价、席别、姓名进行结构化识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject trainTicket(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return trainTicket(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 出租车票识别接口   
     * 针对出租车票（现支持北京）的发票号码、发票代码、车号、日期、时间、金额进行结构化识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject taxiReceipt(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.TAXI_RECEIPT);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 出租车票识别接口
     * 针对出租车票（现支持北京）的发票号码、发票代码、车号、日期、时间、金额进行结构化识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject taxiReceipt(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return taxiReceipt(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 表格文字识别同步接口接口   
     * 自动识别表格线及表格内容，结构化输出表头、表尾及每个单元格的文字内容。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject form(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.FORM);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 表格文字识别同步接口接口
     * 自动识别表格线及表格内容，结构化输出表头、表尾及每个单元格的文字内容。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject form(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return form(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 表格文字识别接口   
     * 自动识别表格线及表格内容，结构化输出表头、表尾及每个单元格的文字内容。表格文字识别接口为异步接口，分为两个API：提交请求接口、获取结果接口。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject tableRecognitionAsync(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.TABLE_RECOGNIZE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 表格文字识别接口
     * 自动识别表格线及表格内容，结构化输出表头、表尾及每个单元格的文字内容。表格文字识别接口为异步接口，分为两个API：提交请求接口、获取结果接口。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject tableRecognitionAsync(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return tableRecognitionAsync(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 表格识别结果接口   
     * 获取表格文字识别结果
     *
     * @param requestId - 发送表格文字识别请求时返回的request id
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   result_type 期望获取结果的类型，取值为“excel”时返回xls文件的地址，取值为“json”时返回json格式的字符串,默认为”excel”
     * @return JSONObject
     */
    public JSONObject tableResultGet(String requestId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("request_id", requestId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.TABLE_RESULT_GET);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * VIN码识别接口   
     * 对车辆车架上、挡风玻璃上的VIN码进行识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject vinCode(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.VIN_CODE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * VIN码识别接口
     * 对车辆车架上、挡风玻璃上的VIN码进行识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject vinCode(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return vinCode(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 定额发票识别接口   
     * 对各类定额发票的代码、号码、金额进行识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject quotaInvoice(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.QUOTA_INVOICE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 定额发票识别接口
     * 对各类定额发票的代码、号码、金额进行识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject quotaInvoice(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return quotaInvoice(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 户口本识别接口   
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对出生地、出生日期、姓名、民族、与户主关系、性别、身份证号码字段进行识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject householdRegister(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.HOUSEHOLD_REGISTER);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 户口本识别接口
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对出生地、出生日期、姓名、民族、与户主关系、性别、身份证号码字段进行识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject householdRegister(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return householdRegister(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 港澳通行证识别接口   
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对港澳通行证证号、姓名、姓名拼音、性别、有效期限、签发地点、出生日期字段进行识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject HKMacauExitentrypermit(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.HK_MACAU_EXITENTRYPERMIT);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 港澳通行证识别接口
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对港澳通行证证号、姓名、姓名拼音、性别、有效期限、签发地点、出生日期字段进行识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject HKMacauExitentrypermit(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return HKMacauExitentrypermit(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 台湾通行证识别接口   
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对台湾通行证证号、签发地、出生日期、姓名、姓名拼音、性别、有效期字段进行识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject taiwanExitentrypermit(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.TAIWAN_EXITENTRYPERMIT);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 台湾通行证识别接口
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对台湾通行证证号、签发地、出生日期、姓名、姓名拼音、性别、有效期字段进行识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject taiwanExitentrypermit(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return taiwanExitentrypermit(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 出生医学证明识别接口   
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对台湾通行证证号、签发地、出生日期、姓名、姓名拼音、性别、有效期字段进行识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject birthCertificate(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.BIRTH_CERTIFICATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 出生医学证明识别接口
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对台湾通行证证号、签发地、出生日期、姓名、姓名拼音、性别、有效期字段进行识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject birthCertificate(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return birthCertificate(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 机动车销售发票识别接口   
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】识别机动车销售发票号码、代码、日期、价税合计等14个字段
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject vehicleInvoice(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.VEHICLE_INVOICE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 机动车销售发票识别接口
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】识别机动车销售发票号码、代码、日期、价税合计等14个字段
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject vehicleInvoice(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return vehicleInvoice(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 车辆合格证识别接口   
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】识别车辆合格证编号、车架号、排放标准、发动机编号等12个字段
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject vehicleCertificate(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.VEHICLE_CERTIFICATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 车辆合格证识别接口
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】识别车辆合格证编号、车架号、排放标准、发动机编号等12个字段
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject vehicleCertificate(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return vehicleCertificate(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 税务局通用机打发票识别接口   
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对国家/地方税务局发行的横/竖版通用机打发票的号码、代码、日期、合计金额、类型、商品名称字段进行结构化识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   location 是否输出位置信息，true：输出位置信息，false：不输出位置信息，默认false
     * @return JSONObject
     */
    public JSONObject invoice(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.INVOICE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 税务局通用机打发票识别接口
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对国家/地方税务局发行的横/竖版通用机打发票的号码、代码、日期、合计金额、类型、商品名称字段进行结构化识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   location 是否输出位置信息，true：输出位置信息，false：不输出位置信息，默认false
     * @return JSONObject
     */
    public JSONObject invoice(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return invoice(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 行程单识别接口   
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对飞机行程单中的姓名、始发站、目的站、航班号、日期、票价字段进行结构化识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   location 是否输出位置信息，true：输出位置信息，false：不输出位置信息，默认false
     * @return JSONObject
     */
    public JSONObject airTicket(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.AIR_TICKET);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 行程单识别接口
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对飞机行程单中的姓名、始发站、目的站、航班号、日期、票价字段进行结构化识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   location 是否输出位置信息，true：输出位置信息，false：不输出位置信息，默认false
     * @return JSONObject
     */
    public JSONObject airTicket(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return airTicket(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 保单识别接口   
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对各类保单中投保人、受益人的各项信息、保费、保险名称等字段进行结构化识别
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   rkv_business 是否进行商业逻辑处理，rue：进行商业逻辑处理，false：不进行商业逻辑处理，默认true
     * @return JSONObject
     */
    public JSONObject insuranceDocuments(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.INSURANCE_DOCUMENTS);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 保单识别接口
     * 【此接口需要您在[申请页面](https://cloud.baidu.com/survey/AICooperativeConsultingApply.html)中提交合作咨询开通权限】对各类保单中投保人、受益人的各项信息、保费、保险名称等字段进行结构化识别
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   rkv_business 是否进行商业逻辑处理，rue：进行商业逻辑处理，false：不进行商业逻辑处理，默认true
     * @return JSONObject
     */
    public JSONObject insuranceDocuments(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return insuranceDocuments(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 增值税发票识别接口   
     * 此接口需要您在页面中提交合作咨询开通权限】 识别并结构化返回增值税发票的各个字段及其对应值，包含了发票基础信息9项，货物相关信息12项，购买方/销售方的名称、识别号、地址电话、开户行及账号，共29项结构化字段。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject vatInvoice(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.VAT_INVOICE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 增值税发票识别接口
     * 此接口需要您在页面中提交合作咨询开通权限】 识别并结构化返回增值税发票的各个字段及其对应值，包含了发票基础信息9项，货物相关信息12项，购买方/销售方的名称、识别号、地址电话、开户行及账号，共29项结构化字段。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject vatInvoice(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return vatInvoice(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 二维码识别接口   
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限识别条形码、二维码中包含的URL或其他信息内容
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject qrcode(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.QRCODE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 二维码识别接口
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限识别条形码、二维码中包含的URL或其他信息内容
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject qrcode(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return qrcode(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 数字识别接口   
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】对图像中的阿拉伯数字进行识别提取，适用于快递单号、手机号、充值码提取等场景
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     * @return JSONObject
     */
    public JSONObject numbers(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.NUMBERS);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 数字识别接口
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】对图像中的阿拉伯数字进行识别提取，适用于快递单号、手机号、充值码提取等场景
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     * @return JSONObject
     */
    public JSONObject numbers(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return numbers(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 彩票识别接口   
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】对大乐透、双色球彩票进行识别，并返回识别结果
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     * @return JSONObject
     */
    public JSONObject lottery(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.LOTTERY);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 彩票识别接口
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】对大乐透、双色球彩票进行识别，并返回识别结果
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     * @return JSONObject
     */
    public JSONObject lottery(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return lottery(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 护照识别接口   
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】支持对中国大陆居民护照的资料页进行结构化识别，包含国家码、姓名、性别、护照号、出生日期、签发日期、有效期至、签发地点。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject passport(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.PASSPORT);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 护照识别接口
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】支持对中国大陆居民护照的资料页进行结构化识别，包含国家码、姓名、性别、护照号、出生日期、签发日期、有效期至、签发地点。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject passport(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return passport(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 名片识别接口   
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】提供对各类名片的结构化识别功能，提取姓名、邮编、邮箱、电话、网址、地址、手机号字段
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject businessCard(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.BUSINESS_CARD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 名片识别接口
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】提供对各类名片的结构化识别功能，提取姓名、邮编、邮箱、电话、网址、地址、手机号字段
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject businessCard(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return businessCard(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 手写文字识别接口   
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】提供对各类名片的结构化识别功能，提取姓名、邮编、邮箱、电话、网址、地址、手机号字段
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     * @return JSONObject
     */
    public JSONObject handwriting(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.HANDWRITING);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 手写文字识别接口
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】提供对各类名片的结构化识别功能，提取姓名、邮编、邮箱、电话、网址、地址、手机号字段
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     * @return JSONObject
     */
    public JSONObject handwriting(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return handwriting(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }


    /**
     * 支持对试卷中的数学公式及题目内容进行识别，可提取公式部分进行单独识别，也可对题目和公式进行混合识别，并返回Latex格式公式内容及位置信息，便于进行后续处理
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】提供对各类名片的结构化识别功能，提取姓名、邮编、邮箱、电话、网址、地址、手机号字段
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   disp_formula 是否分离输出公式识别结果，在words_result外单独输出公式结果，展示在“formula_result”中
     * @return JSONObject
     */
    public JSONObject formula(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);

        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.FORMULA);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 支持对试卷中的数学公式及题目内容进行识别，可提取公式部分进行单独识别，也可对题目和公式进行混合识别，并返回Latex格式公式内容及位置信息，便于进行后续处理
     * 【此接口需要您在[页面](http://ai.baidu.com/tech/ocr)中提交合作咨询开通权限】提供对各类名片的结构化识别功能，提取姓名、邮编、邮箱、电话、网址、地址、手机号字段
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   recognize_granularity 是否定位单字符位置，big：不定位单字符位置，默认值；small：定位单字符位置
     *   detect_direction 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     *   disp_formula 是否分离输出公式识别结果，在words_result外单独输出公式结果，展示在“formula_result”中
     * @return JSONObject
     */
    public JSONObject formula(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return formula(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 自定义模板文字识别接口   
     * 自定义模板文字识别，是针对百度官方没有推出相应的模板，但是当用户需要对某一类卡证/票据（如房产证、军官证、火车票等）进行结构化的提取内容时，可以使用该产品快速制作模板，进行识别。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   templateSign 您在自定义文字识别平台制作的模板的ID
     *   classifierId 分类器Id。这个参数和templateSign至少存在一个，优先使用templateSign。存在templateSign时，表示使用指定模板；如果没有templateSign而有classifierId，表示使用分类器去判断使用哪个模板
     * @return JSONObject
     */
    public JSONObject custom(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.CUSTOM);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 自定义模板文字识别接口
     * 自定义模板文字识别，是针对百度官方没有推出相应的模板，但是当用户需要对某一类卡证/票据（如房产证、军官证、火车票等）进行结构化的提取内容时，可以使用该产品快速制作模板，进行识别。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   templateSign 您在自定义文字识别平台制作的模板的ID
     *   classifierId 分类器Id。这个参数和templateSign至少存在一个，优先使用templateSign。存在templateSign时，表示使用指定模板；如果没有templateSign而有classifierId，表示使用分类器去判断使用哪个模板
     * @return JSONObject
     */
    public JSONObject custom(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return custom(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 获取表格识别结果（异步）.
     * @param requestId 由tableRecognition接口返回的requestId
     * @return 识别状态和结果
     */
    public JSONObject getTableRecognitionJsonResult(String requestId) {
        return getTableResultHelper(requestId, "json");
    }

    /**
     * 获取表格识别结果（异步）.
     * @param requestId 由tableRecognition接口返回的requestId
     * @return 识别状态和excel下载地址
     */
    public JSONObject getTableRecognitionExcelResult(String requestId) {
        return getTableResultHelper(requestId, "excel");
    }

    // 表格识别获取结果接口公共逻辑
    private JSONObject getTableResultHelper(String requestId, String resultType) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("request_id", requestId);
        request.addBody("result_type", resultType);
        request.setUri(OcrConsts.TABLE_RESULT_GET);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 表格识别接口，返回json结果
     * @param imgPath 识别图片路径
     * @param timeoutMiliseconds 等待超时(ms)
     * @return json格式识别结果
     */
    public JSONObject tableRecognizeToJson(String imgPath, long timeoutMiliseconds) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return tableRecognizeToJson(imgData, timeoutMiliseconds);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 表格识别接口，返回json结果
     * @param imgData 识别图片二进制数据
     * @param timeoutMiliseconds 等待超时(ms)
     * @return json格式识别结果
     */
    public JSONObject tableRecognizeToJson(byte[] imgData, long timeoutMiliseconds) {
        return tableRecSyncHelper(imgData, timeoutMiliseconds, "json");
    }

    /**
     * 表格识别接口，返回生成excel的url地址
     * @param imgPath 识别图片路径
     * @param timeoutMiliseconds 等待超时(ms)
     * @return json result
     */
    public JSONObject tableRecognizeToExcelUrl(String imgPath, long timeoutMiliseconds) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return tableRecognizeToExcelUrl(imgData, timeoutMiliseconds);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 表格识别接口，返回生成excel的url地址
     * @param imgData 识别图片二进制数据
     * @param timeoutMiliseconds 等待超时(ms)
     * @return json result
     */
    public JSONObject tableRecognizeToExcelUrl(byte[] imgData, long timeoutMiliseconds) {
        return tableRecSyncHelper(imgData, timeoutMiliseconds, "excel");
    }

    // 表格识别接口包装同步接口，负责发起识别请求并等待结果生成。
    private JSONObject tableRecSyncHelper(byte[] imgData, long timeout, String resultType) {
        JSONObject res = tableRecognitionAsync(imgData, null);
        if (res.has("error_code")) {
            return res;
        }
        String reqId = res.getJSONArray("result").getJSONObject(0).getString("request_id");
        long start = Calendar.getInstance().getTimeInMillis();
        long sleepInterval = 2000;
        JSONObject result;
        while (true) {
            long now = Calendar.getInstance().getTimeInMillis();
            if (now - start > timeout) {
                // 超时
                return AipError.ASYNC_TIMEOUT_ERROR.toJsonResult();
            }
            result = getTableResultHelper(reqId, resultType);
            if (result.has("error_code")) {
                // 返回错误
                return result;
            }
            int retCode = result.getJSONObject("result").getInt("ret_code");
            if (retCode == OcrConsts.ASYNC_TASK_STATUS_FINISHED) {
                return result;
            }
            else {
                try {
                    Thread.sleep(sleepInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}