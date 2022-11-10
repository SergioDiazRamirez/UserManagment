package userManagement;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
/**
 *
 * @author sdiazram
 */
public class UserManagement {

    static boolean keepAlive = true;
    static User user;

    private static int askOption(int numberOfOptions) {
        Scanner sc = new Scanner(System.in);
        int selectedOption;
        do {
            try {
                selectedOption = sc.nextInt();
            } catch (Exception e) {
                selectedOption = -1;
                sc.next();
            }
            if (selectedOption < 0 || selectedOption > numberOfOptions - 1) {
                System.out.println("Elige una opción válida");
            }
        } while (selectedOption < 0 || selectedOption > numberOfOptions - 1);

        return selectedOption;
    }

    private static User useSecurityQuestion(User userTry) {
        user = UserDB.getUser(userTry.getName());
        if(user.getSecurityQuestion() == null) {
            Login.setErrorMessage("No tiene pregunta de seguridad");
            Login.setAttempts(0);
            return null;
        } 
        
        System.out.println(user.getSecurityQuestion() + "\nRespuesta: ");
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        return Login.loginWithSecurityQuestion(userTry, answer);
    }

    private static void menu() {
        System.out.println("""
                           0: Establecer pregunta de seguridad
                           1: Modificar email
                           2: Modificar contraseña
                           3: Log out
                           4: Ver email
                           5: Enviar mensajes
                           6: Ver mis chats
                           """);
        if (!user.getUnreadUserNamesSet().isEmpty()) {
            System.out.println("*Tiene mensajes sin leer*");
        }
        int option = askOption(7);
        Scanner sc = new Scanner(System.in);
        switch (option) {
            //Set security question
            case 0 -> {
                System.out.println("Introduce tu pregunta de seguridad:");
                user.setSecurityQuestion(sc.nextLine());
                System.out.println("Introduce la respuesta");
                user.setSecurityAnswer(sc.nextLine());
                System.out.println("Pregunta de seguridad configurada correctamente");
            }
            //Modify email
            case 1 -> {
                System.out.println("Introduce tu nuevo email:");
                user.setEmail(sc.nextLine());
                System.out.println("Su nuevo correo es " + user.getEmail());
            }
            //Modify password
            case 2 -> {
                System.out.println("Introduce tu contraseña actual:");
                String password = sc.nextLine();
                if (password.equals(user.getPassword())) {
                    System.out.println("Introduce tu nueva contraseña:");
                    String newPassword = sc.nextLine();
                    System.out.println("Vuelve a introducir la nueva contraseña");
                    String newPasswordConfirm = sc.nextLine();
                    if (newPassword.equals(newPasswordConfirm)) {
                        user.setPassword(newPassword);
                        System.out.println("Contraseña cambiada.");
                    } else {
                        System.out.println("Las contraseñas no coinciden");
                    }

                } else {
                    System.out.println("Contraseña incorrecta");
                }
            }
            //Log out
            case 3 -> {
                user = null;
            }
            //Show email
            case 4 -> {
                System.out.println("Email: " + user.getEmail());
            }
            //Send message
            case 5 -> {
                System.out.println("¿ A quién desea enviarle un mensaje ?");
                UserDB.printUsers();
                String destinyName = sc.nextLine();
                if (UserDB.contains(destinyName)) {
                    System.out.println("======== Chat con " + destinyName + "===========\n"
                            + " (* + Intro) para salir:");
                    String messageContent = sc.nextLine();
                    while (!messageContent.equals("*")) {                       
                        Message message = new Message(messageContent, user.getName(),
                                destinyName);
                        MessageService.send(message);
                        messageContent = sc.nextLine();
                    }
                } else {
                    System.out.println("El usuario no existe");
                }
            }
            //See chats
            case 6 -> {
                HashMap<String, ArrayList<String>> myInBox = user.getInBox();
                if (myInBox.isEmpty()) {
                    System.out.println("No tienes mensajes");
                } else {
                    System.out.println("¿ Qué chat desea abrir ?");

                    for (String userName : myInBox.keySet()) {
                        if (user.getUnreadUserNamesSet().contains(userName)) {
                            System.out.println("*" + userName);
                        } else {
                            System.out.println(userName);
                        }
                    }
                    String userName = sc.nextLine();
                    user.getUnreadUserNamesSet().remove(userName);
                    ArrayList<String> chat = myInBox.get(userName);
                    if (chat != null) {
                        System.out.println("======== Chat con " + userName + "===========\n"
                                + " (* + Intro) para salir:");
                        for (String message : chat) {
                            System.out.println(message);
                        }
                        String messageContent = sc.nextLine();
                        while (!messageContent.equals("*")) {                           
                            Message message = new Message(messageContent, user.getName(),
                                    userName);
                            MessageService.send(message);
                            messageContent = sc.nextLine();
                        }
                    } else {
                        System.out.println("El chat no existe");
                    }
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        while (keepAlive) {
            System.out.println("""
                               0: login
                               1: registro
                               2: salir
                               """);
            int option = askOption(3);

            switch (option) {
                //Login
                case 0 -> {
                    Scanner sc = new Scanner(System.in);
                    if (user == null) {
                        System.out.println("Nombre: ");
                        String name = sc.nextLine();
                        System.out.println("Contraseña: ");
                        String password = sc.nextLine();
                        User userTry = new User(name, password);
                        user = Login.login(userTry);
                        if (user != null) {
                            System.out.println("Sesión iniciada\n");
                            while (user != null) {
                                menu();
                            }
                        } else {
                            System.out.println(Login.getErrorMessage());
                            if (Login.getAttempts() > 1) {
                                System.out.println("¿ Desea utilizar su pregunta de seguridad ? (s/n)");
                                String opt = sc.nextLine();
                                if (opt.equals("s")) {
                                    user = useSecurityQuestion(userTry);
                                    if(user == null) {
                                        System.out.println(Login.getErrorMessage());
                                    }
                                } else {
                                    Login.setAttempts(0);
                                }                                
                            }
                        }
                    }
                }
                //Register
                case 1 -> {
                    System.out.println("Nombre: ");
                    Scanner sc = new Scanner(System.in);
                    String name = sc.nextLine();
                    System.out.println("Contraseña: ");
                    String password = sc.nextLine();
                    System.out.println("Correo");
                    String email = sc.nextLine();
                    User userTry = new User(name, password, email);
                    user = Register.register(userTry);
                    if (user != null) {
                        System.out.println("Se ha registrado correctamente\n");
                        while (user != null) {
                            menu();
                            System.out.println();
                        }
                    } else {
                        System.out.println(Register.getErrorMessage());
                    }
                }
                //Exit
                case 2 -> {
                    keepAlive = false;
                }
            }
        }
    }
}
