package view.jFrames.visualizar_registros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import modelSuplerclasses.Visita;
import view.jFrames.cadastros.JFCOrcamento_materiais;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JFVisualizarVisita extends JFrame {

	Visita visita = new Visita();

	private JPanel contentPane;
	private JTextField tfnome;
	private JTextField tfendereco;
	private JTextField tfhorario;
	private JFormattedTextField tftelefone;
	private JTextField tfDiaVisita;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public JFVisualizarVisita(final Visita visita) {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"Certeza que deseja sair da visualização? /nQualquer alteração feita será perdida.",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		String title = "Visita ao cliente: " + visita.getNome();
		setTitle(title);

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/visita.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 499, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 101, 46, 14);
		contentPane.add(lblNome);

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(10, 132, 67, 14);
		contentPane.add(lblEndereo);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(10, 163, 67, 14);
		contentPane.add(lblTelefone);

		JLabel lblDiaDaVisita = new JLabel("Dia da visita:");
		lblDiaDaVisita.setBounds(10, 191, 79, 14);
		contentPane.add(lblDiaDaVisita);

		JLabel lblHorrio = new JLabel("Hor\u00E1rio:");
		lblHorrio.setBounds(263, 163, 67, 14);
		contentPane.add(lblHorrio);

		JLabel lblObservao = new JLabel("Observa\u00E7\u00E3o:");
		lblObservao.setBounds(10, 224, 79, 14);
		contentPane.add(lblObservao);

		tfnome = new JTextField();
		tfnome.setBounds(87, 98, 387, 20);
		contentPane.add(tfnome);
		tfnome.setColumns(10);

		tfendereco = new JTextField();
		tfendereco.setBounds(87, 129, 387, 20);
		contentPane.add(tfendereco);
		tfendereco.setColumns(10);

		tfhorario = new JTextField();
		tfhorario.setBounds(318, 160, 156, 20);
		contentPane.add(tfhorario);
		tfhorario.setColumns(10);

		// Máscara de Campo.
		MaskFormatter campoTelefone = null;
		try {
			campoTelefone = new MaskFormatter("(##)#####-####");
		} catch (Exception e) {
		}
		tftelefone = new JFormattedTextField(campoTelefone);
		tftelefone.setBounds(87, 160, 156, 20);
		contentPane.add(tftelefone);
		tftelefone.setColumns(10);

		JButton btnFechar = new JButton("Fechar");
		Image imgFechar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnFechar.setIcon(new ImageIcon(imgFechar));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
			}
		});
		btnFechar.setBounds(355, 367, 119, 23);
		contentPane.add(btnFechar);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(190, 367, 119, 23);
		contentPane.add(btnAlterar);
		setLocationRelativeTo(null);

		tfDiaVisita = new JTextField();
		tfDiaVisita.setBounds(87, 188, 156, 20);
		contentPane.add(tfDiaVisita);
		tfDiaVisita.setColumns(10);

		JTextArea taobservacao = new JTextArea();
		taobservacao.setBounds(87, 219, 387, 99);
		contentPane.add(taobservacao);

		tfnome.setEditable(false);
		tfendereco.setEditable(false);
		tfhorario.setEditable(false);
		tftelefone.setEditable(false);
		tfDiaVisita.setEditable(false);
		taobservacao.setEditable(false);

		JCheckBox chckbxAberta = new JCheckBox("Aberta");
		buttonGroup.add(chckbxAberta);
		chckbxAberta.setBounds(86, 324, 73, 23);
		contentPane.add(chckbxAberta);

		JCheckBox chckbxConcluir = new JCheckBox("Concluir");
		buttonGroup.add(chckbxConcluir);
		chckbxConcluir.setBounds(221, 325, 89, 23);
		contentPane.add(chckbxConcluir);

		JCheckBox chckbxCancelar = new JCheckBox("Cancelar");
		buttonGroup.add(chckbxCancelar);
		chckbxCancelar.setBounds(377, 325, 97, 23);
		contentPane.add(chckbxCancelar);

		JButton bntalterarstaus = new JButton("Salvar Status");
		bntalterarstaus.setBounds(10, 367, 149, 23);
		contentPane.add(bntalterarstaus);

		JLabel label = new JLabel("Status:");
		label.setBounds(38, 328, 46, 14);
		contentPane.add(label);

		JLabel lblstatus = new JLabel("Visita");
		// Adicionando imagem.
		Image imgVisita = new ImageIcon(this.getClass().getResource(
				"/50px/visita.png")).getImage();
		lblstatus.setIcon(new ImageIcon(imgVisita));
		lblstatus.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblstatus.setBounds(10, 11, 394, 58);
		contentPane.add(lblstatus);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 80, 493, 2);
		contentPane.add(separator);

		// da os valores para os campos ao abrir a tela
		tfnome.setText(visita.getNome());
		tfendereco.setText(visita.getEndereco());
		tfhorario.setText(visita.getHorarioVisita());
		tftelefone.setText(visita.getTelefone());
		tfDiaVisita.setText(visita.getDataVisita());
		taobservacao.setText(visita.getObservacao());
		Image imgOrcamento = new ImageIcon(this.getClass().getResource(
				"/16px/orcamento.png")).getImage();

		if (visita.getStatus() == 0) {
			chckbxAberta.setSelected(true);

			lblstatus.setText("Aberta");
		}

		else if (visita.getStatus() == 1) {
			chckbxAberta.setEnabled(false);
			chckbxCancelar.setEnabled(false);

			chckbxConcluir.setSelected(true);

			chckbxConcluir.setText("Concluída");

			lblstatus.setText("Concluída");

		} else if (visita.getStatus() == 11) {
			chckbxAberta.setEnabled(false);
			chckbxConcluir.setEnabled(false);

			chckbxCancelar.setSelected(true);

			chckbxConcluir.setText("Concluída");

			lblstatus.setText("Cancelada");
		}

		// evento do concluir
		chckbxConcluir.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				if (chckbxConcluir.isSelected()) {

					try {
						Class.forName("com.mysql.jdbc.Driver");

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando = "UPDATE tbl_visita set visita_status = 1 where visita_id = "
								+ visita.getId() + ";";

						st.execute(comando);

						JOptionPane.showMessageDialog(null, "Status Salvo!");

						JOptionPane.showMessageDialog(null,
								"Pedido progredido!");

						st.close();
						// fecha a concxão
						con.close();
					} catch (SQLException | ClassNotFoundException e) {

					}

				}

			}
		});

		// evento d cancelar
		chckbxCancelar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (chckbxCancelar.isSelected()) {

					try {
						Class.forName("com.mysql.jdbc.Driver");

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando = "DELETE FROM tbl_visita where visita_id = "
								+ visita.getId() + ";";

						st.execute(comando);

						JOptionPane.showMessageDialog(null, "Status Salvo!");

						JOptionPane
								.showMessageDialog(null,
										"Visita cancelada. Seu registro foi excluído do sistema.");
						st.close();
						dispose();
						// fecha a concxão
						con.close();
					} catch (SQLException | ClassNotFoundException error) {
						JOptionPane
								.showMessageDialog(
										null,
										"Ocorreu algum problema ao salvar o Status da visita.",
										"ERRO!", JOptionPane.ERROR_MESSAGE);

					}

					chckbxConcluir.setText("Concluir");
					chckbxAberta.setForeground(Color.GRAY);
					chckbxCancelar.setForeground(Color.BLACK);
					chckbxConcluir.setForeground(Color.GRAY);
					lblstatus.setText("Cancelada");
				}

			}
		});

		// evento aberto
		chckbxAberta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (chckbxAberta.isSelected()) {

					chckbxConcluir.setText("Concluir");

					chckbxAberta.setForeground(Color.BLACK);
					chckbxCancelar.setForeground(Color.BLACK);
					chckbxConcluir.setForeground(Color.BLACK);
				} else if (chckbxAberta.isSelected()) {

					chckbxConcluir.setText("Concluir");

					chckbxAberta.setForeground(Color.BLACK);
					chckbxCancelar.setForeground(Color.BLACK);
				}

			}
		});

		// salvar o status
		Image imgSalvarStatus = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		bntalterarstaus.setIcon(new ImageIcon(imgSalvarStatus));
		bntalterarstaus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxAberta.isSelected()) {

				} else if (chckbxConcluir.isSelected()) {

					String comando = "UPDATE tbl_visita SET visita_status = 1  WHERE visita_id = "
							+ visita.getId() + ";";

					try {

						// cria a conecxão
						Class.forName("com.mysql.jdbc.Driver");

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						st.execute(comando);

						// fala q executou co sucesso
						JOptionPane.showMessageDialog(null, "Status Alterado");

						st.close();
						// fecha a concxão
						con.close();
						dispose();

					}

					catch (SQLException erroBanco) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha na conexão com o banco de dados. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						erroBanco.printStackTrace();
					}

					catch (NumberFormatException erroNumero) {

						JOptionPane
								.showMessageDialog(
										null,
										"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);

						erroNumero.printStackTrace();
					} catch (IllegalFormatException erroFormato) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
										"Aviso!", JOptionPane.ERROR_MESSAGE);
					}

					catch (ClassNotFoundException erroJDBC) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);
					}

				} else if (chckbxCancelar.isSelected()) {

					String comando = "UPDATE tbl_visita SET visita_status = 11  WHERE visita_id = "
							+ visita.getId() + ";";

					try {
						Class.forName("com.mysql.jdbc.Driver");

						// cria a conexão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						st.execute(comando);
						// fala q executou co sucesso
						JOptionPane.showMessageDialog(null, "Status Alterado");

					}

					catch (SQLException erroBanco) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha na conexão com o banco de dados. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						erroBanco.printStackTrace();
					}

					catch (NumberFormatException erroNumero) {

						JOptionPane
								.showMessageDialog(
										null,
										"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);

						erroNumero.printStackTrace();
					} catch (IllegalFormatException erroFormato) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
										"Aviso!", JOptionPane.ERROR_MESSAGE);
					}

					catch (ClassNotFoundException erroJDBC) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);
					}

				}

			}
		});

		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imgAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Settando os valores.

				tfnome.setEditable(true);
				tfendereco.setEditable(true);
				tfhorario.setEditable(true);
				tftelefone.setEditable(true);
				tfDiaVisita.setEditable(true);
				taobservacao.setEditable(true);
				tfnome.grabFocus();

				tfDiaVisita.setVisible(false);

				JDateChooser dateChooser = new JDateChooser();
				dateChooser.setBounds(87, 188, 156, 20);
				contentPane.add(dateChooser);

				tfnome.setText(visita.getNome());
				tfendereco.setText(visita.getEndereco());
				tfhorario.setText(visita.getHorarioVisita());
				tftelefone.setText(visita.getTelefone());
				tfDiaVisita.setText(visita.getDataVisita());
				taobservacao.setText(visita.getObservacao());

				Date x = null;
				try {
					x = (new SimpleDateFormat("dd/MM/yyyy").parse(tfDiaVisita
							.getText()));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				dateChooser.setDate(x);

				// cria o bnt salvar no lugar do alterar que está invisivel
				JButton btnSalvar = new JButton("Salvar");
				btnSalvar.setBounds(87, 129, 317, 20);
				contentPane.add(btnSalvar);

				btnAlterar.setVisible(false);

				btnSalvar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// pega o valor alterado

						SimpleDateFormat Formatodatabr = new SimpleDateFormat(
								"dd/MM/yyyy");

						visita.setDataVisita(String.valueOf(Formatodatabr
								.format(dateChooser.getDate())));

						visita.setEndereco(tfendereco.getText());

						visita.setHorarioVisita(tfhorario.getText());

						visita.setNome(tfnome.getText());

						visita.setObservacao(taobservacao.getText());

						visita.setTelefone(tftelefone.getText());

						// da os valores alterados para parecer que atualizou rs
						tfnome.setText(visita.getNome());

						tfendereco.setText(visita.getEndereco());

						tfhorario.setText(visita.getHorarioVisita());

						tftelefone.setText(visita.getTelefone());

						tfDiaVisita.setText(visita.getDataVisita());

						taobservacao.setText(visita.getObservacao());

						tfDiaVisita.setText(visita.getDataVisita());

						// coloca os valores no banco
						// formata data para padrão americano
						SimpleDateFormat Formatodataam = new SimpleDateFormat(
								"yyyy/MM/dd");

						String comando = "UPDATE tbl_visita SET visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "', visita_horario = '"
								+ tfhorario.getText()
								+ "', visita_nome = '"
								+ tfnome.getText()
								+ "', visita_endereco = '"
								+ tfendereco.getText()
								+ "', visita_telefone = '"
								+ tftelefone.getText()
								+ "', visita_observacao = '"
								+ taobservacao.getText()
								+ "' WHERE visita_id = " + visita.getId() + ";";

						try {
							Class.forName("com.mysql.jdbc.Driver");

							// cria a conecxão
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();

							java.sql.Statement st = con.createStatement();

							st.execute(comando);

							// fala q executou co sucesso
							JOptionPane.showMessageDialog(null,
									"Dados alterados com sucesso!");

							st.close();
							// fecha a concxão
							con.close();

							// torna os campoas não editaveis dnv

							tfnome.setEditable(false);
							tfendereco.setEditable(false);
							tfhorario.setEditable(false);
							tftelefone.setEditable(false);
							tfDiaVisita.setEditable(false);
							taobservacao.setEditable(false);

							dateChooser.setVisible(false);

							tfDiaVisita.setVisible(true);

							btnSalvar.setVisible(false);
							btnAlterar.setVisible(true);

						}

						catch (Exception ee) {

							JOptionPane.showMessageDialog(null,
									"Falha na alteração");

							ee.printStackTrace();

						}

					}
				});

			}
		});

	}
}
