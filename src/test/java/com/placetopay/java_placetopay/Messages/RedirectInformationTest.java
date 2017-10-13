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
package com.placetopay.java_placetopay.Messages;

import com.placetopay.java_placetopay.AppTest;
import com.placetopay.java_placetopay.Entities.Models.RedirectInformation;
import com.placetopay.java_placetopay.Entities.Status;
import com.placetopay.java_placetopay.Entities.Token;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author hernan_saldarriaga
 */
public class RedirectInformationTest extends AppTest {
    
    public void testItParsesARestUpdatedResponse()
    {
        String data = "{\"requestId\":371,\"status\":{\"status\":\"PENDING\",\"reason\":\"PT\",\"message\":\"La petici\\u00f3n se encuentra pendiente\",\"date\":\"2017-05-17T15:57:44-05:00\"},\"request\":{\"locale\":\"es_CO\",\"payer\":{\"document\":\"1040035000\",\"documentType\":\"CC\",\"name\":\"Diego\",\"surname\":\"Calle\",\"email\":\"dnetix@gmail.com\",\"mobile\":\"3006108399\",\"address\":{\"street\":\"123 Main Street\",\"city\":\"Chesterfield\",\"postalCode\":\"63017\",\"country\":\"US\"}},\"buyer\":{\"document\":\"1040035000\",\"documentType\":\"CC\",\"name\":\"Aisha\",\"surname\":\"Nikolaus\",\"email\":\"dcallem88@msn.com\",\"mobile\":\"3006108300\"},\"payment\":{\"reference\":\"TEST_20170517_205552\",\"description\":\"Ut aut consequatur maxime doloremque iure voluptatem omnis.\",\"amount\":{\"currency\":\"USD\",\"total\":\"178\"},\"allowPartial\":false},\"subscription\":null,\"fields\":null,\"returnUrl\":\"http:\\/\\/redirect.p2p.dev\\/client\",\"paymentMethod\":null,\"cancelUrl\":null,\"ipAddress\":\"127.0.0.1\",\"userAgent\":\"Mozilla\\/5.0 (X11; Linux x86_64) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/57.0.2987.98 Safari\\/537.36\",\"expiration\":\"2017-05-18T20:55:52+00:00\",\"captureAddress\":false,\"skipResult\":false,\"noBuyerFill\":false},\"payment\":[{\"status\":{\"status\":\"REJECTED\",\"reason\":\"01\",\"message\":\"Negada, Transacci\\u00f3n declinada\",\"date\":\"2017-05-17T15:56:37-05:00\"},\"internalReference\":1447498827,\"paymentMethod\":\"masterpass\",\"paymentMethodName\":\"MasterCard\",\"amount\":{\"from\":{\"currency\":\"USD\",\"total\":178},\"to\":{\"currency\":\"COP\",\"total\":511433.16},\"factor\":2873.22},\"authorization\":\"000000\",\"reference\":\"TEST_20170517_205552\",\"receipt\":\"1495054597\",\"franchise\":\"RM_MC\",\"refunded\":false,\"processorFields\":[{\"keyword\":\"lastDigits\",\"value\":\"****0206\",\"displayOn\":\"none\"},{\"keyword\":\"id\",\"value\":\"e6bc23b9f16980bc3e5422dbb6218f59\",\"displayOn\":\"none\"}]}],\"subscription\":null}";
        JSONObject object = new JSONObject(data);
        cleanResponse(object);
        RedirectInformation information = new RedirectInformation(object);

        assertEquals(new Integer(371), information.getRequestId());
        assertEquals(Status.ST_PENDING, information.getStatus().getStatus());

        assertTrue(information.isSuccessful());
        assertFalse(information.isApproved());

        assertEquals("TEST_20170517_205552", information.getRequest().getPayment().getReference());
        assertEquals("1040035000", information.getRequest().getBuyer().getDocument().toString());

        assertNull(information.lastApprovedTransaction());
        assertEquals("1495054597", information.lastTransaction().getReceipt().toString());
        assertNull(information.lastAuthorization());
    }

