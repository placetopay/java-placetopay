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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class NameValuePair extends Entity {
    protected String keyword;
    protected String value;
    protected String displayOn = "none";

    public NameValuePair(String content) throws JSONException {
        this(new JSONObject(content));
    }
    
    public NameValuePair(JSONObject object)  {
        this.keyword = object.getString("keyword");
        this.value = object.has("value") ? object.getString("value") : null;
        if (object.has("displayOn"))
            this.displayOn = object.getString("displayOn");
    }

    public NameValuePair(String keyword, String value) {
        this.keyword = keyword;
        this.value = value;
    }
    
    public NameValuePair(String keyword, String value, String displayOn) {
        this.keyword = keyword;
        this.value = value;
        this.displayOn = displayOn;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getValue() {
        return value;
    }

    public String getDisplayOn() {
        return displayOn;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("keyword", this.keyword);
        object.put("value", this.value);
        object.put("displayOn", this.displayOn);
        return object;
    }
}
