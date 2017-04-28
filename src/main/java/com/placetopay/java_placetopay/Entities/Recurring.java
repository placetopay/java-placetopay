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
 * Estructura que contiene la información requerida para una solicitud de pago recurrente
 * @author hernan_saldarriaga
 */
class Recurring extends Entity {
    /**
     * Periodicidad de la factura
     * Y = annual  M = monthly  D = daily
     */
    protected String periodicity;
    /**
     * Periodicidad de la factura
     */
    protected Integer interval;
    /**
     * Periodicidad de la factura
     */
    protected String nextPayment;
    /**
     * Número máximo de periodo (-1 en caso de que no haya  restricción.)
     * 
     * Depends on the number of times that it makes the charge, corresponds to maximum times that the recurrence
     * will happen. If you do not want to set up should indicated -1.
     * You must specify this parameter or dueDate
     */
    protected Integer maxPeriods;
    /**
     * Fecha para finalizar el pago
     */
    protected String dueDate;
    /**
     * URL en el que el servicio notificará cada vez que se haga un pago recurrente
     */
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
     * Crea una instancia de {@link Recurring}
     * @param periodicity {@link Recurring#periodicity}
     * @param interval {@link Recurring#interval}
     * @param nextPayment {@link Recurring#nextPayment} in Y-m-d format
     * @param maxPeriods {@link Recurring#maxPeriods}
     * @param dueDate {@link Recurring#dueDate} in Y-m-d format
     * @param notificationUrl {@link Recurring#notificationUrl}
     */
    public Recurring(String periodicity, Integer interval, String nextPayment, Integer maxPeriods, String dueDate, String notificationUrl) {
        this.periodicity = periodicity;
        this.interval = interval;
        this.nextPayment = nextPayment;
        this.maxPeriods = maxPeriods;
        this.dueDate = dueDate;
        this.notificationUrl = notificationUrl;
    }
    /**
     * Devuelve el parámetro periodicity
     * @return {@link Recurring#periodicity}
     */
    public String getPeriodicity() {
        return periodicity;
    }
    /**
     * Devuelve el parámetro interval
     * @return {@link Recurring#interval}
     */
    public Integer getInterval() {
        return interval;
    }
    /**
     * Devuelve el parámetro nextPayment
     * @return {@link Recurring#nextPayment}
     */
    public String getNextPayment() {
        return nextPayment;
    }
    /**
     * Devuelve el parámetro maxPeriods
     * @return {@link Recurring#maxPeriods}
     */
    public Integer getMaxPeriods() {
        return maxPeriods;
    }
    /**
     * Devuelve el parámetro dueDate
     * @return {@link Recurring#dueDate}
     */
    public String getDueDate() {
        return dueDate;
    }
    /**
     * Devuelve el parámetro notificationUrl
     * @return {@link Recurring#notificationUrl}
     */
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