    public void testItParsesARestCreatedResponse()
    {
        String data = "{\"requestId\":368,\"status\":{\"status\":\"PENDING\",\"reason\":\"PC\",\"message\":\"La petici\\u00f3n se encuentra activa\",\"date\":\"2017-05-17T14:44:05-05:00\"},\"request\":{\"locale\":\"es_CO\",\"payer\":null,\"buyer\":{\"document\":\"1040035000\",\"documentType\":\"CC\",\"name\":\"Jakob\",\"surname\":\"Macejkovic\",\"email\":\"dcallem88@msn.com\",\"mobile\":\"3006108300\"},\"payment\":{\"reference\":\"TEST_20170517_144129\",\"description\":\"Quisquam architecto optio rem in non expedita.\",\"amount\":{\"taxes\":[{\"kind\":\"valueAddedTax\",\"amount\":20,\"base\":140}],\"currency\":\"USD\",\"total\":\"199.8\"},\"allowPartial\":false},\"subscription\":null,\"fields\":null,\"returnUrl\":\"http:\\/\\/local.dev\\/redirect\\/client\",\"paymentMethod\":null,\"cancelUrl\":null,\"ipAddress\":\"192.168.33.20\",\"userAgent\":\"Mozilla\\/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/58.0.3029.96 Safari\\/537.36\",\"expiration\":\"2017-05-18T14:41:29+00:00\",\"captureAddress\":false,\"skipResult\":false,\"noBuyerFill\":false},\"payment\":null,\"subscription\":null}";
        JSONObject object = new JSONObject(data);
        cleanResponse(object);
        RedirectInformation information = new RedirectInformation(object);

        assertEquals(new Integer(368), information.getRequestId());
        assertEquals(Status.ST_PENDING, information.getStatus().getStatus());

        assertTrue(information.isSuccessful());
        assertFalse(information.isApproved());

        assertEquals("TEST_20170517_144129", information.getRequest().getPayment().getReference());
        assertEquals("1040035000", information.getRequest().getBuyer().getDocument().toString());

        assertNull(information.lastApprovedTransaction());
        assertNull(information.lastAuthorization());
        assertNull(information.lastTransaction());
        
        assertArrayHasKey("requestId", information.toJsonObject());
    }

    public void testItParsesARestFinishedResponse()
    {
        String data = "{\"requestId\":360,\"status\":{\"status\":\"APPROVED\",\"reason\":\"00\",\"message\":\"La petici\\u00f3n ha sido aprobada exitosamente\",\"date\":\"2017-05-17T14:53:54-05:00\"},\"request\":{\"locale\":\"es_CO\",\"payer\":{\"document\":\"1040035000\",\"documentType\":\"CC\",\"name\":\"Leilani\",\"surname\":\"Zulauf\",\"email\":\"dcallem88@msn.com\",\"mobile\":\"3006108300\"},\"buyer\":{\"document\":\"1040035000\",\"documentType\":\"CC\",\"name\":\"Leilani\",\"surname\":\"Zulauf\",\"email\":\"dcallem88@msn.com\",\"mobile\":\"3006108300\"},\"payment\":{\"reference\":\"TEST_20170516_154231\",\"description\":\"Et et dolorem tenetur et cum.\",\"amount\":{\"currency\":\"USD\",\"total\":\"0.3\"},\"allowPartial\":false},\"subscription\":null,\"fields\":null,\"returnUrl\":\"http:\\/\\/redirect.p2p.dev\\/client\",\"paymentMethod\":null,\"cancelUrl\":null,\"ipAddress\":\"127.0.0.1\",\"userAgent\":\"Mozilla\\/5.0 (X11; Linux x86_64) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/57.0.2987.98 Safari\\/537.36\",\"expiration\":\"2017-05-17T15:42:31+00:00\",\"captureAddress\":false,\"skipResult\":false,\"noBuyerFill\":false},\"payment\":[{\"status\":{\"status\":\"APPROVED\",\"reason\":\"00\",\"message\":\"Aprobada\",\"date\":\"2017-05-16T10:43:39-05:00\"},\"internalReference\":1447466623,\"paymentMethod\":\"paypal\",\"paymentMethodName\":\"PayPal\",\"amount\":{\"from\":{\"currency\":\"USD\",\"total\":0.3},\"to\":{\"currency\":\"USD\",\"total\":0.3},\"factor\":1},\"authorization\":\"2DG26929XX8381738\",\"reference\":\"TEST_20170516_154231\",\"receipt\":\"1447466623\",\"franchise\":\"PYPAL\",\"refunded\":false,\"processorFields\":[{\"keyword\":\"trazabilyCode\",\"value\":\"PAY-9BU08130ME378305MLENR4CI\",\"displayOn\":\"none\"}]}],\"subscription\":null}";
        JSONObject object = new JSONObject(data);
        cleanResponse(object);
        RedirectInformation information = new RedirectInformation(object);

        assertEquals(new Integer(360), information.getRequestId());
        assertEquals(Status.ST_APPROVED, information.getStatus().getStatus());

        assertTrue(information.isSuccessful());
        assertTrue(information.isApproved());

        assertEquals("TEST_20170516_154231", information.getRequest().getPayment().getReference());
        assertEquals("Leilani", information.getRequest().getPayer().getName());
        assertEquals("Zulauf", information.getRequest().getPayer().getSurname());
        assertEquals("dcallem88@msn.com", information.getRequest().getPayer().getEmail());
        assertEquals("USD", information.getRequest().getPayment().getAmount().getCurrency());
        assertEquals("0.3", String.valueOf(information.getRequest().getPayment().getAmount().getTotal()));

        assertEquals("2DG26929XX8381738", information.lastAuthorization());
        assertEquals("1447466623", information.lastTransaction().getReceipt().toString());
        assertEquals("PYPAL", information.lastTransaction().getFranchise());
        JSONAssert.assertEquals(new JSONObject("{\"trazabilyCode\": \"PAY-9BU08130ME378305MLENR4CI\"}"), information.lastTransaction().additionalData(), false);

        assertArrayHasKey("requestId", information.toJsonObject());
    }

