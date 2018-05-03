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

import org.json.JSONObject;

public class MatchRequest {

    private String image;
    private String imageType;
    private String faceType;
    private String qualityControl;
    private String livenessControl;

    public MatchRequest(String image, String imageType) {
        this.image = image;
        this.imageType = imageType;
        this.faceType = null;
        this.qualityControl = null;
        this.livenessControl = null;
    }

    public MatchRequest(String image, String imageType, String faceType,
                        String qualityControl, String livenessControl) {
        this.image = image;
        this.imageType = imageType;
        this.faceType = faceType;
        this.qualityControl = qualityControl;
        this.livenessControl = livenessControl;
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        obj.put("image", this.image);
        obj.put("image_type", this.imageType);
        if (this.faceType != null) {
            obj.put("face_type", this.faceType);
        }
        if (this.qualityControl != null) {
            obj.put("quality_control", this.qualityControl);
        }
        if (this.livenessControl != null) {
            obj.put("liveness_control", this.livenessControl);
        }
        return obj;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getFaceType() {
        return faceType;
    }

    public void setFaceType(String faceType) {
        this.faceType = faceType;
    }

    public String getQualityControl() {
        return qualityControl;
    }

    public void setQualityControl(String qualityControl) {
        this.qualityControl = qualityControl;
    }

    public String getLivenessControl() {
        return livenessControl;
    }

    public void setLivenessControl(String livenessControl) {
        this.livenessControl = livenessControl;
    }
}
