//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Random;

public class YodafyClienteTCP {

	public static void main(String[] args) {


		// Puerto en el que espera el servidor:
		int port=8989;
		InetAddress direccion = null; //Dirección IP del servidor
		DatagramPacket paquete = null; //Paquete que contiene los datos
		byte[] buferEnvio = new byte[256];
		byte[] buferRecepcion = new byte[256];
		DatagramSocket socketServicio = null;

			//Enviar paquete a través de socket
		try {
			socketServicio= new DatagramSocket();
		} catch (IOException e) {
					System.err.println("Error de entrada/salida al abrir el socket.");
		}
		try {
			direccion= InetAddress.getByName("ei150142.ugr.es");
		} catch (UnknownHostException e) {
					System.out.println("Error al recuperar la direccion.");
			}
			buferEnvio="Al monte del volcan debes ir sin demora".getBytes();

try {
			//Crear y enviar paquete
			paquete = new DatagramPacket(buferEnvio,buferEnvio.length,direccion,port);
			socketServicio.send(paquete);

			//Recibir paquete a través de socket
			paquete = new DatagramPacket(buferRecepcion, buferRecepcion.length);//Creas un paquete del mismo tamaño
			socketServicio.receive(paquete);
			String respuesta;
			respuesta = new String(paquete.getData());

			//Mostramos lo recibido
			System.out.println("Recibido: " + respuesta + "\n");

			// Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
			// el inpuStream  y el outputStream)
			//////////////////////////////////////////////////////
			socketServicio.close();
			//////////////////////////////////////////////////////

			// Excepciones:
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
