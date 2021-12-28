package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class Chat extends JFrame {
	
	private JPanel panel;
	private JTextArea areaDeMensajes;
	private JButton boton;
	private JTextField cajaDeTexto;
	private Bot bot;
	private JLabel estadoBot;
	private Clip sonidoNotificacion;
	
	public Chat() {
		bot = new Bot("Boti");
		setSize(500, 700);
		setTitle("ChatBot");
		setLocationRelativeTo(null);  // Establecemos la ventana en el centro de la pantalla
		iniciarComponentes();
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Apagamos el programa cuando cerramos la ventana
	}
	
	private void iniciarComponentes() {
		agregarPanel();
		agregarNombreChat(bot.getNombre());
		agregarBoton();
		agregarCajaDeTexto();
		agregarAreaDeMensajes();
	}
	
	private void agregarPanel() {
		panel = new JPanel();
		panel.setLayout(null); // Desactivamos el diseño por defecto
		panel.setBackground(Color.BLACK);
		getContentPane().add(panel); // Agregamos el panel a la ventana
	}
	
	private void agregarNombreChat(String nombre) {
		JLabel nombreChat = new JLabel(nombre);
		nombreChat.setFont(new Font("arial", 0, 30));
		nombreChat.setBounds(70, 10, 60, 30);
		nombreChat.setForeground(Color.WHITE);
		//nombreChat.setOpaque(true);
		//nombreChat.setBackground(Color.RED);
		panel.add(nombreChat);
		
		agregarEstadoBot();
		agregarImagenBot();
	}
	
	private void agregarEstadoBot() {
		estadoBot = new JLabel("en linea");
		estadoBot.setFont(new Font("arial", 0, 20));
		estadoBot.setBounds(70, 40, 120, 20);
		estadoBot.setForeground(Color.WHITE);
		//estadoBot.setOpaque(true);
		//estadoBot.setBackground(Color.RED);
		panel.add(estadoBot);
	}
	
	private void agregarImagenBot() {
		ImageIcon imagenBot = new ImageIcon("Bot.png");
		JLabel etiquetaImagen = new JLabel();
		etiquetaImagen.setBounds(10, 10, 50, 50);
		etiquetaImagen.setIcon(new ImageIcon(imagenBot.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		panel.add(etiquetaImagen);
	}
	
	private void agregarAreaDeMensajes() {
		areaDeMensajes = new JTextArea();
		//areaDeTexto.setBounds(10, 10, this.getWidth() - 35, this.getHeight() - 100);
		areaDeMensajes.setEditable(false);
		areaDeMensajes.setBackground(Color.BLACK);
		areaDeMensajes.setForeground(Color.WHITE);
		areaDeMensajes.setFont(areaDeMensajes.getFont().deriveFont(20f));
		panel.add(areaDeMensajes);
		
		JScrollPane scroll = new JScrollPane(areaDeMensajes, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 65, this.getWidth() - 35, this.getHeight() - 140);
		panel.add(scroll);
		}
	
	private void agregarBoton() {
		ImageIcon imagenBoton = new ImageIcon("boton.png");
		boton = new JButton(imagenBoton);
		boton.setBounds(this.getWidth() - 65, this.getHeight() - 75, 40, 30);
		boton.setBackground(Color.GREEN);
		boton.setIcon(new ImageIcon(imagenBoton.getImage().getScaledInstance(50, 25, Image.SCALE_SMOOTH)));
		panel.add(boton);
		eventoOyenteDeMouse();
	}
	
	private void eventoOyenteDeMouse() {
		MouseListener mouseListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				simularConversacion();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		};
		boton.addMouseListener(mouseListener);
	}
	
	private void agregarCajaDeTexto() {
		cajaDeTexto = new JTextField();
		cajaDeTexto.setBounds(10, this.getHeight() - 75, this.getWidth() - 75, 30);
		cajaDeTexto.setBackground(Color.DARK_GRAY);
		cajaDeTexto.setForeground(Color.WHITE);
		panel.add(cajaDeTexto);
		
		eventosDelTeclado();
	}
	
	private void eventosDelTeclado() {
		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == '\n') {
					simularConversacion();
				}
			}
		};
		cajaDeTexto.addKeyListener(keyListener);
	}
	
	private void simularConversacion() {
		String mensaje = cajaDeTexto.getText();
		Thread t1 = new Thread() {
			
			@Override
			public void run() {
				estadoBot.setText("Escribiendo...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				escrituraBot(mensaje);
				estadoBot.setText("En linea");
			}
		};
		t1.start();
		agregarMensaje();
		cajaDeTexto.setText("");
	}
	
	private void agregarMensaje() {
		areaDeMensajes.append("Tú: " + cajaDeTexto.getText() + "\n");
	}
	
	private void escrituraBot(String s) {
		areaDeMensajes.append(bot.enviarRespuesta(s));
		try {
			iniciarNotificacion();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		sonidoNotificacion.start();
	}
	
	private void iniciarNotificacion() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		sonidoNotificacion = AudioSystem.getClip();
		sonidoNotificacion.open(AudioSystem.getAudioInputStream(new File("notificacion.wav")));
	}
}
