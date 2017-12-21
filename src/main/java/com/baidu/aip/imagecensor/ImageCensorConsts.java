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

import java.util.Arrays;
import java.util.HashSet;

public class ImageCensorConsts {

    static final String ANTI_PORN_URL = "https://aip.baidubce.com/rest/2.0/antiporn/v1/detect";
    static final String ANTI_PORN_GIF_URL =
            "https://aip.baidubce.com/rest/2.0/antiporn/v1/detect_gif";
    static final String ANTI_TERROR_URL = "https://aip.baidubce.com/rest/2.0/antiterror/v1/detect";

    static final String IMAGE_CENSOR_COMB_URL = "https://aip.baidubce.com/api/v1/solution/direct/img_censor";
    static final String FACE_AUDIT_URL = "https://aip.baidubce.com/rest/2.0/solution/v1/face_audit";

    static final String REPORT_URL = "https://aip.baidubce.com/rpc/2.0/feedback/v1/report";

    static final String USER_DEFINED_URL = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/user_defined";

    public static final Long ANTIPORN_MAX_IMAGE_SIZE = 4194304L; // 4 * 1024 * 1024

    public static final Integer ANTIPORN_MIN_IMAGE_SIDE_LENGTH = 10;
    public static final Integer ANTIPORN_MAX_IMAGE_SIDE_LENGTH = 2048;

    public static final HashSet<String> ANTIPORN_SUPPORT_IMAGE_FORMAT =
            new HashSet<String>(Arrays.asList("JPEG", "png", "bmp", "gif"));
}
