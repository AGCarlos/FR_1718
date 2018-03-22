import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente{
	public static void Mensaje(String texto, PrintWriter p, BufferedReader b){
		String envio = new String();
		String recepcion = new String();

		try{
			envio = texto;

			p.println(envio);
			p.flush();

			recepcion = b.readLine();

			System.out.println("\nServidor: "+recepcion+ "\n");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}

	public static void main(String[] args) {
		String buferEnvio;
		String buferRecepcion;
		String host="localhost";
		int port=8989;
		Socket socketServicio = null;
		Boolean logout = true;

		try {

			socketServicio = new Socket(host,port);
			InputStream inputStream = socketServicio.getInputStream();
			OutputStream outputStream = socketServicio.getOutputStream();
			PrintWriter outPrinter = new PrintWriter(outputStream, true);
			BufferedReader inReader = new BufferedReader(new InputStreamReader(inputStream));
			Scanner sc = new Scanner(System.in);

			//Enviamos el saludo
			buferEnvio="HELO";
			Mensaje(buferEnvio, outPrinter, inReader);

			while(logout){
				buferEnvio = sc.nextLine();
				if(!buferEnvio.equals("LOGOUT") && !buferEnvio.equals("logout"))
					Mensaje(buferEnvio, outPrinter, inReader);
				else
					logout = false;
			}

			socketServicio.close();


		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
