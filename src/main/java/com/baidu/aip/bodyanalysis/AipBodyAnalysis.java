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

package com.baidu.aip.bodyanalysis;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.error.AipError;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class AipBodyAnalysis extends BaseClient {

    public AipBodyAnalysis(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * 人体关键点识别接口   
     * 对于输入的一张图片（可正常解码，且长宽比适宜），检测图片中的所有人体，**输出每个人体的14个关键点**，包含四肢、脖颈、鼻子等部位，**以及人体的坐标信息和数量**。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject bodyAnalysis(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(BodyAnalysisConsts.BODY_ANALYSIS);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人体关键点识别接口
     * 对于输入的一张图片（可正常解码，且长宽比适宜），检测图片中的所有人体，**输出每个人体的14个关键点**，包含四肢、脖颈、鼻子等部位，**以及人体的坐标信息和数量**。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject bodyAnalysis(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return bodyAnalysis(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人体属性识别接口   
     * 对于输入的一张图片（可正常解码，且长宽比适宜），**检测图像中的所有人体并返回每个人体的矩形框位置，识别人体的静态属性和行为，共支持14种属性，包括：性别、年龄阶段、衣着（含类别/颜色）、是否戴帽子、是否戴眼镜、是否背包、是否使用手机、身体朝向等**。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   type gender,<br>age,<br>lower_wear,<br>upper_wear,<br>headwear,<br>glasses,<br>upper_color,<br>lower_color,<br>cellphone,<br>upper_wear_fg,<br>upper_wear_texture,<br>lower_wear_texture,<br>orientation,<br>umbrella or 1）可选值说明：<br>gender-性别，age-年龄阶段，lower_wear-下身服饰，upper_wear-上身服饰，headwear-是否戴帽子，glasses-是否戴眼镜，upper_color-上身服饰颜色，lower_color-下身服饰颜色，cellphone-是否使用手机，upper_wear_fg-上身服饰细分类，upper_wear_texture-上身服饰纹理，lower_wear_texture-下身服饰纹理，orientation-身体朝向，umbrella-是否撑伞；<br>2）type 参数值可以是可选值的组合，用逗号分隔；**如果无此参数默认输出全部14个属性**
     * @return JSONObject
     */
    public JSONObject bodyAttr(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(BodyAnalysisConsts.BODY_ATTR);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人体属性识别接口
     * 对于输入的一张图片（可正常解码，且长宽比适宜），**检测图像中的所有人体并返回每个人体的矩形框位置，识别人体的静态属性和行为，共支持14种属性，包括：性别、年龄阶段、衣着（含类别/颜色）、是否戴帽子、是否戴眼镜、是否背包、是否使用手机、身体朝向等**。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   type gender,<br>age,<br>lower_wear,<br>upper_wear,<br>headwear,<br>glasses,<br>upper_color,<br>lower_color,<br>cellphone,<br>upper_wear_fg,<br>upper_wear_texture,<br>lower_wear_texture,<br>orientation,<br>umbrella or 1）可选值说明：<br>gender-性别，age-年龄阶段，lower_wear-下身服饰，upper_wear-上身服饰，headwear-是否戴帽子，glasses-是否戴眼镜，upper_color-上身服饰颜色，lower_color-下身服饰颜色，cellphone-是否使用手机，upper_wear_fg-上身服饰细分类，upper_wear_texture-上身服饰纹理，lower_wear_texture-下身服饰纹理，orientation-身体朝向，umbrella-是否撑伞；<br>2）type 参数值可以是可选值的组合，用逗号分隔；**如果无此参数默认输出全部14个属性**
     * @return JSONObject
     */
    public JSONObject bodyAttr(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return bodyAttr(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人流量统计接口   
     * 对于输入的一张图片（可正常解码，且长宽比适宜），**识别和统计图像中的人体个数**，以俯拍角度为主要识别视角，**支持特定框选区域的人数统计，并可以输出渲染图片（人体头顶热力图）**。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   area 特定框选区域坐标，逗号分隔，如‘x1,y1,x2,y2,x3,y3...xn,yn'，默认尾点和首点相连做闭合，**此参数为空或无此参数默认识别整个图片的人数**
     *   show 是否输出渲染的图片，默认不返回，**选true时返回渲染后的图片(base64)**，其它无效值或为空则默认false
     * @return JSONObject
     */
    public JSONObject bodyNum(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(BodyAnalysisConsts.BODY_NUM);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人流量统计接口
     * 对于输入的一张图片（可正常解码，且长宽比适宜），**识别和统计图像中的人体个数**，以俯拍角度为主要识别视角，**支持特定框选区域的人数统计，并可以输出渲染图片（人体头顶热力图）**。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   area 特定框选区域坐标，逗号分隔，如‘x1,y1,x2,y2,x3,y3...xn,yn'，默认尾点和首点相连做闭合，**此参数为空或无此参数默认识别整个图片的人数**
     *   show 是否输出渲染的图片，默认不返回，**选true时返回渲染后的图片(base64)**，其它无效值或为空则默认false
     * @return JSONObject
     */
    public JSONObject bodyNum(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return bodyNum(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 手势识别接口   
     * 识别图片中的手势类型，返回手势名称、手势矩形框、概率分数，可识别22种手势，支持动态手势识别，适用于手势特效、智能家居手势交互等场景；支持的22类手势列表：手指、掌心向前、拳头、OK、祈祷、作揖、作别、单手比心、点赞、diss、rock、掌心向上、双手比心（3种）、数字（7种）。
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject gesture(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(BodyAnalysisConsts.GESTURE);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 手势识别接口
     * 识别图片中的手势类型，返回手势名称、手势矩形框、概率分数，可识别22种手势，支持动态手势识别，适用于手势特效、智能家居手势交互等场景；支持的22类手势列表：手指、掌心向前、拳头、OK、祈祷、作揖、作别、单手比心、点赞、diss、rock、掌心向上、双手比心（3种）、数字（7种）。
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject gesture(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return gesture(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    /**
     * 人像分割接口   
     * 对于输入的一张图片（可正常解码，且长宽比适宜），**识别人体的轮廓范围，与背景进行分离，适用于拍照背景替换、照片合成、身体特效等场景。输入正常人像图片，返回分割后的二值结果图和分割类型（目前仅支持person）。**
     *
     * @param image - 二进制图像数据
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject bodySeg(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(BodyAnalysisConsts.BODY_SEG);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 人像分割接口
     * 对于输入的一张图片（可正常解码，且长宽比适宜），**识别人体的轮廓范围，与背景进行分离，适用于拍照背景替换、照片合成、身体特效等场景。输入正常人像图片，返回分割后的二值结果图和分割类型（目前仅支持person）。**
     *
     * @param image - 本地图片路径
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject bodySeg(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return bodySeg(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    /**
     * 针对人像分割接口，将返回的二值图转化为灰度图,存储为jpg格式
     * @param labelmapBase64 人像分割接口返回的二值图base64
     * @param realWidth 图片原始宽度
     * @param realHeight 图片原始高度
     * @param outPath 灰度图输出路径
     */
    public static void convert(String labelmapBase64, int realWidth, int realHeight, String outPath) {
        try {

            byte[] bytes = Base64Util.decode(labelmapBase64);
            InputStream is = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(is);
            BufferedImage newImage = resize(image, realWidth, realHeight);
            BufferedImage grayImage = new BufferedImage(realWidth, realHeight, BufferedImage.TYPE_BYTE_GRAY);
            for (int i = 0 ; i < realWidth ; i++) {
                for (int j = 0 ; j < realHeight; j++) {
                    int rgb = newImage.getRGB(i, j);
                    grayImage.setRGB(i, j, rgb * 255);  // 将像素存入缓冲区
                }
            }
            File newFile = new File(outPath);
            ImageIO.write(grayImage, "jpg", newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}