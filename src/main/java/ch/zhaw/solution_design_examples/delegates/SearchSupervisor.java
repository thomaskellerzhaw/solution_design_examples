/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpResponse;
import static org.camunda.spin.Spin.JSON;
import org.camunda.spin.json.SpinJsonNode;

/**
 *
 * @author kell
 */
public class SearchSupervisor implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        
        String requester = (String) de.getVariable("requester");
        String url = "http://localhost:8080/gpi_restserver/supervisor/"+requester;
        
        //make http call
        HttpConnector http = Connectors.getConnector(HttpConnector.ID);
        HttpResponse response = http.createRequest()
                .get()
                .url(url)
                .header("Accept","application/json")
                .execute();
        Integer statusCode = response.getStatusCode();
        String contentTypeHeader = response.getHeader("Content-Type");
        String body = response.getResponse();
        System.out.println("Supervisor: " + body);
        SpinJsonNode json = JSON(body);
        SpinJsonNode supervisorNode = json.prop("supervisor");
        de.setVariable("supervisor", supervisorNode.value());
        System.out.println("Supervisor is: "+supervisorNode.value());
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
