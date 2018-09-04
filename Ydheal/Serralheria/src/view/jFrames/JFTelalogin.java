package view.jFrames;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import controller.Validacaologin;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class JFTelalogin extends JFrame {

	private JPanel contentPane;
	private JTextField txtlogin;
	private JPasswordField txtsenha;

	public JFTelalogin() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				
				txtlogin.grabFocus();
			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/login.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setResizable(false);
		setTitle("Ydheal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setLocationRelativeTo(null);

		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setBounds(170, 84, 46, 14);
		contentPane.add(lblLogin);

		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setBounds(170, 109, 46, 14);
		contentPane.add(lblSenha);

		txtlogin = new JTextField();
		txtlogin.setBounds(220, 81, 164, 20);
		contentPane.add(txtlogin);
		txtlogin.setColumns(10);

		txtsenha = new JPasswordField();
		txtsenha.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == e.VK_ENTER) {

					Validacaologin Validar = new Validacaologin();

					if (Validar.Logar(txtlogin.getText(),
							String.valueOf(txtsenha.getPassword()))) {

						JOptionPane.showMessageDialog(null, "Bem-Vindo!");
						dispose();

					} else {
						JOptionPane.showMessageDialog(null, "Falha no login",
								"Login Incorreto", JOptionPane.ERROR_MESSAGE);

					}
				}

			}
		});
		txtsenha.setBounds(220, 109, 164, 20);
		contentPane.add(txtsenha);
		txtsenha.setColumns(10);

		JLabel lblEsqueceuSuaSenha = new JLabel("Esqueceu sua senha?");
		lblEsqueceuSuaSenha.setToolTipText("Recuperar senha");
		lblEsqueceuSuaSenha.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
			}

			public void ancestorMoved(AncestorEvent arg0) {
			}

			public void ancestorRemoved(AncestorEvent arg0) {
			}
		});
		lblEsqueceuSuaSenha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				// Redireciona para JFTelaesqueciminhasenha
				try {
					JFTelaesqueciminhasenha frame = new JFTelaesqueciminhasenha();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		lblEsqueceuSuaSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblEsqueceuSuaSenha.setBounds(170, 168, 129, 14);
		contentPane.add(lblEsqueceuSuaSenha);

		JButton btnEntrar = new JButton("Entrar");
		// Adicionando imagem.
		Image imgEntrar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnEntrar.setIcon(new ImageIcon(imgEntrar));
		btnEntrar.setToolTipText("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Validacaologin Validar = new Validacaologin();

				if (Validar.Logar(txtlogin.getText(),
						String.valueOf(txtsenha.getPassword()))) {

					JOptionPane.showMessageDialog(null, "Bem-Vindo!");
					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "Falha no login",
							"Login Incorreto", JOptionPane.ERROR_MESSAGE);

				}

			}
		});
		btnEntrar.setBounds(170, 134, 100, 23);
		contentPane.add(btnEntrar);

		JButton btnSair = new JButton("Sair");
		// Adicionando imagem.
		Image imgSair = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnSair.setIcon(new ImageIcon(imgSair));
		btnSair.setToolTipText("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.exit(0);

			}
		});
		btnSair.setBounds(284, 134, 100, 23);
		contentPane.add(btnSair);

		JLabel lblNewLabel_1 = new JLabel("");
		// Adicionando imagem.
		Image imgNew = new ImageIcon(this.getClass().getResource(
				"/100px/login.png")).getImage();
		lblNewLabel_1.setIcon(new ImageIcon(imgNew));
		lblNewLabel_1.setBounds(60, 58, 100, 100);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("Serralheria Ydheal");
		lblNewLabel.setFont(new Font("Stencil", Font.PLAIN, 20));
		lblNewLabel.setForeground(new Color(60, 179, 113));
		lblNewLabel.setBounds(124, 11, 245, 42);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 0, 0));
		separator.setForeground(new Color(0, 0, 0));
		separator.setBounds(10, 28, 100, 1);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBackground(Color.BLACK);
		separator_1.setBounds(346, 28, 100, 1);
		contentPane.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setForeground(Color.BLACK);
		separator_2.setBackground(Color.BLACK);
		separator_2.setBounds(444, 28, 1, 182);
		contentPane.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setForeground(Color.BLACK);
		separator_3.setBackground(Color.BLACK);
		separator_3.setBounds(10, 28, 1, 182);
		contentPane.add(separator_3);

		
		
		
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.BLACK);
		separator_4.setBackground(Color.BLACK);
		separator_4.setBounds(10, 208, 436, 1);
		contentPane.add(separator_4);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { lblLogin, txtlogin, txtsenha, btnEntrar,
						btnSair, lblSenha, lblEsqueceuSuaSenha, lblNewLabel_1,
						lblNewLabel, separator, separator_1, separator_2,
						separator_3, separator_4 }));
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				contentPane, txtlogin, txtsenha, btnEntrar, btnSair,
				lblEsqueceuSuaSenha, lblLogin, lblSenha, lblNewLabel_1 }));
	}
}
