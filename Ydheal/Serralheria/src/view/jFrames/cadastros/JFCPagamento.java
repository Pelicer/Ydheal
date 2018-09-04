package view.jFrames.cadastros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.DAO.ConexaoMySQL;
import modelHerancas.Pedido;
import modelSuplerclasses.Orcamento;
import modelSuplerclasses.Pagamento;
import modelSuplerclasses.Visita;
import view.jDialogs.JDSelecionarCodigoPedido;

@SuppressWarnings("serial")
public class JFCPagamento extends JDialog {

	private JPanel contentPane;
	private JTextField tfPedidoId;
	private JTextField tfValorFinal;
	private JTextField tfJuros;
	private JTextField tfTotalPrazo;

	Pagamento p = new Pagamento();
	Pedido pedido = new Pedido();
	Orcamento o = new Orcamento();
	Visita v = new Visita();
	private JTextField tfValorPago;
	private JTextField tfValorPagar;
	private JTextField tfParcelamento;
	private JTextField tfFormaDePagamento;
	private JTextField tfVisita;

	public JFCPagamento() {
		
		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro do pagamento será cancelado. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/pagamento.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Cadastro de Pagamento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 539, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		JLabel lblCadastroDePagamento = new JLabel("Cadastro de Pagamento");
		// Adicionando imagem.
		Image imgPagamento = new ImageIcon(this.getClass().getResource(
				"/50px/pagamento.png")).getImage();
		lblCadastroDePagamento.setIcon(new ImageIcon(imgPagamento));
		lblCadastroDePagamento.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastroDePagamento.setBounds(10, 0, 343, 50);
		contentPane.add(lblCadastroDePagamento);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 58, 523, 2);
		contentPane.add(separator);

		JLabel lblPedidoId = new JLabel("Pedido ID:");
		lblPedidoId.setBounds(10, 76, 80, 14);
		contentPane.add(lblPedidoId);

		tfPedidoId = new JTextField();
		tfPedidoId.setEditable(false);
		tfPedidoId.setBounds(140, 73, 80, 20);
		contentPane.add(tfPedidoId);
		tfPedidoId.setColumns(10);

		JLabel lblJurostotal = new JLabel("Juros (Total):");
		lblJurostotal.setBounds(10, 212, 111, 20);
		contentPane.add(lblJurostotal);

		JLabel label_1 = new JLabel("Valor Final:");
		label_1.setBounds(10, 300, 90, 23);
		contentPane.add(label_1);

		tfValorFinal = new JTextField();
		tfValorFinal.setEditable(false);
		tfValorFinal.setEnabled(false);
		tfValorFinal.setColumns(10);
		tfValorFinal.setBounds(140, 301, 367, 20);
		contentPane.add(tfValorFinal);

		tfJuros = new JTextField();
		tfJuros.setEditable(false);
		tfJuros.setEnabled(false);
		tfJuros.setColumns(10);
		tfJuros.setBounds(140, 214, 367, 20);
		contentPane.add(tfJuros);

		tfTotalPrazo = new JTextField();
		tfTotalPrazo.setEditable(false);
		tfTotalPrazo.setEnabled(false);
		tfTotalPrazo.setColumns(10);
		tfTotalPrazo.setBounds(140, 185, 367, 20);
		contentPane.add(tfTotalPrazo);

		JLabel label_2 = new JLabel("Total a Prazo:");
		label_2.setBounds(10, 185, 111, 20);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("Parcelamento:");
		label_3.setBounds(10, 158, 120, 20);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("Forma de Pagamento:");
		label_4.setBounds(10, 128, 120, 20);
		contentPane.add(label_4);

		JButton btnSelecionarPedido = new JButton("Selecionar");
		btnSelecionarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Selecionando pedido.
				try {
					JDSelecionarCodigoPedido frame = new JDSelecionarCodigoPedido(
							pedido);
					frame.setModal(true);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}

				tfPedidoId.setText(String.valueOf(pedido.getId()));

