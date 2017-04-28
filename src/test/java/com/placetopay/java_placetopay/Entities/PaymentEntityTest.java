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

import com.placetopay.java_placetopay.AppTest;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author hernan_saldarriaga
 */
public class PaymentEntityTest extends AppTest {
        
    public void testIfParsesTheDataCorrectly() {
        String data = 
        "{  " +
        "   \"reference\":\"required\"," +
        "   \"amount\":{  " +
        "      \"total\":10000," +
        "      \"currency\":\"COP\"" +
        "   }," +
        "   \"fields\":[  " +
        "      {  " +
        "         \"keyword\":\"no_empty\"," +
        "         \"value\":\"no_empty_value\"," +
        "         \"displayOn\":\"none\"" +
        "      }" +
        "   ]," +
        "   \"items\":[  " +
        "      {  " +
        "         \"sku\":\"1234\"," +
        "         \"name\":\"Testing1\"" +
        "      }," +
        "      {  " +
        "         \"sku\":\"1111\"," +
        "         \"name\":\"Testing2\"" +
        "      }" +
        "   ]," +
        "   \"allowPartial\":false" +
        "}";
        JSONObject object = new JSONObject(data);
        Payment payment = new Payment(object);
        assertEquals(1, payment.fields.size());
        JSONAssert.assertEquals(object, payment.toJsonObject(), false);
    }
}
