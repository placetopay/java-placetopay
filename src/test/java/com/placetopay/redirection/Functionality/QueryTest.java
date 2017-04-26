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
package com.placetopay.redirection.Functionality;

import com.placetopay.redirection.AppTest;
import com.placetopay.redirection.Contracts.Gateway;
import com.placetopay.redirection.Entities.Models.RedirectInformation;

/**
 *
 * @author hernan_saldarriaga
 */
public class QueryTest extends AppTest {
    
    public void testItSOAPQueryRequestInformation() {
        Gateway gateway = getGateway(Gateway.TP_SOAP);
        RedirectInformation info = gateway.query("60");
        assertEquals("Fail to query by SOAP", info.isSuccessful(), true);
    }
    
    public void testItRESTQueryRequestInformation() {
        Gateway gateway = getGateway();
        RedirectInformation info = gateway.query("60");
        assertEquals("Fail to query by REST", info.isSuccessful(), true);
    }
}