    public void testItParsesASubscriptionRestResponse()
    {
        String data = "{\"requestId\":372,\"status\":{\"status\":\"APPROVED\",\"reason\":\"00\",\"message\":\"La petici\\u00f3n ha sido aprobada exitosamente\",\"date\":\"2017-05-17T16:00:47-05:00\"},\"request\":{\"locale\":\"es_CO\",\"payer\":{\"document\":\"1040035000\",\"documentType\":\"CC\",\"name\":\"Ulises\",\"surname\":\"Bosco\",\"email\":\"dcallem88@msn.com\",\"mobile\":\"3006108300\"},\"buyer\":{\"document\":\"1040035000\",\"documentType\":\"CC\",\"name\":\"Ulises\",\"surname\":\"Bosco\",\"email\":\"dcallem88@msn.com\",\"mobile\":\"3006108300\"},\"payment\":null,\"subscription\":{\"reference\":\"TEST_20170517_205952\",\"description\":\"Architecto illum et aut nihil.\"},\"fields\":null,\"returnUrl\":\"http:\\/\\/redirect.p2p.dev\\/client\",\"paymentMethod\":null,\"cancelUrl\":null,\"ipAddress\":\"127.0.0.1\",\"userAgent\":\"Mozilla\\/5.0 (X11; Linux x86_64) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/57.0.2987.98 Safari\\/537.36\",\"expiration\":\"2017-05-18T20:59:52+00:00\",\"captureAddress\":false,\"skipResult\":false,\"noBuyerFill\":false},\"payment\":null,\"subscription\":{\"type\":\"token\",\"status\":{\"status\":\"OK\",\"reason\":\"00\",\"message\":\"Token generated successfully\",\"date\":\"2017-05-17T16:00:42-05:00\"},\"instrument\":[{\"keyword\":\"token\",\"value\":\"4b85ecd661bd6b2e1e69dbd42473c52ed9209c17f5157ede301fde94f66c5a2a\",\"displayOn\":\"none\"},{\"keyword\":\"subtoken\",\"value\":\"0751944147051111\",\"displayOn\":\"none\"},{\"keyword\":\"franchise\",\"value\":\"CR_VS\",\"displayOn\":\"none\"},{\"keyword\":\"franchiseName\",\"value\":\"VISA\",\"displayOn\":\"none\"},{\"keyword\":\"issuerName\",\"value\":null,\"displayOn\":\"none\"},{\"keyword\":\"lastDigits\",\"value\":\"1111\",\"displayOn\":\"none\"},{\"keyword\":\"validUntil\",\"value\":\"2020-12-15\",\"displayOn\":\"none\"},{\"keyword\":\"installments\",\"value\":\"1\",\"displayOn\":\"none\"}]}}";
        JSONObject object = new JSONObject(data);
        cleanResponse(object);
        RedirectInformation information = new RedirectInformation(object);

        assertEquals(new Integer(372), information.getRequestId());
        assertEquals(Status.ST_APPROVED, information.getStatus().getStatus());

        assertTrue(information.isSuccessful());
        assertTrue(information.isApproved());

        assertEquals("TEST_20170517_205952", information.getRequest().getSubscription().getReference());
        assertEquals("Ulises", information.getRequest().getPayer().getName());
        assertEquals("Bosco", information.getRequest().getPayer().getSurname());
        assertEquals("dcallem88@msn.com", information.getRequest().getPayer().getEmail());

        Token token = (Token) information.getSubscription().parseInstrument();
        assertNotNull(token);
        assertTrue(token.isSuccessful());

        assertEquals("4b85ecd661bd6b2e1e69dbd42473c52ed9209c17f5157ede301fde94f66c5a2a", token.getToken());
        assertEquals("0751944147051111", token.getSubtoken());
        assertEquals("CR_VS", token.getFranchise());
        assertEquals("VISA", token.getFranchiseName());
        assertEquals("1111", token.getLastDigits());
        assertEquals("12/20", token.getExpiration());
        assertEquals(String.valueOf(1), token.getInstallments().toString());
           
        JSONObject obj1 = token.toJsonObject();
        JSONAssert.assertEquals(new JSONObject(
                "{" +
                    "\"status\": {" +
                        "\"status\": \"OK\"," +
                        "\"reason\": \"00\"," +
                        "\"message\": \"Token generated successfully\"," +
                        "\"date\": \"2017-05-17T16:00:42-05:00\"" +
                    "}," + 
                    "\"token\": \"4b85ecd661bd6b2e1e69dbd42473c52ed9209c17f5157ede301fde94f66c5a2a\"," +
                    "\"subtoken\": \"0751944147051111\"," +
                    "\"franchise\": \"CR_VS\"," +
                    "\"franchiseName\": \"VISA\"," +
                    "\"lastDigits\": \"1111\"," +
                    "\"validUntil\": \"2020-12-15\"," +
                    "\"installments\": 1" +
                "}"
        ), obj1, false);
    }

