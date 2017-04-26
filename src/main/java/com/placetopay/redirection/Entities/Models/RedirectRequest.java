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
package com.placetopay.redirection.Entities.Models;

import com.placetopay.redirection.Contracts.Entity;
import com.placetopay.redirection.Entities.EntityWithNameValuePair;
import com.placetopay.redirection.Entities.Payment;
import com.placetopay.redirection.Entities.Person;
import com.placetopay.redirection.Entities.Subscription;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class RedirectRequest extends EntityWithNameValuePair {
    protected String locale = "es_CO";
    
    protected Person payer;
    protected Person buyer;
    protected Payment payment;
    protected Subscription subscription;
    protected String returnUrl;
    protected String paymentMethod;
    protected String cancelUrl;
    protected String ipAddress;
    protected String userAgent;
    protected String expiration;
    protected boolean captureAddress;
    protected boolean skipResult = false;

    public RedirectRequest(JSONObject object) {
        super(object);
        this.payer = object.has("payer") ? new Person(object.getJSONObject("payer")) : null;
        this.buyer = object.has("buyer") ? new Person(object.getJSONObject("buyer")) : null;
        this.payment = object.has("payment") ? new Payment(object.getJSONObject("payment")) : null;
        this.subscription = object.has("subscription") ? new Subscription(object.getJSONObject("subscription")) : null;
        
        this.userAgent = object.has("userAgent") ? object.getString("userAgent") : null;
        this.ipAddress = object.has("ipAddress") ? object.getString("ipAddress") : null;
        this.returnUrl = object.has("returnUrl") ? object.getString("returnUrl") : null;
        this.paymentMethod = object.has("paymentMethod") ? object.getString("paymentMethod") : null;
        this.cancelUrl = object.has("cancelUrl") ? object.getString("cancelUrl") : null;
        this.captureAddress = object.has("captureAddress") ? object.getBoolean("captureAddress") : false;
        this.skipResult = object.has("skipResult") ? object.getBoolean("skipResult") : false;
        
        if (object.has("locale"))
            this.locale = object.getString("locale");
        if (object.has("expiration"))
            this.expiration = object.getString("expiration");
        else {
            Date dt = new Date(); Calendar c = Calendar.getInstance(); 
            c.setTime(dt); c.add(Calendar.DATE, 1); dt = c.getTime();
            this.expiration = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(dt);
        }
    }

    public RedirectRequest(Person payer, Person buyer, Payment payment, String returnUrl, String paymentMethod) {
        this.payer = payer;
        this.buyer = buyer;
        this.payment = payment;
        this.returnUrl = returnUrl;
        this.paymentMethod = paymentMethod;
    }

    public RedirectRequest(Person payer, Person buyer, Payment payment, Subscription subscription, String returnUrl, String paymentMethod, String cancelUrl, String ipAddress, String userAgent, String expiration, boolean captureAddress) {
        this.payer = payer;
        this.buyer = buyer;
        this.payment = payment;
        this.subscription = subscription;
        this.returnUrl = returnUrl;
        this.paymentMethod = paymentMethod;
        this.cancelUrl = cancelUrl;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.expiration = expiration;
        this.captureAddress = captureAddress;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
    
    public String language() {
        return locale.substring(0, 2).toUpperCase();
    }

    public String getLocale() {
        return locale;
    }

    public Person getPayer() {
        return payer;
    }

    public Person getBuyer() {
        return buyer;
    }

    public Payment getPayment() {
        return payment;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }
    public String getReference() {
        if (this.payment != null)
            return this.payment.getReference();
        return this.subscription.getReference();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getExpiration() {
        return expiration;
    }

    public boolean getCaptureAddress() {
        return captureAddress;
    }

    public boolean isSkipResult() {
        return skipResult;
    }

    
    
    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("locale", locale);
        object.put("payer", payer == null ? null : payer.toJsonObject());
        object.put("buyer", buyer == null ? null : buyer.toJsonObject());
        object.put("payment", payment == null ? null : payment.toJsonObject());
        object.put("subscription", subscription == null ? null : subscription.toJsonObject());
        object.put("fields", fieldsToArrayObject());
        object.put("returnUrl", returnUrl);
        object.put("paymentMethod", paymentMethod);
        object.put("cancelUrl", cancelUrl);
        object.put("ipAddress", ipAddress);
        object.put("userAgent", userAgent);
        object.put("expiration", expiration);
        object.put("captureAddress", captureAddress);
        object.put("skipResult", skipResult);
        return Entity.filterJSONObject(object);
    }
}
