/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.soap.SoapHttpConnector;
import org.camunda.connect.httpclient.soap.SoapHttpResponse;

/**
 *
 * @author kell
 */
public class SoapClient implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        
        //making a very simple xml string wihtout using any framework
        String soap_envelope = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        soap_envelope = soap_envelope + "<order><orderId>"+de.getVariable("orderId")+"</orderId><device>"+de.getVariable("device")+"</device><employee>"+de.getVariable("requester")+"</employee></order>";
        SoapHttpConnector soap = Connectors.getConnector(SoapHttpConnector.ID);
        SoapHttpResponse resp = soap.createRequest()
                .url("http://localhost:8080/gpi_restserver/soap")
                .soapAction("bookOrder")
                .contentType("application/soap+xml")
                .header("Accept", "application/xml")
                .payload(soap_envelope)
                .execute();
        resp.close();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
