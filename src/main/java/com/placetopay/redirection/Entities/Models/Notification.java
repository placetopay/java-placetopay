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
import com.placetopay.redirection.Exceptions.PlaceToPayException;
import com.placetopay.redirection.Interfaces.HasStatus;
import com.placetopay.redirection.Utils;
import java.security.NoSuchAlgorithmException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class Notification extends Entity implements HasStatus {

    protected Status status;
    protected final int requestId;
    protected final String reference;
    protected final String signature;
    
    private String tranKey;

    public Notification(Status status, int requestId, String reference, String signature, String tranKey) {
        this.status = status;
        this.requestId = requestId;
        this.reference = reference;
        this.signature = signature;
        this.tranKey = tranKey;
    }
    
    public Notification(String content, String tranKey) throws JSONException {
        this(new JSONObject(content), tranKey);
    }
    
    public Notification(JSONObject object, String tranKey) {
        this(
                new Status(object.getJSONObject("status")) , 
                object.getInt("requestId"), 
                object.getString("reference"), 
                object.getString("signature"),
                tranKey
        );
    }

    public int getRequestId() {
        return requestId;
    }

    public String getReference() {
        return reference;
    }

    public String getSignature() {
        return signature;
    }
        
    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
    
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
    
    public boolean isValidNotification() {
        return this.signature.equals(this.makeSignature());
    }
    
    public boolean isApproved() {
        return this.status.getStatus().equals(Status.ST_APPROVED);
    }
    
    public boolean isRejected() {
        return this.status.getStatus().equals(Status.ST_REJECTED);
    }

    @Override
    public JSONObject toJsonObject() {
        return new JSONObject();
    }
}
