import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Random;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo{

	public static void main(String[] args) {
		DatagramSocket socketServicio;
		// Puerto de escucha
		int port=8989;
		// array de bytes auxiliar para recibir o enviar datos.
		byte []buferEnvio=new byte[256];
		byte []buferRecepcion=new byte[256];

		// Número de bytes leídos
		int bytesLeidos=0;

		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			//////////////////////////////////////////////////
			socketServicio = new DatagramSocket(port);
			//////////////////////////////////////////////////

			// Mientras ... siempre!
			do {
				//Recibimos el paquete a yodificar
				DatagramPacket paquete;
				paquete = new DatagramPacket(buferRecepcion, buferRecepcion.length);//Creas un paquete del mismo tamaño
				socketServicio.receive(paquete);
				String fraseAYodificar;
				fraseAYodificar = new String(paquete.getData()); // Frase a yodificar

				//Yodificamos el paquete
				Random random=new Random();
				String[] s = fraseAYodificar.split(" ");
				String resultado="";

				for(int i=0;i<s.length;i++){
					int j=random.nextInt(s.length);
					int k=random.nextInt(s.length);
					String tmp=s[j];

					s[j]=s[k];
					s[k]=tmp;
				}

				resultado=s[0];
				for(int i=1;i<s.length;i++){
				  resultado+=" "+s[i];
				}

				System.out.println("Recibido: " + resultado + "\n");


			} while (true);

		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}
