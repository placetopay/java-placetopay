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
package com.placetopay.redirection.Entities.Models;

import com.placetopay.redirection.Contracts.Entity;
import com.placetopay.redirection.Entities.Status;
import com.placetopay.redirection.Interfaces.HasStatus;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class RedirectResponse extends Entity implements HasStatus {
    
    public int requestId;
    public String processUrl;
    protected Status status;

    public RedirectResponse(Status status) {
        this.status = status;
    }
    
    public RedirectResponse(JSONObject object) {
        this.requestId = object.has("requestId") ? object.getInt("requestId") : null;
        this.processUrl = object.has("processUrl") ? object.getString("processUrl") : null;
        this.status = object.has("status") ? new Status(object.getJSONObject("status")) : null;
    }

    public RedirectResponse(int requestId, String processUrl, Status status) {
        this.requestId = requestId;
        this.processUrl = processUrl;
        this.status = status;
    }

    public RedirectResponse(Status status, String format) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getRequestId() {
        return requestId;
    }

    public String getProcessUrl() {
        return processUrl;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
    
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
