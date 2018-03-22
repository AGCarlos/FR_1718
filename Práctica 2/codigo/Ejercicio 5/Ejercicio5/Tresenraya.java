import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Tresenraya {
	private Socket socketServicio;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String user;
	private String password;
	private boolean login = false;
	private String nombrePartida = new String();
	private ArrayList<String> Tablero = new ArrayList<String>();
	private String ficha;
	private String fichaJugador;
	private String nombreJugador;
	private Map<String,Integer> Ranking = new HashMap<String,Integer>();//Ranking
	private HashMap<String,String> usuarios = new HashMap<String,String>();//usuarios
	private HashMap<String,ArrayList<ArrayList<String>>> partidag = new HashMap<String,ArrayList<ArrayList<String>>>();
	private ArrayList<String> listaPartidas = new ArrayList<String>();
	private ArrayList<ArrayList<String>> proceso = new ArrayList<ArrayList<String>>();
	private int posicion;

public void IA(){
	Tablero.set(Tablero.indexOf("_"),ficha);
}

public boolean VeredictoJugador(){
	boolean x = false;

	if( (Tablero.get(0) == fichaJugador && Tablero.get(1) == fichaJugador && Tablero.get(2) == fichaJugador) ||
			(Tablero.get(3) == fichaJugador && Tablero.get(4) == fichaJugador && Tablero.get(5) == fichaJugador) ||
			(Tablero.get(6) == fichaJugador && Tablero.get(7) == fichaJugador && Tablero.get(8) == fichaJugador) ||
			(Tablero.get(0) == fichaJugador && Tablero.get(3) == fichaJugador && Tablero.get(6) == fichaJugador) ||
			(Tablero.get(1) == fichaJugador && Tablero.get(4) == fichaJugador && Tablero.get(7) == fichaJugador) ||
			(Tablero.get(2) == fichaJugador && Tablero.get(5) == fichaJugador && Tablero.get(8) == fichaJugador) ||
			(Tablero.get(0) == fichaJugador && Tablero.get(4) == fichaJugador && Tablero.get(8) == fichaJugador) ||
			(Tablero.get(2) == fichaJugador && Tablero.get(4) == fichaJugador && Tablero.get(6) == fichaJugador) ){
				System.out.println("\n" + "|"+Tablero.get(0)+"|"+Tablero.get(1)+"|"+Tablero.get(2)+"|" +"\n"+ "|"+Tablero.get(3)+"|"+Tablero.get(4)+"|"+Tablero.get(5)+"|" +"\n"+ "|"+Tablero.get(6)+"|"+Tablero.get(7)+"|"+Tablero.get(8)+"|");
				System.out.println("\nWINNER WINNER CHIKEN DINNER");
				x= true;
			}

			return x;
}

public boolean VeredictoServidor(){
	boolean x = false;

	if( (Tablero.get(0) == ficha && Tablero.get(1) == ficha && Tablero.get(2) == ficha) ||
			(Tablero.get(3) == ficha && Tablero.get(4) == ficha && Tablero.get(5) == ficha) ||
			(Tablero.get(6) == ficha && Tablero.get(7) == ficha && Tablero.get(8) == ficha) ||
			(Tablero.get(0) == ficha && Tablero.get(3) == ficha && Tablero.get(6) == ficha) ||
			(Tablero.get(1) == ficha && Tablero.get(4) == ficha && Tablero.get(7) == ficha) ||
			(Tablero.get(2) == ficha && Tablero.get(5) == ficha && Tablero.get(8) == ficha) ||
			(Tablero.get(0) == ficha && Tablero.get(4) == ficha && Tablero.get(8) == ficha) ||
			(Tablero.get(2) == ficha && Tablero.get(4) == ficha && Tablero.get(6) == ficha) ){
				System.out.println("\n" + "|"+Tablero.get(0)+"|"+Tablero.get(1)+"|"+Tablero.get(2)+"|" +"\n"+ "|"+Tablero.get(3)+"|"+Tablero.get(4)+"|"+Tablero.get(5)+"|" +"\n"+ "|"+Tablero.get(6)+"|"+Tablero.get(7)+"|"+Tablero.get(8)+"|");
				System.out.println("\nGana el Servidor");
				x= true;
			}

			return x;
}

public boolean listaPartidasVacia(){
	boolean x = true;

	for (int i = 0; i < listaPartidas.size(); i++) {
		if(!listaPartidas.get(i).equals("vacio"))
			x = false;
	}

	return x;
}

public void InicializarListaPartidas(){
	for (int i = 0; i < 5; i++ ) {
		listaPartidas.add("vacio");
	}
}
public void GuardarProceso(){
	ArrayList<String> t = new ArrayList<String>();

	t.addAll(Tablero);

	proceso.add(t);
}

public void Envio(String texto, PrintWriter p){
	String envio = new String();

	envio = texto;
	p.println(envio);
}

public boolean newUser(String usuario, String pass){
	boolean exito = false;

	if (!usuarios.containsKey(usuario))
		usuarios.put(usuario,pass);
		exito = true;         // Usuario creado con exito

	return exito;
}

public boolean loginUser(String usuario, String pass){
 return (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(pass));
}

public Tresenraya(Socket socketServicio){
	this.socketServicio = socketServicio;
}

	void Saludo(){
		InicializarListaPartidas();
		String datosRecibidos;
		String datosEnviar = new String();
		boolean reg = false;
		boolean trylogin = false;

		try {
			inputStream = socketServicio.getInputStream();
			outputStream = socketServicio.getOutputStream();
			BufferedReader inReader = new BufferedReader(new InputStreamReader(inputStream));
			PrintWriter outPrinter = new PrintWriter(outputStream,true);

			datosRecibidos = inReader.readLine();

			while(!login){
				datosEnviar = "Bienvenido al 3 en raya online.Seleccione una opcion: 1.Registrar usuario. 2.Login.";
				Envio(datosEnviar, outPrinter);

				datosRecibidos = inReader.readLine();

				//Registrarse
				if(datosRecibidos.equals("1")){
					datosEnviar = "Registro Ingrese el nombre y la contraseña(LOGIN + user + PASS + password):";
					Envio(datosEnviar,outPrinter);

					datosRecibidos = inReader.readLine();
					user = datosRecibidos.substring(6, datosRecibidos.indexOf("PASS"));
					password = datosRecibidos.substring(datosRecibidos.indexOf("PASS")+5, datosRecibidos.length());

					if(newUser(user,password))
						System.out.println("Ingresado correctamente");
					else
						System.out.println("Ingresado no correctamente");
				}

				//Login
				if(datosRecibidos.equals("2")){
					datosEnviar = "Login Ingrese el nombre y la contraseña(LOGIN + user + PASS + password):";
					Envio(datosEnviar,outPrinter);

					datosRecibidos = inReader.readLine();
					user = datosRecibidos.substring(6, datosRecibidos.indexOf("PASS"));
					password = datosRecibidos.substring(datosRecibidos.indexOf("PASS")+5, datosRecibidos.length());

					if(loginUser(user,password)){
						System.out.println("Logeado correctamente");
						login = true;
					}else{
						System.out.println("Logeado no correctamente");
					}
				}
			}

			while(login){
				datosEnviar = "Bienvenido. Seleccione una opcion: 1.Jugar 3 en raya. 2.Administrar partidas guardadas. 3.Ver ranking. Para salir escriba logout ";
				Envio(datosEnviar, outPrinter);

				datosRecibidos = inReader.readLine();

				if(datosRecibidos.equals("1")){
					for (int i = 0; i <= 9; i++){
						Tablero.add("_");
					}
					boolean finJugador = false;
					boolean finServidor = false;
					boolean fin = false;
					boolean Colocado=false;
					boolean fichacorrecta = false;
					boolean numerocorrecto = false;

					//Nombre del jugador
          datosEnviar = "Introduzca el nombre del jugador";
          Envio(datosEnviar, outPrinter);

          datosRecibidos = inReader.readLine();
          nombreJugador = datosRecibidos;

					//Nombre de partida
					datosEnviar = "Introduzca el nombre para la partida";
					Envio(datosEnviar, outPrinter);

					datosRecibidos = inReader.readLine();
					nombrePartida = datosRecibidos;

					//Elección de ficha
					datosEnviar = "Seleccione la ficha (x ó o)";
					Envio(datosEnviar, outPrinter);

					datosRecibidos = inReader.readLine();

					while(!fichacorrecta){
						if(datosRecibidos.equals("x") || datosRecibidos.equals("o")){
							fichaJugador = datosRecibidos;
							fichacorrecta = true;
							if(datosRecibidos.equals("x"))
								ficha = "o";
							else
								ficha = "x";
						}else{
							datosEnviar = " Ficha incorrecta, vuelva a introducir ficha";
							Envio(datosEnviar, outPrinter);

							datosRecibidos = inReader.readLine();
						}
					}

					//Partida
					while(!fin){

						System.out.println("\n" + "|"+Tablero.get(0)+"|"+Tablero.get(1)+"|"+Tablero.get(2)+"|" +"\n"+ "|"+Tablero.get(3)+"|"+Tablero.get(4)+"|"+Tablero.get(5)+"|" +"\n"+ "|"+Tablero.get(6)+"|"+Tablero.get(7)+"|"+Tablero.get(8)+"|");

						numerocorrecto = false;
						while(!numerocorrecto){
							datosEnviar = "Introduzca ficha [0-8]";
							Envio(datosEnviar, outPrinter);

							datosRecibidos = inReader.readLine();
							posicion = Integer.parseInt(datosRecibidos);
							if(posicion >= 0 && posicion <= 8){
								numerocorrecto = true;
							}
						}

						Colocado=false;

						while(!Colocado){
							if(!(Tablero.get(posicion).equals("_"))){
								datosEnviar = "Ya hay una ficha en esa posición, introduzca otra posición o el numero no es correcto";
								Envio(datosEnviar, outPrinter);

								datosRecibidos = inReader.readLine();
								posicion = Integer.parseInt(datosRecibidos);
							}else{
								Colocado=true;
								Tablero.set(posicion, fichaJugador);
							}

						}

						posicion = -1;
						finJugador = VeredictoJugador();

						if(!finJugador){
							IA();
							finServidor = VeredictoServidor();
						}

						GuardarProceso();

						fin = (finJugador || finServidor);

						if(fin){
							datosEnviar = "Quieres guardar la partida";
							Envio(datosEnviar, outPrinter);

							datosRecibidos = inReader.readLine();

							if(datosRecibidos.equals("SI") || datosRecibidos.equals("si")){
								System.out.println("Partida Guardada");
								listaPartidas.set(listaPartidas.indexOf("vacio"),nombrePartida);
								ArrayList<ArrayList<String>> a = new ArrayList<ArrayList<String>>();
								a.addAll(proceso);
								partidag.put(nombrePartida,a);
								proceso.clear();
							}else{
								System.out.println("Partida no guardada");
								proceso.clear();
							}
							nombrePartida = new String();
						}
					}

					//Guardar jugador en el ranking
          if(finJugador){
            if(Ranking.containsKey(nombreJugador)){
              int victorias=Ranking.get(nombreJugador);
              Ranking.remove(nombreJugador);
              Ranking.put(nombreJugador,victorias+1);
            }else{
              Ranking.put(nombreJugador,1);
            }
          }
					Tablero.clear();
				}

				if(datosRecibidos.equals("2")){
					if(!listaPartidasVacia()){
						for (int i = 0; i < listaPartidas.size(); i++) {
							System.out.println(i+1 +". "+ listaPartidas.get(i));
						}

						datosEnviar = "1. Reproducir partida 2. Borrar partida";
						Envio(datosEnviar, outPrinter);

						datosRecibidos = inReader.readLine();

						if(datosRecibidos.equals("1")){
							datosEnviar ="Seleccione una partida para reproducir: ";
							Envio(datosEnviar, outPrinter);

							datosRecibidos = inReader.readLine();

							String s = listaPartidas.get(Integer.parseInt(datosRecibidos)-1);

							if(partidag.containsKey(s)){
								ArrayList<ArrayList<String>> p = partidag.get(s);

								System.out.println("Nombre Partida "+ s + " Reproduciendo...");

								for (int i = 0; i < p.size(); i++) {
									System.out.println("Estado " + (i+1) + "\n"+ "|"+p.get(i).get(0)+"|"+p.get(i).get(1)+"|"+p.get(i).get(2)+"|" +"\n"+ "|"+p.get(i).get(3)+"|"+p.get(i).get(4)+"|"+p.get(i).get(5)+"|" +"\n"+ "|"+p.get(i).get(6)+"|"+p.get(i).get(7)+"|"+p.get(i).get(8)+"|" + "\n");
								}

								datosRecibidos = " ";
							}else{
								System.out.println("Esta vacia");
							}
						}

						if(datosRecibidos.equals("2")){
							datosEnviar ="Seleccione una partida para borrar: ";
							Envio(datosEnviar, outPrinter);

							datosRecibidos = inReader.readLine();

							if(partidag.containsKey(listaPartidas.get(Integer.parseInt(datosRecibidos)-1))){
								partidag.remove(listaPartidas.get(Integer.parseInt(datosRecibidos)-1));
								listaPartidas.set(listaPartidas.indexOf(listaPartidas.get(Integer.parseInt(datosRecibidos)-1)),"vacio");
								System.out.println("Partida borrada");
							}else{
								System.out.println("No existe la partida");
							}
						}
					}else{
						System.out.println("No tiene partidas guardadas");
					}
				}

				if(datosRecibidos.equals("3")){
          String jugador = new String();
          Integer nVic = 0;
          Integer vueltas= Ranking.size();

          Map<String,Integer> RankingTmp = new HashMap<String,Integer>();//Ranking

          RankingTmp.putAll(Ranking);

            //Esto se repite Ranking.size() veces
            System.out.println("Ranking:");
            for(int i=0;i<vueltas;i++){
              for (Map.Entry<String, Integer> entrada : RankingTmp.entrySet()) {
                  if(entrada.getValue() >= nVic){
                    nVic=entrada.getValue();
                    jugador = entrada.getKey();
                  }
              }
              RankingTmp.remove(jugador);
              System.out.println((i+1) + "."+ jugador + " con "+ nVic +" victorias");
              nVic=0;
            }
        }
			}
		} catch (IOException e) {
			System.err.println("Error al obtener los flujo de entrada/salida.");
		}
	}
}
