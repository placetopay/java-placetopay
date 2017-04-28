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
package com.placetopay.java_placetopay.Functionality;

import com.placetopay.java_placetopay.AppTest;
import com.placetopay.java_placetopay.Contracts.Gateway;
import com.placetopay.java_placetopay.Entities.Models.RedirectRequest;
import com.placetopay.java_placetopay.Entities.Models.RedirectResponse;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class RequestTest extends AppTest {

    protected String data = 
            "{  " +
            "   \"payment\":{  " +
            "      \"reference\":\"TESTING123456\"," +
            "      \"amount\":{  " +
            "         \"currency\":\"COP\"," +
            "         \"total\":\"10000\"" +
            "      }" +
            "   }," +
            "   \"returnUrl\":\"http:\\/\\/your.url.com\\/return?reference=TESTING123456\"" +
            "}";
    
    public void testItMakesASOAPPaymentRequest() {
        Gateway gateway = getGateway(Gateway.TP_SOAP);
        JSONObject object = addRequest(data);
        RedirectRequest request = new RedirectRequest(object);
        RedirectResponse response = gateway.request(request);
        assertEquals("Fail to request by SOAP", response.isSuccessful(), true);
    }
    
    public void testItMakesRESTPaymentRequest() {
        Gateway gateway = getGateway();
        JSONObject object = addRequest(data);
        RedirectRequest request = new RedirectRequest(object);
        RedirectResponse response = gateway.request(request);
        assertEquals("Fail to request by REST", response.isSuccessful(), true);
    }
}
