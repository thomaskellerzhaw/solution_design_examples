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
import org.camunda.bpm.engine.runtime.ProcessInstance;

/**
 *
 * @author kell
 */
public class SendMessage implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        
        String device = (String) de.getVariable("device");
        Random random = new Random();
        int orderId = random.nextInt(9999);
        System.out.println("gpi_hw: SendMessage: orderId: "+orderId);
        de.setVariable("orderId", orderId);
        de.setVariable("orderFromStock", true);
        Map<String,Object> vars = de.getVariables();
        try{
            de.getProcessEngineServices().getRuntimeService()
                    .startProcessInstanceByMessage("new order received", Integer.toString(orderId), vars);
        } catch (Exception ex) {
            System.out.println("gpi_hw: SendMessage: sending message failed");
            System.out.println(ex.getMessage());
            de.setVariable("orderFromStock",false);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
