/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 *
 * @author kell
 */
public class InitDossierList implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        List<Map> dossiers = new ArrayList<Map>();
        de.setVariable("dossiers", dossiers);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
