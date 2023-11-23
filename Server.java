import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

class ClientHandler{
    private String name;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    

    public ClientHandler(String name, Socket socket) {
      this.socket = socket;
      try{
      this.dataInputStream = new DataInputStream(socket.getInputStream());
      this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
      }
      catch (Exception e){
          this.socket.send
      }
    }

    public String formatMessage(String message){
       return "%s -> %s".formatted(this.name, message);
    }

    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public Socket getSocket() {
      return socket;
    }
    public void setSocket(Socket socket) {
      this.socket = socket;
    }


    

}


public class Server {

  private static ArrayList<ClientHandler> connections = new ArrayList<>();
  private static ServerSocket serverSocket;
  public static void main(String[] args) {
    try {
      System.out.println("this is the server. listening on port 2006.");
      serverSocket = new ServerSocket(2006);
      //listen for people
      Socket connectedSocket = server.accept();
      DataInputStream inputStream = new DataInputStream(connectedSocket.getInputStream());
      String entered = inputStream.readUTF();
      System.out.println("Client sent the path -> " + entered);
      entered = entered.replace("\\", "/");
      //"entered" is the file's path. lets read it.
      String content = readFile(entered);

      //lets send the content back.
      DataOutputStream outputStream = new DataOutputStream(connectedSocket.getOutputStream());
      outputStream.writeUTF(content);
      outputStream.flush();
      System.out.println("Sent the client the content of the file at that path");
      
      
      server.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private static void acceptAndConnect(){
      Runnable acceptor = new Runnable() {
          @Override
          public void run(){
             while(true){
              try{  
              Socket newConnection = Server.serverSocket.accept();
              //get his name
              String name = newConnection.
            }
              catch(IOException ioException){
                 
              }
             }
          }
      };
  }

  private static String readFile(String path){
    try{
    File file = new File(path);
    Scanner reader = new Scanner(file);

    StringBuilder builder = new StringBuilder();

    while(reader.hasNextLine()){
        builder.append(reader.nextLine() + "\n");
    }
    return builder.toString();
  }

    catch(Exception e){
      e.printStackTrace();
    }
  return null;
  }

}
