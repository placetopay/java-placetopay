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
import com.placetopay.java_placetopay.Interfaces.HasFields;
import com.placetopay.java_placetopay.Utils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public abstract class EntityWithNameValuePair extends Entity implements HasFields {

    /**
     * Información adicional relacionada con la solicitud que el comercio requiere para guardar con la transacción.
     */
    protected final List<NameValuePair> fields;
    
    public EntityWithNameValuePair(JSONObject object) {
        this.fields = object.has("fields") ? getFieldsFromJsonArray(object.get("fields")) : new ArrayList<NameValuePair>();
    }
    
    public EntityWithNameValuePair() {
        this.fields = new ArrayList<NameValuePair>();
    }

    public EntityWithNameValuePair(List<NameValuePair> fields) {
        this.fields = fields;
    }
    
    /**
     * {@link EntityWithNameValuePair#fields}
     * @return La Información adicional
     */
    @Override
    public List<NameValuePair> getFields() {
        return this.fields;
    }

    /**
     * {@link EntityWithNameValuePair#fields}
     * @param pairs Lista de campos
     */
    @Override
    public void addFields(List<NameValuePair> pairs) {
        this.fields.addAll(pairs);
    }
    /**
     * {@link EntityWithNameValuePair#fields}
     * @param key Clave del campo
     * @param value Valor del campo
     */
    @Override
    public void addField(String key, String value) {
        this.fields.add(new NameValuePair(key, value));
    }
    /**
     * 
     * @param key Clave del campo
     * @param value Valor del campo
     * @param displayOn Bajo qué circunstancias el campo debe ser mostrado en la interfaz de redirección [none, payment, receipt, both]
     */
    @Override
    public void addField(String key, String value, String displayOn) {
        this.fields.add(new NameValuePair(key, value, displayOn));
    }

    @Override
    public JSONArray fieldsToArrayObject() {
        JSONArray arrayFields = new JSONArray();
        for(NameValuePair field : fields) {
            arrayFields.put(field.toJsonObject());
        }
        return arrayFields;
    }

    @Override
    public JSONArray fieldsToKeyValueArrayObject() {
        JSONArray arrayFields = new JSONArray();
        for(NameValuePair field : fields) {
            JSONObject object = new JSONObject();
            object.put("keyword", field.keyword);
            object.put("value", field.value);
            arrayFields.put(object);
        }
        return arrayFields;
    }

    private static List<NameValuePair> getFieldsFromJsonArray(Object json) {
        return Utils.convertToList(json, "fields", NameValuePair.class);
    }
    
}
