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
package com.baidu.aip.ocr;

import java.util.Arrays;
import java.util.HashSet;

public class OcrConsts {

    static final String OCR_IDCARD_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";

    static final String OCR_BANDCARD_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/bankcard";

    static final String OCR_GENERAL_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/general";

    static final String OCR_BASIC_GENERAL_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";

    static final String OCR_WEB_IMAGE_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage";

    static final String OCR_ENHANCED_GENERAL_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/general_enhanced";

    static final String DRIVING_LICENSE_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/driving_license";

    static final String VEHICLE_LICENSE_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/vehicle_license";

    static final String OCR_TABLE_URL =
            "https://aip.baidubce.com/rest/2.0/solution/v1/form_ocr/request";

    static final String OCR_TABLE_RESULT_URL =
            "https://aip.baidubce.com/rest/2.0/solution/v1/form_ocr/get_request_result";

    static final String OCR_LICENSE_PLATE_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";

    static final String OCR_RECEIPT_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/receipt";

    static final String OCR_ACCURATE_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate";

    static final String OCR_BASIC_ACCURATE_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";

    static final String OCR_BUSINESS_LICENSE_URL =
            "https://aip.baidubce.com/rest/2.0/ocr/v1/business_license";

    static final Long OCR_MAX_IMAGE_SIZE = 4194304L; // 4 * 1024 * 1024

    static final Integer OCR_MIN_IMAGE_SIDE_LENGTH = 15;
    static final Integer OCR_MAX_IMAGE_SIDE_LENGTH = 4096;

    static final HashSet<String> OCR_SUPPORT_IMAGE_FORMAT =
            new HashSet<String>(Arrays.asList("JPEG", "png", "bmp"));


    static final int ASYNC_TASK_STATUS_FINISHED = 3;
}
