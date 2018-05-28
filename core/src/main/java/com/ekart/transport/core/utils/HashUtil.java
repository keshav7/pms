package com.ekart.transport.core.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by surender.s on 18/01/18.
 */
public class HashUtil {
    public static String getMD5Hash(String inputString) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(inputString.getBytes());
        return DatatypeConverter.printHexBinary(messageDigest.digest());
    }
}
