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
package com.placetopay.java_placetopay.Entities.Models;

import com.placetopay.java_placetopay.Contracts.Entity;
import com.placetopay.java_placetopay.Entities.EntityWithNameValuePair;
import com.placetopay.java_placetopay.Entities.Payment;
import com.placetopay.java_placetopay.Entities.Person;
import com.placetopay.java_placetopay.Entities.Subscription;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONObject;

/**
 * Estructura que contiene toda la información acerca de la transacción para ser procesada.
 * @author hernan_saldarriaga
 */
public class RedirectRequest extends EntityWithNameValuePair {
    
    /**
     * Definido con los códigos ISO 639 (language) y ISO 3166-1 alpha-2 (2-letras del país). ej. en_US, es_CO
     */
    protected String locale = "es_CO";
    /**
     * Información del ordenante, si establece este objeto, los datos del pagador utilizarán esta información.
     */
    protected Person payer;
    /**
     * Información del comprador en la transacción
     */
    protected Person buyer;
    /**
     * Objeto de pago cuando necesite solicitar un cobro
     */
    protected Payment payment;
    /**
     * Objeto de suscripción utilizado cuando se necesita un tóken
     */
    protected Subscription subscription;
    /**
     * URL para retornar cuando el cliente termine la operación
     */
    protected String returnUrl;
    /**
     * Forzar el medio de pago en la interfaz de redirección, los códigos aceptados son los de la lista. Si necesita más de uno separarlos con coma. I.e. _ATH_,_PSE_,CR_VS
     */
    protected String paymentMethod;
    /**
     * URL para retornar cuando el cliente aborte la operación
     */
    protected String cancelUrl;
    /**
     * Dirección IP del cliente.
     */
    protected String ipAddress;
    /**
     * Agente de usuario informado por el cliente
     */
    protected String userAgent;
    /**
     * Expiración de esta solicitud, el cliente debe terminar el proceso antes de esta fecha. i.e. 2016-07-22T15:43:25-05:00
     */
    protected String expiration;
    /**
     * Captura la información de la dirección
     */
    protected boolean captureAddress;
    /**
     * No muestra los detalles de la respuesta una vez el pago ha finalizado
     */
    protected boolean skipResult = false;

    /**
     * Crea una nueva instancia de {@link RedirectRequest}
     * Convierte el json en una nueva instancia de esta clase
     * @param object Json que contiene la información
     */
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
    
    /**
     * Crea una nueva instancia de {@link RedirectRequest}
     * @param payer {@link RedirectRequest#payer}
     * @param buyer {@link RedirectRequest#buyer}
     * @param payment {@link RedirectRequest#payment}
     * @param returnUrl {@link RedirectRequest#returnUrl}
     * @param paymentMethod {@link RedirectRequest#paymentMethod} 
     */
    public RedirectRequest(Person payer, Person buyer, Payment payment, String returnUrl, String paymentMethod) {
        this.payer = payer;
        this.buyer = buyer;
        this.payment = payment;
        this.returnUrl = returnUrl;
        this.paymentMethod = paymentMethod;
    }
    /**
     * Crea una nueva instancia de {@link RedirectRequest}
     * @param payer {@link RedirectRequest#payer}
     * @param buyer {@link RedirectRequest#buyer}
     * @param payment {@link RedirectRequest#payment}
     * @param subscription {@link RedirectRequest#subscription}
     * @param returnUrl {@link RedirectRequest#returnUrl}
     * @param paymentMethod {@link RedirectRequest#paymentMethod}
     * @param cancelUrl {@link RedirectRequest#cancelUrl}
     * @param ipAddress {@link RedirectRequest#ipAddress}
     * @param userAgent {@link RedirectRequest#userAgent}
     * @param expiration {@link RedirectRequest#expiration}
     * @param captureAddress {@link RedirectRequest#captureAddress} 
     */
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
    
    /**
     * Asigna el parámetro {@link RedirectRequest#locale}
     * @param locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * Asigna el parámetro {@link RedirectRequest#returnUrl}
     * @param returnUrl
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    /**
     * Asigna el parámetro {@link RedirectRequest#cancelUrl}
     * @param cancelUrl
     */
    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    /**
     * Asigna el parámetro {@link RedirectRequest#ipAddress}
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Asigna el parámetro {@link RedirectRequest#userAgent}
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Asigna el parámetro {@link RedirectRequest#expiration}
     * @param expiration
     */
    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
    
    /**
     * @return el idioma de la transacción
     */
    public String language() {
        return locale.substring(0, 2).toUpperCase();
    }

    /**
     * Devuelve el parámetro locale
     * @return {@link RedirectRequest#locale}
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Devuelve el parámetro payer
     * @return {@link RedirectRequest#payer}
     */
    public Person getPayer() {
        return payer;
    }

    /**
     * Devuelve el parámetro buyer
     * @return {@link RedirectRequest#buyer}
     */
    public Person getBuyer() {
        return buyer;
    }

    /**
     * Devuelve el parámetro payment
     * @return {@link RedirectRequest#payment}
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Devuelve el parámetro subscription
     * @return {@link RedirectRequest#subscription}
     */
    public Subscription getSubscription() {
        return subscription;
    }

    /**
     * Devuelve el parámetro returnUrl
     * @return {@link RedirectRequest#returnUrl}
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * Devuelve el parámetro cancelUrl
     * @return {@link RedirectRequest#cancelUrl}
     */
    public String getCancelUrl() {
        return cancelUrl;
    }

    /**
     * Devuelve el parámetro ipAddress
     * @return {@link RedirectRequest#ipAddress}
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Devuelve el parámetro userAgent
     * @return {@link RedirectRequest#userAgent}
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Devuelve la referencia del pago
     * @return {@link Payment#reference}
     */
    public String getReference() {
        if (this.payment != null)
            return this.payment.getReference();
        return this.subscription.getReference();
    }

    /**
     * Devuelve el parámetro paymentMethod
     * @return {@link RedirectRequest#paymentMethod}
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Devuelve el parámetro expiration
     * @return {@link RedirectRequest#expiration}
     */
    public String getExpiration() {
        return expiration;
    }

    /**
     * Devuelve el parámetro captureAddress
     * @return {@link RedirectRequest#captureAddress}
     */
    public boolean getCaptureAddress() {
        return captureAddress;
    }

    /**
     * Devuelve el parámetro skipResult
     * @return {@link RedirectRequest#skipResult}
     */
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
