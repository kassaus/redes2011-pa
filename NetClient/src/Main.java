import entidades.Cliente;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("usage: java EchoClient2 <host>");
			System.exit(1);
		}
		Cliente cliente = new Cliente(args[0]);
		cliente.desenhaMenuClienteAdmin();

	}

}
