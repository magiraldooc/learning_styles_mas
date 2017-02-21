/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.behaviour;

import agent.InterfaceAgent;
import agent.model.LOEvaluation;
import agent.model.SearchLO;
import agent.model.StudentAcceptLOList;
import agent.utils.Utils;
import com.google.gson.Gson;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author magir
 */
public class InterfaceBehaviour extends CyclicBehaviour{
    
    InterfaceAgent guiAgent;

    public InterfaceBehaviour(InterfaceAgent a) {
        super(a);
        
        guiAgent = a;
        myAgent = a;
    }

    @Override
    public void action() {
        
        Utils utils = new Utils();
        
        ACLMessage receivedMessage = utils.receiveMessage(myAgent, null);
        
        Gson gson = new Gson();
        
        if(receivedMessage != null){
            if(receivedMessage.getPerformative() == ACLMessage.INFORM){

                if(utils.getMessage(receivedMessage.getContent()).
                        equals("lo list")){

                    SearchLO searchLO = gson.fromJson(
                                utils.getJsonObject(receivedMessage.getContent()),
                                SearchLO.class);

                    guiAgent.getGUI().fillLOList(searchLO.getLoList());

                }else if(utils.getMessage(receivedMessage.getContent()).
                        equals("lo evaluation")){

                    StudentAcceptLOList studentAcceptLOList = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            StudentAcceptLOList.class);
                    
                    guiAgent.getGUI().fillTreeLO(studentAcceptLOList);


                }
            }
        }
    }
}
