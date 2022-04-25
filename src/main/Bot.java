package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Bot {
	
	private String nombre;
	private Map<List<String>, List<String>> mensajesRespuestas;
	private List<String> predeterminadas;
	private final File mensajesTxt = new File("data/mensajes.txt");
	private final File respuestasTxt = new File("data/respuestas.txt");
	
	public Bot(String nombre) {
		this.nombre = nombre;
		this.mensajesRespuestas = new HashMap<>();
		this.predeterminadas = new ArrayList<>();
		agregarMensajesRespuestas();
		agregarPredeterminadas();
	}
	
	public String enviarRespuesta(String s) {
		if(s.isEmpty()) {
			return nombre + ": " + "Escribe algo" + "\n";
		}
		s = s.toLowerCase();
		s = Normalizer.normalize(s, Normalizer.Form.NFD); 
		s = s.replaceAll("[^\\p{ASCII}]", "");
		for(List<String> mensajes: mensajesRespuestas.keySet()) {
			if(mensajes.contains(s)) {
				Random r = new Random();
				int stringAleatorio = r.nextInt(mensajesRespuestas.get(mensajes).size());
				return nombre + ": " + mensajesRespuestas.get(mensajes).get(stringAleatorio) + "\n";
			}
		}
		return respuestaAleatoria();
	}
	
	private void agregarMensajesRespuestas() {
		try {
			FileReader archivo = new FileReader(mensajesTxt);
			@SuppressWarnings("resource")
			BufferedReader lector = new BufferedReader(archivo);
			int c, i = 1;
			String string = "";
			List<String> mensajes = new ArrayList<>();
			while((c = lector.read()) != -1) {
				if(c == '-') {
					mensajes.add(string);
					string = "";
				} else if(c == '\n') {
					mensajesRespuestas.put(mensajes, respuestas(i));
					mensajes = new ArrayList<>();
					string = "";
					i++;
				} else {
					string += (char) c;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<String> respuestas(Integer linea) {
		List<String> respuestas = new ArrayList<>();
		try {
			FileReader archivo = new FileReader(respuestasTxt);
			@SuppressWarnings("resource")
			BufferedReader lector = new BufferedReader(archivo);
			int i = 1;
			String s = "", string = "";
			while((string = lector.readLine()) != null) {
				if(i == linea) {
					s = string;
				}
				i++;
			}
			string = "";
			for(int j = 0; j < s.length(); j++) {
				if(s.charAt(j) == '-') {
					respuestas.add(string);
					string = "";
				} else if(s.charAt(j) == '\n') {
					return respuestas;
				} else {
					string += s.charAt(j);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respuestas;
	}
	
	private void agregarPredeterminadas() {
		predeterminadas.add("No sé que responder ante eso :(\n");
		predeterminadas.add("Mi creador no me programó para responderte eso\n");
		predeterminadas.add("Mi conocimiento es demasiado escaso para responderte\n");
		predeterminadas.add("Es todo un tema\n");
		predeterminadas.add("Me dejaste sin palabras\n");
		predeterminadas.add("No puedo responderte todo :(\n");
		predeterminadas.add("No soy real, no puedo responderte eso\n");
		predeterminadas.add("Lo siento, no sé como responder\n");
	}
	
	private String respuestaAleatoria() {
		Random r = new Random();
		int stringAleatorio = r.nextInt(predeterminadas.size());
		return nombre + ": " + predeterminadas.get(stringAleatorio);
	}

	public String getNombre() {
		return nombre;
	}

}
