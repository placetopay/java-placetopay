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
import com.placetopay.java_placetopay.Entities.Instrument;
import com.placetopay.java_placetopay.Entities.Payment;
import com.placetopay.java_placetopay.Entities.Person;
import org.json.JSONObject;

/**
 * Estructura que representa la información para realizar el cobro en base a un medio de pago suscrito.
 * @author hernan_saldarriaga
 */
public class CollectRequest extends EntityWithNameValuePair {
    
    /**
     * Definido con los códigos ISO 639 (language) y ISO 3166-1 alpha-2 (2-letras del país). ej. en_US, es_CO
     */
    protected String locale = "es_CO";
    /**
     * Datos del titular del medio de pago almacenado
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
     * Datos asociados al medio de pago suscrito
     */
    protected Instrument instrument;

    /**
     * Crea una nueva instancia de {@link CollectRequest}
     * Convierte el json en una nueva instancia de esta clase
     * @param object Json que contiene la información
     */
    public CollectRequest(JSONObject object) {
        super(object);
        this.payer = object.has("payer") ? new Person(object.getJSONObject("payer")) : null;
        this.buyer = object.has("buyer") ? new Person(object.getJSONObject("buyer")) : null;
        this.payment = object.has("payment") ? new Payment(object.getJSONObject("payment")) : null;
        this.instrument = object.has("instrument") ? new Instrument(object.getJSONObject("instrument")) : null;
        if (object.has("locale"))
            this.locale = object.getString("locale");
    }

    /**
     * Crea una nueva instancia de {@link CollectRequest}
     * @param payer {@link CollectRequest#payer}
     * @param buyer {@link CollectRequest#buyer}
     * @param payment {@link CollectRequest#payment}
     * @param instrument {@link CollectRequest#instrument} 
     */
    public CollectRequest(Person payer, Person buyer, Payment payment, Instrument instrument) {
        this.payer = payer;
        this.buyer = buyer;
        this.payment = payment;
        this.instrument = instrument;
    }
    
    /**
     * Crea una nueva instancia de {@link CollectRequest}
     * @param locale {@link CollectRequest#locale}
     * @param payer {@link CollectRequest#payer}
     * @param buyer {@link CollectRequest#buyer}
     * @param payment {@link CollectRequest#payment}
     * @param instrument {@link CollectRequest#instrument}
     */
    public CollectRequest(String locale, Person payer, Person buyer, Payment payment, Instrument instrument) {
        this.locale = locale;
        this.payer = payer;
        this.buyer = buyer;
        this.payment = payment;
        this.instrument = instrument;
    }

    /**
     * Asigna el parámetro {@link CollectRequest#locale}
     * @param locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * Asigna el parámetro {@link CollectRequest#payer}
     * @param payer
     */
    public void setPayer(Person payer) {
        this.payer = payer;
    }

    /**
     * Asigna el parámetro {@link CollectRequest#buyer}
     * @param buyer
     */
    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }

    /**
     * Asigna el parámetro {@link CollectRequest#payment}
     * @param payment
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * Asigna el parámetro {@link CollectRequest#instrument}
     * @param instrument
     */
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
    /**
     * @return el idioma de la transacción
     */
    public String language() {
        return locale.substring(0, 2).toUpperCase();
    }

    /**
     * Devuelve el parámetro locale
     * @return {@link CollectRequest#locale}
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Devuelve el parámetro payer
     * @return {@link CollectRequest#payer}
     */
    public Person getPayer() {
        return payer;
    }

    /**
     * Devuelve el parámetro buyer
     * @return {@link CollectRequest#buyer}
     */
    public Person getBuyer() {
        return buyer;
    }

    /**
     * Devuelve el parámetro payment
     * @return {@link CollectRequest#payment}
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Devuelve el parámetro instrument
     * @return {@link CollectRequest#instrument}
     */
    public Instrument getInstrument() {
        return instrument;
    }
    
    /**
     * Devuelve la referencia del pago
     * @return {@link Payment#reference}
     */
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
