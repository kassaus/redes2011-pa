import entidades.Cliente;
import gestor.Gestor;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    private static final Gestor GESTOR = Gestor.getInstance();
    private static String ip = null;

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: java EchoClient2 <host>");
            System.exit(1);
        }
        ip = args[0];
        GESTOR.getListaAdmin();
        Cliente cliente = new Cliente(ip);

        if (isAdmin()) {
            cliente.desenhaMenuClienteAdmin();
        } else {
            cliente.desenhaMenuClienteVotante();
        }
    }

    private static Boolean isAdmin() {
        try {
            return GESTOR.getListaAdmin().contains(InetAddress.getByName(ip).getHostAddress());
        } catch (UnknownHostException e) {
            return false;
        }
    }

}
