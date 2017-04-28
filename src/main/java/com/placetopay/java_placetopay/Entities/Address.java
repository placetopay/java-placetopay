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
 * Estructura que contiene la información sobre una dirección física
 * @author hernan_saldarriaga
 */
public class Address extends Entity {
    /**
     * Dirección física completa
     */
    protected String street;
    /**
     * Nombre de la ciudad
     */
    protected String city;
    /**
     * Nombre del estado coincidente con la dirección
     */
    protected String state;
    /**
     * Código postal o equivalente se requiere generalmente para los países que tienen
     */
    protected String postalCode;
    /**
     * Código internacional del país que se aplica a la dirección física según  ISO 3166-1 ALPHA-2
     */
    protected String country;
    /**
     * Número telefónico 
     */
    protected String phone;
    
    public Address(JSONObject object) {
        this(
                object.has("street") ? object.getString("street") : null,
                object.has("city") ? object.getString("city") : null,
                object.has("state") ? object.getString("state") : null,
                object.has("postalCode") ? object.getString("postalCode") : null,
                object.has("country") ? object.getString("country") : null,
                object.has("phone") ? object.getString("phone") : null
        );
    }
    /**
     * Crea una nueva instancia de {@link Address}
     * @param street
     * @param city
     * @param state
     * @param postalCode
     * @param country
     * @param phone 
     */
    public Address(String street, String city, String state, String postalCode, String country, String phone) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }

    /**
     * Devuelve el parámetro street
     * @return {@link Address#street}
     */
    public String getStreet() {
        return street;
    }

    /**
     * Devuelve el parámetro city
     * @return {@link Address#city}
     */
    public String getCity() {
        return city;
    }

    /**
     * Devuelve el parámetro state
     * @return {@link Address#state}
     */
    public String getState() {
        return state;
    }

    /**
     * Devuelve el parámetro postalCode
     * @return {@link Address#postalCode}
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Devuelve el parámetro country
     * @return {@link Address#country}
     */
    public String getCountry() {
        return country;
    }

    /**
     * Devuelve el parámetro phone
     * @return {@link Address#phone}
     */
    public String getPhone() {
        return phone;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("street", this.street);
        object.put("city", this.city);
        object.put("state", this.state);
        object.put("postalCode", this.postalCode);
        object.put("country", this.country);
        object.put("phone", this.phone);
        return Entity.filterJSONObject(object);
    }
    
    
}
