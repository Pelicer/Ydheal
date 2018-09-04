package view.jDialogs;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import view.jFrames.JFTelalogin;

@SuppressWarnings("serial")
public class JDFechar extends JDialog {

	public JDFechar() {
		
		setResizable(false);
		setTitle("Sair");
		Image imgSair = new ImageIcon(this.getClass().getResource("/logo.png"))
				.getImage();
		setIconImage(imgSair);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 456, 250);
		getContentPane().setLayout(null);

		JLabel lblParaOnde = new JLabel("Para onde deseja ser redirecionado?");
		lblParaOnde.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblParaOnde.setBounds(10, 11, 414, 25);
		getContentPane().add(lblParaOnde);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 47, 434, 2);
		getContentPane().add(separator);

		JButton btnDesktop = new JButton("");
		// Adicionando imagem.
		Image imgDesktop = new ImageIcon(this.getClass().getResource(
				"/50px/dektop.png")).getImage();
		btnDesktop.setIcon(new ImageIcon(imgDesktop));
		btnDesktop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnDesktop.setToolTipText("\u00C1rea de Trabalho");
		btnDesktop.setBounds(123, 88, 50, 50);
		getContentPane().add(btnDesktop);

		JButton btnContinuar = new JButton("");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnContinuar.setToolTipText("Continuar no Sistema");
		// Adicionando imagem.
		Image imgContinuar = new ImageIcon(this.getClass().getResource(
				"/logo.png")).getImage();
		btnContinuar.setIcon(new ImageIcon(imgContinuar));
		btnContinuar.setBounds(331, 159, 103, 41);
		getContentPane().add(btnContinuar);

		JButton btnLogin = new JButton("");
		// Adicionando imagem.
		Image imgLogin = new ImageIcon(this.getClass().getResource(
				"/50px/login.png")).getImage();
		btnLogin.setIcon(new ImageIcon(imgLogin));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					JFTelalogin frame = new JFTelalogin();
					// fecha a tela de opção de para onde quer ir
					dispose();
					// Adicionando imagem.
					Image imgLogin = new ImageIcon(this.getClass().getResource(
							"/logo.png")).getImage();
					frame.setIconImage(imgLogin);
					frame.setVisible(true);

				} catch (Exception erro) {
					erro.printStackTrace();
				}

			}
		});
		btnLogin.setToolTipText("Tela de Login");
		btnLogin.setBounds(249, 88, 50, 50);
		getContentPane().add(btnLogin);

		JLabel lblreaDeTrabalho = new JLabel("\u00C1rea de Trabalho");
		lblreaDeTrabalho.setHorizontalAlignment(SwingConstants.CENTER);
		lblreaDeTrabalho.setBounds(96, 149, 103, 14);
		getContentPane().add(lblreaDeTrabalho);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(253, 149, 46, 14);
		getContentPane().add(lblLogin);

	}
}
