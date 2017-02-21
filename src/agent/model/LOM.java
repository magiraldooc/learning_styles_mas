/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.model;

import java.util.ArrayList;

/**
 *
 * @author magir
 */
public class LOM {
    
    private Integer id;
    
    private Integer sendedLOId;
    
    private String title;
    
    private ArrayList<String> learningResourceTypeList;
    
    private String interactivityLevel;
    
    private String interactivityType;
    
    private String auditory;
    
    private String hearingAlternative;
    
    private String format;
    
    private String textualAlternative;
    
    private String signLanguage;
    
    private String textual;

    public LOM() {
        this.learningResourceTypeList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendedLOId() {
        return sendedLOId;
    }

    public void setSendedLOId(Integer sendedLOId) {
        this.sendedLOId = sendedLOId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getLearningResourceTypeList() {
        return learningResourceTypeList;
    }

    public void addLearningResourceType(String learningResourceType) {
        learningResourceTypeList.add(learningResourceType);
    }

    public String getInteractivityLevel() {
        return interactivityLevel;
    }

    public void setInteractivityLevel(String interactivityLevel) {
        this.interactivityLevel = interactivityLevel;
    }

    public String getInteractivityType() {
        return interactivityType;
    }

    public void setInteractivityType(String interactivityType) {
        this.interactivityType = interactivityType;
    }

    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }

    public String getHearingAlternative() {
        return hearingAlternative;
    }

    public void setHearingAlternative(String hearingAlternative) {
        this.hearingAlternative = hearingAlternative;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTextualAlternative() {
        return textualAlternative;
    }

    public void setTextualAlternative(String textualAlternative) {
        this.textualAlternative = textualAlternative;
    }

    public String getSignLanguage() {
        return signLanguage;
    }

    public void setSignLanguage(String signLanguage) {
        this.signLanguage = signLanguage;
    }

    public String getTextual() {
        return textual;
    }

    public void setTextual(String textual) {
        this.textual = textual;
    }

    @Override
    public String toString() {
        return "LOM{" + "learningResourceTypeList=" + learningResourceTypeList + ", interactivityLevel=" + interactivityLevel + ", interactivityType=" + interactivityType + ", auditory=" + auditory + ", hearingAlternative=" + hearingAlternative + ", format=" + format + ", textualAlternative=" + textualAlternative + ", signLanguage=" + signLanguage + ", textual=" + textual + '}';
    }
    
}
