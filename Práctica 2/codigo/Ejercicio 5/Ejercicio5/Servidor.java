import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor{

	public static void main(String[] args) {
		ServerSocket socketServidor;
		int port=8989;
		String buffer;

		try {
			socketServidor = new ServerSocket(port);

			do {
				Socket socketConexion = null;
				try {
						socketConexion = socketServidor.accept();
				} catch (IOException e) {
					System.out.println("Error: no se pudo aceptar la conexión solicitada");
				}

				Tresenraya procesador = new Tresenraya(socketConexion);
				procesador.Saludo();

			} while (true);

		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}
