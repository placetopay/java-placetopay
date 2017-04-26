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
package com.placetopay.redirection;

import com.placetopay.redirection.Carrier.Authentication;
import com.placetopay.redirection.Carrier.RestCarrier;
import com.placetopay.redirection.Carrier.SoapCarrier;
import com.placetopay.redirection.Contracts.Carrier;
import com.placetopay.redirection.Contracts.Gateway;
import com.placetopay.redirection.Entities.Models.CollectRequest;
import com.placetopay.redirection.Entities.Models.RedirectInformation;
import com.placetopay.redirection.Entities.Models.RedirectRequest;
import com.placetopay.redirection.Entities.Models.RedirectResponse;
import com.placetopay.redirection.Entities.Models.ReverseResponse;
import java.net.URL;
import java.util.Map;

/**
 *
 * @author hernan_saldarriaga
 */
public class PlaceToPay extends Gateway {

    public PlaceToPay(String login, String trankey, URL url) {
        super(login, trankey, url);
    }

    public PlaceToPay(String login, String trankey, URL url, String type) {
        super(login, trankey, url, type);
    }

    public PlaceToPay(String login, String trankey, URL url, String type, Map<String, String> additional) {
        super(login, trankey, url, type, additional);
    }

    public PlaceToPay(String login, String tranKey, URL url, String type, Authentication.Parameter AuthParameters, Map<String, String> additional) {
        super(login, tranKey, url, type, AuthParameters, additional);
    }
    
    private Carrier carrier() {
        if (this.carrier != null)
            return this.carrier;
        Authentication auth = new Authentication(
                config.getLogin(), config.getTranKey(), 
                config.getAuthParameter(), config.getAdditional());
        if (config.getType() != null && config.getType().equals(TP_SOAP)) {
            config.setWsdl(config.getUrl() + "soap/redirect/?wsdl");
            config.setLocation(config.getUrl() + "soap/redirect");
            this.carrier = new SoapCarrier(auth, config);
        } else {
            this.carrier = new RestCarrier(auth, config);
        }
        return this.carrier;
    }

    @Override
    public RedirectResponse request(RedirectRequest redirectRequest) {
        return this.carrier().request(redirectRequest);
    }

    @Override
    public RedirectInformation query(String requestId) {
        return this.carrier().query(requestId);
    }

    @Override
    public RedirectInformation collect(CollectRequest collectRequest) {
        return this.carrier().collect(collectRequest);
    }

    @Override
    public ReverseResponse reverse(String internalReference) {
        return this.carrier().reverse(internalReference);
    }
    
}
