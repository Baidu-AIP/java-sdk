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
import com.baidu.aip.util.ImageUtil;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AipOcr extends BaseClient {

    public AipOcr(String appId, String aipKey, String aipToken) {
        super(appId, aipKey, aipToken);
    }


    /**
     *
     * @param imgPath 本地文件路径
     * @param isFront 图像是否为身份证正面
     * @param options 可选参数
     * @return 服务器检测结果
     */
    public JSONObject idcard(String imgPath, Boolean isFront, HashMap<String, String> options) {

        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return idcard(imgData, isFront, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     *
     * @param imgData 本地图像二进制数据
     * @param isFront 是否为正面
     * @param options 可选参数
     * @return json result
     */
    public JSONObject idcard(byte[] imgData, Boolean isFront, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        // check param
        JSONObject checkRet = checkParam(imgData);
        if (!"0".equals(checkRet.getString("error_code"))) {
            return checkRet;
        }

        preOperation(request);

        String base64Content = Base64Util.encode(imgData);
        // check size
        if (base64Content.length() > OcrConsts.OCR_MAX_IMAGE_SIZE) {
            return AipError.IMAGE_SIZE_ERROR.toJsonResult();
        }

        request.addBody("image", base64Content);
        request.addBody("id_card_side", isFront ? "front" : "back");
        for (Map.Entry<String, String> entry : options.entrySet()) {
            request.addBody(entry.getKey(), entry.getValue());
        }

        request.setUri(OcrConsts.OCR_IDCARD_URL);
        postOperation(request);

        return requestServer(request);
    }

    // 银行卡识别接口
    public JSONObject bankcard(String imgPath) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return bankcard(imgData);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    public JSONObject bankcard(byte[] imgData) {
        AipRequest request = new AipRequest();
        // check param
        JSONObject checkRet = checkParam(imgData);
        if (!"0".equals(checkRet.getString("error_code"))) {
            return checkRet;
        }
        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        // check size
        if (base64Content.length() > OcrConsts.OCR_MAX_IMAGE_SIZE) {
            return AipError.IMAGE_SIZE_ERROR.toJsonResult();
        }
        request.addBody("image", base64Content);
        request.setUri(OcrConsts.OCR_BANDCARD_URL);
        postOperation(request);

        return requestServer(request);
    }

    // 通用识别接口

    /**
     * 通用文字识别（带位置信息版）
     * @param imgPath 图片路径
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                recognize_granularity： big/small
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject general(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return general(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 通用文字识别（带位置信息版）
     * @param imgData 图片原始文件数据
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                recognize_granularity： big/small
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject general(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        // check param
        JSONObject checkRet = checkParam(imgData);
        if (!"0".equals(checkRet.getString("error_code"))) {
            return checkRet;
        }
        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        // check size
        if (base64Content.length() > OcrConsts.OCR_MAX_IMAGE_SIZE) {
            return AipError.IMAGE_SIZE_ERROR.toJsonResult();
        }
        request.addBody("image", base64Content);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_GENERAL_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 通用文字识别（带位置信息版）
     * @param imgUrl 图片链接，需要有完整协议头，如http://some.com
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                recognize_granularity： big/small
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject generalUrl(String imgUrl, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        preOperation(request);

        request.addBody("url", imgUrl);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_GENERAL_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 基础通用文字识别
     * @param imgPath 图片路径
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject basicGeneral(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return basicGeneral(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 基础通用文字识别
     * @param imgData 图片原始文件数据
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject basicGeneral(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        // check param
        JSONObject checkRet = checkParam(imgData);
        if (!"0".equals(checkRet.getString("error_code"))) {
            return checkRet;
        }
        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        // check size
        if (base64Content.length() > OcrConsts.OCR_MAX_IMAGE_SIZE) {
            return AipError.IMAGE_SIZE_ERROR.toJsonResult();
        }
        request.addBody("image", base64Content);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_BASIC_GENERAL_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 基础通用文字识别
     * @param imgUrl 图片链接，需要有完整协议头，如http://some.com
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject basicGeneralUrl(String imgUrl, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);

        request.addBody("url", imgUrl);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_BASIC_GENERAL_URL);
        postOperation(request);

        return requestServer(request);
    }

    // 高精度版通用文字识别
    /**
     * 通用文字识别高精度版（带位置信息）
     * @param imgPath 图片路径
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                recognize_granularity： big/small
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject accurateGeneral(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return accurateGeneral(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 通用文字识别高精度版（带位置信息）
     * @param imgData 图片原始文件数据
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                recognize_granularity： big/small
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject accurateGeneral(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);

        request.addBody("image", base64Content);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_ACCURATE_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 基础通用文字识别高精度版
     * @param imgPath 图片路径
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject basicAccurateGeneral(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return basicAccurateGeneral(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 基础通用文字识别高精度版
     * @param imgData 图片原始文件数据
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject basicAccurateGeneral(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);

        request.addBody("image", base64Content);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_BASIC_ACCURATE_URL);
        postOperation(request);

        return requestServer(request);
    }

    // 网图识别接口
    /**
     * 网图识别接口
     * @param imgPath 图片路径
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject webImage(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return webImage(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }


    /**
     * 网图识别接口
     * @param imgData 图片二进制数据
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject webImage(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        // check param
        JSONObject checkRet = checkParam(imgData);
        if (!"0".equals(checkRet.getString("error_code"))) {
            return checkRet;
        }
        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        // check size
        if (base64Content.length() > OcrConsts.OCR_MAX_IMAGE_SIZE) {
            return AipError.IMAGE_SIZE_ERROR.toJsonResult();
        }
        request.addBody("image", base64Content);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_WEB_IMAGE_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 网图识别接口
     * @param imgUrl 图片链接，需要有完整协议头，如http://some.com
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject webImageUrl(String imgUrl, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        preOperation(request);
        request.addBody("url", imgUrl);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_WEB_IMAGE_URL);
        postOperation(request);

        return requestServer(request);
    }

    // 生僻字识别接口
    /**
     * 生僻字识别接口
     * @param imgPath 图片路径
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject enhancedGeneral(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return enhancedGeneral(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }


    /**
     * 生僻字识别接口
     * @param imgData 图片二进制数据
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject enhancedGeneral(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        // check param
        JSONObject checkRet = checkParam(imgData);
        if (!"0".equals(checkRet.getString("error_code"))) {
            return checkRet;
        }
        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        // check size
        if (base64Content.length() > OcrConsts.OCR_MAX_IMAGE_SIZE) {
            return AipError.IMAGE_SIZE_ERROR.toJsonResult();
        }
        request.addBody("image", base64Content);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_ENHANCED_GENERAL_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 生僻字识别接口
     * @param imgUrl 图片链接，需要有完整协议头，如http://some.com
     * @param options 接口可选参数
     *                detect_direction : true/false
     *                language_type :
     *                <p>识别语言类型，若不传则默认为CHN_ENG。
     *                  可选值包括：CHN_ENG - 中英文混合；
     *                  ENG - 英文；
     *                  POR - 葡萄牙语；
     *                  FRE - 法语；
     *                  GER - 德语；
     *                  ITA - 意大利语；
     *                  SPA - 西班牙语；
     *                  RUS - 俄语；
     *                  JAP - 日语</p>
     *                mask : 表示mask区域的黑白灰度图片，白色代表选中, base64编码
     *                detect_language: true/false
     * @return  Json格式的服务器返回值
     */
    public JSONObject enhancedGeneralUrl(String imgUrl, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        preOperation(request);

        request.addBody("url", imgUrl);
        request.addBody(options);
        request.setUri(OcrConsts.OCR_ENHANCED_GENERAL_URL);
        postOperation(request);

        return requestServer(request);
    }

    public JSONObject drivingLicense(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return drivingLicense(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    public JSONObject drivingLicense(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.DRIVING_LICENSE_URL);
        postOperation(request);

        return requestServer(request);
    }

    public JSONObject vehicleLicense(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return vehicleLicense(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    public JSONObject vehicleLicense(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.VEHICLE_LICENSE_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 车牌识别接口
     * @param imgPath 图片路径
     * @return json识别结果
     */
    public JSONObject plateLicense(String imgPath) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return plateLicense(imgData);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 车牌识别接口
     * @param imgData 图片二进制数据
     * @return json识别结果
     */
    public JSONObject plateLicense(byte[] imgData) {
        AipRequest request = new AipRequest();

        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        request.addBody("image", base64Content);
        request.setUri(OcrConsts.OCR_LICENSE_PLATE_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 票据识别接口
     * @param imgPath 图片路径
     * @param options 可选参数
     * @return json识别结果
     */
    public JSONObject receipt(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return receipt(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 票据识别接口
     * @param imgData 图片二进制数据
     * @param options 可选参数
     * @return json识别结果
     */
    public JSONObject receipt(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.OCR_RECEIPT_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 营业执照识别接口
     * @param imgPath 图片路径
     * @param options 可选参数
     * @return json识别结果
     */
    public JSONObject businessLicense(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return businessLicense(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 营业执照识别接口
     * @param imgData 图片二进制数据
     * @param options 可选参数
     * @return json识别结果
     */
    public JSONObject businessLicense(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        preOperation(request);
        // 添加内容到request
        String base64Content = Base64Util.encode(imgData);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.OCR_BUSINESS_LICENSE_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 表格文字识别接口。（异步）
     * @param imgPath 识别图片路径
     * @return json对象，包含request_id
     */
    public JSONObject tableRecognitionAsync(String imgPath) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return tableRecognitionAsync(imgData);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 表格文字识别接口。（异步）
     * @param imgData 识别图片二进制数据
     * @return json对象，包含request_id
     */
    public JSONObject tableRecognitionAsync(byte[] imgData) {
        AipRequest request = new AipRequest();
        preOperation(request);

        String base64Content = Base64Util.encode(imgData);
        request.addBody("image", base64Content);

        request.setUri(OcrConsts.OCR_TABLE_URL);
        postOperation(request);

        return requestServer(request);
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
        request.setUri(OcrConsts.OCR_TABLE_RESULT_URL);
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
        JSONObject res = tableRecognitionAsync(imgData);
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

    private JSONObject checkParam(byte[] imgData) {
        // side length
        HashMap<String, Integer> imgInfo = ImageUtil.getImageInfoByBytes(imgData);
        if (imgInfo == null) {
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
        Integer width = imgInfo.get("width");
        Integer height = imgInfo.get("height");
        if (width < OcrConsts.OCR_MIN_IMAGE_SIDE_LENGTH
                || width > OcrConsts.OCR_MAX_IMAGE_SIDE_LENGTH
                || height < OcrConsts.OCR_MIN_IMAGE_SIDE_LENGTH
                || height > OcrConsts.OCR_MAX_IMAGE_SIDE_LENGTH) {
            return AipError.IMAGE_LENGTH_ERROR.toJsonResult();
        }

        // image format
        String format = ImageUtil.getImageFormatByBytes(imgData);
        if (!OcrConsts.OCR_SUPPORT_IMAGE_FORMAT.contains(format)) {
            return AipError.UNSUPPORTED_IMAGE_FORMAT_ERROR.toJsonResult();
        }

        return AipError.SUCCESS.toJsonResult();
    }
}
