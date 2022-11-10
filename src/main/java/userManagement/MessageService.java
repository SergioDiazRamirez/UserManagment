/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userManagement;

/**
 *
 * @author sdiazram
 */
public class MessageService {
    
    public static void send(Message message) {
        String originUserName = message.getOriginUserName();
        String destinyUserName = message.getDestinyUserName();
        
        //add message to the destiny's chat
        User userDestiny = UserDB.getUser(destinyUserName);
        userDestiny.addMessage(message);
        
        //if I'm not writing to myself
        if(!originUserName.equals(destinyUserName)) {
            //add my message in my chat with destiny
            User userOrigin = UserDB.getUser(originUserName);
            String contentFromMyView = "\tYo: " + message.getContent();
            message.setContent(contentFromMyView);
            message.setOriginUserName(destinyUserName);

            userOrigin.addMessage(message);
        }
    }
}
