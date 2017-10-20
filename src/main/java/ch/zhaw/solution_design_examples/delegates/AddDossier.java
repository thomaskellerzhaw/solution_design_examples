/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 *
 * @author thomas
 */
public class AddDossier implements JavaDelegate{

    @Override
    public void execute(DelegateExecution de) throws Exception {
        List<Map> dossiers = (List<Map>) de.getVariable("dossiers");
        Map<String,Object> newdossier = new HashMap<String,Object>();
        newdossier.put("applicnatMail", de.getVariable("applicantMail"));
        newdossier.put("dossierLink", de.getVariable("dossierLink"));
        dossiers.add(newdossier);
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
