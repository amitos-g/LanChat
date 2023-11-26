import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

    private static Socket connection;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    public static Scanner reader = new Scanner(System.in);
    public static void init(){
        try{
            System.out.println("Hello! Please enter your name to enter the chat. -> ");
            String name = reader.nextLine();
            connection = new Socket("localhost",2006);
            dataInputStream = new DataInputStream(connection.getInputStream());
            dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeUTF(name);
            //listen for acceptance
            String message = dataInputStream.readUTF();
            if(message != "Error"){
                System.out.println(message);
                listenToServer();
                listenToClient();
            }
            else{
                throw new IOException();
            }

        }

        catch(UnknownHostException hostException){
            System.out.println("Unkown Host, do you want to connect to your localhost? type - [yes],[no]");
            String message = reader.nextLine();
            switch (message) {
                case "yes":
                    init();
                    break;
                case "no":
                    System.exit(0);
                    break;
                default:
                    break;
            }

        }
        catch(IOException message){
            System.out.println("Can't send message");
        }
    }
    

    private static void listenToServer(){
        Runnable listener = new Runnable() {
            
            @Override
            public void run(){
                while(true){
                    try{
                        String msg = Client.dataInputStream.readUTF();
                        System.out.println(msg);
                    }
                    catch(Exception e){
                        System.out.println("Server is down");
                        System.exit(0);
                    }
                }
            }
        };
        Thread thread = new Thread(listener);
        thread.start();
    }

    private static void listenToClient(){
        while(true){
            try{
                 String msg = reader.nextLine();

                 Client.dataOutputStream.writeUTF(msg);
                    
            }
            catch(Exception e){
                    System.out.println("The Server Is Probably Closed. Please Try Again Later.");
                    System.exit(0);
                }
            }
    }

    public static void main(String[] args) {
        init();
    }
}
