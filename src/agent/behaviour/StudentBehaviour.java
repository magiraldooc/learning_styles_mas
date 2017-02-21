/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.behaviour;

import agent.StudentAgent;
import agent.model.LOEvaluation;
import agent.model.LOM;
import agent.model.Student;
import agent.utils.Utils;
import com.google.gson.Gson;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author magir
 */
public class StudentBehaviour extends CyclicBehaviour{
    
    private StudentAgent studentAgent;
    
    public StudentBehaviour(StudentAgent agent) {
        super(agent);
        myAgent = agent;
        studentAgent = agent;
    }
    
    @Override
    public void action() {
        Utils utils = new Utils();
        ACLMessage receivedMessage = utils.receiveMessage(myAgent, null);
        
        Gson gson = new Gson();
        
        if(receivedMessage != null){
            if(receivedMessage.getPerformative() == ACLMessage.INFORM){
                
                if(utils.getMessage(receivedMessage.getContent()).
                        equals("student profile")){
                    
                    Student student = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            Student.class);
                    
                    System.out.println(student.getId() + ": Hola");
                    
                    studentAgent.setVARKStyle(student.getVARKStyle());
                    studentAgent.setDichotomyStyle(student.getDichotomyStyle());
                    
                }else if(utils.getMessage(receivedMessage.getContent()).
                        equals("evaluate lo")){
                    
                    LOEvaluation lOEvaluation = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            LOEvaluation.class);
                    
                    LOM lom = lOEvaluation.getLom();
                    
                    Boolean acceptLO = Boolean.FALSE;
                    
                    if(studentAgent.getVARKStyle().toLowerCase().trim().equals("v")){

                        if(studentAgent.getDichotomyStyle().toLowerCase().trim().equals("g")){
                            if(
                                    (lom.getLearningResourceTypeList().indexOf("diagram") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("figure") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("graph") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("selfassessment") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("table") > -1) &&
                                    
                                    (lom.getInteractivityLevel().toLowerCase().trim().equals("very low") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("low") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("medium") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("high") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("very high") ||
                                    
                                    lom.getInteractivityType().toLowerCase().trim().equals("activo") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("mixto"))
                                    ){
                                acceptLO = Boolean.TRUE;
                            }
                        }else if(studentAgent.getDichotomyStyle().toLowerCase().trim().equals("s")){
                            if(
                                    (lom.getLearningResourceTypeList().indexOf("diagram") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("selfassessment") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("simulation") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("questionnaire") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("slide") > -1) &&
                                    
                                    (lom.getInteractivityLevel().toLowerCase().trim().equals("very low") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("low") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("medium") ||
                                    
                                    lom.getInteractivityType().toLowerCase().trim().equals("activo") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("mixto"))
                                    ){
                                acceptLO = Boolean.TRUE;
                            }
                        }

                    }else if(studentAgent.getVARKStyle().toLowerCase().trim().equals("a")){

                        if(studentAgent.getDichotomyStyle().toLowerCase().trim().equals("g")){
                            
                            if(
                                    (lom.getLearningResourceTypeList().indexOf("narrative text") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("lecture") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("audio") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("video") > -1) &&
                                    (lom.getInteractivityLevel().toLowerCase().trim().equals("medium") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("high") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("expositivo") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("mixto"))
                                    ){
                                acceptLO = Boolean.TRUE;
                            }
                            
                        }else if(studentAgent.getDichotomyStyle().toLowerCase().trim().equals("s")){
                            if(
                                    (lom.getLearningResourceTypeList().indexOf("narrative text") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("lecture") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("audio") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("video") > -1) &&
                                    (lom.getInteractivityLevel().toLowerCase().trim().equals("very low") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("low") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("expositivo") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("mixto"))
                                    ){
                                acceptLO = Boolean.TRUE;
                            }
                        }

                    }else if(studentAgent.getVARKStyle().toLowerCase().trim().equals("r")){

                        if(studentAgent.getDichotomyStyle().toLowerCase().trim().equals("g")){
                            if(
                                    (lom.getLearningResourceTypeList().indexOf("narrative text") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("presentation") > -1) &&
                                    (lom.getInteractivityLevel().toLowerCase().trim().equals("medium") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("high") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("expositivo") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("mixto"))
                                    ){
                                acceptLO = Boolean.TRUE;
                            }
                        }else if(studentAgent.getDichotomyStyle().toLowerCase().trim().equals("s")){
                            if(
                                    (lom.getLearningResourceTypeList().indexOf("narrative text") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("presentation") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("questionnaire") > -1) &&
                                    (lom.getInteractivityLevel().toLowerCase().trim().equals("very low") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("low") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("medium") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("expositivo") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("mixto"))
                                    ){
                                acceptLO = Boolean.TRUE;
                            }
                        }
                        
                    }else if(studentAgent.getVARKStyle().toLowerCase().trim().equals("k")){
                        
                        if(studentAgent.getDichotomyStyle().toLowerCase().trim().equals("g")){
                            if(
                                    (lom.getLearningResourceTypeList().indexOf("selfassessment") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("exercise") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("problemstatement") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("simulation") > -1) &&
                                    (lom.getInteractivityLevel().toLowerCase().trim().equals("medium") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("high") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("very high") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("activo") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("mixto"))
                                    ){
                                acceptLO = Boolean.TRUE;
                            }
                        }else if(studentAgent.getDichotomyStyle().toLowerCase().trim().equals("s")){
                            if(
                                    (lom.getLearningResourceTypeList().indexOf("exercise") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("simulation") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("experiment") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("selfassessment") > -1 ||
                                    lom.getLearningResourceTypeList().indexOf("problemstatement") > -1) &&
                                    (lom.getInteractivityLevel().toLowerCase().trim().equals("medium") ||
                                    lom.getInteractivityLevel().toLowerCase().trim().equals("high") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("activo") ||
                                    lom.getInteractivityType().toLowerCase().trim().equals("mixto"))
                                    ){
                                acceptLO = Boolean.TRUE;
                            }
                        }
                    }
                    
                    lOEvaluation.setAcceptLO(acceptLO);
                    
                    String json = gson.toJson(lOEvaluation);
                    
                    utils.sendMessage(myAgent,
                            new AID("ControllerAgent", AID.ISLOCALNAME),
                            "obj;" + json + ";" + "msg;" + "lo evaluation",
                            ACLMessage.INFORM);
                    
                }
            }
        }
    }
    
    
}
