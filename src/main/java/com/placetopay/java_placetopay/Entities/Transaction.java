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
 *
 * @author hernan_saldarriaga
 */
public class Transaction extends Entity {
    /**
     * Estado de la transacción.
     */
    protected Status status;
    /**
     * Referencia enviada por el comercio para la transacción
     */
    protected String reference;
    /**
     * Referencia interna en Place to Pay
     */
    protected Integer internalReference;
    /**
     * Código del método de pago utilizado
     */
    protected String paymentMethod;
    /**
     * Nombre del método de pago utilizado 
     */
    protected String paymentMethodName;
    /**
     * Nombre del emisor o del procesador
     */
    protected String issuerName;
    /**
     * Valor procesado
     */
    protected AmountConversion amount;
    /**
     * Código de autorización
     */
    protected String authorization;
    /**
     * Numero de recibo de la transacción
     */
    protected Long receipt;
    /**
     * Código de la franquicia en redirección.
     */
    protected String franchise;
    /**
     * Indica si la transacción es una devolución o no
     */
    protected boolean refunded = false;
    /**
     * Campos adicionales del procesador
     */
    protected List<NameValuePair> processorFields;

    public Transaction(JSONObject object) {
        this.status = object.has("status") ? new Status(object.getJSONObject("status")) : null;
        this.reference = object.has("reference") ? object.getString("reference") : null;
        this.internalReference = object.has("internalReference") ? object.getInt("internalReference") : null;
        this.paymentMethod = object.has("paymentMethod") ? object.getString("paymentMethod") : null;
        this.paymentMethodName = object.has("paymentMethodName") ? object.getString("paymentMethodName") : null;
        this.issuerName = object.has("issuerName") ? object.getString("issuerName") : null;
        this.amount = object.has("amount") ? new AmountConversion(object.getJSONObject("amount")) : null;
        this.authorization = object.has("authorization") ? object.getString("authorization") : null;
        this.receipt = object.has("receipt") ? object.getLong("receipt") : null;
        this.franchise = object.has("franchise") ? object.getString("franchise") : null;
        this.refunded = object.has("refunded") ? object.getBoolean("refunded") : false;
        this.processorFields = object.has("processorFields") ? setProcessorFields(object.get("processorFields")) : null;
    }
        
    public Transaction(Status status, String reference, Integer internalReference, String paymentMethod, String paymentMethodName, String issuerName, AmountConversion amount, String authorization, Long receipt, String franchise, boolean refunded, List<NameValuePair> processorFields) {
        this.status = status;
        this.reference = reference;
        this.internalReference = internalReference;
        this.paymentMethod = paymentMethod;
        this.paymentMethodName = paymentMethodName;
        this.issuerName = issuerName;
        this.amount = amount;
        this.authorization = authorization;
        this.receipt = receipt;
        this.franchise = franchise;
        this.refunded = refunded;
        this.processorFields = processorFields;
    }

    /**
     * Devuelve el parámetro status
     * @return {@link Transaction#status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Devuelve el parámetro reference
     * @return {@link Transaction#reference}
     */
    public String getReference() {
        return reference;
    }

    /**
     * Devuelve el parámetro internalReference
     * @return {@link Transaction#internalReference}
     */
    public Integer getInternalReference() {
        return internalReference;
    }

    /**
     * Devuelve el parámetro paymentMethod
     * @return {@link Transaction#paymentMethod}
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Devuelve el parámetro paymentMethodName
     * @return {@link Transaction#paymentMethodName}
     */
    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    /**
     * Devuelve el parámetro issuerName
     * @return {@link Transaction#issuerName}
     */
    public String getIssuerName() {
        return issuerName;
    }

    /**
     * Devuelve el parámetro amount
     * @return {@link Transaction#amount}
     */
    public AmountConversion getAmount() {
        return amount;
    }

    /**
     * Devuelve el parámetro authorization
     * @return {@link Transaction#authorization}
     */
    public String getAuthorization() {
        return authorization;
    }

    /**
     * Devuelve el parámetro receipt
     * @return {@link Transaction#receipt}
     */
    public Long getReceipt() {
        return receipt;
    }

    /**
     * Devuelve el parámetro franchise
     * @return {@link Transaction#franchise}
     */
    public String getFranchise() {
        return franchise;
    }

    /**
     *
     * @return Si la transacción es un reverso
     */
    public boolean isRefunded() {
        return refunded;
    }

    /**
     * Devuelve el parámetro processorFields
     * @return {@link Transaction#processorFields}
     */
    public List<NameValuePair> getProcessorFields() {
        return processorFields;
    }
    
    /**
     *
     * @param base
     */
    public void setAmountBase(JSONObject base) {
        AmountBase base1 = new AmountBase(base);
        amount = new AmountConversion().setAmountBase(base1);
    }
    
    /**
     *
     * @return Verifica que no hubo un error en la transacción
     */
    public boolean isSuccessful() {
        return this.status != null && !this.status.getStatus().equals(Status.ST_ERROR);
    }
    
    public JSONArray processorFieldsToArray() {
        JSONArray jsona = new JSONArray();
        for (NameValuePair pair: processorFields) {
            jsona.put(pair.toJsonObject());
        }
        return jsona;
    }
    
    private static List<NameValuePair> setProcessorFields(Object json) {
        return Utils.convertToList(json, "item", NameValuePair.class);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("status", status == null ? null : status.toJsonObject());
        object.put("internalReference", internalReference);
        object.put("paymentMethod", paymentMethod);
        object.put("paymentMethodName", paymentMethodName);
        object.put("issuerName", issuerName);
        object.put("amount", amount == null ? null : amount.toJsonObject());
        object.put("authorization", authorization);
        object.put("reference", reference);
        object.put("receipt", receipt);
        object.put("franchise", franchise);
        object.put("refunded", refunded);
        object.put("processorFields", processorFields == null ? null : processorFieldsToArray());
        return Entity.filterJSONObject(object);
    }
}
