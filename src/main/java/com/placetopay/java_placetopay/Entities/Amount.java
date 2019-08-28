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
import com.placetopay.java_placetopay.Utils;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Extiende de AmountBase y describe el contenido de la cantidad completa, incluyendo impuestos y detalles.
 * @author hernan_saldarriaga
 */
public class Amount extends AmountBase {
    /**
     * Descripción de los impuestos
     */
    protected List<TaxDetail> taxes = null;
    /**
     * Descripción del importe total
     */
    protected List<AmountDetail> details = null;
    protected double taxAmount;
    
    protected double vatDevolutionBase = 0;
    
    protected double subtotal = 0;

    public Amount(JSONObject object) {
        super(object);
        if (object.has("taxes"))
            this.taxes = convertToTaxList(object.get("taxes"));
        if (object.has("details"))
            this.details = convertToAmountList(object.get("details"));
    }

    public Amount(List<TaxDetail> taxes, List<AmountDetail> details, String currency, float total) {
        super(currency, total);
        this.taxes = taxes;
        this.details = details;
    }

    private List<TaxDetail> convertToTaxList(Object json) {
        List<TaxDetail> localDetails = Utils.convertToList(json, "taxes", TaxDetail.class);
        for (TaxDetail item: localDetails) {
            taxAmount += item.amount;
        }
        return localDetails;
    }

    private List<AmountDetail> convertToAmountList(Object json) {
        return Utils.convertToList(json, "details", AmountDetail.class);
    }
    /**
     * Devuelve el parámetro taxes
     * @return {@link Amount#taxes}
     */
    public List<TaxDetail> getTaxes() {
        return taxes;
    }
    /**
     * Devuelve el parámetro details
     * @return {@link Amount#details}
     */
    public List<AmountDetail> getDetails() {
        return details;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getDevolutionBase() {
        return vatDevolutionBase;
    }

    public double getSubtotal() {
        if (subtotal == 0.0f)
            return this.total - this.taxAmount;
        return subtotal;
    }

    public void setVatDevolutionBase(float vatDevolutionBase) {
        this.vatDevolutionBase = vatDevolutionBase;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
    
    public JSONArray taxesToJsonArray() {
        if (this.taxes != null) {
            JSONArray array = new JSONArray();
            for (TaxDetail tax: taxes) {
                array.put(tax.toJsonObject());
            }
        }
        return null;
    }
    
    public JSONArray detailsToJsonArray() {
        if (this.details != null) {
            JSONArray array = new JSONArray();
            for (AmountDetail amount: details) {
                array.put(amount.toJsonObject());
            }
        }
        return null;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = super.toJsonObject();
        JSONArray taxesArray = taxesToJsonArray();
        if (taxesArray != null)
            object.put("taxes", taxesArray);
        JSONArray detailsArray = detailsToJsonArray();
        if (detailsArray != null)
            object.put("details", details);
        return Entity.filterJSONObject(object);
    }
    
    
    
}
