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
import com.placetopay.redirection.Entities.Status;
import com.placetopay.redirection.Entities.SubscriptionInformation;
import com.placetopay.redirection.Entities.Transaction;
import com.placetopay.redirection.Interfaces.HasStatus;
import com.placetopay.redirection.Utils;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class RedirectInformation extends Entity implements HasStatus {
    
    public Integer requestId;
    public RedirectRequest request;
    public List<Transaction> payment;
    public SubscriptionInformation subscription;
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
    
    public RedirectInformation(Integer requestId, RedirectRequest request, List<Transaction> payment, SubscriptionInformation subscription) {
        this.requestId = requestId;
        this.request = request;
        this.payment = payment;
        this.subscription = subscription;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public RedirectRequest getRequest() {
        return request;
    }

    public List<Transaction> getPayment() {
        return payment;
    }

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
