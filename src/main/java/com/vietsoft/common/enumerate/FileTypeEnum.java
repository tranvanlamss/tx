package com.vietsoft.common.enumerate;

import com.vietsoft.common.Constants;

public enum FileTypeEnum {
    IMAGE,
    PDF,
    DOC,
    EXCEL;

    public static String getResourcesPath(FileTypeEnum type){
        switch (type){
            case IMAGE:
                return Constants.RESOURCES_PATH + "images/";
            case DOC:
                return Constants.RESOURCES_PATH + "doc/";
            case PDF:
                return Constants.RESOURCES_PATH + "pdf/";
            case EXCEL:
                return Constants.RESOURCES_PATH + "excel/";
        }
        return Constants.RESOURCES_PATH + "images/";
    }
    public static String getResourcesPath(String resourcePath, FileTypeEnum type){
        switch (type){
            case IMAGE:
                return resourcePath + "images/";
            case DOC:
                return resourcePath + "doc/";
            case PDF:
                return resourcePath + "pdf/";
            case EXCEL:
                return resourcePath + "excel/";
        }
        return resourcePath + "images/";
    }
}
