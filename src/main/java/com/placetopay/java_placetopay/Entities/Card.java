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
public class Card extends Entity {
    public static final String TP_CREDIT = "C";
    public static final String TP_DEBIT_SAVING = "A";
    public static final String TP_DEBIT_CURRENT = "R";
    
    protected String name;
    private String number;
    private String cvv;
    private String expirationMonth;
    private String expirationYear;
    protected Integer installments;
    protected String kind = TP_CREDIT;

    public Card(JSONObject object) {
        this(
                object.has("name") ? object.getString("name") : null,
                object.has("number") ? object.getString("number") : null,
                object.has("cvv") ? object.getString("cvv") : null,
                object.has("expirationMonth") ? object.getString("expirationMonth") : null,
                object.has("expirationYear") ? object.getString("expirationYear") : null,
                object.has("installments") ? object.getInt("installments") : null
        );
        if (object.has("kind"))
            this.kind = object.getString("kind");
    }
    
    public Card(String name, String number, String cvv, String expirationMonth, String expirationYear, Integer installments, String kind) {
        this(name, number, cvv, expirationMonth, expirationYear, installments);
        this.kind = kind;
    }
    
    public Card(String name, String number, String cvv, String expirationMonth, String expirationYear, Integer installments) {
        this.name = name;
        this.number = number;
        this.cvv = cvv;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.installments = installments;
    } 

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
    
    public String getexpirationYear() {
        if (expirationYear.length() == 2) {
            expirationYear = "20" + expirationYear;
        }
        return expirationYear;
    }
    
    public String getExpirationYearShort() {
        if (expirationYear.length() == 4) {
            expirationYear = expirationYear.substring(2);
        }
        return expirationYear;
    }
    
    public String getExpirationMonth() {
        return String.format("%1$" + 2 + "s", expirationMonth).replace(" ", "0");
    }

    public String getCvv() {
        return cvv;
    }
    
    public Integer getInstallments() {
        return installments;
    }
    
    public String getKind() {
        return kind;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("installments", installments);
        object.put("kind", kind);
        return Entity.filterJSONObject(object);
    }
}
