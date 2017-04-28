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
import com.placetopay.java_placetopay.Interfaces.HasStatus;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class RedirectResponse extends Entity implements HasStatus {
    /**
     * Identificador de la sesión a consultar.
     */
    public Integer requestId;
    /**
     * URL para redireccionar el cliente para completar el proceso
     */
    public String processUrl;
    /**
     * Estado de esta solicitud
     */
    protected Status status;

    public RedirectResponse(Status status) {
        this.status = status;
    }
    
    public RedirectResponse(JSONObject object) {
        this.requestId = object.has("requestId") ? object.getInt("requestId") : null;
        this.processUrl = object.has("processUrl") ? object.getString("processUrl") : null;
        this.status = object.has("status") ? new Status(object.getJSONObject("status")) : null;
    }

    public RedirectResponse(Integer requestId, String processUrl, Status status) {
        this.requestId = requestId;
        this.processUrl = processUrl;
        this.status = status;
    }

    public RedirectResponse(Status status, String format) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Devuelve el parámetro requestId
     * @return {@link RedirectResponse#requestId}
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     * Devuelve el parámetro processUrl
     * @return {@link RedirectResponse#processUrl}
     */
    public String getProcessUrl() {
        return processUrl;
    }

    /**
     * Devuelve el parámetro status
     * @return {@link RedirectResponse#status}
     */
    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
    
    /**
     * 
     * @return Si la respuesta es exitosa
     */
    public boolean isSuccessful() {
        return this.status.getStatus().equals(Status.ST_OK);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("status", status == null ? null : status.toJsonObject());
        object.put("requestId", requestId);
        object.put("processUrl", processUrl);
        return Entity.filterJSONObject(object);
    }
}
