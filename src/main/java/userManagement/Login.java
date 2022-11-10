/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userManagement;

/**
 *
 * @author sdiazram
 */
public class Login {

    private static String errorMessage;
    private static int attempts = 0;
    private static User userTry;
    
    public static User login(User myUser) {
        User correctUser = UserDB.getUser(myUser.getName());
        if (correctUser == null) {
            errorMessage = "El usuario no existe";
            return null;
        }
        if (myUser.getPassword().equals(correctUser.getPassword())) {
            attempts = 0;
            return correctUser;
        }
        errorMessage = "La contraseña no es correcta";
        if(userTry == null) {
            userTry = myUser;
            attempts++;
        } else if(userTry.equals(myUser)) {
            attempts++;
        } else {
            userTry = myUser;
            attempts = 1;
        }
        return null;
    }

    public static User loginWithSecurityQuestion(User myUser, String securityAnswer) {
        User correctUser = UserDB.getUser(myUser.getName());
        if (correctUser == null) {
            errorMessage = "El usuario no existe";
            return null;
        }
        if(myUser.getSecurityQuestion() == null) {
            errorMessage = "El usuario no tiene una pregunta de seguridad";
            return null;
        }
        if (securityAnswer.equals(correctUser.getSecurityAnswer())) {
            attempts = 0;
            return correctUser;
        }
        errorMessage = "La respuesta de seguridad no es correcta";
        attempts++;
        return null;
    }

    public static String getErrorMessage() {
        return errorMessage;
    }
    public static void setErrorMessage(String message) {
        errorMessage = message;
    }
    public static int getAttempts() {
        return attempts;
    }

    public static void setAttempts(int attempts) {
        Login.attempts = attempts;
    }
}
