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
package com.baidu.aip.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class ImageUtil {

    public static String getImageFormatByFile(String filePath) {
        ImageInputStream input = null;
        try {
            input = ImageIO.createImageInputStream(new File(filePath));
            return getImageFormat(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "unknown";
    }

    public static String getImageFormatByBytes(byte[] content) {
        ImageInputStream input = null;
        try {
            input = new MemoryCacheImageInputStream(new ByteArrayInputStream(content));
            return getImageFormat(input);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // this function will not close input, need caller to close it.
    public static String getImageFormat(ImageInputStream input) {
        Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
        String format = "unknown";
        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            try {
                format = reader.getFormatName();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.dispose();
            }
        }

        return format;
    }


    public static HashMap<String, Integer> getImageInfoByFile(String filePath) {
        ImageInputStream input;
        try {
            input = ImageIO.createImageInputStream(new File(filePath));
            return getImageInfo(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, Integer> getImageInfoByBytes(byte[] content) {
        ImageInputStream input = new MemoryCacheImageInputStream(new ByteArrayInputStream(content));
        return getImageInfo(input);
    }

    // this function will not close input, need caller to close it.
    public static HashMap<String, Integer> getImageInfo(ImageInputStream input) {
        HashMap<String, Integer> map = new HashMap<String, Integer>(2);

        BufferedImage buff = null;
        try {
            buff = ImageIO.read(input);
            map.put("width", buff.getWidth());
            map.put("height", buff.getHeight());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
