/*
 * The MIT License
 *
 * Copyright 2017 Place to Pay.
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author hernan_saldarriaga
 */
public class TransactionTest extends AppTest {
    
    private final String data = 
        "{" +
            "\"status\":{" +
                "\"status\":\"APPROVED\"," +
                "\"reason\":\"00\"," +
                "\"message\":\"Aprobada\"," +
                "\"date\":\"2017-05-10T12:36:36-05:00\"" +
            "}," +
            "\"internalReference\":1447325855," +
            "\"paymentMethod\":\"card\"," +
            "\"paymentMethodName\":\"Visa\"," +
            "\"issuerName\":\"BANCO DE PRUEBAS\"," +
            "\"amount\":{" +
                "\"from\":{" +
                    "\"currency\":\"COP\"," +
                    "\"total\":178100" +
                "}," +
                "\"to\":{" +
                    "\"currency\":\"COP\"," +
                    "\"total\":178100" +
                "}," +
                "\"factor\":1" +
            "}," +
            "\"authorization\":\"000000\"," +
            "\"reference\":\"TEST_20170510_173143\"," +
            "\"receipt\":\"1494437796\"," +
            "\"franchise\":\"CR_VS\"," +
            "\"refunded\":false," +
            "\"processorFields\":[" +
                "{" +
                    "\"keyword\":\"lastDigits\"," +
                    "\"value\":\"****1111\"," +
                    "\"displayOn\":\"none\"" +
                "}," +
                "{" +
                    "\"keyword\":\"id\"," +
                    "\"value\":\"25cdbc2252d6e70480c969580c7edb32\"," +
                    "\"displayOn\":\"none\"" +
                "}" +
            "]" +
        "}";

    public void testItParsesTheDataCorrectly()
    {
        JSONObject object = new JSONObject(data);
        Transaction transaction = new Transaction(object);
        assertEquals(Status.ST_APPROVED, transaction.getStatus().getStatus());
        assertTrue(transaction.isSuccessful());
        assertTrue(transaction.isApproved());
        
        JSONObject additionalData = new JSONObject("{" +
                    "\"lastDigits\":\"****1111\"," +
                    "\"id\":\"25cdbc2252d6e70480c969580c7edb32\"" +
                "}"
        );
        JSONAssert.assertEquals(additionalData, transaction.additionalData(), false);
        
        JSONArray processorFields = new JSONArray("[" +
                "{" +
                    "\"keyword\":\"lastDigits\"," +
                    "\"value\":\"****1111\"," +
                    "\"displayOn\":\"none\"" +
                "}," +
                "{" +
                    "\"keyword\":\"id\"," +
                    "\"value\":\"25cdbc2252d6e70480c969580c7edb32\"," +
                    "\"displayOn\":\"none\"" +
                "}" +
            "]"
        );
        JSONAssert.assertEquals(processorFields, transaction.processorFieldsToArray(), false);
    }
}
