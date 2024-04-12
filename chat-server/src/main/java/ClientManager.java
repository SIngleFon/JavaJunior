import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ClientManager implements Runnable {
    private final Socket socket;
    private String name;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    public static ArrayList<ClientManager> clients = new ArrayList();

    public ClientManager(Socket socket) {
        this.socket = socket;

        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.name = this.bufferedReader.readLine();
            clients.add(this);
            System.out.println(this.name + " подключился к чату.");
            this.broadcastMessage("Server: " + this.name + " подключился к чату.");
        } catch (IOException var3) {
            this.closeEverything(socket, this.bufferedReader, this.bufferedWriter);
        }

    }

    private void removeClient() {
        clients.remove(this);
        System.out.println(this.name + " покинул чат.");
        this.broadcastMessage("Server: " + this.name + " покинул чат.");
    }

    public String getName() {
        return name;
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        this.removeClient();

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (socket != null) {
                socket.close();
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    private void broadcastMessage(String message) {
        Iterator var2 = clients.iterator();
        String[] spl = message.split(" ");
        StringBuilder builder = new StringBuilder();
        if (spl[1].equals("/pm")){
            builder.append("PersonalMessage от ").append(spl[0]).append(" ");
            for (int i = 3; i < spl.length; i++) {
                builder.append(spl[i]).append(" ");
            }
            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }
            String result = builder.toString();


            for(ClientManager clientManager : clients){
                if(spl[2].equals(clientManager.getName())){
                    try{
                        clientManager.bufferedWriter.write(result);
                        clientManager.bufferedWriter.newLine();
                        clientManager.bufferedWriter.flush();
                    }catch (IOException var5) {
                        this.closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
                    }
                }
            }
        }else {

            while (var2.hasNext()) {
                ClientManager client = (ClientManager) var2.next();

                try {
                    if (!client.name.equals(this.name) && message != null) {
                        client.bufferedWriter.write(message);
                        client.bufferedWriter.newLine();
                        client.bufferedWriter.flush();
                    }
                } catch (IOException var5) {
                    this.closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
                }
            }
        }
    }

    public void run() {
        while(true) {
            if (this.socket.isConnected()) {
                try {
                    String massageFromClient = this.bufferedReader.readLine();
                    this.broadcastMessage(massageFromClient);
                    continue;
                } catch (IOException var3) {
                    this.closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
                }
            }

            return;
        }
    }
}