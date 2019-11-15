package com.example.mybatis.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExceptionUtils {

    public static String getStackTrace(Throwable throwable) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintStream(baos));
            return baos.toString();
        } finally {

        }
    }
}
