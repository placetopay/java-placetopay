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
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class Token extends Entity {
    
    protected Status status;
    
    protected String token;
    protected String subtoken;
    protected String franchiseName;
    protected String issuerName;
    protected String lastDigits;
    protected String validUntil;
    // Just in case the token will be utilized
    protected String cvv;
    protected String installments;

    public Token(JSONObject object) {
        this(
                object.has("token") ? object.getString("token") : null,
                object.has("subtoken") ? object.getString("subtoken") : null,
                object.has("franchiseName") ? object.getString("franchiseName") : null,
                object.has("issuerName") ? object.getString("issuerName") : null,
                object.has("lastDigits") ? object.getString("lastDigits") : null,
                object.has("validUntil") ? object.getString("validUntil") : null,
                object.has("cvv") ? object.getString("cvv") : null,
                object.has("installments") ? object.getString("installments") : null
        );
        if (object.has("status"))
            status = new Status(object.getJSONObject("status"));
    }
    
    public Token(String token, String subtoken, String franchiseName, String issuerName, String lastDigits, String validUntil, String cvv, String installments, Status status) {
        this(token, subtoken, franchiseName, issuerName, lastDigits, validUntil, cvv, installments);
        this.status = status;
    }
    
    public Token(String token, String subtoken, String franchiseName, String issuerName, String lastDigits, String validUntil, String cvv, String installments) {
        this.token = token;
        this.subtoken = subtoken;
        this.franchiseName = franchiseName;
        this.issuerName = issuerName;
        this.lastDigits = lastDigits;
        this.validUntil = validUntil;
        this.cvv = cvv;
        this.installments = installments;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Status getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getSubtoken() {
        return subtoken;
    }

    public String getFranchiseName() {
        return franchiseName;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public String getLastDigits() {
        return lastDigits;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public String getCvv() {
        return cvv;
    }

    public String getInstallments() {
        return installments;
    }
    
    public boolean isSuccessful() {
        return this.status.getStatus().equals(Status.ST_OK);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("status", status == null ? null : status.toJsonObject());
        object.put("token", token);
        object.put("subtoken", subtoken);
        object.put("franchiseName", franchiseName);
        object.put("issuerName", issuerName);
        object.put("lastDigits", lastDigits);
        object.put("validUntil", validUntil);
        object.put("cvv", cvv);
        object.put("installments", installments);
        return Entity.filterJSONObject(object);
    }
}
