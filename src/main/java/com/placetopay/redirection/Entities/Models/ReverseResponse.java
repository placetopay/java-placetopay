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
import com.placetopay.redirection.Entities.Transaction;
import com.placetopay.redirection.Interfaces.HasStatus;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class ReverseResponse extends Entity implements HasStatus {
    
    public Transaction payment;
    protected Status status;

    public ReverseResponse(Status status) {
        this.status = status;
    }
    
    public ReverseResponse(JSONObject object) {
        this.payment = object.has("payment") ? new Transaction(object.getJSONObject("payment")) : null;
        this.status = new Status(object.getJSONObject("status"));
    }
    
    public ReverseResponse(Transaction payment, Status status) {
        this.payment = payment;
        this.status = status;
    }

    public Transaction getPayment() {
        return payment;
    }
    
    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public boolean isSuccessful() {
        return !this.status.getStatus().equals(Status.ST_ERROR);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("status", status == null ? null : status.toJsonObject());
        object.put("payment", payment == null ? null: payment.toJsonObject());
        return Entity.filterJSONObject(object);
    }
}
