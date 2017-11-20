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

public class FaceConsts {

    static final String FACE_DETECT_URL = "https://aip.baidubce.com/rest/2.0/face/v2/detect";
    static final String FACE_MATCH_URL = "https://aip.baidubce.com/rest/2.0/face/v2/match";
    static final String FACE_SEARCH_FACESET_USER_ADD_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
    static final String FACE_SEARCH_FACESET_USER_UPDATE_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update";
    static final String FACE_SEARCH_FACESET_USER_DELETE_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
    static final String FACE_SEARCH_VERIFY_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/verify";
    static final String FACE_SEARCH_IDENTIFY_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/identify";
    static final String FACE_SEARCH_FACESET_USER_GET_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/get";
    static final String FACE_SEARCH_FACESET_GROUP_GET_LIST_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getlist";
    static final String FACE_SEARCH_FACESET_GROUP_GET_USERS_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getusers";
    static final String FACE_SEARCH_FACESET_GROUP_ADD_USER_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/adduser";
    static final String FACE_SEARCH_FACESET_GROUP_DELETE_USER_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/deleteuser";

    static final String FACE_LIVENESS_VERIFY_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceverify";

    static final Long FACE_DETECT_MAX_IMAGE_SIZE = 2097152L; // 2 * 1024 * 1024
    static final Long FACE_MATCH_MAX_IMAGE_SIZE = 20971520L; // 20 * 1024 * 1024
    static final Long FACE_SEARCH_MAX_IMAGE_SIZE = 20971520L; // 20 * 1024 * 1024

    static final Integer FACE_SEARCH_MAX_UID_SIZE = 128;
    static final Integer FACE_SEARCH_MAX_USER_INFO_SIZE = 256;
    static final Integer FACE_SEARCH_MAX_GROUP_ID_SIZE = 48;
}
