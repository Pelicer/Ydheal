package view.jFrames.visualizar_registros;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.DAO.ConexaoMySQL;
import view.jFrames.cadastros.JFCInstalacao;
import modelSuplerclasses.Pagamento;

@SuppressWarnings("serial")
public class JFVisualizarPagamento extends JFrame {

	private JPanel contentPane;
	private JTextField tfPedidoId;
	private JTextField tfValorFinal;
	private JTextField tfJuros;
	private JTextField tfTotalPrazo;

	private JTextField tfValorPago;
	private JTextField tfValorPagar;
	private JTextField tfParcelamento;
	private JTextField tfFormaDePagamento;

	public JFVisualizarPagamento(Pagamento p) {

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

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/pagamento.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Pagamento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 445, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCadastroDePagamento = new JLabel("Pagamento");
		// Adicionando imagem.
		Image imgPagamento = new ImageIcon(this.getClass().getResource(
				"/50px/pagamento.png")).getImage();
		lblCadastroDePagamento.setIcon(new ImageIcon(imgPagamento));
		lblCadastroDePagamento.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastroDePagamento.setBounds(10, 0, 343, 50);
		contentPane.add(lblCadastroDePagamento);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 58, 435, 2);
		contentPane.add(separator);

		JLabel lblPedidoId = new JLabel("Pedido ID:");
		lblPedidoId.setBounds(10, 76, 80, 14);
		contentPane.add(lblPedidoId);

		tfPedidoId = new JTextField();
		tfPedidoId.setEditable(false);
		tfPedidoId.setBounds(140, 73, 130, 20);
		contentPane.add(tfPedidoId);
		tfPedidoId.setColumns(10);

		JLabel lblJurostotal = new JLabel("Juros (Total):");
		lblJurostotal.setBounds(10, 187, 111, 20);
		contentPane.add(lblJurostotal);

		JLabel label_1 = new JLabel("Valor Final:");
		label_1.setBounds(10, 268, 90, 37);
		contentPane.add(label_1);

		tfValorFinal = new JTextField();
		tfValorFinal.setEditable(false);
		tfValorFinal.setEnabled(false);
		tfValorFinal.setColumns(10);
		tfValorFinal.setBounds(140, 276, 130, 20);
		contentPane.add(tfValorFinal);

		tfJuros = new JTextField();
		tfJuros.setEditable(false);
		tfJuros.setEnabled(false);
		tfJuros.setColumns(10);
		tfJuros.setBounds(140, 189, 130, 20);
		contentPane.add(tfJuros);

		tfTotalPrazo = new JTextField();
		tfTotalPrazo.setEditable(false);
		tfTotalPrazo.setEnabled(false);
		tfTotalPrazo.setColumns(10);
		tfTotalPrazo.setBounds(140, 160, 130, 20);
		contentPane.add(tfTotalPrazo);

		JLabel label_2 = new JLabel("Total a Prazo:");
		label_2.setBounds(10, 160, 111, 20);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("Parcelamento:");
		label_3.setBounds(10, 133, 120, 20);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("Forma de Pagamento:");
		label_4.setBounds(10, 103, 130, 20);
		contentPane.add(label_4);

		JButton btnEfetuarPagamento = new JButton("Efetuar Pagamento");
		btnEfetuarPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					p.setPedidoId(Integer.parseInt(tfPedidoId.getText()));
					p.setValorPagamento(Double.parseDouble(tfValorFinal
							.getText()));
					p.setFormaPagamento(tfFormaDePagamento.getText());
					p.setParcelamentoPagamento(Integer.parseInt((tfParcelamento
							.getText())));

					String pegarId = "select pagamento_id from tbl_pagamento where pedido_id = "
							+ p.getPedidoId() + ";";

					String parcelasRestantes = "select pagamento_parcelamento from tbl_pagamento where pedido_id = "
							+ p.getPedidoId() + ";";

					ResultSet parcela = st.executeQuery(parcelasRestantes);
					int i = 0;
					while (parcela.next()) {
						i = parcela.getInt("pagamento_parcelamento");
					}

					double valorParcela = Double.parseDouble(tfValorFinal
							.getText()) / i;

					ResultSet resultSet = st.executeQuery(pegarId);
					while (resultSet.next()) {

						int id = resultSet.getInt("pagamento_id");
						p.setIdPagamento(id);

					}
					st.execute("update tbl_pagamento set pagamento_forma = '"
							+ p.getFormaPagamento()
							+ "', pagamento_valor =  "
							+ p.getValorPagamento()
							+ ", pagamento_parcelamento = "
							+ p.getParcelamentoPagamento()
							+ ", pagamento_status = 'Efetuado', pagamento_valorPago = "
							+ (Double.parseDouble(tfValorPago.getText()) + valorParcela)
							+ ", pagamento_valorPendente = "
							+ (Double.parseDouble(tfTotalPrazo.getText()) - valorParcela)
							+ ", pedido_id = " + p.getPedidoId()
							+ " where pagamento_id =" + p.getIdPagamento()
							+ ";");

					// fala que executou com sucesso

					String verificacao = "select pagamento_valorPrazo, pagamento_valorPago from tbl_pagamento where pedido_id = "
							+ p.getPedidoId() + ";";
					ResultSet ver = st.executeQuery(verificacao);
					while (ver.next()) {

						p.setPagamentoValorPago(ver
								.getDouble("pagamento_valorPago"));
						p.setPagamentoValorPrazo(ver
								.getDouble("pagamento_valorPrazo"));

					}

