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
class Recurring extends Entity {
    /**
     * Frequency to resubmit the transaction.
     * Y = annual  M = monthly  D = daily
     */
    protected String periodicity;
    protected int interval;
    protected String nextPayment;
    /**
     * Depends on the number of times that it makes the charge, corresponds to maximum times that the recurrence
     * will happen. If you do not want to set up should indicated -1.
     * You must specify this parameter or dueDate
     */
    protected int maxPeriods;
    protected String dueDate;
    protected String notificationUrl;
    
    public Recurring(JSONObject object) {
        periodicity = object.has("periodicity") ? object.getString("periodicity") : null;
        interval = object.has("interval") ? object.getInt("interval") : 0;
        maxPeriods = object.has("maxPeriods") ? object.getInt("maxPeriods") : 0;
        notificationUrl = object.has("notificationUrl") ? object.getString("notificationUrl") : null;
        
        nextPayment = object.has("nextPayment") ? object.getString("nextPayment") : null;
        dueDate = object.has("dueDate") ? object.getString("dueDate") : null;
    }

    /**
     * 
     * @param periodicity
     * @param interval
     * @param nextPayment in Y-m-d format
     * @param maxPeriods
     * @param dueDate in Y-m-d format
     * @param notificationUrl
     */
    public Recurring(String periodicity, int interval, String nextPayment, int maxPeriods, String dueDate, String notificationUrl) {
        this.periodicity = periodicity;
        this.interval = interval;
        this.nextPayment = nextPayment;
        this.maxPeriods = maxPeriods;
        this.dueDate = dueDate;
        this.notificationUrl = notificationUrl;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public int getInterval() {
        return interval;
    }

    public String getNextPayment() {
        return nextPayment;
    }

    public int getMaxPeriods() {
        return maxPeriods;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("periodicity", periodicity);
        object.put("interval", interval);
        object.put("nextPayment", nextPayment);
        object.put("maxPeriods", maxPeriods);
        object.put("dueDate", dueDate);
        object.put("notificationUrl", notificationUrl);
        return Entity.filterJSONObject(object);
    }
}