				try {
					Class.forName("com.mysql.jdbc.Driver");

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					String visita = "select visita_id from tbl_pedido where pedido_id ="
							+ pedido.getId() + ";";

					ResultSet resultSet = st.executeQuery(visita);
					while (resultSet.next()) {

						v.setId(resultSet.getInt("visita_id"));

					}

					st.close();
					con.close();

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				tfVisita.setText(String.valueOf(v.getId()));

				try {
					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection connection = ConexaoMySQL
							.getConexaoMySQL();

					java.sql.Statement statement = connection.createStatement();

					String comando = "select orcamento_valorTotal, orcamento_totalPrazo, orcamento_metodoPagamento, orcamento_parcelamento, orcamento_juros from tbl_orcamento where visita_id = "
							+ tfVisita.getText() + ";";

					ResultSet resultSetValores = statement
							.executeQuery(comando);
					while (resultSetValores.next()) {

						o.setValorTotal(resultSetValores
								.getDouble("orcamento_valorTotal"));
						o.setTotalPrazo(resultSetValores
								.getDouble("orcamento_totalPrazo"));
						o.setFormaPagamento(resultSetValores
								.getString("orcamento_metodoPagamento"));
						o.setParcelamento(resultSetValores
								.getDouble("orcamento_parcelamento"));
						o.setJuros(resultSetValores
								.getDouble("orcamento_juros"));

					}

					tfFormaDePagamento.setText(String.valueOf(o
							.getFormaPagamento()));
					tfJuros.setText(String.valueOf(o.getJuros()));
					tfParcelamento.setText(String.valueOf(o.getParcelamento())
							.replaceAll("\\.\\d+", ""));
					tfTotalPrazo.setText(String.valueOf(o.getTotalPrazo()));
					tfValorFinal.setText(String.valueOf(o.getValorTotal()));

					statement.close();
					connection.close();

				} catch (SQLException erroBanco) {
					JOptionPane.showMessageDialog(null,
							"Falha na conexão com o banco de dados.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);
					erroBanco.printStackTrace();
				} catch (ClassNotFoundException erro) {
					erro.printStackTrace();
				}

			}
		});
		// Adicionando imagem.
		Image imgSelecionar = new ImageIcon(this.getClass().getResource(
				"/16px/pedidos.png")).getImage();
		btnSelecionarPedido.setIcon(new ImageIcon(imgSelecionar));
		btnSelecionarPedido.setBounds(230, 72, 277, 23);
		contentPane.add(btnSelecionarPedido);

		JButton btnEfetuarPagamento = new JButton("Efetuar Pagamento");
		btnEfetuarPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tfPedidoId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						Class.forName("com.mysql.jdbc.Driver");

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						p.setPedidoId(Integer.parseInt(tfPedidoId.getText()));
						p.setValorPagamento(Double.parseDouble(tfValorFinal
								.getText()));
						p.setFormaPagamento(tfFormaDePagamento.getText());
						p.setParcelamentoPagamento(Integer
								.parseInt((tfParcelamento.getText())));

						st.execute("insert into tbl_pagamento (pagamento_forma, pagamento_valor, pagamento_parcelamento, pagamento_status, pagamento_valorPago, pagamento_valorPendente, pedido_id) values ('"
								+ p.getFormaPagamento()
								+ "',  "
								+ p.getValorPagamento()
								+ ", "
								+ p.getParcelamentoPagamento()
								+ ", 'Efetuado', "
								+ tfValorFinal.getText()
								+ ", 0, " + p.getPedidoId() + ");");

						// fala que executou com sucesso
						JOptionPane.showMessageDialog(null,
								"Pagamento efetuado com sucesso!");

						st.close();
						// fecha a concxão
						con.close();

						// fecha a tela após sucesso
						dispose();

					} catch (SQLException erroBanco) {
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
		// Adicionando imagem.
		Image imgEfetuarPagamento = new ImageIcon(this.getClass().getResource(
				"/16px/pagamento.png")).getImage();
		btnEfetuarPagamento.setIcon(new ImageIcon(imgEfetuarPagamento));
		btnEfetuarPagamento.setBounds(0, 334, 170, 23);
		contentPane.add(btnEfetuarPagamento);

		JButton btnAgendarPagamento = new JButton("Agendar Pagamento");
		// Adicionando imagem.
		Image imgAgendar = new ImageIcon(this.getClass().getResource(
				"/16px/agendar.png")).getImage();
		btnAgendarPagamento.setIcon(new ImageIcon(imgAgendar));
		btnAgendarPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"Nóis tem que fazer esse botão. :v");
			}
		});
		btnAgendarPagamento.setBounds(169, 334, 184, 23);
		contentPane.add(btnAgendarPagamento);

		JButton btnNewButton_1 = new JButton("Cancelar");
		// Adicionando imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnNewButton_1.setIcon(new ImageIcon(imgCancelar));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialog = JOptionPane.YES_NO_OPTION;
				JOptionPane.showConfirmDialog(null,
						"Certeza de que deseja cancelar?", "WARNING!",
						JOptionPane.YES_NO_OPTION);
				if (dialog == JOptionPane.YES_OPTION) {
					dispose();
				} else {
					// Continua no formulário.
				}
			}
		});
		btnNewButton_1.setBounds(353, 334, 170, 23);
		contentPane.add(btnNewButton_1);

		JLabel label_5 = new JLabel("*");
		label_5.setForeground(Color.RED);
		label_5.setBackground(Color.RED);
		label_5.setBounds(115, 76, 25, 14);
		contentPane.add(label_5);

		JLabel label_6 = new JLabel("*");
		label_6.setForeground(Color.RED);
		label_6.setBackground(Color.RED);
		label_6.setBounds(115, 129, 25, 14);
		contentPane.add(label_6);

		JLabel label_7 = new JLabel("*");
		label_7.setForeground(Color.RED);
		label_7.setBackground(Color.RED);
		label_7.setBounds(105, 304, 25, 14);
		contentPane.add(label_7);

		JLabel label_8 = new JLabel("*");
		label_8.setForeground(Color.RED);
		label_8.setBackground(Color.RED);
		label_8.setBounds(115, 161, 25, 14);
		contentPane.add(label_8);

		JLabel lblCampos = new JLabel("* - Campos Obrigat\u00F3rios.");
		lblCampos.setForeground(Color.RED);
		lblCampos.setBackground(Color.RED);
		lblCampos.setBounds(269, 21, 238, 14);
		contentPane.add(lblCampos);

		tfValorPago = new JTextField();
		tfValorPago.setText("0");
		tfValorPago.setEditable(false);
		tfValorPago.setBounds(140, 245, 367, 20);
		contentPane.add(tfValorPago);
		tfValorPago.setColumns(10);

		tfValorPagar = new JTextField();
		tfValorPagar.setText("0");
		tfValorPagar.setEditable(false);
		tfValorPagar.setBounds(140, 276, 367, 20);
		contentPane.add(tfValorPagar);
		tfValorPagar.setColumns(10);

		JLabel lblValorPago = new JLabel("Valor Pago:");
		lblValorPago.setBounds(10, 243, 80, 14);
		contentPane.add(lblValorPago);

		JLabel lblValorAPagar = new JLabel("Valor a Pagar:");
		lblValorAPagar.setBounds(10, 276, 80, 14);
		contentPane.add(lblValorAPagar);

		tfParcelamento = new JTextField();
		tfParcelamento.setEditable(false);
		tfParcelamento.setBounds(140, 154, 367, 20);
		contentPane.add(tfParcelamento);
		tfParcelamento.setColumns(10);

		tfFormaDePagamento = new JTextField();
		tfFormaDePagamento.setEditable(false);
		tfFormaDePagamento.setBounds(140, 126, 367, 20);
		contentPane.add(tfFormaDePagamento);
		tfFormaDePagamento.setColumns(10);

		JLabel label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setBackground(Color.RED);
		label.setBounds(115, 101, 25, 14);
		contentPane.add(label);

		JLabel lblVisitaId = new JLabel("Visita ID:");
		lblVisitaId.setBounds(10, 101, 80, 14);
		contentPane.add(lblVisitaId);

		tfVisita = new JTextField();
		tfVisita.setEditable(false);
		tfVisita.setBounds(140, 101, 367, 20);
		contentPane.add(tfVisita);
		tfVisita.setColumns(10);
		JLabel lblR = new JLabel("R$");
		lblR.setBounds(115, 188, 25, 14);
		contentPane.add(lblR);

		JLabel label_9 = new JLabel("R$");
		label_9.setBounds(115, 218, 25, 14);
		contentPane.add(label_9);

		JLabel label_10 = new JLabel("R$");
		label_10.setBounds(115, 251, 25, 14);
		contentPane.add(label_10);

		JLabel label_11 = new JLabel("R$");
		label_11.setBounds(115, 281, 25, 14);
		contentPane.add(label_11);

		JLabel label_12 = new JLabel("R$");
		label_12.setBounds(115, 304, 25, 14);
		contentPane.add(label_12);
	}
}
