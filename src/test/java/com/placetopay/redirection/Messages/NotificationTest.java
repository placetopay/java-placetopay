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
package com.placetopay.redirection.Messages;

import com.placetopay.redirection.AppTest;
import com.placetopay.redirection.Contracts.Gateway;
import com.placetopay.redirection.Entities.Models.Notification;
import com.placetopay.redirection.Entities.Status;
import com.placetopay.redirection.Exceptions.BadPlaceToPayException;
import com.placetopay.redirection.Exceptions.PlaceToPayException;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class NotificationTest extends AppTest {

    public void testItParsesCorrectlyTheNotification() {
        String data = 
            "{  " +
            "   \"status\":{  " +
            "      \"status\":\"APPROVED\"," +
            "      \"reason\":\"00\"," +
            "      \"message\":\"Se ha aprobado su pago, puede imprimir el recibo o volver a la pagina del comercio\"," +
            "      \"date\":\"2016-10-10T16:39:57-05:00\"" +
            "   }," +
            "   \"requestId\":83," +
            "   \"reference\":\"TEST_20161010_213937\"," +
            "   \"signature\":\"8fb4beea130ab3e75a1de956bd0213892e0f6839\"" +
            "}";
        JSONObject object = new JSONObject(data);
        Notification notification = new Notification(object, "024h1IlD");
        assertTrue("Valid notification", notification.isValidNotification());
        assertTrue(notification.getStatus().getStatus(), notification.isApproved());
        assertFalse(notification.getStatus().getStatus(), notification.isRejected());
        assertEquals("Same request identifier", notification.getRequestId(), new Integer(83));
        assertEquals("Same reference", notification.getReference(), "TEST_20161010_213937");
    }
    
    public void testItParsesANotificationPost() {
        String data = 
                "{  \n" +
                "   \"status\":{  \n" +
                "      \"status\":\"REJECTED\",\n" +
                "      \"reason\":\"?C\",\n" +
                "      \"message\":\"El proceso de pago ha sido cancelado por el usuario\",\n" +
                "      \"date\":\"2016-10-12T01:44:37-05:00\"\n" +
                "   },\n" +
                "   \"requestId\":126,\n" +
                "   \"reference\":\"100000071\",\n" +
                "   \"signature\":\"554fa6c36bd5d1376b192b8bc3a1e3dd9a01e448\"\n" +
                "}";
        Gateway gateway = getGateway(Gateway.TP_REST, "024h1IlD");
        try {
            Notification notification = gateway.readNotification(data);
            assertTrue("Its a valid notification", notification.isValidNotification());
            assertEquals(Status.ST_REJECTED, notification.getStatus().getStatus());
        } catch (BadPlaceToPayException ex) {
            throw new PlaceToPayException(ex.getMessage());
        }
    }
}
