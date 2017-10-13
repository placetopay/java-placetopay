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
 * Estructura para almacenar información sobre un impuesto.
 * @author hernan_saldarriaga
 */
public class TaxDetail extends Entity {
    
    /**
     * Valor de clasificación, puede ser [valueAddedTax, exciseDuty]
     */
    protected String kind;
    /**
     * Valor discriminado
     */
    protected float amount;
    /**
     * Valor base
     */
    protected float base;

    
    public TaxDetail(JSONObject object) {
        this(
                object.getString("kind"),
                (float)object.getDouble("amount"),
                (float)object.getDouble("base")
        );
    }
    /**
     * Crea una nueva instancia de {@link TaxDetail}
     * @param kind {@link TaxDetail#kind}
     * @param amount {@link TaxDetail#amount}
     * @param base {@link TaxDetail#base} 
     */
    public TaxDetail(String kind, float amount, float base) {
        this.kind = kind;
        this.amount = amount;
        this.base = base;
    }

    /**
     * Devuelve el parámetro kind
     * @return {@link TaxDetail#kind}
     */
    public String getKind() {
        return kind;
    }

    /**
     * Devuelve el parámetro amount
     * @return {@link TaxDetail#amount}
     */
    public float getAmount() {
        return amount;
    }

    /**
     * Devuelve el parámetro base
     * @return {@link TaxDetail#base}
     */
    public float getBase() {
        return base;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("kind", kind);
        object.put("amount", this.amount);
        object.put("base", this.base);
        return Entity.filterJSONObject(object);
    }
}
