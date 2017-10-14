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
public class PrintVariables implements JavaDelegate{

    @Override
    public void execute(DelegateExecution de) throws Exception {
        System.out.println("JavaDelegate printing local variables");
        Map<String,Object> vars = de.getVariablesLocal();
        for(Map.Entry<String,Object> entry : vars.entrySet()){
            System.out.println(entry.getKey()+": "+entry.getValue());
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
