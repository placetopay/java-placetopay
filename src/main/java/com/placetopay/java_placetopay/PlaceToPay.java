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
package com.placetopay.java_placetopay;

import com.placetopay.java_placetopay.Carrier.Authentication;
import com.placetopay.java_placetopay.Carrier.RestCarrier;
import com.placetopay.java_placetopay.Carrier.SoapCarrier;
import com.placetopay.java_placetopay.Contracts.Carrier;
import com.placetopay.java_placetopay.Contracts.Gateway;
import com.placetopay.java_placetopay.Entities.Models.CollectRequest;
import com.placetopay.java_placetopay.Entities.Models.RedirectInformation;
import com.placetopay.java_placetopay.Entities.Models.RedirectRequest;
import com.placetopay.java_placetopay.Entities.Models.RedirectResponse;
import com.placetopay.java_placetopay.Entities.Models.ReverseResponse;
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

    /**
     * Solicita la creación de la sesión (petición de cobro o suscripción) y retorna el identificador y la URL de procesamiento.
     * @param redirectRequest Información sobre el pago requerido
     * @return Es un objeto con la información de redirección
     */
    @Override
    public RedirectResponse request(RedirectRequest redirectRequest) {
        return this.carrier().request(redirectRequest);
    }

    /**
     * Obtiene la información de la sesión y transacciones realizadas.  
     * @param requestId Identificador de la sesión a consultar.
     * @return Información del estado de la transacción
     */
    @Override
    public RedirectInformation query(String requestId) {
        return this.carrier().query(requestId);
    }

    /**
     * Permite realizar cobros sin la intervención del usuario usando medios de pago previamente suscritos.
     * @param collectRequest Datos del pago, pagador y medio de pago a implementar 
     * @return Información de estado de la operación.
     */
    @Override
    public RedirectInformation collect(CollectRequest collectRequest) {
        return this.carrier().collect(collectRequest);
    }

    /**
     * Permite reversar un pago aprobado con el código de referencia interna.
     * @param internalReference Referencia interna de la transacción a reversar que se encuentra en el listado de transacciones del getRequestInformation (payment).
     * @return Información de estado de la operación.
     */
    @Override
    public ReverseResponse reverse(String internalReference) {
        return this.carrier().reverse(internalReference);
    }
    
}
