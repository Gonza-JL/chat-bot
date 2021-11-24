package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	private JTextArea areaDeTexto;
	private JButton boton;
	private JTextField cajaDeTexto;
	private Bot bot;
	
	public Chat() {
		bot = new Bot("Boti");
		setSize(800, 600);
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
		
		JLabel enLinea = new JLabel("en linea");
		enLinea.setFont(new Font("arial", 0, 20));
		enLinea.setBounds(70, 40, 70, 20);
		enLinea.setForeground(Color.WHITE);
		//enLinea.setOpaque(true);
		//enLinea.setBackground(Color.RED);
		panel.add(enLinea);
		
		ImageIcon imagenBot = new ImageIcon("Bot.png");
		JLabel etiquetaImagen = new JLabel();
		etiquetaImagen.setBounds(10, 10, 50, 50);
		etiquetaImagen.setIcon(new ImageIcon(imagenBot.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		panel.add(etiquetaImagen);
	}
	
	private void agregarAreaDeMensajes() {
		areaDeTexto = new JTextArea();
		//areaDeTexto.setBounds(10, 10, this.getWidth() - 35, this.getHeight() - 100);
		areaDeTexto.setEditable(false);
		areaDeTexto.setBackground(Color.BLACK);
		areaDeTexto.setForeground(Color.WHITE);
		areaDeTexto.setFont(areaDeTexto.getFont().deriveFont(20f));
		panel.add(areaDeTexto);
		
		JScrollPane scroll = new JScrollPane(areaDeTexto, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 65, this.getWidth() - 35, this.getHeight() - 140);
		panel.add(scroll);
		}
	
	private void agregarBoton() {
		boton = new JButton("Enviar");
		boton.setBounds(this.getWidth() - 105, this.getHeight() - 75, 80, 30);
		boton.setBackground(Color.GREEN);
		panel.add(boton);
		eventoOyenteDeMouse();
	}
	
	private void eventoOyenteDeMouse() {
		MouseListener mouseListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				agregarMensaje();
				escrituraBot(cajaDeTexto.getText());
				cajaDeTexto.setText("");
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
		cajaDeTexto.setBounds(10, this.getHeight() - 75, this.getWidth() - 115, 30);
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
					agregarMensaje();
					escrituraBot(cajaDeTexto.getText());
					cajaDeTexto.setText("");
				}
			}
		};
		cajaDeTexto.addKeyListener(keyListener);
	}
	
	private void agregarMensaje() {
		areaDeTexto.append("Tú: " + cajaDeTexto.getText() + "\n");
	}
	
	private void escrituraBot(String s) {
		areaDeTexto.append(bot.enviarRespuesta(s));
	}
}
