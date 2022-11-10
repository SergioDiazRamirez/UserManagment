/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userManagement;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author sdiazram
 */
public class Message {
    private String content;
    private String originUserName;
    private String destinyUserName;

    public Message(String content, String originUserName, String destinyUserName) {
        LocalDateTime timeSended = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        
        this.content = content + "   (" + timeSended.format(dtf) + ")";
        this.originUserName = originUserName;
        this.destinyUserName = destinyUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOriginUserName() {
        return originUserName;
    }

    public void setOriginUserName(String originUserName) {
        this.originUserName = originUserName;
    }

    public String getDestinyUserName() {
        return destinyUserName;
    }

    public void setDestinyUserName(String destinyUserName) {
        this.destinyUserName = destinyUserName;
    }
    
}
