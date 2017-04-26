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
import com.placetopay.redirection.Interfaces.HasFields;
import com.placetopay.redirection.Utils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public abstract class EntityWithNameValuePair extends Entity implements HasFields {

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
    
    @Override
    public List<NameValuePair> getFields() {
        return this.fields;
    }

    @Override
    public void addFields(List<NameValuePair> pairs) {
        this.fields.addAll(pairs);
    }

    @Override
    public void addField(String key, String value) {
        this.fields.add(new NameValuePair(key, value));
    }

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
