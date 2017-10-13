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
 * Estructura que contiene información para el método de pago suscripción .
 * @author hernan_saldarriaga
 */
public class SubscriptionInformation extends Entity {
    
    /**
     * Esta cadena dicta el tipo de suscripción que se devuelve, puede ser [token, cuenta]
     */
    public String type;
    /**
     * Estado de la suscripción.
     */
    public Status status;
    /**
     * Acorde con el tipo de suscripción los valore retornados puede cambiar y serán devueltos en la estructura de NameValuePair.
     * token: [token, subtoken, franchise, franchiseName, lastDigits, validUntil]
     * account: [bankCode, bankName, accountType, accountNumber] 
     */
    public List<NameValuePair> instrument;
    
    public SubscriptionInformation(JSONObject object) {
        this(
                object.has("type") ? object.getString("type") : null,
                object.has("status") ? new Status(object.getJSONObject("status")) : null,
                object.has("instrument") ? setInstrument(object.get("instrument")) : null
        );
    }
    
    public SubscriptionInformation(String type, Status status, List<NameValuePair> instrument) {
        this.type = type;
        this.status = status;
        this.instrument = instrument;
    }

    /**
     * Devuelve el parámetro type
     * @return {@link SubscriptionInformation#type}
     */
    public String getType() {
        return type;
    }

    /**
     * Devuelve el parámetro status
     * @return {@link SubscriptionInformation#status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Devuelve el parámetro instrument
     * @return {@link SubscriptionInformation#instrument}
     */
    public List<NameValuePair> getInstrument() {
        return instrument;
    }

    public JSONArray instrumentToArray() {
        JSONArray jsona = new JSONArray();
        for (NameValuePair pair: instrument) {
            jsona.put(pair.toJsonObject());
        }
        return jsona;
    }
    /**
     * Retorna el instrumento definido en la subscripcion
     * @return Un ojecto de tipo {@link Token} o {@link Account}
     */
    public Entity parseInstrument() {
        List<NameValuePair> instrumentNVP = instrument;
        if (instrumentNVP == null)
            return null;
        JSONObject object = new JSONObject();
        object.put("status", this.getStatus().toJsonObject());
        for (NameValuePair pair: instrumentNVP) {
            object.put(pair.getKeyword(), pair.getValue());
        }
        if (type.equals("token"))
            return new Token(object);
        else if (type.equals("acccount")) 
            return new Account(object);
        return null;
    }
    
    private static List<NameValuePair> setInstrument(Object json) {
        return Utils.convertToList(json, "item", NameValuePair.class);
    }
    
    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("type", type);
        object.put("status", status == null ? null : status.toJsonObject());
        object.put("instrument", instrument == null ? null : instrumentToArray());
        return Entity.filterJSONObject(object);
    }
}
