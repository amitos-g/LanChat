import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;
//C:\Users\Admin\Desktop\doc.txt
public class Client {

    //the client will send a path to a server
    //the server will respond with the contents of the file in that path

  
    public static Scanner reader = new Scanner(System.in);
    public static void main(String[] args) {
        try{
        System.out.println("This is the client, please enter a path");
        //get path from input
        String path = reader.next();

        Socket clientSocket = new Socket("localhost",2006);
        //get output stream and send path to file
        DataOutputStream stream = new DataOutputStream(clientSocket.getOutputStream());
        stream.writeUTF(path);
        stream.flush();
        //we sent it
        //lets read the response

        DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
        String content = inputStream.readUTF();
        System.out.println("The server sent that the file contains -> \n" + content);
        inputStream.close();
        stream.close();

        //we close the socket
        clientSocket.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
  }
  




}
