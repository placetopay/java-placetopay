/*
 * The MIT License
 *
 * Copyright 2017 EGM Ingenieria sin fronteras S.A.S.
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
package com.placetopay.java_placetopay.Carrier;

import com.placetopay.java_placetopay.Contracts.Carrier;
import com.placetopay.java_placetopay.Contracts.Configuration;
import com.placetopay.java_placetopay.Entities.Models.CollectRequest;
import com.placetopay.java_placetopay.Entities.Models.RedirectInformation;
import com.placetopay.java_placetopay.Entities.Models.RedirectRequest;
import com.placetopay.java_placetopay.Entities.Models.RedirectResponse;
import com.placetopay.java_placetopay.Entities.Models.ReverseResponse;
import com.placetopay.java_placetopay.Entities.Status;
import com.placetopay.java_placetopay.Exceptions.PlaceToPayException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class RestCarrier extends Carrier {

    public RestCarrier(Authentication auth, Configuration config) {
        super(auth, config);
        if (config.getUrl() == null)
            throw new PlaceToPayException("Base URL not found for this");
    }

    @Override
    public RedirectResponse request(RedirectRequest redirectRequest) {
        try {
            String response = makeRequest("POST", url("api/session"), redirectRequest.toJsonObject());
            JSONObject jsonResponse = new JSONObject(response);
            cleanResponse(jsonResponse);
            return new RedirectResponse(jsonResponse);
        } catch (IOException ex) {
            return new RedirectResponse(new Status(
                    Status.ST_ERROR, 
                    "WR", 
                    PlaceToPayException.readException(ex), 
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
            ));
        }
    }

    @Override
    public RedirectInformation query(String requestId) {
        try {
            String response = makeRequest("POST", url("api/session/" + requestId), new JSONObject());
            JSONObject jsonResponse = new JSONObject(response);
            cleanResponse(jsonResponse);
            return new RedirectInformation(jsonResponse);
        } catch (IOException ex) {
            return new RedirectInformation(new Status(
                    Status.ST_ERROR, 
                    "WR", 
                    PlaceToPayException.readException(ex), 
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
            ));
        }
    }

    @Override
    public RedirectInformation collect(CollectRequest collectRequest) {
        try {
            String response = makeRequest("POST", url("api/collect"), collectRequest.toJsonObject());
            JSONObject jsonResponse = new JSONObject(response);
            cleanResponse(jsonResponse);
            return new RedirectInformation(jsonResponse);
        } catch (IOException ex) {
            return new RedirectInformation(new Status(
                    Status.ST_ERROR, 
                    "WR", 
                    PlaceToPayException.readException(ex), 
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
            ));
        }
    }

    @Override
    public ReverseResponse reverse(String internalReference) {
        try {
            JSONObject object = new JSONObject();
            object.put("internalReference", internalReference);
            String response = makeRequest("POST", url("api/reverse"), object);
            JSONObject jsonResponse = new JSONObject(response);
            cleanResponse(jsonResponse);
            return new ReverseResponse(jsonResponse);
        } catch (IOException ex) {
            return new ReverseResponse(new Status(
                    Status.ST_ERROR, 
                    "WR", 
                    PlaceToPayException.readException(ex), 
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
            ));
        }
    }
    
    public String makeRequest(String method, String urlString, JSONObject arguments) throws MalformedURLException, ProtocolException, IOException {
        arguments.put("auth", auth.toJsonObject());
        URL url = new URL(urlString);
        HttpURLConnection httpConnection  = (HttpURLConnection) url.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod(method);
        httpConnection.setRequestProperty("User-Agent", "Java/" + System.getProperty("java.version"));
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setRequestProperty("Accept", "application/json");
        // Not required
        // urlConnection.setRequestProperty("Content-Length", String.valueOf(input.getBytes().length));

        // Writes the JSON parsed as string to the connection
        DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
        wr.write(arguments.toString().getBytes());
        Integer responseCode = httpConnection.getResponseCode();

        BufferedReader bufferedReader;

        // Creates a reader buffer
        if (responseCode > 199 && responseCode < 300) {
            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
        }

        // To receive the response
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();
        return content.toString();
    }

    private String url(String endPoint) {
        return config.getUrl() + endPoint;
    }

    private void cleanResponse(Object response) {
        if (response instanceof JSONObject) {
            JSONObject object = (JSONObject)response;
            Set<String> keys = new HashSet<String>(object.keySet());
            for (String key : keys) {
                if (object.isNull(key))
                    object.remove(key);
                else {
                    Object value = object.get(key);
                    if (value instanceof JSONObject || value instanceof JSONArray) {
                        cleanResponse(value);
                    }
                }
            }
        } else if (response instanceof JSONArray) {
            JSONArray array = (JSONArray)response;
            for (int i = 0; i < array.length(); i++) {
                cleanResponse(array.get(i));
            }
        }
    }
}
