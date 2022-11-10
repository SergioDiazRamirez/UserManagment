/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userManagement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author sdiazram
 */
public class User {

    private String name, password, email;
    private String securityQuestion, securityAnswer;
    //userName, all their messages
    private HashMap<String, ArrayList<String>> inBox = new HashMap<>();
    private HashSet<String> unreadUserNamesSet = new HashSet<>();

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void addMessage(Message message) {
        ArrayList<String> messages = inBox.get(message.getOriginUserName());
        if (messages == null) {
            messages = new ArrayList<>();
            messages.add(message.getContent());
            inBox.put(message.getOriginUserName(), messages);
        } else {
            messages.add(message.getContent());
        }
        String origin = message.getOriginUserName();
        String destiny = message.getDestinyUserName();
        //Messages sended to myself are not treated as unread messages
        if(!origin.equals(destiny))
            unreadUserNamesSet.add(message.getOriginUserName());
    }
    
    public boolean equals(User user) {
        return name.equals(user.getName());
    }

    // GETTERS AND SETTERS
    public HashMap<String, ArrayList<String>> getInBox() {
        return inBox;
    }

    public HashSet<String> getUnreadUserNamesSet() {
        return unreadUserNamesSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    
}
