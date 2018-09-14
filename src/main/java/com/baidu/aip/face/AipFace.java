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
import com.baidu.aip.http.EBodyFormat;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AipFace extends BaseClient {

    public AipFace(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * 人脸检测接口   
     *
     * @param image - 图片信息(**总数据大小应小于10M**)，图片上传方式根据image_type来判断
     * @param imageType - 图片类型 **BASE64**:图片的base64值，base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M；**URL**:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)**；FACE_TOKEN**: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   face_field 包括**age,beauty,expression,faceshape,gender,glasses,landmark,race,quality,facetype信息**  <br> 逗号分隔. 默认只返回face_token、人脸框、概率和旋转角度
     *   max_face_num 最多处理人脸的数目，默认值为1，仅检测图片中面积最大的那个人脸；**最大值10**，检测图片中面积最大的几张人脸。
     *   face_type 人脸的类型 **LIVE**表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等**IDCARD**表示身份证芯片照：二代身份证内置芯片中的人像照片 **WATERMARK**表示带水印证件照：一般为带水印的小图，如公安网小图 **CERT**表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片 默认**LIVE**
     * @return JSONObject
     */
    public JSONObject detect(String image, String imageType, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("image", image);
        
        request.addBody("image_type", imageType);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.DETECT);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸搜索接口   
     *
     * @param image - 图片信息(**总数据大小应小于10M**)，图片上传方式根据image_type来判断
     * @param imageType - 图片类型 **BASE64**:图片的base64值，base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M；**URL**:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)**；FACE_TOKEN**: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个
     * @param groupIdList - 从指定的group中进行查找 用逗号分隔，**上限20个**
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   quality_control 图片质量控制  **NONE**: 不进行控制 **LOW**:较低的质量要求 **NORMAL**: 一般的质量要求 **HIGH**: 较高的质量要求 **默认 NONE**
     *   liveness_control 活体检测控制  **NONE**: 不进行控制 **LOW**:较低的活体要求(高通过率 低攻击拒绝率) **NORMAL**: 一般的活体要求(平衡的攻击拒绝率, 通过率) **HIGH**: 较高的活体要求(高攻击拒绝率 低通过率) **默认NONE**
     *   user_id 当需要对特定用户进行比对时，指定user_id进行比对。即人脸认证功能。
     *   max_user_num 查找后返回的用户数量。返回相似度最高的几个用户，默认为1，最多返回20个。
     * @return JSONObject
     */
    public JSONObject search(String image, String imageType, String groupIdList, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("image", image);
        
        request.addBody("image_type", imageType);
        
        request.addBody("group_id_list", groupIdList);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.SEARCH);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸注册接口   
     *
     * @param image - 图片信息(**总数据大小应小于10M**)，图片上传方式根据image_type来判断
     * @param imageType - 图片类型 **BASE64**:图片的base64值，base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M；**URL**:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)**；FACE_TOKEN**: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个
     * @param groupId - 用户组id（由数字、字母、下划线组成），长度限制128B
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   user_info 用户资料，长度限制256B
     *   quality_control 图片质量控制  **NONE**: 不进行控制 **LOW**:较低的质量要求 **NORMAL**: 一般的质量要求 **HIGH**: 较高的质量要求 **默认 NONE**
     *   liveness_control 活体检测控制  **NONE**: 不进行控制 **LOW**:较低的活体要求(高通过率 低攻击拒绝率) **NORMAL**: 一般的活体要求(平衡的攻击拒绝率, 通过率) **HIGH**: 较高的活体要求(高攻击拒绝率 低通过率) **默认NONE**
     * @return JSONObject
     */
    public JSONObject addUser(String image, String imageType, String groupId, String userId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("image", image);
        
        request.addBody("image_type", imageType);
        
        request.addBody("group_id", groupId);
        
        request.addBody("user_id", userId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.USER_ADD);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸更新接口   
     *
     * @param image - 图片信息(**总数据大小应小于10M**)，图片上传方式根据image_type来判断
     * @param imageType - 图片类型 **BASE64**:图片的base64值，base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M；**URL**:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)**；FACE_TOKEN**: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个
     * @param groupId - 更新指定groupid下uid对应的信息
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   user_info 用户资料，长度限制256B
     *   quality_control 图片质量控制  **NONE**: 不进行控制 **LOW**:较低的质量要求 **NORMAL**: 一般的质量要求 **HIGH**: 较高的质量要求 **默认 NONE**
     *   liveness_control 活体检测控制  **NONE**: 不进行控制 **LOW**:较低的活体要求(高通过率 低攻击拒绝率) **NORMAL**: 一般的活体要求(平衡的攻击拒绝率, 通过率) **HIGH**: 较高的活体要求(高攻击拒绝率 低通过率) **默认NONE**
     * @return JSONObject
     */
    public JSONObject updateUser(String image, String imageType, String groupId, String userId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("image", image);
        
        request.addBody("image_type", imageType);
        
        request.addBody("group_id", groupId);
        
        request.addBody("user_id", userId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.USER_UPDATE);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人脸删除接口   
     *
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param groupId - 用户组id（由数字、字母、下划线组成），长度限制128B
     * @param faceToken - 需要删除的人脸图片token，（由数字、字母、下划线组成）长度限制64B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject faceDelete(String userId, String groupId, String faceToken, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("user_id", userId);
        
        request.addBody("group_id", groupId);
        
        request.addBody("face_token", faceToken);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.FACE_DELETE);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 用户信息查询接口   
     *
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param groupId - 用户组id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject getUser(String userId, String groupId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("user_id", userId);
        
        request.addBody("group_id", groupId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.USER_GET);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 获取用户人脸列表接口   
     *
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param groupId - 用户组id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject faceGetlist(String userId, String groupId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("user_id", userId);
        
        request.addBody("group_id", groupId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.FACE_GETLIST);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 获取用户列表接口   
     *
     * @param groupId - 用户组id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   start 默认值0，起始序号
     *   length 返回数量，默认值100，最大值1000
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
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 复制用户接口   
     *
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   src_group_id 从指定组里复制信息
     *   dst_group_id 需要添加用户的组id
     * @return JSONObject
     */
    public JSONObject userCopy(String userId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("user_id", userId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.USER_COPY);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 删除用户接口   
     *
     * @param groupId - 用户组id（由数字、字母、下划线组成），长度限制128B
     * @param userId - 用户id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject deleteUser(String groupId, String userId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("group_id", groupId);
        
        request.addBody("user_id", userId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.USER_DELETE);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 创建用户组接口   
     *
     * @param groupId - 用户组id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject groupAdd(String groupId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("group_id", groupId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.GROUP_ADD);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 删除用户组接口   
     *
     * @param groupId - 用户组id（由数字、字母、下划线组成），长度限制128B
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject groupDelete(String groupId, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("group_id", groupId);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.GROUP_DELETE);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 组列表查询接口   
     *
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   start 默认值0，起始序号
     *   length 返回数量，默认值100，最大值1000
     * @return JSONObject
     */
    public JSONObject getGroupList(HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.GROUP_GETLIST);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 身份验证接口   
     *
     * @param image - 图片信息(**总数据大小应小于10M**)，图片上传方式根据image_type来判断
     * @param imageType - 图片类型 **BASE64**:图片的base64值，base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M；**URL**:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)**；FACE_TOKEN**: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个
     * @param idCardNumber - 身份证号（真实身份证号号码）
     * @param name - utf8，姓名（真实姓名，和身份证号匹配）
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   quality_control 图片质量控制  **NONE**: 不进行控制 **LOW**:较低的质量要求 **NORMAL**: 一般的质量要求 **HIGH**: 较高的质量要求 **默认 NONE**
     *   liveness_control 活体检测控制  **NONE**: 不进行控制 **LOW**:较低的活体要求(高通过率 低攻击拒绝率) **NORMAL**: 一般的活体要求(平衡的攻击拒绝率, 通过率) **HIGH**: 较高的活体要求(高攻击拒绝率 低通过率) **默认NONE**
     * @return JSONObject
     */
    public JSONObject personVerify(String image, String imageType, String idCardNumber, String name, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("image", image);
        
        request.addBody("image_type", imageType);
        
        request.addBody("id_card_number", idCardNumber);
        
        request.addBody("name", name);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.PERSON_VERIFY);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 语音校验码接口接口   
     *
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   appid 百度云创建应用时的唯一标识ID
     * @return JSONObject
     */
    public JSONObject videoSessioncode(HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.VIDEO_SESSIONCODE);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 视频活体检测接口接口
     *
     * @param sessionId - 语音校验码会话id，使用此接口的前提是已经调用了语音校验码接口
     * @param video - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject videoFaceliveness(String sessionId, byte[] video, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);

        request.addBody("session_id", sessionId);

        String base64Content = Base64Util.encode(video);
        request.addBody("video_base64", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.VIDEO_FACELIVENESS);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 视频活体检测接口接口
     *
     * @param sessionId - 语音校验码会话id，使用此接口的前提是已经调用了语音校验码接口
     * @param video - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject videoFaceliveness(String sessionId, String video, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(video);
            return videoFaceliveness(sessionId, data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }


    /**
     * 人脸对比接口
     * 两张人脸图片相似度对比：比对两张图片中人脸的相似度，并返回相似度分值
     *
     * @param input - 请求参数array
     *              image: 必须，图片信息(**总数据大小应小于10M**)，图片上传方式根据image_type来判断
     *              imageType: 必须，图片类型 **BASE64**:图片的base64值，base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M；**URL**:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)**；FACE_TOKEN**: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个
     *              faceType: 可选，人脸的类型 LIVE表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等 IDCARD表示身份证芯片照：二代身份证内置芯片中的人像照片 WATERMARK表示带水印证件照：一般为带水印的小图，如公安网小图 CERT表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片 默认LIVE
     *              qualityControl: 可选，质量控制 NONE: 不进行控制 LOW:较低的质量要求 NORMAL: 一般的质量要求 HIGH: 较高的质量要求 默认NONE
     *              livenessControl: 可选，活体控制 NONE: 不进行控制 LOW:较低的活体要求(高通过率 低攻击拒绝率) NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率) HIGH: 较高的活体要求(高攻击拒绝率 低通过率) 默认NONE
     * @return JSONObject
     */
    public JSONObject match(List<MatchRequest> input) {
        AipRequest request = new AipRequest();

        preOperation(request);
        JSONArray arr = new JSONArray();
        for (MatchRequest req : input) {
            arr.put(req.toJsonObject());
        }
        request.addBody("body", arr.toString());
        request.setBodyFormat(EBodyFormat.RAW_JSON_ARRAY);
        request.setUri(FaceConsts.MATCH);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 在线活体检测接口
     *
     *
     * @param input - 请求参数array
     *              image: 必须，图片信息(**总数据大小应小于10M**)，图片上传方式根据image_type来判断
     *              imageType: 必须 图片类型 **BASE64**:图片的base64值，base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M；**URL**:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)**；FACE_TOKEN**: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个
     *              face_field：可选 包括age,beauty,expression,faceshape,gender,glasses,landmark,race,quality,facetype,parsing信息，逗号分隔，默认只返回face_token、活体数、人脸框、概率和旋转角度。
     * @return JSONObject
     */
    public JSONObject faceverify(List<FaceVerifyRequest> input) {
        AipRequest request = new AipRequest();

        preOperation(request);
        JSONArray arr = new JSONArray();
        for (FaceVerifyRequest req : input) {
            arr.put(req.toJsonObject());
        }
        request.addBody("body", arr.toString());
        request.setBodyFormat(EBodyFormat.RAW_JSON_ARRAY);
        request.setUri(FaceConsts.FACEVERIFY);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 身份证与名字比对接口
     */
    public JSONObject idMatch(String idCardNum, String name, HashMap<String, Object> options) {
        AipRequest request = new AipRequest();

        preOperation(request);
        request.addBody("id_card_number", idCardNum);
        request.addBody("name", name);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(FaceConsts.ID_MATCH);
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        postOperation(request);
        return requestServer(request);

    }

}