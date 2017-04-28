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
import com.placetopay.java_placetopay.Carrier.Authentication;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class AuthenticationTest extends AppTest {

    public void testItCreatesTheAuthenticationCorrectly() {
        Authentication auth = new Authentication(
                "login",
                "ABCD1234",
                new Authentication.Parameter("2016-10-26T21:37:00+00:00", "ifYEPnAcJbpDVR1t")
        );
        
        JSONObject object = auth.toJsonObject();
        assertEquals("Login matches", "login", object.getString("login"));
        assertEquals("Seed matches", "2016-10-26T21:37:00+00:00", object.getString("seed"));
        assertEquals("Nonce matches", "aWZZRVBuQWNKYnBEVlIxdA==", object.get("nonce"));
        assertEquals("Trankey matches", "Xi5xrRwrqPU21WE2JI4hyMaCvQ8=", object.getString("tranKey"));
    }
}
