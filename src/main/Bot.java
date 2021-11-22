package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot {
	
	private Map<List<String>, List<String>> mensajesRespuestas;
	
	public Bot() {
		mensajesRespuestas = new HashMap<>();
		agregarMensajesRespuestas();
		
	}
	
	private void agregarMensajesRespuestas() {
		try {
			FileReader archivo = new FileReader("mensajes.txt");
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
			FileReader archivo = new FileReader("respuestas.txt");
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
	
	public static void main(String[] args) {
		Bot bot = new Bot();
		bot.agregarMensajesRespuestas();
		System.out.println(bot.mensajesRespuestas);
	}

}
