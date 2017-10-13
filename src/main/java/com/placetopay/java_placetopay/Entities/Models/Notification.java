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
package com.placetopay.java_placetopay.Entities.Models;

import com.placetopay.java_placetopay.Contracts.Entity;
import com.placetopay.java_placetopay.Entities.Status;
import com.placetopay.java_placetopay.Exceptions.BadPlaceToPayException;
import com.placetopay.java_placetopay.Exceptions.PlaceToPayException;
import com.placetopay.java_placetopay.Interfaces.HasStatus;
import com.placetopay.java_placetopay.Utils;
import java.security.NoSuchAlgorithmException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Notificación del estado de la petición
 * Ejemplo de notificación
 * <strong>
 * {
 *   "status": {
 *     "status": "APPROVED",
 *     "message": "Se ha aprobado su pago",
 *     "reason": "00",
 *     "date": "2016-09-15T13:49:01-05:00"
 *   },
 *   "requestId": 58,
 *   "reference": "ORDER-1000",
 *   "signature": "feb3e7cc76939c346f9640573a208662f30704ab"
 * }
 * </strong>
 * @author hernan_saldarriaga
 */
public class Notification extends Entity implements HasStatus {

    /**
     * Estado de esta solicitud, debe observar el estado interno de cada objeto.
     */
    protected Status status;
    /**
     * Identificador de la sesión a consultar.
     */
    protected final Integer requestId;
    /**
     * Referencia enviada por el comercio para la transacción
     */
    protected final String reference;
    /**
     * Firma para validar que la respuesta proviene de placetopay
     */
    protected final String signature;
    
    private String tranKey;

    /**
     * Normalmente no necesitas instanciar desde este constructor
     * @param status {@link Notification#status}
     * @param requestId {@link Notification#requestId}
     * @param reference {@link Notification#reference}
     * @param signature {@link Notification#signature}
     * @param tranKey {@link Notification#tranKey} 
     */
    public Notification(Status status, Integer requestId, String reference, String signature, String tranKey) {
        this.status = status;
        this.requestId = requestId;
        this.reference = reference;
        this.signature = signature;
        this.tranKey = tranKey;
    }
    
    /**
     * 
     * @param content json content
     * @param tranKey credential
     * @throws BadPlaceToPayException Si la estructura de la notificación no es correcta
     */
    public Notification(String content, String tranKey) throws BadPlaceToPayException {
        try {
            JSONObject object = new JSONObject(content);
            this.status = new Status(object.getJSONObject("status"));
            this.requestId = object.getInt("requestId");
            this.reference = object.getString("reference");
            this.signature = object.getString("signature");
            this.tranKey = tranKey;
        } catch(JSONException ex) {
            throw new BadPlaceToPayException(ex.getMessage());
        }
    }
    /**
     * 
     * @param object json content
     * @param tranKey credential
     * @throws BadPlaceToPayException Si la estructura de la notificación no es correcta
     */
    public Notification(JSONObject object, String tranKey) throws BadPlaceToPayException {
        this.status = new Status(object.getJSONObject("status"));
        this.requestId = object.getInt("requestId");
        this.reference = object.getString("reference");
        this.signature = object.getString("signature");
        this.tranKey = tranKey;
    }

    /**
     * Devuelve el parámetro requestId
     * @return {@link Notification#requestId}
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     * Devuelve el parámetro reference
     * @return {@link Notification#reference}
     */
    public String getReference() {
        return reference;
    }

    /**
     * Devuelve el parámetro signature
     * @return {@link Notification#signature}
     */
    public String getSignature() {
        return signature;
    }
        
    /**
     * Asigna el parámetro {@link Notification#status}
     * @param status {@link Notification#status}
     */
    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Devuelve el parámetro status
     * @return {@link Notification#status}
     */
    @Override
    public Status getStatus() {
        return this.status;
    }
    
    /**
     * 
     * @return Firma de la notificación
     */
    public String makeSignature() {
        try {
            return Utils.byteArrayToHexString(Utils.sha1(new StringBuilder().append(this.requestId)
                    .append(this.status.getStatus())
                    .append(this.status.getDate())
                    .append(this.tranKey).toString()));
        } catch (NoSuchAlgorithmException ex) {
            throw new PlaceToPayException(ex.getMessage());
        }
    }
    
    /**
     *
     * @return Si la firma es válido
     */
    public boolean isValidNotification() {
        return this.signature.equals(this.makeSignature());
    }
    
    /**
     *
     * @return Si el estado es aprobado
     */
    public boolean isApproved() {
        return this.status.getStatus().equals(Status.ST_APPROVED);
    }
    
    /**
     *
     * @return Si el estado es rechazado
     */
    public boolean isRejected() {
        return this.status.getStatus().equals(Status.ST_REJECTED);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("status", this.status != null ? this.status.toJsonObject() : null);
        object.put("requestId", this.requestId);
        object.put("reference", this.reference);
        object.put("signature", this.signature);
        return object;
    }
}
