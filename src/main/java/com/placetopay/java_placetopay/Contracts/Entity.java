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
package com.placetopay.java_placetopay.Contracts;

import com.placetopay.java_placetopay.Exceptions.PlaceToPayException;
import com.placetopay.java_placetopay.Interfaces.HasValidator;
import com.placetopay.java_placetopay.validators.BaseValidator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
@SuppressWarnings("UseSpecificCatch")
public abstract class Entity implements HasValidator {
    
    private BaseValidator validatorInstance;
    private Class validatorClass;
    
    @Override
    public BaseValidator getValidator() {
        try {
            if (this.validatorInstance == null)
                this.validatorInstance = (BaseValidator)validatorClass.newInstance();
            return this.validatorInstance;
        } catch (Exception ex) {
            throw new PlaceToPayException("Failed to load validator: " + ex.getMessage());
        }
    }
    
    public void setValidator(String validatorName) throws ClassNotFoundException {
        this.validatorClass = Class.forName("com.placetopay.java_placetopay.validators." + validatorName);
    }
    
    @Override
    public boolean isValid(List<String> fields) {
        return this.isValid(fields, true);
    }

    @Override
    public boolean isValid(List<String> fields, boolean silent) {
        return this.getValidator().isValid(this, fields, silent);
    }

    @Override
    public List<String> checkMissingFields(List<String> requiredFields) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public abstract JSONObject toJsonObject();
    
    public static JSONObject filterJSONObject(JSONObject object) {
        JSONObject result = new JSONObject();
        Iterator<String> keys = object.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = object.get(key);
            if (value != null) {
                if (value instanceof String && value.equals("")) {
                    continue;
                } else if (value instanceof JSONObject && ((JSONObject)value).length() == 0) {
                    continue;
                } else if (value instanceof JSONArray && ((JSONArray)value).length() == 0) {
                    continue;
                }
            }
            result.put(key, value);
        }
        return result;
    }
}
