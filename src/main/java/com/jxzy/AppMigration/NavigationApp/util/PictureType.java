package com.jxzy.AppMigration.NavigationApp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 李波帆
 * @Version 1.0
 * Create: 2023/10/31-16:58
 * Description: 图片类型枚举
 */
public enum PictureType {
    JPG("jpg"),
    PNG("png"),
    JPEG("jpeg"),
    BMP("bmp"),
    GIF("gif");
    public final String value;
    public static final List<String> generalPictureType;
    static {
        generalPictureType = new ArrayList<>();
        for (PictureType pictureType : values()) {
            generalPictureType.add(pictureType.value);
        }
    }

    PictureType(String value) {
        this.value = value;
    }


}
