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
package com.placetopay.redirection.Entities;

import com.placetopay.redirection.Contracts.Entity;
import com.placetopay.redirection.Utils;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class Transaction extends Entity {
    
    protected Status status;
    
    protected String reference;
    protected Integer internalReference;
    protected String paymentMethod;
    protected String paymentMethodName;
    protected String issuerName;
    
    protected AmountConversion amount;
    protected String authorization;
    protected Long receipt;
    protected String franchise;
    protected boolean refunded = false;
    
    protected List<NameValuePair> processorFields;

    public Transaction(JSONObject object) {
        this.status = object.has("status") ? new Status(object.getJSONObject("status")) : null;
        this.reference = object.has("reference") ? object.getString("reference") : null;
        this.internalReference = object.has("internalReference") ? object.getInt("internalReference") : null;
        this.paymentMethod = object.has("paymentMethod") ? object.getString("paymentMethod") : null;
        this.paymentMethodName = object.has("paymentMethodName") ? object.getString("paymentMethodName") : null;
        this.issuerName = object.has("issuerName") ? object.getString("issuerName") : null;
        this.amount = object.has("amount") ? new AmountConversion(object.getJSONObject("amount")) : null;
        this.authorization = object.has("authorization") ? object.getString("authorization") : null;
        this.receipt = object.has("receipt") ? object.getLong("receipt") : null;
        this.franchise = object.has("franchise") ? object.getString("franchise") : null;
        this.refunded = object.has("refunded") ? object.getBoolean("refunded") : false;
        this.processorFields = object.has("processorFields") ? setProcessorFields(object.get("processorFields")) : null;
    }
        
    public Transaction(Status status, String reference, Integer internalReference, String paymentMethod, String paymentMethodName, String issuerName, AmountConversion amount, String authorization, Long receipt, String franchise, boolean refunded, List<NameValuePair> processorFields) {
        this.status = status;
        this.reference = reference;
        this.internalReference = internalReference;
        this.paymentMethod = paymentMethod;
        this.paymentMethodName = paymentMethodName;
        this.issuerName = issuerName;
        this.amount = amount;
        this.authorization = authorization;
        this.receipt = receipt;
        this.franchise = franchise;
        this.refunded = refunded;
        this.processorFields = processorFields;
    }

    public Status getStatus() {
        return status;
    }

    public String getReference() {
        return reference;
    }

    public Integer getInternalReference() {
        return internalReference;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public AmountConversion getAmount() {
        return amount;
    }

    public String getAuthorization() {
        return authorization;
    }

    public Long getReceipt() {
        return receipt;
    }

    public String getFranchise() {
        return franchise;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public List<NameValuePair> getProcessorFields() {
        return processorFields;
    }
    
    public void setAmountBase(JSONObject base) {
        AmountBase base1 = new AmountBase(base);
        amount = new AmountConversion().setAmountBase(base1);
    }
    
    public boolean isSuccessful() {
        return this.status != null && !this.status.getStatus().equals(Status.ST_ERROR);
    }
    
    public JSONArray processorFieldsToArray() {
        JSONArray jsona = new JSONArray();
        for (NameValuePair pair: processorFields) {
            jsona.put(pair.toJsonObject());
        }
        return jsona;
    }
    
    private static List<NameValuePair> setProcessorFields(Object json) {
        return Utils.convertToList(json, "item", NameValuePair.class);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("status", status == null ? null : status.toJsonObject());
        object.put("internalReference", internalReference);
        object.put("paymentMethod", paymentMethod);
        object.put("paymentMethodName", paymentMethodName);
        object.put("issuerName", issuerName);
        object.put("amount", amount == null ? null : amount.toJsonObject());
        object.put("authorization", authorization);
        object.put("reference", reference);
        object.put("receipt", receipt);
        object.put("franchise", franchise);
        object.put("refunded", refunded);
        object.put("processorFields", processorFields == null ? null : processorFieldsToArray());
        return Entity.filterJSONObject(object);
    }
}
