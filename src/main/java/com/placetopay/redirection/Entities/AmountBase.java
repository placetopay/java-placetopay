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
public class AmountBase extends Entity {
    
    protected String currency = "COP";
    protected float total;
    
    public AmountBase(JSONObject object) {
        if (!object.has("currency"))
            this.total = (float) object.getDouble("total");
        else {
            this.currency = object.getString("currency");
            this.total = (float) object.getDouble("total");
        }
    }

    public AmountBase(String currency, float total) {
        this.currency = currency;
        this.total = total;
    }
    
    public AmountBase(float total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public float getTotal() {
        return total;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("currency", this.currency);
        object.put("total", this.total);
        return Entity.filterJSONObject(object);
    }
}
