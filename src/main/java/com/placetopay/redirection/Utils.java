package com.placetopay.redirection;


import com.placetopay.redirection.Exceptions.PlaceToPayException;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * The MIT License
 *
 * Copyright 2017 hernan_saldarriaga.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 *
 * @author hernan_saldarriaga
 */
public class Utils {
    public static String base64(byte[] input) {
        byte[] encodedBytes = (Base64.getEncoder()).encode(input);
        return new String(encodedBytes);
    }
    
    public static String base64(String input) {
        byte[] encodedBytes = (Base64.getEncoder()).encode(input.getBytes());
        return new String(encodedBytes);
    }
    
    public static byte[] sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        return mDigest.digest(input.getBytes());
    }
    
    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
    
    public static <T> List<T> convertToList(Object object, String keyName, Class c) {
        List<T> list = new ArrayList<T>();
        if (object instanceof JSONObject) {
            JSONObject ele = (JSONObject) object;
            if (ele.has(keyName) && ele.keySet().size() == 1) {
                object = ((JSONObject)object).get(keyName);
            }
        }
        if (object instanceof JSONObject) {
            list.add((T)instanceEntity(object, c));
        } else if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray)object;
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add((T)instanceEntity(jsonArray.getJSONObject(i), c));
            }
        }
        return list;
    }
    
    private static Object instanceEntity(Object object, Class c) {
        try {
            return c.getDeclaredConstructor(JSONObject.class).newInstance(object);
        } catch (Exception ex) {
            throw new PlaceToPayException("Error in implementation, Entity class mismatch: " + ex.getMessage());
        }
    }
}
