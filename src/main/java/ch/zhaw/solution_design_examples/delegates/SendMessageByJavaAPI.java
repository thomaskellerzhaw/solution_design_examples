/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.xml.ModelInstance;

/**
 *
 * @author kell
 */
public class SendMessageByJavaAPI implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        //Listener expects following local variables:
        System.out.println("SendMessageByJavaAPI expects following local variables:");
        System.out.println("messageName: Name of receiving message event");
        System.out.println("businessKey: businessKey of new instance or waiting instance");
        System.out.println("messageBody: a Map of variables");
        boolean abort=false;
        String messageName, businessKey;
        Map<String,Object> messageBody;
        if(de.hasVariableLocal("messageName")){
            messageName = (String) de.getVariableLocal("messageName");
        } else {
            messageName="";
            System.out.println("SendMessageByJavaAPI: Sending message failed due to missing messageName");
            abort=true;
        }
        if(de.hasVariableLocal("businessKey")){
            businessKey = (String) de.getVariableLocal("businessKey");
        } else {
            businessKey = "";
            System.out.println("SendMessageByJavaAPI: Sending message failed due to missing businessKey");
            abort=true;
        }
        if(de.hasVariableLocal("messageBody")){
            messageBody = (Map<String, Object>) de.getVariableLocal("messageBody");
        } else {
            System.out.println("SendMessageByJavaAPI: Sending message with no messageBody");
            messageBody = new HashMap<String,Object>();
        }
        try{
            if(!abort){
                de.getProcessEngineServices().getRuntimeService()
                        .startProcessInstanceByMessage(messageName, businessKey, messageBody);
            }
        } catch (Exception ex) {
            System.out.println("SendMessageByJavaAPI: sending message failed");
            System.out.println(ex.getMessage());
            de.setVariable("sendMessageByJavaAPIError",false);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
