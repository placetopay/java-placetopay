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
public class Address extends Entity {
    
    protected String street;
    protected String city;
    protected String state;
    protected String postalCode;
    protected String country;
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

    public Address(String street, String city, String state, String postalCode, String country, String phone) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

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
