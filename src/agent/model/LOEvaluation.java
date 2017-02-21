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
public class LOEvaluation {
    
    private LOM lom;
    
    private Student student;
    
    private Boolean acceptLO;

    public LOM getLom() {
        return lom;
    }

    public void setLom(LOM lom) {
        this.lom = lom;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Boolean getAcceptLO() {
        return acceptLO;
    }

    public void setAcceptLO(Boolean acceptLO) {
        this.acceptLO = acceptLO;
    }
}
