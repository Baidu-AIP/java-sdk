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

package com.baidu.aip.face;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.error.AipError;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class AipFace extends BaseClient {

    public AipFace(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * 人脸检测接口   
     *
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   max_face_num 最多处理人脸数目，默认值1
     *   face_fields 包括age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities信息，逗号分隔，默认只返回人脸框、概率和旋转角度
     * @return JSONObject
     */
    public JSONObject detect(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.DETECT);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸检测接口
     *
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   max_face_num 最多处理人脸数目，默认值1
     *   face_fields 包括age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities信息，逗号分隔，默认只返回人脸框、概率和旋转角度
     * @return JSONObject
     */
    public JSONObject detect(String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return detect(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人脸比对接口   
     *
     *
     * @param images - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   ext_fields 返回质量信息，取值固定:目前支持qualities(质量检测)。(对所有图片都会做改处理)
     *   image_liveness 返回的活体信息，“faceliveness,faceliveness” 表示对比对的两张图片都做活体检测；“,faceliveness” 表示对第一张图片不做活体检测、第二张图做活体检测；“faceliveness,” 表示对第一张图片做活体检测、第二张图不做活体检测；<br>**注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px\*100px，人脸长宽与图片长宽比例，不小于1/3**
     *   types 请求对比的两张图片的类型，示例：“7,13”<br>**12**表示带水印证件照：一般为带水印的小图，如公安网小图<br>**7**表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等<br>**13**表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片，**注**：需要确保人脸部分不可太小，通常为100px\*100px
     * @return JSONObject
     */
    public JSONObject match(byte[][] images, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        ArrayList<String> buffer = new ArrayList<String>();
        for (byte[] data : images) {
            String base64Str = Base64Util.encode(data);
            buffer.add(base64Str);
        }
        String imgDataAll = Util.mkString(buffer.iterator(), ',');
        request.addBody("images", imgDataAll);

        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.MATCH);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸比对接口
     *
     *
     * @param images - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   ext_fields 返回质量信息，取值固定:目前支持qualities(质量检测)。(对所有图片都会做改处理)
     *   image_liveness 返回的活体信息，“faceliveness,faceliveness” 表示对比对的两张图片都做活体检测；“,faceliveness” 表示对第一张图片不做活体检测、第二张图做活体检测；“faceliveness,” 表示对第一张图片做活体检测、第二张图不做活体检测；<br>**注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px\*100px，人脸长宽与图片长宽比例，不小于1/3**
     *   types 请求对比的两张图片的类型，示例：“7,13”<br>**12**表示带水印证件照：一般为带水印的小图，如公安网小图<br>**7**表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等<br>**13**表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片，**注**：需要确保人脸部分不可太小，通常为100px\*100px
     * @return JSONObject
     */
    public JSONObject match(List<String> images, HashMap<String, String> options) {
        try {
            byte[][] imgData = new byte[images.size()][];
            int idx = 0;
            for (String path : images) {
                imgData[idx] = Util.readFileByBytes(path);
                ++idx;
            }
            
            return match(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人脸识别接口   
     *
     *
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   ext_fields 特殊返回信息，多个用逗号分隔，取值固定: 目前支持faceliveness(活体检测)。**注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px\*100px，人脸长宽与图片长宽比例，不小于1/3**
     *   user_top_num 返回用户top数，默认为1，最多返回5个
     * @return JSONObject
     */
    public JSONObject identifyUser(String groupId, byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("group_id", groupId);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.IDENTIFY);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸识别接口
     *
     *
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   ext_fields 特殊返回信息，多个用逗号分隔，取值固定: 目前支持faceliveness(活体检测)。**注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px\*100px，人脸长宽与图片长宽比例，不小于1/3**
     *   user_top_num 返回用户top数，默认为1，最多返回5个
     * @return JSONObject
     */
    public JSONObject identifyUser(String groupId, String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return identifyUser(groupId, imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人脸认证接口   
     *
     *
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   top_num 返回用户top数，默认为1
     *   ext_fields 特殊返回信息，多个用逗号分隔，取值固定: 目前支持faceliveness(活体检测)。**注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px\*100px，人脸长宽与图片长宽比例，不小于1/3**
     * @return JSONObject
     */
    public JSONObject verifyUser(String uid, String groupId, byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("uid", uid);
        
        request.addBody("group_id", groupId);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.VERIFY);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸认证接口
     *
     *
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   top_num 返回用户top数，默认为1
     *   ext_fields 特殊返回信息，多个用逗号分隔，取值固定: 目前支持faceliveness(活体检测)。**注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px\*100px，人脸长宽与图片长宽比例，不小于1/3**
     * @return JSONObject
     */
    public JSONObject verifyUser(String uid, String groupId, String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return verifyUser(uid, groupId, imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * M:N 识别接口   
     *
     *
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   ext_fields 特殊返回信息，多个用逗号分隔，取值固定: 目前支持faceliveness(活体检测)。**注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px\*100px，人脸长宽与图片长宽比例，不小于1/3**
     *   detect_top_num 检测多少个人脸进行比对，默认值1（最对返回10个）
     *   user_top_num 返回识别结果top人数”，当同一个人有多张图片时，只返回比对最高的1个分数（即，scores参数只有一个值），默认为1（最多返回20个）
     * @return JSONObject
     */
    public JSONObject multiIdentify(String groupId, byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("group_id", groupId);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.MULTI_IDENTIFY);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * M:N 识别接口
     *
     *
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   ext_fields 特殊返回信息，多个用逗号分隔，取值固定: 目前支持faceliveness(活体检测)。**注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px\*100px，人脸长宽与图片长宽比例，不小于1/3**
     *   detect_top_num 检测多少个人脸进行比对，默认值1（最对返回10个）
     *   user_top_num 返回识别结果top人数”，当同一个人有多张图片时，只返回比对最高的1个分数（即，scores参数只有一个值），默认为1（最多返回20个）
     * @return JSONObject
     */
    public JSONObject multiIdentify(String groupId, String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return multiIdentify(groupId, imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人脸注册接口   
     *
     *
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param userInfo - 用户资料，长度限制256B
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   action_type 参数包含append、replace。**如果为“replace”，则每次注册时进行替换replace（新增或更新）操作，默认为append操作**。例如：uid在库中已经存在时，对此uid重复注册时，新注册的图片默认会**追加**到该uid下，如果手动选择`action_type:replace`，则会用新图替换库中该uid下所有图片。
     * @return JSONObject
     */
    public JSONObject addUser(String uid, String userInfo, String groupId, byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("uid", uid);
        
        request.addBody("user_info", userInfo);
        
        request.addBody("group_id", groupId);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.USER_ADD);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸注册接口
     *
     *
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param userInfo - 用户资料，长度限制256B
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   action_type 参数包含append、replace。**如果为“replace”，则每次注册时进行替换replace（新增或更新）操作，默认为append操作**。例如：uid在库中已经存在时，对此uid重复注册时，新注册的图片默认会**追加**到该uid下，如果手动选择`action_type:replace`，则会用新图替换库中该uid下所有图片。
     * @return JSONObject
     */
    public JSONObject addUser(String uid, String userInfo, String groupId, String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return addUser(uid, userInfo, groupId, imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人脸更新接口   
     *
     *
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param userInfo - 用户资料，长度限制256B
     * @param groupId - 更新指定groupid下uid对应的信息
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   action_type 目前仅支持replace，uid不存在时，不报错，会自动变为注册操作；未选择该参数时，如果uid不存在会提示错误
     * @return JSONObject
     */
    public JSONObject updateUser(String uid, String userInfo, String groupId, byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("uid", uid);
        
        request.addBody("user_info", userInfo);
        
        request.addBody("group_id", groupId);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.USER_UPDATE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸更新接口
     *
     *
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param userInfo - 用户资料，长度限制256B
     * @param groupId - 更新指定groupid下uid对应的信息
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   action_type 目前仅支持replace，uid不存在时，不报错，会自动变为注册操作；未选择该参数时，如果uid不存在会提示错误
     * @return JSONObject
     */
    public JSONObject updateUser(String uid, String userInfo, String groupId, String image, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(image);
            return updateUser(uid, userInfo, groupId, imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人脸删除接口   
     *
     *
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   group_id 删除指定groupid下uid对应的信息
     * @return JSONObject
     */
    public JSONObject deleteUser(String uid, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("uid", uid);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.USER_DELETE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 用户信息查询接口   
     *
     *
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   group_id 选择指定group_id则只查找group列表下的uid内容，如果不指定则查找所有group下对应uid的信息
     * @return JSONObject
     */
    public JSONObject getUser(String uid, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("uid", uid);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.USER_GET);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 组列表查询接口   
     *
     *
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   start 默认值0，起始序号
     *   end 返回数量，默认值100，最大值1000
     * @return JSONObject
     */
    public JSONObject getGroupList(HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.GROUP_GETLIST);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 组内用户列表查询接口   
     *
     *
     * @param groupId - 用户组id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   start 默认值0，起始序号
     *   end 返回数量，默认值100，最大值1000
     * @return JSONObject
     */
    public JSONObject getGroupUsers(String groupId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("group_id", groupId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.GROUP_GETUSERS);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 组间复制用户接口   
     *
     *
     * @param srcGroupId - 从指定group里复制信息
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject addGroupUser(String srcGroupId, String groupId, String uid, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("src_group_id", srcGroupId);
        
        request.addBody("group_id", groupId);
        
        request.addBody("uid", uid);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.GROUP_ADDUSER);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 组内删除用户接口   
     *
     *
     * @param groupId - 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group\_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。**注：group无需单独创建，注册用户时则会自动创建group。**<br>**产品建议**：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param uid - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject deleteGroupUser(String groupId, String uid, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("group_id", groupId);
        
        request.addBody("uid", uid);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.GROUP_DELETEUSER);
        postOperation(request);
        return requestServer(request);
    }

    // 活体检测接口
    public JSONObject livenessVerify(String imgPath, HashMap<String, Object> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return livenessVerify(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    public JSONObject livenessVerify(byte[] imgData, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        String base64Str = Base64Util.encode(imgData);
        request.addBody("image", base64Str);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.FACE_LIVENESS_VERIFY_URL);
        postOperation(request);
        return requestServer(request);
    }

}