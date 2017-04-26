package com.placetopay.redirection;

import com.placetopay.redirection.Contracts.Gateway;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import junit.framework.TestCase;
import org.json.JSONObject;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
 
    public Gateway getGateway() {
        try {
            return new PlaceToPay(
                    getEnv("login"),
                    getEnv("tranKey"),
                    new URL(getEnv("url"))
            );
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public Gateway getGateway(String type) {
        try {
            return new PlaceToPay(
                    getEnv("login"),
                    getEnv("tranKey"),
                    new URL(getEnv("url")),
                    type
            );
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public Gateway getGateway(String type, String tranKey) {
        try {
            return new PlaceToPay(
                    getEnv("login"),
                    tranKey,
                    new URL(getEnv("url")),
                    type
            );
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public JSONObject addRequest(String data) {
        JSONObject object = new JSONObject(data);
        object.put("ipAddress", "127.0.0.1");
        object.put("userAgent", "PHPUnit");
        return object;
    }
    
    public String getEnv(String key) {
        try {
                File file = new File( "src/test/java/com/placetopay/redirection/test.properties");
                FileInputStream fileInput = new FileInputStream(file);
                Properties properties = new Properties();
                properties.load(fileInput);
                fileInput.close();

                return properties.getProperty(key);
        } catch (IOException e) {
                System.out.println(e.getMessage());
        }
        return null;
    }
}
