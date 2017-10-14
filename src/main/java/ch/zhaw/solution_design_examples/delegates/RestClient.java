/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import java.util.Map;
import java.util.Random;
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
public class RestClient implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        //Reading local variables
        String url = (String) de.getVariableLocal("url");
        Map<String,Object> params = (Map<String,Object>) de.getVariableLocal("payload");
        //defaults
        String headers = "application/json;charset=UTF-8";
        String method = "POST";
        //generate orderId
        Random random = new Random();
        int orderId = random.nextInt(9999);
        de.setVariable("orderId",orderId);
        
        //build payload
        String payload = "{";
        if(params!=null){
            for(Map.Entry<String,Object> entry : params.entrySet()){
                payload = payload + "\""+ entry.getKey() + "\""+ ":" + "\""+ entry.getValue() + "\""+ ",";
            }
            payload = payload + "\""+ "orderId" + "\""+ ":" + "\""+ orderId + "\"";
        }
        payload = payload + "}";
        System.out.println("RestClient: payload: " + payload);
        
        //make http call
        HttpConnector http = Connectors.getConnector(HttpConnector.ID);
        HttpResponse response = http.createRequest()
                .post()
                .url(url)
                .contentType("text/plain")
                .header("Accept","application/json")
                .payload(payload)
                .execute();
        Integer statusCode = response.getStatusCode();
        String contentTypeHeader = response.getHeader("Content-Type");
        String body = response.getResponse();
        System.out.println("RestClient: " + body);
        SpinJsonNode json = JSON(body);
        response.close();
    }
    
}
