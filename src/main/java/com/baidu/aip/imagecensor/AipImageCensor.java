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
package com.baidu.aip.imagecensor;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.error.AipError;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;
import com.baidu.aip.http.Headers;
import com.baidu.aip.http.HttpContentType;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.ImageUtil;
import com.baidu.aip.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AipImageCensor extends BaseClient {

    public AipImageCensor(String appId, String aipKey, String aipToken) {
        super(appId, aipKey, aipToken);
    }

    public JSONObject antiPorn(String imgPath) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return antiPorn(imgData);
        } catch (IOException e) {
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    public JSONObject antiPorn(byte[] imgData) {
        AipRequest request = new AipRequest();
        // check param

        JSONObject checkRet = checkParam(imgData);
        if (!"0".equals(checkRet.getString("error_code"))) {
            return checkRet;
        }
        preOperation(request);
        // add API params
        String base64Content = Base64Util.encode(imgData);

        request.addBody("image", base64Content);
        request.setUri(ImageCensorConsts.ANTI_PORN_URL);
        postOperation(request);

        return requestServer(request);
    }

    public JSONObject antiPornGif(String imgPath) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return antiPornGif(imgData);
        } catch (IOException e) {
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    public JSONObject antiPornGif(byte[] imgData) {
        AipRequest request = new AipRequest();
        // check param

        JSONObject checkRet = checkImgFormat(imgData, "gif");
        if (!"0".equals(checkRet.getString("error_code"))) {
            return checkRet;
        }

        preOperation(request);
        // add API params
        String base64Content = Base64Util.encode(imgData);
        request.addBody("image", base64Content);
        request.setUri(ImageCensorConsts.ANTI_PORN_GIF_URL);

        postOperation(request);

        return requestServer(request);
    }

    public JSONObject antiTerror(String imgPath) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return antiTerror(imgData);
        } catch (IOException e) {
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    public JSONObject antiTerror(byte[] imgData) {
        AipRequest request = new AipRequest();

        preOperation(request);
        // add API params
        String base64Content = Base64Util.encode(imgData);

        request.addBody("image", base64Content);
        request.setUri(ImageCensorConsts.ANTI_TERROR_URL);
        postOperation(request);

        return requestServer(request);
    }

    public JSONObject imageCensorComb(String imgPath, EImgType type,
                                      List<String> scenes, HashMap<String, String> options) {
        if (type == EImgType.FILE) {
            try {
                byte[] imgData = Util.readFileByBytes(imgPath);
                return imageCensorComb(imgData, scenes, options);
            } catch (IOException e) {
                return AipError.IMAGE_READ_ERROR.toJsonResult();
            }
        }

        // url
        AipRequest request = new AipRequest();

        request.addBody("imgUrl", imgPath);

        return imageCensorCombHelper(request, scenes, options);
    }

    public JSONObject imageCensorComb(byte[] imgData, List<String> scenes, HashMap<String, String> options) {
        AipRequest request = new AipRequest();

        String base64Content = Base64Util.encode(imgData);
        request.addBody("image", base64Content);

        return imageCensorCombHelper(request, scenes, options);
    }

    private JSONObject imageCensorCombHelper(AipRequest request, List<String> scenes, HashMap<String, String> options) {
        preOperation(request);
        JSONArray obj = new JSONArray();
        for (String scene : scenes) {
            obj.put(scene);
        }
        request.addBody("scenes", obj);

        if (options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(ImageCensorConsts.IMAGE_CENSOR_COMB_URL);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        postOperation(request);
        return requestServer(request);
    }


    public JSONObject faceAudit(List<String> imgPaths, EImgType type,
                                HashMap<String, String> options) {
        if (type == EImgType.FILE) {
            try {
                byte[][] imgData = new byte[imgPaths.size()][];
                int idx = 0;
                for (String path : imgPaths) {
                    imgData[idx] = Util.readFileByBytes(path);
                    ++idx;
                }
                return faceAudit(imgData, options);
            } catch (IOException e) {
                e.printStackTrace();
                return AipError.IMAGE_READ_ERROR.toJsonResult();
            }
        }

        AipRequest request = new AipRequest();
        request.addBody("imgUrls", Util.mkString(imgPaths.iterator(), ','));

        return faceAuditHelper(request, options);
    }

    public JSONObject faceAudit(byte[][] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        ArrayList<String> buffer = new ArrayList<String>();
        for (byte[] data : imgData) {
            String base64Str = Base64Util.encode(data);
            buffer.add(base64Str);
        }
        String imgDataAll = Util.mkString(buffer.iterator(), ',');

        request.addBody("images", imgDataAll);
        if (options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }

        return faceAuditHelper(request, options);
    }

    private JSONObject faceAuditHelper(AipRequest request, HashMap<String, String> options) {
        preOperation(request);
        request.setUri(ImageCensorConsts.FACE_AUDIT_URL);
        postOperation(request);
        return requestServer(request);
    }


    private JSONObject checkParam(byte[] imgData) {
        // image format
        String format = ImageUtil.getImageFormatByBytes(imgData);
        if (!ImageCensorConsts.ANTIPORN_SUPPORT_IMAGE_FORMAT.contains(format)) {
            return AipError.UNSUPPORTED_IMAGE_FORMAT_ERROR.toJsonResult();
        }

        return AipError.SUCCESS.toJsonResult();
    }

    private JSONObject checkImgFormat(byte[] imgData, String format) {
        String realFormat = ImageUtil.getImageFormatByBytes(imgData);
        if (realFormat.equals(format)) {
            return AipError.SUCCESS.toJsonResult();
        }
        return AipError.UNSUPPORTED_IMAGE_FORMAT_ERROR.toJsonResult();
    }
}
