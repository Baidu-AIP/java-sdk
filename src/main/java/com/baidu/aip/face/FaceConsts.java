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

    static final String DETECT =
            "https://aip.baidubce.com/rest/2.0/face/v2/detect";

    static final String MATCH =
            "https://aip.baidubce.com/rest/2.0/face/v2/match";

    static final String IDENTIFY =
            "https://aip.baidubce.com/rest/2.0/face/v2/identify";

    static final String VERIFY =
            "https://aip.baidubce.com/rest/2.0/face/v2/verify";

    static final String MULTI_IDENTIFY =
            "https://aip.baidubce.com/rest/2.0/face/v2/multi-identify";

    static final String USER_ADD =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";

    static final String USER_UPDATE =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update";

    static final String USER_DELETE =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";

    static final String USER_GET =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/get";

    static final String GROUP_GETLIST =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getlist";

    static final String GROUP_GETUSERS =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getusers";

    static final String GROUP_ADDUSER =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/adduser";

    static final String GROUP_DELETEUSER =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/deleteuser";

    static final String FACE_LIVENESS_VERIFY_URL =
            "https://aip.baidubce.com/rest/2.0/face/v2/faceverify";
}