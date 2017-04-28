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
package com.placetopay.java_placetopay.Contracts;

import com.placetopay.java_placetopay.Carrier.Authentication;
import com.placetopay.java_placetopay.Entities.Models.CollectRequest;
import com.placetopay.java_placetopay.Entities.Models.Notification;
import com.placetopay.java_placetopay.Entities.Models.RedirectInformation;
import com.placetopay.java_placetopay.Entities.Models.RedirectRequest;
import com.placetopay.java_placetopay.Entities.Models.RedirectResponse;
import com.placetopay.java_placetopay.Entities.Models.ReverseResponse;
import com.placetopay.java_placetopay.Exceptions.BadPlaceToPayException;
import com.placetopay.java_placetopay.Exceptions.PlaceToPayException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

/**
 *
 * @author hernan_saldarriaga
 */
public abstract class Gateway {
    
    public static final String TP_SOAP = "soap";
    public static final String TP_REST = "rest";
    
    protected String type = TP_REST;
    protected Carrier carrier = null;
    protected final Configuration config;
    
    public Gateway(String login, String trankey, URL url) {
       this(login, trankey, url, TP_REST);
    }
    
    public Gateway(String login, String trankey, URL url, String type) {
        this(login, trankey, url, type, new HashMap<String, String>());
    }
    
    public Gateway(String login, String trankey, URL url, String type, Map<String,String> additional) {
        this(login, trankey, url, type, null, additional);
    }
    
    public Gateway(String login, String tranKey, URL url, String type, Authentication.Parameter AuthParameters, Map<String,String> additional) {
        if (login == null || tranKey == null)
            throw new PlaceToPayException("No login or tranKey provided on gateway");
        if (url == null)
            throw new PlaceToPayException("No service URL provided on gateway");
        List<String> types = Arrays.asList(TP_SOAP, TP_REST);
        if (types.contains(type))
            this.type = type;
        this.config = new Configuration(login, tranKey, url, type, additional, AuthParameters);
    }
    
    public abstract RedirectResponse request(RedirectRequest redirectRequest);

    public abstract RedirectInformation query(String requestId);

    public abstract RedirectInformation collect(CollectRequest collectRequest);

    public abstract ReverseResponse reverse(String transactionId);
    
    public void using(String type) {
        List<String> types = Arrays.asList(TP_SOAP, TP_REST);
        if (types.contains(type)) {
            this.type = type;
            this.carrier = null;
        } else {
            throw new PlaceToPayException("The only connection methods are SOAP or REST");
        }
    }
    
    public Notification readNotification(String content) throws BadPlaceToPayException {
        if (content == null) {
            throw new BadPlaceToPayException("The notification content is empty");
        }
        try {
            return new Notification(content, config.getTranKey());
        } catch (JSONException ex) {
            throw new BadPlaceToPayException("The notification content is bad :" + ex.getMessage());
        }
    }
    
    public Gateway addAuthenticationHeader(Map<String, String> data) {
        if (this.config.getAdditional().isEmpty()) {
            this.config.setAdditional(data);
        } else {
            this.config.getAdditional().putAll(data);
        }
        return this;
    }
}
