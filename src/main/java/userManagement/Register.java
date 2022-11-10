/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userManagement;

/**
 *
 * @author sdiazram
 */
public class Register {
    static String errorMessage;
    
    public static User register(User user) {
        if(UserDB.contains(user.getName())) {
            errorMessage = "El usuario ya existe";
            return null;
        }
        UserDB.put(user);
        return user;
    }
    
    public static String getErrorMessage() {
        return errorMessage;
    }
}
