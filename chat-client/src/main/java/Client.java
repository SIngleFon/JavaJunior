import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final String name;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public Client(Socket socket, String userName) {
        this.socket = socket;
        this.name = userName;

        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException var4) {
            this.closeEverything(socket, this.bufferedReader, this.bufferedWriter);
        }

    }

    public void sendMessage() {
        try {
            this.bufferedWriter.write(this.name);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);

            while(this.socket.isConnected()) {
                String message = scanner.nextLine();
                this.bufferedWriter.write(this.name + ": " + message);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            }
        } catch (IOException var3) {
            this.closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
        }

    }

    public void listenForMessage() {
        (new Thread(new Runnable() {
            public void run() {
                while(true) {
                    if (Client.this.socket.isConnected()) {
                        try {
                            String message = Client.this.bufferedReader.readLine();
                            System.out.println(message);
                            continue;
                        } catch (IOException var3) {
                            Client.this.closeEverything(Client.this.socket, Client.this.bufferedReader, Client.this.bufferedWriter);
                        }
                    }

                    return;
                }
            }
        })).start();
//        System.out.println("Завершение работы клиента");
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
}