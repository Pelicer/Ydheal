package view.jFrames;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Reenviarsenha;

@SuppressWarnings("serial")
public class JFTelaesqueciminhasenha extends JFrame {

	private JPanel contentPane;
	private JTextField txtlogin;
	private JTextField txtemail;
	private JButton btnReenviarMinhaSenha;
	private JLabel lblColoqueSeuNome;
	private JLabel lblEsqueciMinhaSenha;
	private JButton btnFechar;

	public JFTelaesqueciminhasenha() {

		setResizable(false);
		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/login.png")).getImage();
		setIconImage(imgIcon);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// inicia no centro da tela
		setLocationRelativeTo(null);

		JLabel lblNomeDeUsuariologin = new JLabel("Nome de usuario/login: ");
		lblNomeDeUsuariologin.setBounds(10, 96, 169, 14);
		contentPane.add(lblNomeDeUsuariologin);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(10, 121, 46, 14);
		contentPane.add(lblEmail);

		txtlogin = new JTextField();
		txtlogin.setBounds(173, 93, 164, 20);
		contentPane.add(txtlogin);
		txtlogin.setColumns(10);

		txtemail = new JTextField();
		txtemail.setBounds(173, 121, 164, 20);
		contentPane.add(txtemail);
		txtemail.setColumns(10);

		btnReenviarMinhaSenha = new JButton("Reenviar minha senha");
		Image imgReenviar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnReenviarMinhaSenha.setIcon(new ImageIcon(imgReenviar));
		btnReenviarMinhaSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Reenviarsenha Reenviar = new Reenviarsenha();

				JOptionPane.showMessageDialog(
						null,
						Reenviar.reenviar(txtlogin.getText(),
								txtemail.getText()));

			}
		});
		btnReenviarMinhaSenha.setBounds(173, 152, 164, 23);
		contentPane.add(btnReenviarMinhaSenha);

		lblColoqueSeuNome = new JLabel(
				"Informe seu nome de usuario/login e seu e-mail ");
		lblColoqueSeuNome.setVerticalAlignment(SwingConstants.TOP);
		lblColoqueSeuNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblColoqueSeuNome.setBounds(41, 36, 306, 20);
		contentPane.add(lblColoqueSeuNome);

		lblEsqueciMinhaSenha = new JLabel("Esqueci minha senha");
		lblEsqueciMinhaSenha.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEsqueciMinhaSenha.setBounds(10, 11, 158, 14);
		contentPane.add(lblEsqueciMinhaSenha);

		btnFechar = new JButton("Fechar");
		Image imgFechar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnFechar.setIcon(new ImageIcon(imgFechar));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();

			}
		});
		btnFechar.setBounds(10, 152, 153, 23);
		contentPane.add(btnFechar);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 62, 347, 2);
		contentPane.add(separator);
	}
}
