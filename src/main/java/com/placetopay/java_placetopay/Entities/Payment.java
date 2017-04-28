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
 * Estructura que contiene la información acerca del pago de la transacción requerida al servicio web.
 * @author hernan_saldarriaga
 */
public class Payment extends EntityWithNameValuePair {
    
    protected String reference;
    protected String description;
    protected Amount amount;
    protected boolean allowPartial = false;
    protected Person shipping;
    protected List<Item> items;
    protected Recurring recurring;
    protected Instrument instrument;

    
    public Payment(JSONObject object) {
        super(object);
        this.reference = object.has("reference") ? object.getString("reference") : null;
        this.allowPartial = object.has("allowPartial") ? object.getBoolean("allowPartial") : false;
        this.description = object.has("description") ? object.getString("description") : null;
        this.amount = object.has("amount") ? new Amount(object.getJSONObject("amount")) : null;
        this.shipping = object.has("shipping") ? new Person(object.getJSONObject("shipping")) : null;
        this.items = object.has("items") ? convertToItems(object.get("items")) : null;
        this.recurring = object.has("recurring") ? new Recurring(object.getJSONObject("recurring")) : null;
        this.instrument = object.has("instrument") ? new Instrument(object.getJSONObject("instrument")) : null;
    }
    /**
     * Crea una nueva instancia de {@link Payment}
     * @param reference Única referencia para la solicitud de pago.
     * @param description Descripción de la cuenta.
     * @param amount Monto a ser cobrado
     * @param allowPartial Define si el monto a ser cobrado puede ser parcial.
     * @param shipping Información de la persona quien recibe el producto o servicio en la transacción.
     * @param items Productos relacionados con esta solicitud de pago.
     * @param recurring Información recurrente cuando Place to Pay procesa un pago recurrente.
     * @param instrument Contiene los detalles de un medio de pago suscrito.
     */
    public Payment(String reference, String description, Amount amount, boolean allowPartial, Person shipping, List<Item> items, Recurring recurring, Instrument instrument) {
        this.reference = reference;
        this.description = description;
        this.amount = amount;
        this.allowPartial = allowPartial;
        this.shipping = shipping;
        this.items = items;
        this.recurring = recurring;
        this.instrument = instrument;
    }
    
    public String getReference() {
        return reference;
    }

    public String getDescription() {
        return description;
    }

    public Amount getAmount() {
        return amount;
    }

    public boolean isAllowPartial() {
        return allowPartial;
    }

    public Person getShipping() {
        return shipping;
    }

    public List<Item> getItems() {
        return items;
    }

    public Recurring getRecurring() {
        return recurring;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public JSONArray itemToJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (Item item : items) {
            jsonArray.put(item.toJsonObject());
        }
        return jsonArray;
    }
    
    private static List<Item> convertToItems(Object json) {
        return Utils.convertToList(json, "items", Item.class);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("reference", reference);
        object.put("description", description);
        object.put("amount", amount == null ? null : amount.toJsonObject());
        object.put("allowPartial", allowPartial);
        object.put("shipping", shipping == null ? null : shipping.toJsonObject());
        object.put("items", items == null ? null : itemToJsonArray());
        object.put("recurring", recurring == null ? null : recurring.toJsonObject());
        object.put("instrument", instrument == null ? null : instrument.toJsonObject());
        object.put("fields", fieldsToArrayObject());
        return Entity.filterJSONObject(object);
    }
}
