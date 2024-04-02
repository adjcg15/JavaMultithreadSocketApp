package ServidorMultihilo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;

public class HiloHandler implements Runnable {
    private final Socket ch;
    private final int id;
    PrintWriter out;
    BufferedReader in;

    public HiloHandler(Socket ch, int id) throws IOException {
        this.ch = ch;
        this.id = id;
        out = new PrintWriter(ch.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.ch.getInputStream()));

        System.out.println("Conexión recibida del cliente: " + ch.getInetAddress().getHostAddress());
        System.out.println("El servidor tiene " + id + " clientes conectados");
    }

    @Override
    public void run() {
        try {
            String filePath = Paths.get("ServidorMultihilo\\archivote.csv").toAbsolutePath().toString();
            File fileIn = new File(filePath);

            FileReader fr = new FileReader(fileIn);
            BufferedReader br = new BufferedReader(fr);

            String readLine;
            while ((readLine = br.readLine()) != null) {
                out.println(readLine);
            }

            out.println("EOF");
            br.close();
            fr.close();

            System.out.println("Cliente " + id + ": " + in.readLine());

            out.close();
            in.close();
            ch.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
