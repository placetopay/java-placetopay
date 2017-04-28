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
package com.placetopay.java_placetopay.Carrier;

import com.placetopay.java_placetopay.Contracts.Carrier;
import com.placetopay.java_placetopay.Contracts.Configuration;
import com.placetopay.java_placetopay.Entities.Models.CollectRequest;
import com.placetopay.java_placetopay.Entities.Models.RedirectInformation;
import com.placetopay.java_placetopay.Entities.Models.RedirectRequest;
import com.placetopay.java_placetopay.Entities.Models.RedirectResponse;
import com.placetopay.java_placetopay.Entities.Models.ReverseResponse;
import com.placetopay.java_placetopay.Entities.Status;
import com.placetopay.java_placetopay.Exceptions.BadPlaceToPayException;
import com.placetopay.java_placetopay.Exceptions.PlaceToPayException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author hernan_saldarriaga
 */
@SuppressWarnings("UseSpecificCatch")
public class SoapCarrier extends Carrier {

    private static final String WSDL_LOCATION = "http://placetopay.com/soap/redirect/";
    private static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
    private final SOAPConnectionFactory conFactory;
    
    public SoapCarrier(Authentication auth, Configuration config) {
        super(auth, config);
        try {
            this.conFactory = SOAPConnectionFactory.newInstance();
        } catch (Exception ex) {
            throw new PlaceToPayException("Failed to create SOAP connection: " + ex.getMessage());
        }
    }
    
    public SoapCarrier(Authentication auth, Configuration config, Map<String, String> soapSettings) {
        super(auth, config);
        try {
            this.conFactory = SOAPConnectionFactory.newInstance();
        } catch (Exception ex) {
            throw new PlaceToPayException("Failed to create SOAP connection: " + ex.getMessage());
        }
    }
    
