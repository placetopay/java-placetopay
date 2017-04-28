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
import com.placetopay.java_placetopay.Entities.Status;
import com.placetopay.java_placetopay.Entities.SubscriptionInformation;
import com.placetopay.java_placetopay.Entities.Transaction;
import com.placetopay.java_placetopay.Interfaces.HasStatus;
import com.placetopay.java_placetopay.Utils;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Estructura de respuesta a una solicitud para una información de transacción.
 * @author hernan_saldarriaga
 */
public class RedirectInformation extends Entity implements HasStatus {
    
    /**
     * Identificador de la sesión a consultar.
     */
    public Integer requestId;
    /**
     * Información con la solicitud original.
     */
    public RedirectRequest request;
    /**
     * Información relacionada con el pago si este fue solicitado.
     */
    public List<Transaction> payment;
    /**
     * Información relacionado con la suscripción si esta fue solicitada.
     */
    public SubscriptionInformation subscription;
    /**
     * Estado de esta solicitud, debe observar el estado interno de cada objeto.
     */
    protected Status status;

    public RedirectInformation(Status status) {
        this.status = status;
    }    
    
    public RedirectInformation(JSONObject object) {
        this.requestId = object.has("requestId") ? object.getInt("requestId") : null;
        this.request = object.has("request") ? new RedirectRequest(object.getJSONObject("request")) : null;
        this.payment = object.has("payment") ? paymentFromJsonArray(object.get("payment")) : null;
        this.subscription = object.has("subscription") ? new SubscriptionInformation(object.getJSONObject("subscription")) : null;
        this.status = object.has("status") ? new Status(object.getJSONObject("status")) : null;
    }
    
    public RedirectInformation(Integer requestId, RedirectRequest request, List<Transaction> payment, SubscriptionInformation subscription, Status status) {
        this.requestId = requestId;
        this.request = request;
        this.payment = payment;
        this.subscription = subscription;
        this.status = status;
    }

    /**
     * Asigna el parámetro {@link RedirectInformation#status}
     * @param status
     */
    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Asigna el parámetro {@link RedirectInformation#requestId}
     * @param requestId
     */
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    /**
     * Asigna el parámetro {@link RedirectInformation#request}
     * @param request
     */
    public void setRequest(RedirectRequest request) {
        this.request = request;
    }

    /**
     * Asigna el parámetro {@link RedirectInformation#payment}
     * @param payment
     */
    public void setPayment(List<Transaction> payment) {
        this.payment = payment;
    }

    /**
     * Asigna el parámetro {@link RedirectInformation#subscription}
     * @param subscription
     */
    public void setSubscription(SubscriptionInformation subscription) {
        this.subscription = subscription;
    }
    
    /**
     * Devuelve el parámetro status
     * @return {@link RedirectInformation#status}
     */
    @Override
    public Status getStatus() {
        return status;
    }

    /**
     * Devuelve el parámetro requestId
     * @return {@link RedirectInformation#requestId}
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     * Devuelve el parámetro request
     * @return {@link RedirectInformation#request}
     */
    public RedirectRequest getRequest() {
        return request;
    }

    /**
     * Devuelve el parámetro payment
     * @return {@link RedirectInformation#payment}
     */
    public List<Transaction> getPayment() {
        return payment;
    }

    /**
     * Devuelve el parámetro subscription
     * @return {@link RedirectInformation#subscription}
     */
    public SubscriptionInformation getSubscription() {
        return subscription;
    }

    private List<Transaction> paymentFromJsonArray(Object object) {
        return Utils.convertToList(object, "transaction", Transaction.class);
    }
    
    private JSONArray paymentToJsonArray() {
        JSONArray array = new JSONArray();
        for (Transaction transaction : payment) {
            array.put(transaction.toJsonObject());
        }
        return array;
    }
    
    /**
     * Devuelve <strong>true</strong> si la petición no fue un error, de lo contrario <strong>false</strong>
     * @return
     */
    public boolean isSuccessful() {
        return !this.status.getStatus().equals(Status.ST_ERROR);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("requestId", requestId);
        object.put("status", status == null ? null : status.toJsonObject());
        object.put("request", request == null ? null : request.toJsonObject());
        object.put("payment", payment == null ? null : paymentToJsonArray());
        object.put("subscription", subscription == null ? null : subscription.toJsonObject());
        return Entity.filterJSONObject(object);
    }
}
