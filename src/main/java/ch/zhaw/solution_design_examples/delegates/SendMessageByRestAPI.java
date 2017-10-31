/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import com.google.gson.Gson;
import java.util.HashMap;
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
public class SendMessageByRestAPI implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        //Reading local variables
        String url, method;
        if (de.hasVariableLocal("url")){
            url = (String) de.getVariableLocal("url");
        } else {
            url = "http://localhost:8080/engine-rest/message";
        }
        if (de.hasVariableLocal("method")){
            method = (String) de.getVariableLocal("method");
        } else {
            method = "post";
        }
        Map<String,Object> params;
        if (de.hasVariableLocal("payload")){
            params = (Map<String,Object>) de.getVariableLocal("payload");
        } else {
            params = new HashMap<String,Object>();
        }
        //defaults
        String headers = "application/json;charset=UTF-8";
        //build payload - works only for simple types
        /*
        String payload = "{";
        if(params!=null){
            for(Map.Entry<String,Object> entry : params.entrySet()){
                payload = payload + "\""+ entry.getKey() + "\""+ ":" + "\""+ entry.getValue() + "\""+ ",";
            }
        }
        payload = payload + "}";
        */
        //using Gson
        Gson gson = new Gson();
        String payload = gson.toJson(params);
        System.out.println("Json payload: " + payload);
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
        response.close();
    }
    
}