					JOptionPane.showMessageDialog(null,
							"Pagamento efetuado com sucesso!");

					if (p.getPagamentoValorPago() >= p.getPagamentoValorPrazo()) {
						int resposta = JOptionPane
								.showConfirmDialog(
										null,
										"O valor total foi pago."
												+ "\nDeseja criar uma instalação para este pedido?",
										"Pagamento realizado",
										JOptionPane.YES_NO_OPTION);
						if (resposta == JOptionPane.YES_OPTION) {
							try {
								JFCInstalacao frame = new JFCInstalacao();
								frame.setLocationRelativeTo(null);
								frame.setVisible(true);
							} catch (Exception erroInstalacao) {
								erroInstalacao.printStackTrace();
							}
						} else if (resposta == JOptionPane.NO_OPTION) {
							dispose();
						}
					}

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
		});
		// Adicionando imagem.
		Image imgEfetuarPagamento = new ImageIcon(this.getClass().getResource(
				"/16px/pagamento.png")).getImage();
		btnEfetuarPagamento.setIcon(new ImageIcon(imgEfetuarPagamento));
		btnEfetuarPagamento.setBounds(280, 230, 155, 23);
		contentPane.add(btnEfetuarPagamento);
		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}

		});
		// Adicionando imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnNewButton_1.setIcon(new ImageIcon(imgCancelar));
		btnNewButton_1.setBounds(280, 275, 155, 23);
		contentPane.add(btnNewButton_1);

		JLabel label_5 = new JLabel("*");
		label_5.setForeground(Color.RED);
		label_5.setBackground(Color.RED);
		label_5.setBounds(115, 76, 25, 14);
		contentPane.add(label_5);

		JLabel label_6 = new JLabel("*");
		label_6.setForeground(Color.RED);
		label_6.setBackground(Color.RED);
		label_6.setBounds(115, 157, 25, 14);
		contentPane.add(label_6);

		JLabel label_7 = new JLabel("*");
		label_7.setForeground(Color.RED);
		label_7.setBackground(Color.RED);
		label_7.setBounds(105, 279, 25, 14);
		contentPane.add(label_7);

		JLabel label_8 = new JLabel("*");
		label_8.setForeground(Color.RED);
		label_8.setBackground(Color.RED);
		label_8.setBounds(115, 189, 25, 14);
		contentPane.add(label_8);

		JLabel lblCampos = new JLabel("* - Campos Obrigat\u00F3rios.");
		lblCampos.setForeground(Color.RED);
		lblCampos.setBackground(Color.RED);
		lblCampos.setBounds(269, 21, 238, 14);
		contentPane.add(lblCampos);

		tfValorPago = new JTextField();
		tfValorPago.setText("0");
		tfValorPago.setEditable(false);
		tfValorPago.setBounds(140, 220, 130, 20);
		contentPane.add(tfValorPago);
		tfValorPago.setColumns(10);

		tfValorPagar = new JTextField();
		tfValorPagar.setText("0");
		tfValorPagar.setEditable(false);
		tfValorPagar.setBounds(140, 251, 130, 20);
		contentPane.add(tfValorPagar);
		tfValorPagar.setColumns(10);

		JLabel lblValorPago = new JLabel("Valor Pago:");
		lblValorPago.setBounds(10, 218, 80, 14);
		contentPane.add(lblValorPago);

		JLabel lblValorAPagar = new JLabel("Valor a Pagar:");
		lblValorAPagar.setBounds(10, 254, 80, 14);
		contentPane.add(lblValorAPagar);

		tfParcelamento = new JTextField();
		tfParcelamento.setEditable(false);
		tfParcelamento.setBounds(140, 129, 130, 20);
		contentPane.add(tfParcelamento);
		tfParcelamento.setColumns(10);

		tfFormaDePagamento = new JTextField();
		tfFormaDePagamento.setEditable(false);
		tfFormaDePagamento.setBounds(140, 101, 130, 20);
		contentPane.add(tfFormaDePagamento);
		tfFormaDePagamento.setColumns(10);
		tfFormaDePagamento.setText(p.getFormaPagamento());
		tfParcelamento.setText(String.valueOf(p.getParcelamentoPagamento()));
		tfPedidoId.setText(String.valueOf(p.getPedidoId()));
		tfValorFinal.setText(String.valueOf(p.getValorPagamento()));
		tfValorPagar.setText(String.valueOf(p.getPagamentoValorPendente()));
		tfValorPago.setText(String.valueOf(p.getPagamentoValorPago()));
		tfJuros.setText(String.valueOf(p.getPagamentoJuros()));
		tfTotalPrazo.setText(String.valueOf(p.getPagamentoValorPrazo()));

		JButton btnNewButton = new JButton("Alterar");
		Image imgEditar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(imgEditar));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfFormaDePagamento.setEditable(true);
				tfParcelamento.setEditable(true);
				tfPedidoId.setEditable(true);
				tfValorPagar.setEditable(true);
				tfValorPago.setEditable(true);

			}
		});
		btnNewButton.setBounds(280, 253, 155, 23);
		contentPane.add(btnNewButton);

		JLabel label_9 = new JLabel("R$");
		label_9.setBounds(115, 223, 29, 14);
		contentPane.add(label_9);

		JLabel label_10 = new JLabel("R$");
		label_10.setBounds(115, 251, 29, 14);
		contentPane.add(label_10);

		JLabel label_11 = new JLabel("R$");
		label_11.setBounds(115, 279, 29, 14);
		contentPane.add(label_11);

	}
}
