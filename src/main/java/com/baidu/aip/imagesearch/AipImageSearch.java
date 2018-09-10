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
     * **该接口实现单张图片入库，入库时需要同步提交图片及可关联至本地图库的摘要信息（具体变量为brief，具体可传入图片在本地标记id、图片url、图片名称等）；同时可提交分类维度信息（具体变量为tags，最多可传入2个tag），方便对图库中的图片进行管理、分类检索。****注：重复添加完全相同的图片会返回错误。**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
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
     * **该接口实现单张图片入库，入库时需要同步提交图片及可关联至本地图库的摘要信息（具体变量为brief，具体可传入图片在本地标记id、图片url、图片名称等）；同时可提交分类维度信息（具体变量为tags，最多可传入2个tag），方便对图库中的图片进行管理、分类检索。****注：重复添加完全相同的图片会返回错误。**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject sameHqAdd(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return sameHqAdd(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相同图检索—入库接口   
     * **该接口实现单张图片入库，入库时需要同步提交图片及可关联至本地图库的摘要信息（具体变量为brief，具体可传入图片在本地标记id、图片url、图片名称等）；同时可提交分类维度信息（具体变量为tags，最多可传入2个tag），方便对图库中的图片进行管理、分类检索。****注：重复添加完全相同的图片会返回错误。**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject sameHqAddUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SAME_HQ_ADD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相同图检索—检索接口   
     * 完成入库后，可使用该接口实现相同图检索。**支持传入指定分类维度（具体变量tags）进行检索，返回结果支持翻页（具体变量pn、rn）。****请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息。**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     *   tag_logic 检索时tag之间的逻辑， 0：逻辑and，1：逻辑or
     *   pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     *   rn 分页功能，截取条数，例：250
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
     * 完成入库后，可使用该接口实现相同图检索。**支持传入指定分类维度（具体变量tags）进行检索，返回结果支持翻页（具体变量pn、rn）。****请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息。**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     *   tag_logic 检索时tag之间的逻辑， 0：逻辑and，1：逻辑or
     *   pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     *   rn 分页功能，截取条数，例：250
     * @return JSONObject
     */
    public JSONObject sameHqSearch(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return sameHqSearch(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相同图检索—检索接口   
     * 完成入库后，可使用该接口实现相同图检索。**支持传入指定分类维度（具体变量tags）进行检索，返回结果支持翻页（具体变量pn、rn）。****请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息。**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     *   tag_logic 检索时tag之间的逻辑， 0：逻辑and，1：逻辑or
     *   pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     *   rn 分页功能，截取条数，例：250
     * @return JSONObject
     */
    public JSONObject sameHqSearchUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SAME_HQ_SEARCH);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相同图检索—更新接口   
     * **更新图库中图片的摘要和分类信息（具体变量为brief、tags）**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 更新的摘要信息，最长256B。样例：{"name":"周杰伦", "id":"666"}
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject sameHqUpdate(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SAME_HQ_UPDATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相同图检索—更新接口
     * **更新图库中图片的摘要和分类信息（具体变量为brief、tags）**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 更新的摘要信息，最长256B。样例：{"name":"周杰伦", "id":"666"}
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject sameHqUpdate(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return sameHqUpdate(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相同图检索—更新接口   
     * **更新图库中图片的摘要和分类信息（具体变量为brief、tags）**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 更新的摘要信息，最长256B。样例：{"name":"周杰伦", "id":"666"}
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject sameHqUpdateUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SAME_HQ_UPDATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相同图检索—删除接口   
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
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
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject sameHqDeleteByImage(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return sameHqDeleteByImage(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相同图检索—删除接口   
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject sameHqDeleteByUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SAME_HQ_DELETE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相同图检索—删除接口   
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
     *
     * @param contSign - 图片签名
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
     * **该接口实现单张图片入库，入库时需要同步提交图片及可关联至本地图库的摘要信息（具体变量为brief，具体可传入图片在本地标记id、图片url、图片名称等）；同时可提交分类维度信息（具体变量为tags，最多可传入2个tag），方便对图库中的图片进行管理、分类检索。****注：重复添加完全相同的图片会返回错误。**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
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
     * **该接口实现单张图片入库，入库时需要同步提交图片及可关联至本地图库的摘要信息（具体变量为brief，具体可传入图片在本地标记id、图片url、图片名称等）；同时可提交分类维度信息（具体变量为tags，最多可传入2个tag），方便对图库中的图片进行管理、分类检索。****注：重复添加完全相同的图片会返回错误。**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject similarAdd(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return similarAdd(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相似图检索—入库接口   
     * **该接口实现单张图片入库，入库时需要同步提交图片及可关联至本地图库的摘要信息（具体变量为brief，具体可传入图片在本地标记id、图片url、图片名称等）；同时可提交分类维度信息（具体变量为tags，最多可传入2个tag），方便对图库中的图片进行管理、分类检索。****注：重复添加完全相同的图片会返回错误。**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject similarAddUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SIMILAR_ADD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相似图检索—检索接口   
     * 完成入库后，可使用该接口实现相似图检索。**支持传入指定分类维度（具体变量tags）进行检索，返回结果支持翻页（具体变量pn、rn）。****请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息。**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     *   tag_logic 检索时tag之间的逻辑， 0：逻辑and，1：逻辑or
     *   pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     *   rn 分页功能，截取条数，例：250
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
     * 完成入库后，可使用该接口实现相似图检索。**支持传入指定分类维度（具体变量tags）进行检索，返回结果支持翻页（具体变量pn、rn）。****请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息。**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     *   tag_logic 检索时tag之间的逻辑， 0：逻辑and，1：逻辑or
     *   pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     *   rn 分页功能，截取条数，例：250
     * @return JSONObject
     */
    public JSONObject similarSearch(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return similarSearch(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相似图检索—检索接口   
     * 完成入库后，可使用该接口实现相似图检索。**支持传入指定分类维度（具体变量tags）进行检索，返回结果支持翻页（具体变量pn、rn）。****请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息。**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     *   tag_logic 检索时tag之间的逻辑， 0：逻辑and，1：逻辑or
     *   pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     *   rn 分页功能，截取条数，例：250
     * @return JSONObject
     */
    public JSONObject similarSearchUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SIMILAR_SEARCH);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相似图检索—更新接口   
     * **更新图库中图片的摘要和分类信息（具体变量为brief、tags）**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 更新的摘要信息，最长256B。样例：{"name":"周杰伦", "id":"666"}
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject similarUpdate(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SIMILAR_UPDATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相似图检索—更新接口
     * **更新图库中图片的摘要和分类信息（具体变量为brief、tags）**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 更新的摘要信息，最长256B。样例：{"name":"周杰伦", "id":"666"}
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject similarUpdate(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return similarUpdate(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相似图检索—更新接口   
     * **更新图库中图片的摘要和分类信息（具体变量为brief、tags）**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 更新的摘要信息，最长256B。样例：{"name":"周杰伦", "id":"666"}
     *   tags 1 - 65535范围内的整数，tag间以逗号分隔，最多2个tag。样例："100,11" ；检索时可圈定分类维度进行检索
     * @return JSONObject
     */
    public JSONObject similarUpdateUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SIMILAR_UPDATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相似图检索—删除接口   
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
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
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject similarDeleteByImage(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return similarDeleteByImage(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 相似图检索—删除接口   
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject similarDeleteByUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.SIMILAR_DELETE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 相似图检索—删除接口   
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
     *
     * @param contSign - 图片签名
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
     * **该接口实现单张图片入库，入库时需要同步提交图片及可关联至本地图库的摘要信息（具体变量为brief，具体可传入图片在本地标记id、图片url、图片名称等）。同时可提交分类维度信息（具体变量为class_id1、class_id2），方便对图库中的图片进行管理、分类检索。****注：重复添加完全相同的图片会返回错误。**
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
     * **该接口实现单张图片入库，入库时需要同步提交图片及可关联至本地图库的摘要信息（具体变量为brief，具体可传入图片在本地标记id、图片url、图片名称等）。同时可提交分类维度信息（具体变量为class_id1、class_id2），方便对图库中的图片进行管理、分类检索。****注：重复添加完全相同的图片会返回错误。**
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
            byte[] data = Util.readFileByBytes(image);
            return productAdd(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 商品检索—入库接口   
     * **该接口实现单张图片入库，入库时需要同步提交图片及可关联至本地图库的摘要信息（具体变量为brief，具体可传入图片在本地标记id、图片url、图片名称等）。同时可提交分类维度信息（具体变量为class_id1、class_id2），方便对图库中的图片进行管理、分类检索。****注：重复添加完全相同的图片会返回错误。**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 检索时原样带回,最长256B。**请注意，检索接口不返回原图，仅反馈当前填写的brief信息，所以调用该入库接口时，brief信息请尽量填写可关联至本地图库的图片id或者图片url、图片名称等信息**
     *   class_id1 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   class_id2 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     * @return JSONObject
     */
    public JSONObject productAddUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.PRODUCT_ADD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 商品检索—检索接口   
     * 完成入库后，可使用该接口实现商品检索。**支持传入指定分类维度（具体变量class_id1、class_id2）进行检索，返回结果支持翻页（具体变量pn、rn）。****请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   class_id1 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   class_id2 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     *   rn 分页功能，截取条数，例：250
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
     * 完成入库后，可使用该接口实现商品检索。**支持传入指定分类维度（具体变量class_id1、class_id2）进行检索，返回结果支持翻页（具体变量pn、rn）。****请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   class_id1 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   class_id2 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     *   rn 分页功能，截取条数，例：250
     * @return JSONObject
     */
    public JSONObject productSearch(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return productSearch(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 商品检索—检索接口   
     * 完成入库后，可使用该接口实现商品检索。**支持传入指定分类维度（具体变量class_id1、class_id2）进行检索，返回结果支持翻页（具体变量pn、rn）。****请注意，检索接口不返回原图，仅反馈当前填写的brief信息，请调用入库接口时尽量填写可关联至本地图库的图片id或者图片url等信息**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   class_id1 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   class_id2 商品分类维度1，支持1-60范围内的整数。检索时可圈定该分类维度进行检索
     *   pn 分页功能，起始位置，例：0。未指定分页时，默认返回前300个结果；接口返回数量最大限制1000条，例如：起始位置为900，截取条数500条，接口也只返回第900 - 1000条的结果，共计100条
     *   rn 分页功能，截取条数，例：250
     * @return JSONObject
     */
    public JSONObject productSearchUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.PRODUCT_SEARCH);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 商品检索—更新接口   
     * **更新图库中图片的摘要和分类信息（具体变量为brief、class_id1/class_id2）**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 更新的摘要信息，最长256B。样例：{"name":"周杰伦", "id":"666"}
     *   class_id1 更新的商品分类1，支持1-60范围内的整数。
     *   class_id2 更新的商品分类2，支持1-60范围内的整数。
     * @return JSONObject
     */
    public JSONObject productUpdate(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.PRODUCT_UPDATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 商品检索—更新接口
     * **更新图库中图片的摘要和分类信息（具体变量为brief、class_id1/class_id2）**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 更新的摘要信息，最长256B。样例：{"name":"周杰伦", "id":"666"}
     *   class_id1 更新的商品分类1，支持1-60范围内的整数。
     *   class_id2 更新的商品分类2，支持1-60范围内的整数。
     * @return JSONObject
     */
    public JSONObject productUpdate(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return productUpdate(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 商品检索—更新接口   
     * **更新图库中图片的摘要和分类信息（具体变量为brief、class_id1/class_id2）**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   brief 更新的摘要信息，最长256B。样例：{"name":"周杰伦", "id":"666"}
     *   class_id1 更新的商品分类1，支持1-60范围内的整数。
     *   class_id2 更新的商品分类2，支持1-60范围内的整数。
     * @return JSONObject
     */
    public JSONObject productUpdateUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.PRODUCT_UPDATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 商品检索—删除接口   
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
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
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject productDeleteByImage(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return productDeleteByImage(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 商品检索—删除接口   
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
     *
     * @param url - 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式，当image字段存在时url字段失效
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject productDeleteByUrl(String url, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("url", url);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(ImageSearchConsts.PRODUCT_DELETE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 商品检索—删除接口   
     * **删除图库中的图片，支持批量删除，批量删除时请传cont_sign参数，勿传image，最多支持1000个cont_sign**
     *
     * @param contSign - 图片签名
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