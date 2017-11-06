/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import java.util.Map;
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
public class SendMessageByRestAPI implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        //Reading local variables
        String messageName, businessKey, url, method;
        if (de.hasVariableLocal("messageName")){
            messageName = (String) de.getVariableLocal("messageName");
        } else {
            messageName = "";
        }
        System.out.println("messageName: "+messageName);
        if (de.hasVariableLocal("businessKey")){
            businessKey = (String) de.getVariableLocal("businessKey");
        } else {
            businessKey = de.getBusinessKey();
        }
        System.out.println("businessKey: "+businessKey);
        if (de.hasVariableLocal("url")){
            url = (String) de.getVariableLocal("url");
        } else {
            url = "http://localhost:8080/engine-rest/message";
        }
        System.out.println("url: "+url);
        if (de.hasVariableLocal("method")){
            method = (String) de.getVariableLocal("method");
        } else {
            method = "post";
        }
        System.out.println("method: "+method);
        Map<String,Object> params, corrKeys;
        if (de.hasVariableLocal("params")){
            params = (Map<String,Object>) de.getVariableLocal("params");
        } else {
            params = null;
        }
        if (de.hasVariableLocal("corrKeys")){
            corrKeys = (Map<String,Object>) de.getVariableLocal("corrKeys");
        } else {
            corrKeys = null;
        }
        //defaults
        String headers = "application/json;charset=UTF-8";
        
        //build payload - works only for simple types
        String payload = "{";
        payload = payload
                + "\"messageName\" : \""
                + messageName
                + "\"";
        if(businessKey!=null){
            payload = payload 
                    + ",\"businessKey\" : \""
                    + businessKey
                    + "\"";
        }
        
        if(corrKeys!=null){
            String correlation = "{";
            for(Map.Entry<String,Object> entry : corrKeys.entrySet()){
                correlation = correlation + "\""
                        + entry.getKey()
                        + "\" : {\"value\" : \""
                        + entry.getValue().toString()
                        + "\", \"type\": \""
                        + entry.getValue().getClass().getSimpleName()
                        + "\"},";
            }
            correlation = correlation.substring(0, correlation.length()-1);
            correlation = correlation + "}";
            payload = payload + ",\"correlationKeys\" : "
                    + correlation;
        }
        
        if(params!=null){
            String processVariables = "{";
            for(Map.Entry<String,Object> entry : params.entrySet()){
                processVariables = processVariables + "\""
                        + entry.getKey()
                        + "\" : {\"value\" : \""
                        + entry.getValue().toString()
                        + "\", \"type\": \""
                        + entry.getValue().getClass().getSimpleName()
                        + "\"},";
            }
            processVariables = processVariables.substring(0, processVariables.length()-1);
            processVariables = processVariables + "}";
            payload = payload + ",\"processVariables\" : "
                    + processVariables;
        }

        //add "resultEnabled" : true
        payload = payload + ",\"resultEnabled\" : true";
        
        payload = payload + "}";
        System.out.println("payload: "+payload);
        
        //make http call
        HttpConnector http = Connectors.getConnector(HttpConnector.ID);
        HttpResponse response = http.createRequest()
                .method(method)
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
        System.out.println("Response:"+json);
        response.close();
    }
    
}
