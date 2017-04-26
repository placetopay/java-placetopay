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
import com.placetopay.redirection.Utils;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class Amount extends AmountBase {
    
    protected List<TaxDetail> taxes = null;
    protected List<AmountDetail> details = null;
    protected float taxAmount;
    
    protected float vatDevolutionBase = 0;
    
    protected float subtotal = 0;

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
        return Utils.convertToList(json, "taxes", TaxDetail.class);
    }

    private List<AmountDetail> convertToAmountList(Object json) {
        return Utils.convertToList(json, "details", AmountDetail.class);
    }

    public List<TaxDetail> getTaxes() {
        return taxes;
    }

    public List<AmountDetail> getDetails() {
        return details;
    }

    public float getTaxAmount() {
        return taxAmount;
    }

    public float getDevolutionBase() {
        if (vatDevolutionBase == 0.0f)
            return 0;
        return vatDevolutionBase;
    }

    public float getSubtotal() {
        if (subtotal == 0.0f)
            return this.total - this.taxAmount;
        return subtotal;
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
