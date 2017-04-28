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
 * Estructura para almacenar información sobre el valor.
 * @author hernan_saldarriaga
 */
public class AmountDetail extends Entity {
    /**
     * Valor de clasificación, puede ser [discount, additional, vatDevolutionBase, shipping, handlingFee, insurance, giftWrap, subtotal, fee, tip]
     */
    protected String kind;
    /**
     * Valor discriminado (Representación decimal del valor)
     */
    protected Float amount;

    public AmountDetail(JSONObject object) {
        this(
            object.has("kind") ? object.getString("kind") : null,
            object.has("amount") ? (float)object.getDouble("amount") : null
        );
    }    
    /**
     * Crea instancia de {@link AmountDetail}
     * @param kind {@link AmountDetail#kind}
     * @param amount {@link AmountDetail#amount} 
     */
    public AmountDetail(String kind, Float amount) {
        this.kind = kind;
        this.amount = amount;
    }
    
    /**
     * Devuelve el parámetro kind
     * @return {@link AmountDetail#kind}
     */
    public String getKind() {
        return kind;
    }

    /**
     * Devuelve el parámetro amount
     * @return {@link AmountDetail#amount}
     */
    public Float getAmount() {
        return amount;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("kind", this.kind);
        object.put("amount", this.amount);
        return Entity.filterJSONObject(object);
    }
}
