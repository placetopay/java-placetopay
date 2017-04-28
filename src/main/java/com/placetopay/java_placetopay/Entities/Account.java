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
package com.placetopay.java_placetopay.Entities;

import com.placetopay.java_placetopay.Contracts.Entity;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class Account extends Entity {

    public Status status;
    public String bankCode;
    public String bankName;
    public String accountType;
    public String accountNumber;
    
    public Account(JSONObject object) {
        this(
                object.has("status") ? new Status(object.getJSONObject("status")) : null,
                object.has("bankCode") ? object.getString("bankCode") : null,
                object.has("bankName") ? object.getString("bankName") : null,
                object.has("accountType") ? object.getString("accountType") : null,
                object.has("accountNumber") ? object.getString("accountNumber") : null
        );
    }

    public Account(Status status, String bankCode, String bankName, String accountType, String accountNumber) {
        this.status = status;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String franchise() {
        return "_" + this.bankCode + "_";
    }

    public Status getStatus() {
        return this.status;
    }
    
    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("status", status == null ? null : this.status.toJsonObject());
        object.put("bankCode", this.bankCode);
        object.put("bankName", this.bankName);
        object.put("accountType", this.accountType);
        object.put("accountNumber", this.accountNumber);
        object.put("franchise", this.franchise());
        return Entity.filterJSONObject(object);
    }
    
    public String getType() {
        return "account";
    }
    
    public String franchiseName() {
        return this.bankName;
    }
    
    public String lastDigits() {
        int length = this.accountNumber.length();
        return this.accountNumber.substring(length-4);
    }
    
    public JSONObject toNameValuePairJsonObject() {
        JSONObject object = new JSONObject();
        object.put("bankCode", this.bankCode);
        object.put("bankName", this.bankName);
        object.put("accountType", this.accountType);
        object.put("accountNumber", this.accountNumber);
        return Entity.filterJSONObject(object);
    }
}
