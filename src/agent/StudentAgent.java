/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import agent.behaviour.StudentBehaviour;
import agent.utils.Utils;
import com.google.gson.Gson;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author magir
 */
public class StudentAgent extends Agent{
    
    private String VARKStyle;
    
    private String DichotomyStyle;
    
    @Override
    protected void setup() {
        super.setup();
        
        StudentBehaviour behaviour = new StudentBehaviour(this);
        
        addBehaviour(behaviour);
    }

    public String getVARKStyle() {
        return VARKStyle;
    }

    public void setVARKStyle(String VARKStyle) {
        this.VARKStyle = VARKStyle;
    }

    public String getDichotomyStyle() {
        return DichotomyStyle;
    }

    public void setDichotomyStyle(String DichotomyStyle) {
        this.DichotomyStyle = DichotomyStyle;
    }
    
    
    
}
