package com.example.testapp;

import android.provider.BaseColumns;

public class Contract {

    private Contract() {}

    public static final class Entry implements BaseColumns{
        public static final String TABLE_NAME = "List";
        public static final String ROW_ID = "id";
        public static final String COLUMN_CONTENTS = "contents";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_RIGHT_ANSWER = "right_answer";
        public static final String MODE = "mode_text_image";
        public static final String SHOW_1 = "show1";
        public static final String SHOW_2 = "show2";
        public static final String SHOW_3 = "show3";
        public static final String SHOW_4 = "show4";
        public static final String IMAGE_SHOW_1 = "image_show1";
        public static final String IMAGE_SHOW_2 = "image_show2";
        public static final String IMAGE_SHOW_3 = "image_show3";
        public static final String IMAGE_SHOW_4 = "image_show4";


    }
}
