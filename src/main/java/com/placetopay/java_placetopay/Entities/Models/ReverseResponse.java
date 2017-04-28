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
import com.placetopay.java_placetopay.Entities.Transaction;
import com.placetopay.java_placetopay.Interfaces.HasStatus;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class ReverseResponse extends Entity implements HasStatus {
    
    /**
     * Si el reverso fue exitoso, se almacena como una nueva transacción.
     */
    public Transaction payment;
    /**
     * Estado de la solicitud será APROBADO si se ha realizado el reverso de lo contrario puede ser RECHAZADA.
     */
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
    /**
     * Retorna el parámetro payment}
     * @return {@link ReverseResponse#payment}
     */
    public Transaction getPayment() {
        return payment;
    }
    /**
     * Retorna el parámetro status
     * @return {@link ReverseResponse#status}
     */
    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
    /**
     * 
     * @return Si no hubo un error en el proceso
     */
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
