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
public class Person extends Entity {
    
    protected Long document;
    protected String documentType;
    protected String name;
    protected String surname;
    protected String company;
    protected String email;
    protected Address address;
    protected String mobile;

    
    public Person(JSONObject object) {
        this(
                object.has("document") ? object.getLong("document") : null,
                object.has("documentType") ? object.getString("documentType") : null,
                object.has("name") ? object.getString("name") : null,
                object.has("surname") ? object.getString("surname") : null,
                object.has("company") ? object.getString("company") : null,
                object.has("email") ? object.getString("email") : null,
                object.has("address") ? new Address(object.getJSONObject("address")) : null,
                object.has("mobile") ? object.getString("mobile") : null
        );
    }
    
    public Person(Long document, String documentType, String name, String surname, String company, String email, Address address, String mobile) {
        this.document = document;
        this.documentType = documentType;
        this.name = name;
        this.surname = surname;
        this.company = company;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
    }

    public Long getDocument() {
        return document;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("document", document);
        object.put("documentType", documentType);
        object.put("name", name);
        object.put("surname", surname);
        object.put("company", company);
        object.put("email", email);
        object.put("address", address == null ? null : address.toJsonObject());
        object.put("mobile", mobile);
        return Entity.filterJSONObject(object);
    }
}
