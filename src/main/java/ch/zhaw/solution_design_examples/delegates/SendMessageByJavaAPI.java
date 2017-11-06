/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import ch.zhaw.solution_design_examples.exceptions.SendMessageException;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

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
        System.out.println("throws BpmnError 'SendMessageException'");
        String messageName, businessKey;
        Map<String,Object> messageBody;
        try{
            if(de.hasVariableLocal("messageName")){
                messageName = (String) de.getVariableLocal("messageName");
            } else {
                messageName="";
                System.out.println("SendMessageByJavaAPI: Sending message failed due to missing messageName");
                throw new SendMessageException("Missing local variable 'messageName'");
            }
            if(de.hasVariableLocal("businessKey")){
                businessKey = (String) de.getVariableLocal("businessKey");
            } else {
                businessKey = "";
                System.out.println("SendMessageByJavaAPI: Sending message failed due to missing businessKey");
                throw new SendMessageException("Missing local variable 'businessKey'");
            }
            if(de.hasVariableLocal("messageBody")){
                messageBody = (Map<String, Object>) de.getVariableLocal("messageBody");
            } else {
                System.out.println("SendMessageByJavaAPI: Sending message with no messageBody");
                messageBody = new HashMap<String,Object>();
            }
        } catch (SendMessageException ex){
            throw new BpmnError("SendMessageException", ex.getMessage());
        } catch (Exception ex) {
            System.out.println("SendMessageByJavaAPI: sending message failed");
            System.out.println(ex.getMessage());
            throw new BpmnError("SendMessageException", ex.getMessage());
        }
    }
    
}