    public void testItParsesACancelledSubscriptionRestResponse()
    {
        String data = "{\"requestId\":373,\"status\":{\"status\":\"REJECTED\",\"reason\":\"?C\",\"message\":\"La petici\\u00f3n ha sido cancelada por el usuario\",\"date\":\"2017-05-17T16:13:52-05:00\"},\"request\":{\"locale\":\"es_CO\",\"payer\":null,\"buyer\":{\"document\":\"1040035000\",\"documentType\":\"CC\",\"name\":\"Ramiro\",\"surname\":\"Schultz\",\"email\":\"dcallem88@msn.com\",\"mobile\":\"3006108300\"},\"payment\":null,\"subscription\":{\"reference\":\"TEST_20170517_211300\",\"description\":\"Molestiae expedita mollitia natus eligendi.\"},\"fields\":null,\"returnUrl\":\"http:\\/\\/redirect.p2p.dev\\/client\",\"paymentMethod\":null,\"cancelUrl\":null,\"ipAddress\":\"127.0.0.1\",\"userAgent\":\"Mozilla\\/5.0 (X11; Linux x86_64) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/57.0.2987.98 Safari\\/537.36\",\"expiration\":\"2017-05-18T21:13:00+00:00\",\"captureAddress\":false,\"skipResult\":false,\"noBuyerFill\":false},\"payment\":null,\"subscription\":null}";
        JSONObject object = new JSONObject(data);
        cleanResponse(object);
        RedirectInformation information = new RedirectInformation(object);

        assertEquals(new Integer(373), information.getRequestId());
        assertEquals(Status.ST_REJECTED, information.getStatus().getStatus());

        assertTrue(information.isSuccessful());
        assertFalse(information.isApproved());

        assertEquals("TEST_20170517_211300", information.getRequest().getSubscription().getReference());

        assertNull(information.getSubscription());
    }
    
    private void assertArrayHasKey(String key, JSONObject object) {
        assertTrue(object.has("requestId"));
    }
    
    private void cleanResponse(Object response) {
        if (response instanceof JSONObject) {
            JSONObject object = (JSONObject)response;
            Set<String> keys = new HashSet<String>(object.keySet());
            for (String key : keys) {
                if (object.isNull(key))
                    object.remove(key);
                else {
                    Object value = object.get(key);
                    if (value instanceof JSONObject || value instanceof JSONArray) {
                        cleanResponse(value);
                    }
                }
            }
        } else if (response instanceof JSONArray) {
            JSONArray array = (JSONArray)response;
            for (int i = 0; i < array.length(); i++) {
                cleanResponse(array.get(i));
            }
        }
    }
}
