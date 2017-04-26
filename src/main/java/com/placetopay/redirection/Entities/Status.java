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
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class Status extends Entity {
    
    public static final String ST_OK = "OK";
    public static final String ST_FAILED = "FAILED";
    public static final String ST_APPROVED = "APPROVED";
    public static final String ST_APPROVED_PARTIAL = "APPROVED_PARTIAL";
    public static final String ST_REJECTED = "REJECTED";
    public static final String ST_PENDING = "PENDING";
    public static final String ST_PENDING_VALIDATION = "PENDING_VALIDATION";
    public static final String ST_REFUNDED = "REFUNDED";
    public static final String ST_ERROR = "ERROR";
    public static final String ST_UNKNOWN = "UNKNOWN";
    
    protected String status;
    
    protected String reason;
    
    protected String message;
    
    protected String date;

    protected static String[] STATUSES = {
        ST_OK,
        ST_FAILED,
        ST_APPROVED,
        ST_APPROVED_PARTIAL,
        ST_REJECTED,
        ST_PENDING,
        ST_PENDING_VALIDATION,
        ST_REFUNDED,
        ST_ERROR,
        ST_UNKNOWN
    };
    
    public Status(String content) throws JSONException {
        this(new JSONObject(content));
    }
    
    public Status(JSONObject object) {
        this.status = object.getString("status");
        this.reason = object.get("reason").toString();
        this.message = object.getString("message");
        this.date = object.getString("date");
    }

    public Status(String status, String reason, String message, String date) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.date = date;
    }

    public Status(String ST_ERROR, String wr, String readException) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
    
    public boolean isFailed()
    {
        return this.status.equals(ST_FAILED);
    }

    public boolean isSuccessful()
    {
        return this.status.equals(ST_OK);
    }

    public boolean isApproved()
    {
        return this.status.equals(ST_APPROVED);
    }

    public boolean isRejected()
    {
        return this.status.equals(ST_REJECTED);
    }

    public boolean isError()
    {
        return this.status.equals(ST_ERROR);
    }
    
    public static boolean validStatus(String status) {
        if (status != null)
            return Arrays.asList(STATUSES).contains(status);
        return false;
    }
    
    public static String[] validStatus() {
        return STATUSES;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("status", this.status);
        object.put("reason", this.reason);
        object.put("message", this.message);
        object.put("date", this.date);
        return object;
    }
}
