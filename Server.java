import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

class ClientHandler implements Runnable{
    private String name;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    

    public ClientHandler(Socket socket) {
      this.socket = socket;
      try{
      this.dataInputStream = new DataInputStream(socket.getInputStream());
      this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
      //try to get the name of the client
      this.name = this.dataInputStream.readUTF();
      //if got the name:
      this.dataOutputStream.writeUTF("Welcome To The Server %s".formatted(this.name));
        
        
    }
      catch (Exception e){
          
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
    
    public DataOutputStream getDataOutputStream() {
      return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
      this.dataOutputStream = dataOutputStream;
    }

    public DataInputStream getDataInputStream() {
      return dataInputStream;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
      this.dataInputStream = dataInputStream;
    }

    @Override
    public void run(){
      //listen for messages and send them to the server.
      while(true){
        try{
        String message = this.dataInputStream.readUTF();
        Server.sendToServer(this.formatMessage(message));
        }
        catch(IOException e){

        }
      }
      
    }

    

}


public class Server {

  private static ArrayList<ClientHandler> connections = new ArrayList<>();
  private static ServerSocket server;
  public static void main(String[] args) {
    try {
      System.out.println("this is the server. listening on port 2006.");
      server = new ServerSocket(2006);
      acceptAndConnect();

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
              Socket newConnection = Server.server.accept();
              //try to create a socket of him
              ClientHandler newClient = new ClientHandler(newConnection);
              connections.add(newClient);
              Thread clienThread = new Thread(newClient);
              clienThread.start();
            }
              catch(IOException ioException){
                 
              }
             }
          }
      };
      Thread acceptorThread = new Thread(acceptor);
      acceptorThread.start();
    }

  public static void sendToServer(String message){
      for(var client : connections){
        try{
          client.getDataOutputStream().writeUTF(message);
        }
        catch(Exception e){

        }
      }
  }
}
