/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.behaviour;

import agent.model.LOEvaluation;
import agent.model.LOList;
import agent.model.LOM;
import agent.model.StudentAcceptLOList;
import agent.model.SearchLO;
import agent.model.Student;
import agent.model.StudentList;
import agent.utils.Utils;
import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONArray;

/**
 *
 * @author magir
 */
public class ControllerBehaviour extends CyclicBehaviour{

    ArrayList<Student> controllerStudentList;
    HashMap<Integer, Integer> controllerLOResponseCounter;
    HashMap<Integer, StudentAcceptLOList> controllerStudentAcceptLOCounter;
    
    public ControllerBehaviour(Agent a) {
        super(a);
        
        myAgent = a;
        controllerStudentList = new ArrayList<>();
        controllerLOResponseCounter = new HashMap<>();
        controllerStudentAcceptLOCounter = new HashMap<>();
    }
    
    @Override
    public void action() {
        
        Utils utils = new Utils();
        
        Gson gson = new Gson();
        
        ACLMessage receivedMessage = utils.receiveMessage(myAgent, null);
        
        if(receivedMessage != null){
        
            if(receivedMessage.getPerformative() == ACLMessage.INFORM){

                String messageContent = receivedMessage.getContent();

                if(utils.getMessage(messageContent).
                        equals("create simulation agents")){

                    StudentList studentList = gson.fromJson(
                            utils.getJsonObject(messageContent),
                            StudentList.class);

                    ContainerController container = myAgent.getContainerController();

                    for(Student student : studentList.getStudentList()){

                        long start = new Date().getTime();
                        while(new Date().getTime() - start < 1000L){

                        }

//                        String agentName = "Student-" + student.getId();
                        String agentName = student.getId();
                        student.setAgentName(agentName);
                        controllerStudentList.add(student);

                        String json = gson.toJson(student);
                        
                        try {
                            AgentController agent = container.createNewAgent(
                                    agentName,
                                    "agent.StudentAgent",
                                    null);
                            agent.start();

                            ACLMessage studentProfileMessage = new ACLMessage(ACLMessage.INFORM);
                            studentProfileMessage.addReceiver(new AID(agentName, AID.ISLOCALNAME));
                            studentProfileMessage.setContent("obj;" + json + ";" + "msg;" + "student profile");
                            myAgent.send(studentProfileMessage);

                        } catch (StaleProxyException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }else if(utils.getMessage(messageContent).
                        equals("search lo")){
                    
                    SearchLO searchLO = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            SearchLO.class);
                    
                    try {
                        searchLO.setLoList(this.getLO(searchLO.getSearchString()));
                        
                        System.out.println("Lista objetos: " + 
                                searchLO.getLoList().getLOList().size());
                        
                        String json = gson.toJson(searchLO);

                        utils.sendMessage(myAgent, 
                                new AID("InterfaceAgent", AID.ISLOCALNAME), 
                                "obj;" + json + ";" + "msg;" + "lo list", 
                                ACLMessage.INFORM);
                        
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(ControllerBehaviour.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ControllerBehaviour.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JDOMException ex) {
                        Logger.getLogger(ControllerBehaviour.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }else if(utils.getMessage(messageContent).
                        equals("selected lo")){
                    
                    LOM lom = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            LOM.class);
                    
                    controllerLOResponseCounter.put(lom.getSendedLOId(), 0);
                    
                    for(Student student : controllerStudentList){
                        
                        LOEvaluation lOEvaluation = new LOEvaluation();
                    
                        lOEvaluation.setLom(lom);
                        lOEvaluation.setStudent(student);
                        
                        String json = gson.toJson(lOEvaluation);
                        
                        utils.sendMessage(myAgent, 
                                new AID(student.getAgentName(), AID.ISLOCALNAME), 
                                "obj;" + json + ";" + "msg;" + "evaluate lo", 
                                ACLMessage.INFORM);
                    }
                }else if(utils.getMessage(messageContent).
                        equals("lo evaluation")){
                    
                    LOEvaluation lOEvaluation = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            LOEvaluation.class);
                    
                    controllerLOResponseCounter.put(lOEvaluation.getLom().getSendedLOId(),
                            controllerLOResponseCounter.get(
                                    lOEvaluation.getLom().getSendedLOId()) + 1);
                    
                    StudentAcceptLOList studentAcceptLOList;
                    
                    if(controllerStudentAcceptLOCounter.get(lOEvaluation.getLom().getSendedLOId()) == null){
                        studentAcceptLOList = new StudentAcceptLOList();
                        studentAcceptLOList.setLo(lOEvaluation.getLom());
                    }else{
                        studentAcceptLOList = controllerStudentAcceptLOCounter.get(
                                lOEvaluation.getLom().getSendedLOId());
                    }
                    
                    if(lOEvaluation.getAcceptLO()){
                        studentAcceptLOList.addStudent(lOEvaluation.getStudent());
                    }
                    
                    controllerStudentAcceptLOCounter.put(
                                lOEvaluation.getLom().getSendedLOId(), studentAcceptLOList);
                    
                    if(controllerLOResponseCounter.get(lOEvaluation.getLom().getSendedLOId()) ==
                            controllerStudentList.size()){
                        String json = gson.toJson(studentAcceptLOList);
                    
                        utils.sendMessage(myAgent, 
                                new AID("InterfaceAgent", AID.ISLOCALNAME), 
                                "obj;" + json + ";" + "msg;" + "lo evaluation", 
                                ACLMessage.INFORM);
                    }
                    
//                    utils.sendMessage(myAgent, 
//                            new AID("InterfaceAgent", AID.ISLOCALNAME), 
//                            messageContent, 
//                            ACLMessage.REQUEST);
                }

            }
        }
    }
    
    private LOList getLO(String searchString) throws MalformedURLException, UnsupportedEncodingException, IOException, JDOMException{
        
        System.out.println("Cadena de busqueda OA: " + searchString);
        URL url = new URL("http://froac.manizales.unal.edu.co/froacn/?raim=" + searchString);
        
        URLConnection connection = url.openConnection();
        
        // Leyendo el resultado
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        
        String row;
        LOList loList = new LOList();
        
        while ((row = in.readLine()) != null) {
//            System.out.println("Respuesta servicio busqueda OA:");
            System.out.println(row);
            
            //Json array
            JSONArray jsonArray = new JSONArray(row.substring(1, row.length() - 1));
            
            for(int i = 0; i < jsonArray.length(); i ++){
//                System.out.println(jsonArray.getJSONObject(i).getString("xml"));
                LOM lom = new LOM();
                
                lom.setId(i);
                
                SAXBuilder builder = new SAXBuilder();
                
                Document xmlDocument = 
                        builder.build(new ByteArrayInputStream(
                                jsonArray.getJSONObject(i).getString("xml").getBytes()));
                
                Element root = xmlDocument.getRootElement();
                
                List general = root.getChildren(
                        "general", root.getNamespace());
                
                Element generalChild = (org.jdom2.Element) general.get(0);
                
                Element title = generalChild.getChild(
                        "title", generalChild.getNamespace());
                
                lom.setTitle(title == null ? "" : title.getValue().trim());
                
                /**************************************************************/
                
                List educational = root.getChildren(
                        "educational", root.getNamespace());
                
                Element educationalChild = (org.jdom2.Element) educational.get(0);
                
                List<Element> resourceTypeList = educationalChild.getChildren(
                        "learningresourcetype", educationalChild.getNamespace());
                
                for(Element resourceType : resourceTypeList){
                    lom.addLearningResourceType(resourceType == null ? "" : resourceType.getValue().trim());
                }
                
                Element interactivityLevel = educationalChild.getChild(
                        "interactivitylevel", educationalChild.getNamespace());
                
                lom.setInteractivityLevel(interactivityLevel == null ? "" : interactivityLevel.getValue().trim());
                
                Element interactivityType = educationalChild.getChild(
                        "interactivitytype", educationalChild.getNamespace());
                
                lom.setInteractivityType(interactivityType == null ? "" : interactivityType.getValue().trim());
                
                /***************************************************************/
                
                List technical = root.getChildren(
                        "technical", root.getNamespace());
                
                Element technicalChild = (org.jdom2.Element) technical.get(0);
                
                Element format = technicalChild.getChild(
                        "format", technicalChild.getNamespace());
                
                lom.setFormat(format == null ? "" : format.getValue().trim());
                
                /***************************************************************/
                
                List accessibility = root.getChildren(
                        "accessibility", root.getNamespace());
                
                Element accessibilityChild = (org.jdom2.Element) accessibility.get(0);
                
                Element presentationModeChild = accessibilityChild.getChild(
                        "presentationmode", accessibilityChild.getNamespace());
                
                Element auditory = presentationModeChild.getChild(
                        "auditory", presentationModeChild.getNamespace());
                
                lom.setAuditory(auditory == null ? "" : auditory.getValue().trim());
                
                Element textual = presentationModeChild.getChild(
                        "textual", presentationModeChild.getNamespace());
                
                lom.setTextual(textual == null ? "" : textual.getValue().trim());
                
                /*****************/
                
                Element adaptationTypeChild = accessibilityChild.getChild(
                        "adaptationtype", accessibilityChild.getNamespace());
                
                Element hearingAlternative = adaptationTypeChild.getChild(
                        "hearingalternative", adaptationTypeChild.getNamespace());
                
                lom.setHearingAlternative(hearingAlternative == null ? "" : hearingAlternative.getValue().trim());
                
                Element textualAlternative = adaptationTypeChild.getChild(
                        "textualalternative", adaptationTypeChild.getNamespace());
                
                lom.setTextualAlternative(textualAlternative == null ? "" : textualAlternative.getValue().trim());
                
                Element signLanguage = adaptationTypeChild.getChild(
                        "signlanguage", adaptationTypeChild.getNamespace());
                
                lom.setSignLanguage(signLanguage == null ? "" : signLanguage.getValue().trim());
                
                loList.addLO(lom);
            }
        }
        
        System.out.println(loList.toString());
        
        return loList;
        
    }
    
    
}
