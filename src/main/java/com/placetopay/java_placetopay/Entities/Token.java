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
 * Estructura que contiene información acerca de un token usado para cobros de un cliente suscrito.
 * @author hernan_saldarriaga
 */
public class Token extends Entity {
    /**
     * Estado del proceso de tokenización
     */
    protected Status status;
    /**
     * Token completo para tarjeta de crédito, debe ser usada para solicitar cualquier transacción a Place to Pay
     */
    protected String token;
    /**
     * Representación numérica del token para casos donde es requerido un número adicional que parece como una tarjeta de crédito, los últimos 4 dígitos son iguales a los últimos 4 dígitos de la tarjeta de crédito.
     */
    protected String subtoken;
    /**
     * Franquicia de la tarjeta tokenizada
     */
    protected String franchiseName;
    /**
     * Nombre del banco emisor
     */
    protected String issuerName;
    /**
     * Últimos 4 dígitos de la tarjeta de crédito
     */
    protected String lastDigits;
    /**
     * Fecha hasta la cual el token es válido, puede  ser determinada por la  fecha de expiración.
     */
    protected String validUntil;
    
    // Just in case the token will be utilized
    
    /**
     * Número de cuotas en las cuales se solicita el cobro (opcional)
     */
    protected String cvv;
    /**
     * Dígitos del código de seguridad de la tarjeta a usar en los casos en los que sea necesario, generalmente se deja en blanco si se tiene una terminal sin validación de CVV
     */
    protected String installments;

    public Token(JSONObject object) {
        this(
                object.has("token") ? object.getString("token") : null,
                object.has("subtoken") ? object.getString("subtoken") : null,
                object.has("franchiseName") ? object.getString("franchiseName") : null,
                object.has("issuerName") ? object.getString("issuerName") : null,
                object.has("lastDigits") ? object.getString("lastDigits") : null,
                object.has("validUntil") ? object.getString("validUntil") : null,
                object.has("cvv") ? object.getString("cvv") : null,
                object.has("installments") ? object.getString("installments") : null
        );
        if (object.has("status"))
            status = new Status(object.getJSONObject("status"));
    }
    
    public Token(String token, String subtoken, String franchiseName, String issuerName, String lastDigits, String validUntil, String cvv, String installments, Status status) {
        this(token, subtoken, franchiseName, issuerName, lastDigits, validUntil, cvv, installments);
        this.status = status;
    }
    /**
     * Crea una nueva instance de {@link Token}
     * @param token {@link Token#token}
     * @param subtoken {@link Token#subtoken}
     * @param franchiseName {@link Token#franchiseName}
     * @param issuerName {@link Token#issuerName}
     * @param lastDigits {@link Token#lastDigits}
     * @param validUntil {@link Token#validUntil}
     * @param cvv {@link Token#cvv}
     * @param installments {@link Token#installments} 
     */
    public Token(String token, String subtoken, String franchiseName, String issuerName, String lastDigits, String validUntil, String cvv, String installments) {
        this.token = token;
        this.subtoken = subtoken;
        this.franchiseName = franchiseName;
        this.issuerName = issuerName;
        this.lastDigits = lastDigits;
        this.validUntil = validUntil;
        this.cvv = cvv;
        this.installments = installments;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    /**
     * Devuelve el parámetro status
     * @return {@link Token#status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Devuelve el parámetro token
     * @return {@link Token#token}
     */
    public String getToken() {
        return token;
    }
    
    /**
     * Devuelve el parámetro subtoken
     * @return {@link Token#subtoken}
     */
    public String getSubtoken() {
        return subtoken;
    }

    /**
     * Devuelve el parámetro franchiseName
     * @return {@link Token#franchiseName}
     */
    public String getFranchiseName() {
        return franchiseName;
    }

    /**
     * Devuelve el parámetro issuerName
     * @return {@link Token#issuerName}
     */
    public String getIssuerName() {
        return issuerName;
    }

    /**
     * Devuelve el parámetro lastDigits
     * @return {@link Token#lastDigits}
     */
    public String getLastDigits() {
        return lastDigits;
    }

    /**
     * Devuelve el parámetro validUntil
     * @return {@link Token#validUntil}
     */
    public String getValidUntil() {
        return validUntil;
    }

    /**
     * Devuelve el parámetro cvv
     * @return {@link Token#cvv}
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Devuelve el parámetro installments
     * @return {@link Token#installments}
     */
    public String getInstallments() {
        return installments;
    }
    
    /**
     *
     * @return Si el estado es exitoso
     */
    public boolean isSuccessful() {
        return this.status.getStatus().equals(Status.ST_OK);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("status", status == null ? null : status.toJsonObject());
        object.put("token", token);
        object.put("subtoken", subtoken);
        object.put("franchiseName", franchiseName);
        object.put("issuerName", issuerName);
        object.put("lastDigits", lastDigits);
        object.put("validUntil", validUntil);
        object.put("cvv", cvv);
        object.put("installments", installments);
        return Entity.filterJSONObject(object);
    }
}
