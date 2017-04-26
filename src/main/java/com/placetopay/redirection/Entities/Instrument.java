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
public class Instrument extends Entity {
    
    protected Bank bank;
    protected Card card;
    
    protected Token token;
    protected String pin;
    protected String password;

    public Instrument(JSONObject object) {
        if (object.has("bank"))
            bank = new Bank(object.getJSONObject("bank"));
        if (object.has("card"))
            card = new Card(object.getJSONObject("card"));
        if (object.has("token"))
            token = new Token(object.getJSONObject("token"));
        if (object.has("pin"))
            pin = object.getString("pin");
        if (object.has("password"))
            password = object.getString("password");
    }    
    
    public Instrument(Bank bank, Card card, Token token, String pin, String password) {
        this.pin = pin;
        this.password = password;
        this.bank = bank;
        this.card = card;
        this.token = token;
    }

    public Bank getBank() {
        return bank;
    }

    public Card getCard() {
        return card;
    }

    public Token getToken() {
        return token;
    }

    public String getPin() {
        return pin;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("bank", bank == null ? null : bank.toJsonObject());
        object.put("card", card == null ? null : card.toJsonObject());
        object.put("token", token == null ? null : token.toJsonObject());
        return Entity.filterJSONObject(object);
    }
}
