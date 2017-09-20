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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AipFace extends BaseClient {

    public AipFace(String appId, String aipKey, String aipToken) {
        super(appId, aipKey, aipToken);
    }

    /**
     *
     * @param imgPath 图片文件路径
     * @param options 识别接口可选参数
     * @return Json result
     */
    public JSONObject detect(String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return detect(imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     *
     * @param imgData 图片文件内容
     * @param options 识别接口可选参数
     * @return Json result
     */
    public JSONObject detect(byte[] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        // add API params
        String base64Content = Base64Util.encode(imgData);
        if (base64Content.length() > FaceConsts.FACE_DETECT_MAX_IMAGE_SIZE) {
            return AipError.IMAGE_SIZE_ERROR.toJsonResult();
        }
        request.addBody("image", base64Content);
        request.addBody(options);
        request.setUri(FaceConsts.FACE_DETECT_URL);
        postOperation(request);

        return requestServer(request);

    }

    /**
     * 比较多张人脸图片的相似度
     * @param imgPaths 图片路径集合
     * @param options 可选参数：
     *                ext_fields: 返回质量信息，取值固定: 如 qualities(质量检测)
     *                image_liveness: 返回的活体信息，逗号分割，
     *                      ",faceliveness"表示对第一张图片不做活体检测、第二张图做活体检测。
     * @return 服务器返回数据
     */
    public JSONObject match(List<String> imgPaths, HashMap<String, String> options) {
        try {
            byte[][] imgData = new byte[imgPaths.size()][];
            int idx = 0;
            for (String path : imgPaths) {
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
     * 比较多张人脸图片的相似度
     * @param imgData 图片二进制数据集合
     * @param options 可选参数：
     *                ext_fields: 返回质量信息，取值固定: 如 qualities(质量检测)
     *                image_liveness: 返回的活体信息，逗号分割，
     *                      ",faceliveness"表示对第一张图片不做活体检测、第二张图做活体检测。
     * @return 服务器返回数据
     */
    public JSONObject match(byte[][] imgData, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        ArrayList<String> buffer = new ArrayList<String>();
        for (byte[] data : imgData) {
            String base64Str = Base64Util.encode(data);
            buffer.add(base64Str);
        }
        String imgDataAll = Util.mkString(buffer.iterator(), ',');
        if (imgDataAll.length() > FaceConsts.FACE_MATCH_MAX_IMAGE_SIZE) {
            return AipError.IMAGE_SIZE_ERROR.toJsonResult();
        }
        preOperation(request);
        request.addBody("images", imgDataAll);
        if (options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(FaceConsts.FACE_MATCH_URL);
        postOperation(request);

        return requestServer(request);
    }

    /**
     *
     * @param uid 需要增加的用户id，组成为字母/数字/下划线，长度不超过128B
     * @param userInfo 用户个人信息，长度不超过256B
     * @param groupId 用户所在组id, 组成为字母/数字/下划线，长度不超过128B
     * @param imgPath 用户图像本地路径
     * @param options 可选参数
     * @return json对象，包含本次请求的logid
     */
    public JSONObject addUser(String uid, String userInfo, List<String> groupId,
                              String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return addUser(uid, userInfo, groupId, imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     *
     * @param uid 需要增加的用户id，组成为字母/数字/下划线，长度不超过128B
     * @param userInfo 用户个人信息，长度不超过256B
     * @param groupId 用户所在组id, 组成为字母/数字/下划线，长度不超过128B
     * @param imgData 用户图像二进制数据
     * @param options 可选参数
     * @return json对象，包含本次请求的logid
     */
    public JSONObject addUser(String uid, String userInfo, List<String> groupId,
                              byte[] imgData,  HashMap<String, String> options) {
        AipError checkGid = checkGroupId(groupId);
        if (!checkGid.equals(AipError.SUCCESS)) {
            return checkGid.toJsonResult();
        }

        AipRequest request = new AipRequest();
        String base64Str = Base64Util.encode(imgData);

        preOperation(request);
        request.addBody("uid", uid);
        request.addBody("user_info", userInfo);
        request.addBody("group_id", Util.mkString(groupId.iterator(), ','));
        request.addBody("image", base64Str);
        if (options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(FaceConsts.FACE_SEARCH_FACESET_USER_ADD_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 从人脸库更新用户图像
     * @param uid uid
     * @param userInfo 用户信息
     * @param groupId group_id
     * @param imgPath 图片路径
     * @param options 可选参数，包括:
     *                "user_info": 如果有的话则替换该字段，没有则忽略
     *                "action_type": 如果为replace时，则uid不存在时，不报错，会自动注册。
     *                               不存在该参数时，如果uid不存在会提示错误
     * @return 服务器返回值
     */
    public JSONObject updateUser(String uid, String userInfo, String groupId,
                                 String imgPath, HashMap<String, String> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return updateUser(uid, userInfo, groupId, imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 从人脸库更新用户图像
     * @param uid uid
     * @param userInfo 用户信息
     * @param groupId group_id
     * @param imgData 图片二进制数据
     * @param options 可选参数，包括:
     *                "user_info": 如果有的话则替换该字段，没有则忽略
     *                "action_type": 如果为replace时，则uid不存在时，不报错，会自动注册。
     *                               不存在该参数时，如果uid不存在会提示错误
     * @return 服务器返回值
     */
    public JSONObject updateUser(String uid, String userInfo, String groupId,
                                 byte[] imgData, HashMap<String, String> options) {
        AipError ret = checkSingleGroupId(groupId);
        if (ret != AipError.SUCCESS) {
            return ret.toJsonResult();
        }

        AipRequest request = new AipRequest();

        String base64Str = Base64Util.encode(imgData);

        preOperation(request);
        request.addBody("uid", uid);
        request.addBody("image", base64Str);
        request.addBody("group_id", groupId);
        request.addBody("user_info", userInfo);
        if (options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }

        request.setUri(FaceConsts.FACE_SEARCH_FACESET_USER_UPDATE_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);

    }

    /**
     * 从库中整体删除该uid数据
     * @param uid uid
     * @return 服务器返回数据
     */
    public JSONObject deleteUser(String uid) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("uid", uid);
        request.setUri(FaceConsts.FACE_SEARCH_FACESET_USER_DELETE_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 从库中删除指定groupId下对应uid的数据
     * @param uid uid
     * @param groupId groupId
     * @return 服务器返回数据
     */
    public JSONObject deleteUser(String uid, List<String> groupId) {
        AipError ret = checkGroupId(groupId);
        if (ret != AipError.SUCCESS) {
            return ret.toJsonResult();
        }
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("uid", uid);
        request.addBody("group_id", Util.mkString(groupId.iterator(), ','));
        request.setUri(FaceConsts.FACE_SEARCH_FACESET_USER_DELETE_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 从指定用户组中认证用户
     * @param uid uid
     * @param groupId 需要查找的用户组列表
     * @param imgPath 图片路径
     * @param options 可选参数，包括：
     *                top_num: 返回匹配得分top数，默认为1
     *                ext_fields: 特殊返回信息，多个用逗号分隔,取值固定: 如 faceliveness(活体检测)
     * @return 服务器返回数据
     */
    public JSONObject verifyUser(String uid, List<String> groupId,
                                 String imgPath, HashMap<String, Object> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return verifyUser(uid, groupId, imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 从指定用户组中认证用户
     * @param uid uid
     * @param groupId 需要查找的用户组列表
     * @param imgData 图片二进制数据
     * @param options 可选参数，包括：
     *                top_num: 返回匹配得分top数，默认为1
     *                ext_fields: 特殊返回信息，多个用逗号分隔,取值固定: 如 faceliveness(活体检测)
     * @return 服务器返回数据
     */
    public JSONObject verifyUser(String uid, List<String> groupId,
                                 byte[] imgData, HashMap<String, Object> options) {
        AipError checkGid = checkGroupId(groupId);
        if (!checkGid.equals(AipError.SUCCESS)) {
            return checkGid.toJsonResult();
        }
        AipRequest request = new AipRequest();
        String base64Str = Base64Util.encode(imgData);

        preOperation(request);
        request.addBody("uid", uid);
        request.addBody("image", base64Str);
        request.addBody("group_id", Util.mkString(groupId.iterator(), ','));
        if (options != null) {
            for (Map.Entry<String, Object> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(FaceConsts.FACE_SEARCH_VERIFY_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 人脸识别（1：N）接口
     * @param groupId 待查找的groupId列表
     * @param imgPath 图片路径
     * @param options 可选参数：
     *                user_top_num: 返回识别结果top数，默认为1 （最多返回5个）
     *                ext_fields: 特殊返回信息，多个用逗号分隔,取值固定: 如 faceliveness(活体检测)
     * @return 服务器返回数据
     */
    public JSONObject identifyUser(List<String> groupId, String imgPath, HashMap<String, Object> options) {
        try {
            byte[] imgData = Util.readFileByBytes(imgPath);
            return identifyUser(groupId, imgData, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人脸识别（1：N）接口
     * @param groupId 待查找的groupId列表
     * @param imgData 图片二进制数据
     * @param options 可选参数：
     *                user_top_num: 返回识别结果top数，默认为1 （最多返回5个）
     *                ext_fields: 特殊返回信息，多个用逗号分隔,取值固定: 如 faceliveness(活体检测)
     * @return 服务器返回数据
     */
    public JSONObject identifyUser(List<String> groupId, byte[] imgData, HashMap<String, Object> options) {
        AipError checkGid = checkGroupId(groupId);
        if (!checkGid.equals(AipError.SUCCESS)) {
            return checkGid.toJsonResult();
        }

        AipRequest request = new AipRequest();
        String base64Str = Base64Util.encode(imgData);

        preOperation(request);
        request.addBody("group_id", Util.mkString(groupId.iterator(), ','));
        request.addBody("image", base64Str);
        if (options != null) {
            for (Map.Entry<String, Object> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(FaceConsts.FACE_SEARCH_IDENTIFY_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 用户信息查询接口
     * @param uid uid
     * @return 用户信息
     */
    public JSONObject getUser(String uid) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("uid", uid);
        request.setUri(FaceConsts.FACE_SEARCH_FACESET_USER_GET_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 用户信息查询接口
     * @param uid uid
     * @param groupId 组id
     * @return 用户信息
     */
    public JSONObject getUser(String uid, List<String> groupId) {
        AipError ret = checkGroupId(groupId);
        if (ret != AipError.SUCCESS) {
            return ret.toJsonResult();
        }

        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("uid", uid);
        request.addBody("group_id", Util.mkString(groupId.iterator(), ','));
        request.setUri(FaceConsts.FACE_SEARCH_FACESET_USER_GET_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 组列表查询接口
     * @param options 可选参数
     * @return 组列表数据
     */
    public JSONObject getGroupList(HashMap<String, Object> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        for (Map.Entry<String, Object> entry : options.entrySet()) {
            request.addBody(entry.getKey(), entry.getValue());
        }
        request.setUri(FaceConsts.FACE_SEARCH_FACESET_GROUP_GET_LIST_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 组内用户列表查询接口
     * @param groupId 组id
     * @param options 可选参数
     * @return 用户列表数据
     */
    public JSONObject getGroupUsers(String groupId, HashMap<String, Object> options) {
        AipError checkGid = checkSingleGroupId(groupId);
        if (!checkGid.equals(AipError.SUCCESS)) {
            return checkGid.toJsonResult();
        }

        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("group_id", groupId);
        for (Map.Entry<String, Object> entry : options.entrySet()) {
            request.addBody(entry.getKey(), entry.getValue());
        }
        request.setUri(FaceConsts.FACE_SEARCH_FACESET_GROUP_GET_USERS_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 从指定group内复制用户信息，建议谨慎使用
     * @param srcGroupId 源groupId
     * @param dstGroupId 要复制到的group列表
     * @param uid 待复制的uid
     * @return 服务器返回数据
     */
    public JSONObject addGroupUser(String srcGroupId, List<String> dstGroupId, String uid) {
        AipError checkGid = checkSingleGroupId(srcGroupId);
        if (!checkGid.equals(AipError.SUCCESS)) {
            return checkGid.toJsonResult();
        }

        checkGid = checkGroupId(dstGroupId);
        if (!checkGid.equals(AipError.SUCCESS)) {
            return checkGid.toJsonResult();
        }

        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("group_id", Util.mkString(dstGroupId.iterator(), ','));
        request.addBody("uid", uid);
        request.addBody("src_group_id", srcGroupId);
        request.setUri(FaceConsts.FACE_SEARCH_FACESET_GROUP_ADD_USER_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
        postOperation(request);

        return requestServer(request);
    }

    /**
     * 从指定组删除用户信息
     * @param groupId 需要删除该uid的组列表
     * @param uid 待删除的uid
     * @return 服务器返回数据
     */
    public JSONObject deleteGroupUser(List<String> groupId, String uid) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("group_id", Util.mkString(groupId.iterator(), ','));
        request.addBody("uid", uid);
        request.setUri(FaceConsts.FACE_SEARCH_FACESET_GROUP_DELETE_USER_URL);
        // check params
        AipError checkRet = checkSearchParams(request.getBody());
        if (!checkRet.equals(AipError.SUCCESS)) {
            return checkRet.toJsonResult();
        }
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

    private AipError checkSearchParams(HashMap<String, Object> body) {
        // image size
        if (body.containsKey("image")) {
            int imgSize = ((String) body.get("image")).length();
            if (imgSize > FaceConsts.FACE_SEARCH_MAX_IMAGE_SIZE) {
                return AipError.IMAGE_SIZE_ERROR;
            }
        }
        // uid
        if (body.containsKey("uid")) {
            String uid = (String) body.get("uid");
            if (uid.length() > FaceConsts.FACE_SEARCH_MAX_UID_SIZE) {
                return AipError.UID_SIZE_ERROR;
            }
            if (!Util.isLiteral(uid)) {
                return AipError.UID_FORMAT_ERROR;
            }
        }
        // user_info
        if (body.containsKey("user_info")) {
            int size = ((String) body.get("user_info")).length();
            if (size > FaceConsts.FACE_SEARCH_MAX_USER_INFO_SIZE) {
                return AipError.USER_INFO_SIZE_ERROR;
            }
        }

        if (body.containsKey("group_id")) {
            String groupId = (String) body.get("group_id");
            for (String gid : groupId.split(",")) {
                if (gid.length() > FaceConsts.FACE_SEARCH_MAX_GROUP_ID_SIZE) {
                    return AipError.GROUP_ID_SIZE_ERROR;
                }
                if (!Util.isLiteral(gid)) {
                    return AipError.GROUP_ID_FORMAT_ERROR;
                }
            }
        }
        return AipError.SUCCESS;
    }

    private AipError checkGroupId(List<String> groupId) {
        for (String gid : groupId) {
            AipError ret = checkSingleGroupId(gid);
            if (ret != AipError.SUCCESS) {
                return ret;
            }
        }
        return AipError.SUCCESS;
    }

    private AipError checkSingleGroupId(String groupId) {
        if (groupId.length() > FaceConsts.FACE_SEARCH_MAX_GROUP_ID_SIZE) {
            return AipError.GROUP_ID_SIZE_ERROR;
        }
        if (!Util.isLiteral(groupId)) {
            return AipError.GROUP_ID_FORMAT_ERROR;
        }
        return AipError.SUCCESS;
    }

}
