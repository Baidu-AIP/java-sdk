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

package com.baidu.aip.imagesearch;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.error.AipError;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class AipImageSearch extends BaseClient {

    public AipImageSearch(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * 相同图检索—入库接口   
     * 该请求用于实时检索相同图片集合。即对于输入的一张图片（可正常解码，且长宽比适宜），返回自建图库中相同的图片集合。相同图检索包含入库、检索、删除三个子接口；**在正式使用之前请加入QQ群：649285136 联系工作人员完成建库并调用入库接口完成图片入库**。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     * @return JSONObject
     */
    public JSONObject sameHqAdd(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SAME_HQ_ADD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相同图检索—入库接口
     * 该请求用于实时检索相同图片集合。即对于输入的一张图片（可正常解码，且长宽比适宜），返回自建图库中相同的图片集合。相同图检索包含入库、检索、删除三个子接口；**在正式使用之前请加入QQ群：649285136 联系工作人员完成建库并调用入库接口完成图片入库**。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     * @return JSONObject
     */
    public JSONObject sameHqAdd(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return sameHqAdd(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相同图检索—检索接口   
     * 使用该接口前，请加入QQ群：649285136 ，联系工作人员完成建库。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject sameHqSearch(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SAME_HQ_SEARCH);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相同图检索—检索接口
     * 使用该接口前，请加入QQ群：649285136 ，联系工作人员完成建库。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject sameHqSearch(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return sameHqSearch(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相同图检索—删除接口   
     * 删除相同图
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject sameHqDeleteByImage(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SAME_HQ_DELETE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相同图检索—删除接口
     * 删除相同图
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject sameHqDeleteByImage(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return sameHqDeleteByImage(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相同图检索—删除接口   
     * 删除相同图
     *
     * @param contSign - 图片签名（和image二选一，image优先级更高）
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject sameHqDeleteBySign(String contSign, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("cont_sign", contSign);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SAME_HQ_DELETE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相似图检索—入库接口   
     * 该请求用于实时检索相似图片集合。即对于输入的一张图片（可正常解码，且长宽比适宜），返回自建图库中相似的图片集合。相似图检索包含入库、检索、删除三个子接口；**在正式使用之前请加入QQ群：649285136 联系工作人员完成建库并调用入库接口完成图片入库**。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     * @return JSONObject
     */
    public JSONObject similarAdd(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SIMILAR_ADD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相似图检索—入库接口
     * 该请求用于实时检索相似图片集合。即对于输入的一张图片（可正常解码，且长宽比适宜），返回自建图库中相似的图片集合。相似图检索包含入库、检索、删除三个子接口；**在正式使用之前请加入QQ群：649285136 联系工作人员完成建库并调用入库接口完成图片入库**。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     * @return JSONObject
     */
    public JSONObject similarAdd(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return similarAdd(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相似图检索—检索接口   
     * 使用该接口前，请加入QQ群：649285136 ，联系工作人员完成建库。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject similarSearch(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SIMILAR_SEARCH);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相似图检索—检索接口
     * 使用该接口前，请加入QQ群：649285136 ，联系工作人员完成建库。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject similarSearch(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return similarSearch(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相似图检索—删除接口   
     * 删除相似图
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject similarDeleteByImage(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SIMILAR_DELETE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相似图检索—删除接口
     * 删除相似图
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject similarDeleteByImage(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return similarDeleteByImage(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相似图检索—删除接口   
     * 删除相似图
     *
     * @param contSign - 图片签名（和image二选一，image优先级更高）
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject similarDeleteBySign(String contSign, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("cont_sign", contSign);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SIMILAR_DELETE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 商品检索—入库接口   
     * 该请求用于实时检索商品类型图片相同或相似的图片集合，适用于电商平台或商品展示等场景，即对于输入的一张图片（可正常解码，且长宽比适宜），返回自建商品库中相同或相似的图片集合。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。**请注意，检索接口不返回原图，仅反馈当前填写的brief信息，所以调用该入库接口时，brief信息请尽量填写可关联至本地图库的图片id或者图片url、图片名称等信息**
     *   class_id1 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   class_id2 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     * @return JSONObject
     */
    public JSONObject productAdd(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.PRODUCT_ADD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 商品检索—入库接口
     * 该请求用于实时检索商品类型图片相同或相似的图片集合，适用于电商平台或商品展示等场景，即对于输入的一张图片（可正常解码，且长宽比适宜），返回自建商品库中相同或相似的图片集合。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。**请注意，检索接口不返回原图，仅反馈当前填写的brief信息，所以调用该入库接口时，brief信息请尽量填写可关联至本地图库的图片id或者图片url、图片名称等信息**
     *   class_id1 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   class_id2 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     * @return JSONObject
     */
    public JSONObject productAdd(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return productAdd(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 商品检索—检索接口   
     * 完成入库后，可使用该接口实现商品检索。**请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   class_id1 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   class_id2 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     * @return JSONObject
     */
    public JSONObject productSearch(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.PRODUCT_SEARCH);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 商品检索—检索接口
     * 完成入库后，可使用该接口实现商品检索。**请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   class_id1 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   class_id2 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     * @return JSONObject
     */
    public JSONObject productSearch(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return productSearch(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 商品检索—删除接口   
     * 删除商品
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject productDeleteByImage(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.PRODUCT_DELETE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 商品检索—删除接口
     * 删除商品
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject productDeleteByImage(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return productDeleteByImage(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 商品检索—删除接口   
     * 删除商品
     *
     * @param contSign - 图片签名（和image二选一，image优先级更高）
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject productDeleteBySign(String contSign, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("cont_sign", contSign);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.PRODUCT_DELETE);
        postOperation(request);
        return requestServer(request);
    }

}