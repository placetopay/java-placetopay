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

import com.placetopay.java_placetopay.Exceptions.PlaceToPayException;
import com.placetopay.java_placetopay.Utils;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import org.json.JSONObject;

/**
 *
 * @author hernan_saldarriaga
 */
public class Authentication {
    private static final String WSU = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    private static final String WSSE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    
    private String login;
    private String tranKey;
    private Parameter auth;
    private boolean overrided = false;
    private Object additional;
 
    public static class Parameter {
        public String seed;
        public String nonce;

        public Parameter(String seed, String nonce) {
            this.seed = seed;
            this.nonce = nonce;
        }
    }
    
    public Authentication(String login, String trankey, Parameter Auth) {
        this(login, trankey, Auth, null);
    }
    
    public Authentication(String login, String trankey, Parameter auth, Object additional) {
        if (login == null || trankey == null)
            throw new PlaceToPayException("No login or tranKey provided on authentication");
        this.login = login;
        this.tranKey = trankey;
        if (auth != null) {
            if (auth.seed == null || auth.nonce == null)
                throw new PlaceToPayException("Bad definition for the override");
            this.auth = auth;
            this.overrided = true;
        }
        this.additional = additional;
        this.generate();
    }
    
    public String getNonce() {
        return this.getNonce(true);
    }
    
    public String getNonce(boolean encoded) {
        String nonce;
        if (this.auth != null)
            nonce = this.auth.nonce;
        else {
            nonce = new BigInteger(130, new SecureRandom()).toString(16);
        }
        if (encoded)
            return Utils.base64(nonce.getBytes());
        return nonce;
    }
    
    public String getSeed() {
        if (this.auth != null)
            return this.auth.seed;
        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault())).format(new Date());
    }

    public String getLogin() {
        return login;
    }

    public String getTranKey() {
        return tranKey;
    }

    public Object getAdditional() {
        return additional;
    }

    public void setAdditional(Object additional) {
        this.additional = additional;
    }
        
    public String digest() {
        return this.digest(true);
    }
    
    public String digest(boolean encoded) {
        try {
            byte[] digest = Utils.sha1(this.getNonce(false)+this.getSeed()+this.tranKey);
            if (encoded)
                return Utils.base64(digest);
            return new String(digest);
        } catch(NoSuchAlgorithmException ex) {
            throw new PlaceToPayException(ex.getMessage());
        }
    }
    
    private void generate() {
        if (!this.overrided) {
            this.auth = new Parameter(this.getSeed(), this.getNonce());
        }
    }
    
    
    public void addSoapHeader(SOAPEnvelope envelope) throws SOAPException {
        envelope.addNamespaceDeclaration("wsse", Authentication.WSSE);
        envelope.addNamespaceDeclaration("wssu", Authentication.WSU);
        SOAPHeader header = envelope.getHeader();
        SOAPElement securityElement = header.addChildElement("Security", "wsse");
        securityElement.addAttribute(envelope.createQName("mustUnderstand", "SOAP-ENV"), "true");
        SOAPElement userNameTokenElement = securityElement.addChildElement("UsernameToken", "wsse");
        userNameTokenElement.addChildElement("Username", "wsse").addTextNode(this.getLogin());
        userNameTokenElement.addChildElement("Password", "wsse")
                .addAttribute(envelope.createQName("type", "xsi"), "PasswordDigest")
                .addTextNode(this.digest());
        userNameTokenElement.addChildElement("Nonce", "wsse").addTextNode(this.getNonce());
        userNameTokenElement.addChildElement("Created", "wssu").addTextNode(this.getSeed());        
    }
    
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("login", this.getLogin());
        object.put("tranKey", this.digest());
        object.put("nonce", this.getNonce());
        object.put("seed", this.getSeed());
        object.put("additional", this.getAdditional());
        return object;
    }
}
