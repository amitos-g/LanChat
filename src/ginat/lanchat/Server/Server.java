package ginat.lanchat.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler extends Thread{
    private String name;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    public static int lastID;
    private int cid;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try{
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //try to get the name of the client
            this.name = this.dataInputStream.readUTF();
            this.setName(name);
            this.cid = lastID+1;
            lastID++;
            //if got the name:
            this.dataOutputStream.writeUTF("Welcome To The Chat %s!".formatted(this.name));
            Server.sendToServer("%s has connected to the chat!".formatted(this.name));

        }
        catch (Exception e){
            try {
                this.dataOutputStream.writeUTF("Error");
            }
            catch (Exception ignored){
                System.out.println("Error");
            }
        }
    }







    public int getCID(){
        return this.cid;
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
                System.out.println(message);
                Server.sendToServer(message);
            }
            catch (Exception e){
                Server.removeFromServer(this.cid);
                Server.sendToServer("Server -> %s has disconnected".formatted(this.getName()));
                try {
                    this.socket.close();
                    this.dataOutputStream.close();
                    this.dataInputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                interrupt();
                break;
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
                        newClient.start();
                    }
                    catch(IOException ioException){
                        break;
                    }
                }
            }
        };
        Thread acceptorThread = new Thread(acceptor);
        acceptorThread.setName("Connector");
        acceptorThread.start();
    }


    public static void removeFromServer(int cid)  {
        for(var client : connections){
            if(client.getCID() == cid){
                connections.remove(client);
            }
        }
    }


    public static void sendToServer(String message){
        for(var client : connections){
            try{
                client.getDataOutputStream().writeUTF(message);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
