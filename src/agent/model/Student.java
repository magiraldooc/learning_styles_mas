/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.model;

/**
 *
 * @author magir
 */
public class Student {
    
    private String id;
    
    private String agentName;
    
    private String VARKStyle;
    
    private String DichotomyStyle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", agentName=" + agentName + ", VARKStyle=" + VARKStyle + ", DichotomyStyle=" + DichotomyStyle + '}';
    }
    
}
