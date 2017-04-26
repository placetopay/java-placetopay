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
package com.placetopay.redirection.Contracts;

import com.placetopay.redirection.Carrier.Authentication;
import com.placetopay.redirection.Entities.Models.CollectRequest;
import com.placetopay.redirection.Entities.Models.RedirectInformation;
import com.placetopay.redirection.Entities.Models.RedirectRequest;
import com.placetopay.redirection.Entities.Models.RedirectResponse;
import com.placetopay.redirection.Entities.Models.ReverseResponse;
import com.placetopay.redirection.Exceptions.BadPlaceToPayException;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public abstract class Carrier {
    
    protected Authentication auth;
    protected Configuration config;
    
    public Carrier(Authentication auth, Configuration config) {
        this.auth = auth;
        this.config = config;
    }

    public Authentication getAuth() {
        return auth;
    }

    public Configuration getConfig() {
        return config;
    }
    
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("auth", this.auth);
        object.put("config", this.config);
        return object;
    }
    
    public abstract RedirectResponse request(RedirectRequest redirectRequest);

    public abstract RedirectInformation query(String requestId);

    public abstract RedirectInformation collect(CollectRequest collectRequest);

    public abstract ReverseResponse reverse(String transactionId);
}
