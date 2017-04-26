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
import com.placetopay.redirection.Entities.Instrument;
import com.placetopay.redirection.Entities.Payment;
import com.placetopay.redirection.Entities.Person;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class CollectRequest extends EntityWithNameValuePair {
    
    protected String locale = "es_CO";
    
    protected Person payer;
    protected Person buyer;
    protected Payment payment;
    protected Instrument instrument;

    public CollectRequest(JSONObject object) {
        super(object);
        this.payer = object.has("payer") ? new Person(object.getJSONObject("payer")) : null;
        this.buyer = object.has("buyer") ? new Person(object.getJSONObject("buyer")) : null;
        this.payment = object.has("payment") ? new Payment(object.getJSONObject("payment")) : null;
        this.instrument = object.has("instrument") ? new Instrument(object.getJSONObject("instrument")) : null;
        if (object.has("locale"))
            this.locale = object.getString("locale");
    }

    public CollectRequest(Person payer, Person buyer, Payment payment, Instrument instrument) {
        this.payer = payer;
        this.buyer = buyer;
        this.payment = payment;
        this.instrument = instrument;
    }
    
    public CollectRequest(String locale, Person payer, Person buyer, Payment payment, Instrument instrument) {
        this.locale = locale;
        this.payer = payer;
        this.buyer = buyer;
        this.payment = payment;
        this.instrument = instrument;
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

    public Instrument getInstrument() {
        return instrument;
    }
    
    public String getReference() {
        return this.payment.getReference();
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("locale", locale);
        object.put("payer", payer == null ? null : payer.toJsonObject());
        object.put("buyer", buyer == null ? null : buyer.toJsonObject());
        object.put("payment", payment == null ? null : payment.toJsonObject());
        object.put("instrument", instrument == null ? null : instrument.toJsonObject());
        object.put("fields", fieldsToArrayObject());
        return Entity.filterJSONObject(object);
    }
}
