package com.baidu.aip.easydl;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.error.AipError;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;
import com.baidu.aip.http.Headers;
import com.baidu.aip.http.HttpCharacterEncoding;
import com.baidu.aip.http.HttpContentType;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class AipEasyDL extends BaseClient {

    public AipEasyDL(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * easyDL通用请求方法
     * @param url 服务的url
     * @param image 图片本地路径
     * @param options 可选参数
     * @return Json返回
     */
    public JSONObject sendImageRequest(String url, String image, HashMap<String, Object> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return sendImageRequest(url, data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }


    /**
     * easyDL通用请求方法
     * @param url 服务的url
     * @param image 图片二进制数据
     * @param options 可选参数
     * @return Json返回
     */
    public JSONObject sendImageRequest(String url, byte[] image, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        String content = Base64Util.encode(image);
        request.addBody("image", content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(url);
        request.addHeader(Headers.CONTENT_ENCODING,
                HttpCharacterEncoding.ENCODE_UTF8);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * easyDL通用请求方法
     * @param url 服务的url
     * @param file 图片本地路径
     * @param options 可选参数
     * @return Json返回
     */
    public JSONObject sendSoundRequest(String url, String file, HashMap<String, Object> options) {
        try {
            byte[] data = Util.readFileByBytes(file);
            return sendImageRequest(url, data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }


    /**
     * easyDL通用请求方法
     * @param url 服务的url
     * @param data 图片二进制数据
     * @param options 可选参数
     * @return Json返回
     */
    public JSONObject sendSoundRequest(String url, byte[] data, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        String content = Base64Util.encode(data);
        request.addBody("sound", content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(url);
        request.addHeader(Headers.CONTENT_ENCODING,
                HttpCharacterEncoding.ENCODE_UTF8);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

}
