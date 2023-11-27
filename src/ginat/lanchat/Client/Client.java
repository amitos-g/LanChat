package ginat.lanchat.Client;
import java.io.*;
import java.net.*;
import java.util.*;


public class Client{

    private static Socket connection;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    public static Scanner reader = new Scanner(System.in);

    private static String name;

    private static AppUI ui;

    public static void init(String name,AppUI appUI){
        try{
            ui = appUI;
            connection = new Socket("localhost",2006);
            dataInputStream = new DataInputStream(connection.getInputStream());
            dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeUTF(name);
            //listen for acceptance
            String message = dataInputStream.readUTF();
            if(!message.equals("Error")){
                ui.welcome(message);
                listenToServer();
            }
            else{
                ui.error();
            }

        }


        catch(IOException message){
            ui.error();
        }
    }


    private static void listenToServer(){
        Runnable listener = new Runnable() {

            @Override
            public void run(){
                while(true){
                    try{
                        String msg = Client.dataInputStream.readUTF();
                        ui.appendMessage(msg);
                    }
                    catch(Exception e){
                        ui.error();
                        System.exit(0);
                    }
                }
            }
        };
        Thread thread = new Thread(listener);
        thread.start();
    }

    public static void sendToServer(String message){
        try {
            Client.dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            ui.error();
            System.exit(0);
        }
    }

}