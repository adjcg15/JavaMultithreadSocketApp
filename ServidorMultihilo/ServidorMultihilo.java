package ServidorMultihilo;

import java.io.IOException;
import java.net.ServerSocket;

public class ServidorMultihilo {
    public static void main(String[] args) {
        int port = 8080;
        int clientsCounter = 0;

        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en el puerto " + port + "...");
            System.out.println("El servidor tiene " + clientsCounter + " clientes conectados");

            while (true) {
                HiloHandler client = new HiloHandler(ss.accept(), ++clientsCounter);
                Thread t1 = new Thread(client);
                t1.start();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}