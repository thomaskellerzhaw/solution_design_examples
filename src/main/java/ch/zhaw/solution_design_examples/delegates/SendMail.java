/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 *
 * @author kell
 */
public class SendMail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        System.out.println("SendMail: expecting following local variables:");
        System.out.println("from");
        System.out.println("to");
        System.out.println("subject");
        System.out.println("message");
        System.out.println("messageBodyParts [optional]");
        
        Map<String,Object> vars = de.getVariablesLocal();
        String from = (String) de.getVariableLocal("from");
        String to = (String) de.getVariableLocal("to");
        String subject = (String) de.getVariableLocal("subject");
        String message = (String) de.getVariableLocal("message");
        Map<String,Object> messageBodyParts = (Map) de.getVariableLocal("messageBodyParts");
        
        System.out.println("sending mail ...");
        System.out.println(from + " to " + to);
        System.out.println(subject);
        System.out.println(message);
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