    private SOAPMessage createSoapMessage() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance(
                config.getSoapVersion() == 
                        Configuration.SOAP_1_2 ? 
                            SOAPConstants.SOAP_1_2_PROTOCOL : 
                            SOAPConstants.SOAP_1_1_PROTOCOL
        );
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("p2p", WSDL_LOCATION);
        envelope.addNamespaceDeclaration("xsi", XSI);
        auth.addSoapHeader(envelope);
        return soapMessage;
    }
    
    @Override
    public RedirectResponse request(RedirectRequest redirectRequest) { 
        try {
            SOAPConnection connection = conFactory.createConnection();
            SOAPMessage message = createSoapMessage();
            SOAPBody body = message.getSOAPBody();
            SOAPElement functionElement = body.addChildElement("createRequest");
            functionElement.setPrefix("p2p");
            SOAPElement payload = functionElement.addChildElement("payload");
            JSONObject jsonPayload = redirectRequest.toJsonObject();
            appendJsonToSoap(payload, jsonPayload);
            MimeHeaders header = message.getMimeHeaders();
            header.addHeader("SOAPAction", WSDL_LOCATION + "/createRequest");
            message.saveChanges();
            SOAPMessage response = connection.call(message, config.getLocation());
            JSONObject responseBody = extractBody(response, "createRequestResult");
            cleanResponse(responseBody);
            connection.close();
            return new RedirectResponse(responseBody);
        } catch (Exception ex) {
            return new RedirectResponse(new Status(
                    Status.ST_ERROR, 
                    "WR", 
                    PlaceToPayException.readException(ex), 
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
            ));
        }
    }

    @Override
    public RedirectInformation query(String requestId) {
        try {
            SOAPConnection connection = conFactory.createConnection();
            SOAPMessage message = createSoapMessage();
            SOAPBody body = message.getSOAPBody();
            SOAPElement functionElement = body.addChildElement("getRequestInformation");
            functionElement.setPrefix("p2p");
            SOAPElement requestIdElement = functionElement.addChildElement("requestId");
            requestIdElement.addTextNode(requestId);
            MimeHeaders header = message.getMimeHeaders();
            header.addHeader("SOAPAction", WSDL_LOCATION + "/getRequestInformation");
            message.saveChanges();
            SOAPMessage response = connection.call(message, config.getLocation());
            JSONObject responseBody = extractBody(response, "getRequestInformationResult");
            cleanResponse(responseBody);
            connection.close();
            return new RedirectInformation(responseBody);
        } catch (Exception ex) {
            return new RedirectInformation(new Status(
                    Status.ST_ERROR, 
                    "WR", 
                    PlaceToPayException.readException(ex), 
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
            ));
        }
    }

    @Override
    public RedirectInformation collect(CollectRequest collectRequest) {
        try {
            SOAPConnection connection = conFactory.createConnection();
            SOAPMessage message = createSoapMessage();
            SOAPBody body = message.getSOAPBody();
            SOAPElement functionElement = body.addChildElement("collect");
            functionElement.setPrefix("p2p");
            SOAPElement payload = functionElement.addChildElement("payload");
            JSONObject jsonPayload = collectRequest.toJsonObject();
            appendJsonToSoap(payload, jsonPayload);
            MimeHeaders header = message.getMimeHeaders();
            header.addHeader("SOAPAction", WSDL_LOCATION + "/collect");
            message.saveChanges();
            SOAPMessage response = connection.call(message, config.getLocation()); 
            JSONObject responseBody = extractBody(response, "collectResult");
            cleanResponse(responseBody);
            connection.close();
            return new RedirectInformation(responseBody);
        } catch (Exception ex) {
            return new RedirectInformation(new Status(
                    Status.ST_ERROR, 
                    "WR", 
                    PlaceToPayException.readException(ex), 
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
            ));
        }
    }

    @Override
    public ReverseResponse reverse(String internalReference) {
        try {
            SOAPConnection connection = conFactory.createConnection();
            SOAPMessage message = createSoapMessage();
            SOAPBody body = message.getSOAPBody();
            SOAPElement functionElement = body.addChildElement("reversePayment");
            functionElement.setPrefix("p2p");
            SOAPElement requestIdElement = functionElement.addChildElement("internalReference");
            requestIdElement.addTextNode(internalReference);
            MimeHeaders header = message.getMimeHeaders();
            header.addHeader("SOAPAction", WSDL_LOCATION + "/reversePayment");
            message.saveChanges();
            SOAPMessage response = connection.call(message, config.getLocation());
            JSONObject responseBody = extractBody(response, "reversePaymentResult");
            cleanResponse(responseBody);
            connection.close();
            return new ReverseResponse(responseBody);
        } catch (Exception ex) {
            return new ReverseResponse(new Status(
                    Status.ST_ERROR, 
                    "WR", 
                    PlaceToPayException.readException(ex), 
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date())
            ));
        }
    }

    private void appendJsonToSoap(SOAPElement msg, JSONObject jsonPayload) throws SOAPException {
        for (String key : jsonPayload.keySet()) {
            Object value = jsonPayload.get(key);
            if (value instanceof JSONObject) {
                appendJsonToSoap(msg.addChildElement(key), jsonPayload.getJSONObject(key));
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray)value;
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object arrayElement = jsonArray.get(i);
                    if (arrayElement instanceof JSONObject) {
                        appendJsonToSoap(msg.addChildElement(key), (JSONObject) arrayElement);
                    } else {
                        msg.addChildElement(key).addTextNode(arrayElement.toString());
                    }
                }
            } else {
                msg.addChildElement(key).addTextNode(value.toString());
            }
        }
    }

    private JSONObject extractBody(SOAPMessage response, String elementName) throws SOAPException, BadPlaceToPayException, IOException {
        ByteArrayOutputStream baos = null;
        try 
        {
            baos = new ByteArrayOutputStream();
            response.writeTo(baos); 
            String result = baos.toString();
            JSONObject object = XML.toJSONObject(result, false);
            JSONObject body = object.getJSONObject("SOAP-ENV:Envelope").getJSONObject("SOAP-ENV:Body");
            return body.getJSONObject(body.keys().next()).getJSONObject(elementName);
        }
        finally 
        {
            if (baos != null) 
            {
                baos.close();
            }
        }
    }

    private boolean cleanResponse(Object response) {
        if (response instanceof JSONObject) {
            JSONObject object = (JSONObject)response;
            Set<String> keys = new HashSet<String>(object.keySet());
            for (String key : keys) {
                if (key.equals("xsi:nil") && object.getBoolean(key)) {
                    return true;
                } else {
                    boolean result = cleanResponse(object.get(key));
                    if (result) {
                        object.remove(key);
                    }
                }
            }
        } else if (response instanceof JSONArray) {
            JSONArray array = (JSONArray)response;
            for (int i = 0; i < array.length(); i++) {
                cleanResponse(array.get(i));
            }
        }
        return false;
    }
}
