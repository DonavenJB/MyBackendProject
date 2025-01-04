package com.bruce.backend.dataconversion;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class DataConversion {

    public static final byte[] readAll(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.available());
            int byteRead;
            while ((byteRead = inputStream.read()) != -1) {
                byteArrayOutputStream.write(byteRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception exception) {
            exception.printStackTrace();
            return new byte[0];
        }
    }

    public static final String bString(byte[] arrby) {
        try {
            return new String(arrby, "ISO8859-1");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return "";
        }
    }
}
