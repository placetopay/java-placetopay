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
import com.placetopay.java_placetopay.Entities.Models.ReverseResponse;
import static junit.framework.TestCase.assertEquals;

/**
 *
 * @author hernan_saldarriaga
 */
public class ReverseTest extends AppTest {
    
    public void testItSOAPReverseATransaction() {
        Gateway gateway = getGateway(Gateway.TP_SOAP);
        ReverseResponse response = gateway.reverse("1442625531");
        assertEquals("Fail to reverse by SOAP", response.isSuccessful(), true);
    }
    
    public void testItRESTReverseATransaction() {
        Gateway gateway = getGateway(Gateway.TP_SOAP);
        ReverseResponse response = gateway.reverse("1442625531");
        assertEquals("Fail to reverse by REST", response.isSuccessful(), true);
    }
}
