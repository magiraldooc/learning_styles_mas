/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.model;

import agent.model.LOM;
import java.util.ArrayList;

/**
 *
 * @author magir
 */
public class StudentAcceptLOList {
    
    private LOM lo;
    
    private ArrayList<Student> studentList;

    public StudentAcceptLOList() {
        
        this.studentList = new ArrayList<>();
    }

    public LOM getLo() {
        return lo;
    }

    public void setLo(LOM lo) {
        this.lo = lo;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }
    
    public void addStudent(Student student) {
        this.studentList.add(student);
    }
}
