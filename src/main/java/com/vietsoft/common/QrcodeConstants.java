package com.vietsoft.common;

import java.util.Arrays;
import java.util.List;

public class QrcodeConstants {

    public static final String PAGE_SIZE_A4 = "A4";
    public static final String PAGE_SIZE_A5 = "A5";

    public static final List<String> PAGE_SIZE_ALLOW
            = Arrays.asList(PAGE_SIZE_A4,
            PAGE_SIZE_A5);

    public static final String IMAGE_WIDTH_3 = "3";
    public static final String IMAGE_WIDTH_5 = "5";
    public static final String IMAGE_WIDTH_7 = "7";

    public static final List<String> IMAGE_WIDTH_ALLOW
            = Arrays.asList(IMAGE_WIDTH_3,
            IMAGE_WIDTH_5,
            IMAGE_WIDTH_7);

    public static final String TEXT_POSITION_TOP = "TOP";
    public static final String TEXT_POSITION_BOTTOM = "BOTTOM";

    public static final List<String> TEXT_POSITION_ALLOW
            = Arrays.asList(TEXT_POSITION_TOP,
            TEXT_POSITION_BOTTOM);

}
