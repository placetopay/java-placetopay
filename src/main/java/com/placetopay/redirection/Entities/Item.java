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
public class Item extends Entity {
    
    protected String sku;
    protected String name;
    protected String category;
    protected Integer qty;
    protected float price;
    protected float tax;

    public Item(JSONObject object) {
        this(
                object.has("sku") ? object.getString("sku") : null,
                object.has("name") ? object.getString("name") : null,
                object.has("category") ? object.getString("category") : null,
                object.has("qty") ? object.getInt("qty") : 0,
                object.has("price") ? (float)object.getDouble("price") : 0,
                object.has("tax") ? (float)object.getDouble("tax") : 0
        );
    }    
    
    public Item(String sku, String name, String category, Integer qty, float price, float tax) {
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.qty = qty;
        this.price = price;
        this.tax = tax;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Integer getQty() {
        return qty;
    }

    public float getPrice() {
        return price;
    }

    public float getTax() {
        return tax;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("sku", sku);
        object.put("name", name);
        object.put("category", category);
        object.put("qty", qty);
        object.put("price", price);
        object.put("tax", tax);
        return Entity.filterJSONObject(object);
    }
}
