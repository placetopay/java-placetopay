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
public class AmountConversion extends Entity {
    
    protected AmountBase from;
    protected AmountBase to;
    protected float factor;

    public AmountConversion(JSONObject object) {
        this(
                object.has("from") ? new AmountBase(object.getJSONObject("from")) : null,
                object.has("to") ? new AmountBase(object.getJSONObject("to")) : null,
                object.has("factor") ? (float)object.getDouble("factor") : -1
        );
    }

    public AmountConversion() {
    }
    
    public AmountConversion(AmountBase from, AmountBase to, float factor) {
        this.from = from;
        this.to = to;
        this.factor = factor;
    }
    
    public AmountConversion setAmountBase(JSONObject object) {
        return setAmountBase(new AmountBase(object));
    }
    
    public AmountConversion setAmountBase(AmountBase base) {
        this.to = base;
        this.from = base;
        this.factor = 1.0f;
        return this;
    }

    public AmountBase getFrom() {
        return from;
    }

    public AmountBase getTo() {
        return to;
    }

    public float getFactor() {
        return factor;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("from", from.toJsonObject());
        object.put("to", to.toJsonObject());
        object.put("factor", factor);
        return object;
    }
}
