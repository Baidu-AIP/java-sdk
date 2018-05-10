package com.baidu.aip.face;

import org.json.JSONObject;

public class FaceVerifyRequest {
    private String image;
    private String imageType;
    private String faceField;

    public FaceVerifyRequest(String image, String imageType) {
        this.image = image;
        this.imageType = imageType;
        this.faceField = null;
    }

    public FaceVerifyRequest(String image, String imageType, String faceField) {
        this.image = image;
        this.imageType = imageType;
        this.faceField = faceField;
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        obj.put("image", this.image);
        obj.put("image_type", this.imageType);
        if (this.faceField != null) {
            obj.put("face_field", this.faceField);
        }
        return obj;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public String getFaceField() {
        return faceField;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setFaceField(String faceField) {
        this.faceField = faceField;
    }
}
