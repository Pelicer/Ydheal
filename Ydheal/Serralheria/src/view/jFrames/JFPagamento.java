package view.jFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTable.PrintMode;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Pagamento;
import view.jFrames.cadastros.JFCPagamento;
import view.jFrames.visualizar_registros.JFVisualizarPagamento;

@SuppressWarnings("serial")
public class JFPagamento extends JFrame {

	int count = 0;
	private DefaultTableModel ModeloJTPag = new DefaultTableModel();
	private JPanel contentPane;
	private JTextField tfcodigo;
	private JTable tabletbl_pagamentos;

	public JFPagamento() {

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/pagamento.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Pagamento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 990, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 0, 966, 88);
		panel.add(panel_1);

		tfcodigo = new JTextField();
		tfcodigo.setBounds(310, 25, 86, 20);
		panel_1.add(tfcodigo);
		tfcodigo.setColumns(10);

		JLabel label_2 = new JLabel("Status:");
		label_2.setBounds(0, 50, 108, -50);
		panel_1.add(label_2);

		JLabel lblCdigoDoPedido = new JLabel("C\u00F3digo do pedido:");
		lblCdigoDoPedido.setBounds(210, 24, 135, 22);
		panel_1.add(lblCdigoDoPedido);

		JLabel label = new JLabel("Pagamento");
		// Adicionando imagem.
		Image imgPagamento = new ImageIcon(this.getClass().getResource(
				"/50px/pagamento.png")).getImage();
		label.setIcon(new ImageIcon(imgPagamento));
		label.setBounds(10, 11, 345, 77);
		panel_1.add(label);
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(424, 28, 57, 14);
		panel_1.add(lblStatus);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(0, 88, 966, 592);
		panel.add(panel_2);

		JLabel registrosencontrados = new JLabel("0");
		registrosencontrados.setHorizontalAlignment(SwingConstants.CENTER);
		registrosencontrados.setForeground(new Color(0, 128, 0));
		registrosencontrados.setBounds(740, 45, 46, 14);
		panel_2.add(registrosencontrados);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 81, 946, 511);
		panel_2.add(scrollPane);
		// Criando os títulos.

		{
			ModeloJTPag.addColumn("Cod. pagamento");
			ModeloJTPag.addColumn("Cod. pedido");
			ModeloJTPag.addColumn("Forma de pagamento");
			ModeloJTPag.addColumn("Valor total");
		}

		tabletbl_pagamentos = new JTable(ModeloJTPag) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane.setViewportView(tabletbl_pagamentos);

		// Detecção de clique duplo.
		tabletbl_pagamentos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					Pagamento p = new Pagamento();

					String comando = "select pagamento_id, pagamento_forma, pagamento_valor, pagamento_parcelamento, pagamento_status, pagamento_valorPago, pagamento_valorPendente, pagamento_valorPrazo, pagamento_valorJuros, pedido_id from tbl_pagamento where pagamento_id = "
							+ ModeloJTPag.getValueAt(
									tabletbl_pagamentos.getSelectedRow(), 0)
							+ ";";

					count = 0;

					try {

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						// não sei oq isso faz kk
						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {
							p.setIdPagamento(resultSet.getInt("pagamento_id"));
							p.setFormaPagamento(resultSet
									.getString("pagamento_forma"));
							p.setParcelamentoPagamento(resultSet
									.getInt("pagamento_parcelamento"));
							p.setPedidoId(resultSet.getInt("pedido_id"));
							p.setStatus(resultSet.getString("pagamento_status"));
							p.setValorPagamento(resultSet
									.getDouble("pagamento_valor"));
							p.setPagamentoValorPago(resultSet
									.getDouble("pagamento_valorPago"));
							p.setPagamentoValorPendente(resultSet
									.getDouble("pagamento_valorPendente"));
							p.setPagamentoJuros(resultSet
									.getDouble("pagamento_valorJuros"));
							p.setPagamentoValorPrazo(resultSet
									.getDouble("pagamento_valorPrazo"));
						}

						st.close();
						con.close();

					}// fim do try

					catch (NumberFormatException eee) {
						eee.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					} catch (Exception ee) {
						ee.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

					try {
						JFVisualizarPagamento frame = new JFVisualizarPagamento(
								p);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
					} catch (Exception erro) {
						erro.printStackTrace();
					}
				}
			}
		});

		JButton button = new JButton("Novo");
		// Adicionando imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		button.setIcon(new ImageIcon(imgNovo));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFCPagamento frame = new JFCPagamento();
					frame.setModal(true);
					frame.setVisible(true);
				} catch (Exception erro) {
					erro.printStackTrace();
				}
			}
		});
		button.setToolTipText("Criar Novo Registro");
		button.setBounds(101, 11, 116, 23);
		panel_2.add(button);

		JButton button_1 = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		button_1.setIcon(new ImageIcon(imgLimpar));
		button_1.setToolTipText("Limpar Filtros");
		button_1.setBounds(354, 11, 116, 23);
		panel_2.add(button_1);

		JButton button_2 = new JButton("Imprimir");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// API de impressão.
				MessageFormat footer = new MessageFormat("Página {0}");
				MessageFormat header = new MessageFormat("Lista de Pagamentos");

				try {
					boolean complete = tabletbl_pagamentos.print(
							PrintMode.FIT_WIDTH, header, footer, true, null,
							true);
					if (complete) {
						JOptionPane.showMessageDialog(null,
								"Impressão concluida!", "Concluído",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (PrinterException erroImpressao) {
					erroImpressao.printStackTrace();
					JOptionPane
							.showMessageDialog(
									null,
									"Houve uma falha ao imprimir. \nPor favor, contate um técnico ou tente novamente mais tarde.",
									"Erro!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// Adicionando imagem.
		Image imgImprimir = new ImageIcon(this.getClass().getResource(
				"/16px/imprimir.png")).getImage();
		button_2.setIcon(new ImageIcon(imgImprimir));
		button_2.setToolTipText("Imprimir");
		button_2.setBounds(480, 11, 116, 23);
		panel_2.add(button_2);

		JButton button_3 = new JButton("Alterar");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Pagamento p = new Pagamento();

				String comando = "select pagamento_id, pagamento_forma, pagamento_valor, pagamento_parcelamento, pagamento_status, pagamento_valorPago, pagamento_valorPendente, pagamento_valorPrazo, pagamento_valorJuros pedido_id from tbl_pagamento where pagamento_id = "
						+ ModeloJTPag.getValueAt(
								tabletbl_pagamentos.getSelectedRow(), 0) + ";";

				count = 0;

				try {

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					// não sei oq isso faz kk
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {
						p.setIdPagamento(resultSet.getInt("pagamento_id"));
						p.setFormaPagamento(resultSet
								.getString("pagamento_forma"));
						p.setParcelamentoPagamento(resultSet
								.getInt("pagamento_parcelamento"));
						p.setPedidoId(resultSet.getInt("pedido_id"));
						p.setStatus(resultSet.getString("pagamento_status"));
						p.setValorPagamento(resultSet
								.getDouble("pagamento_valor"));
						p.setPagamentoValorPago(resultSet
								.getDouble("pagamento_valorPago"));
						p.setPagamentoValorPendente(resultSet
								.getDouble("pagamento_valorPendente"));
						p.setPagamentoJuros(resultSet
								.getDouble("pagamento_valorJuros"));
						p.setPagamentoValorPrazo(resultSet
								.getDouble("pagamento_valorPrazo"));
					}

					st.close();
					con.close();

				}// fim do try

				catch (NumberFormatException eee) {
					eee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				try {
					JFVisualizarPagamento frame = new JFVisualizarPagamento(p);
					//frame.setModal(true);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		// Adicionando imagem.
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		button_3.setIcon(new ImageIcon(imgAlterar));
		button_3.setToolTipText("Alterar Registro");
		button_3.setBounds(606, 11, 116, 23);
		panel_2.add(button_3);

		JLabel label_3 = new JLabel("Registros Encontrados");
		label_3.setBounds(793, 45, 141, 14);
		panel_2.add(label_3);

		JButton button_4 = new JButton("Filtrar");
		// Adicionando imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		button_4.setIcon(new ImageIcon(imgFiltrar));
		button_4.setToolTipText("Filtrar Registros");
		button_4.setBounds(227, 11, 116, 23);
		panel_2.add(button_4);

		JButton button_5 = new JButton("Excluir");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pede para confirmar a resposta
				int resposta = JOptionPane.showConfirmDialog(null,
						"Deseja realmente excluir os pagamentos selecionados?",
						"Confirmar exclusões ", JOptionPane.YES_NO_OPTION);

				// chaca resposta e faz o de acordo
				if (resposta == JOptionPane.YES_OPTION) {

					// pega as linhas selecionadas
					int x[] = tabletbl_pagamentos.getSelectedRows();

					// exclui as linhas selecionadas

					int id = 0;

					for (int i = x.length - 1; i >= 0; i--) {

						// obtem o id da linha selecionada
						id = Integer.parseInt(String.valueOf(ModeloJTPag
								.getValueAt(x[i], 0)));

						ModeloJTPag.removeRow(x[i]);

						String comando = "delete from tbl_pagamento where pagamento_id = "
								+ id + ";";

						try {

							Class.forName("com.mysql.jdbc.Driver");

							// cria a conecxão
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();

							java.sql.Statement st = con.createStatement();

							st.execute(comando);

							st.close();
							con.close();

						}// fim do try
						catch (Exception ee) {
							ee.printStackTrace();
							JOptionPane
									.showMessageDialog(
											null,
											"Falha na exclusão. Não será possível excluir o pagamento caso exista pendências do mesmo."
													+ "\nPor favor, exclua as pendências antes de excluir o pagamento.",
											"AVISO!",
											JOptionPane.WARNING_MESSAGE);
						}

						registrosencontrados.setText(String.valueOf(Integer
								.parseInt(registrosencontrados.getText()) - 1));

					}// fecha o for de exclusão

				} else if (resposta == JOptionPane.NO_OPTION) {
					// Usuário clicou em não. Executar o código correspondente.

				}

			}

		});
		// Adicionando imagem.
		Image imgExcluir = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		button_5.setIcon(new ImageIcon(imgExcluir));
		button_5.setToolTipText("Excluir Registro");
		button_5.setBounds(732, 11, 116, 23);
		panel_2.add(button_5);

		// Carregando a tela...
		Carregar_tela_pagamento();

	}// Fim da tela inicial.

	public void Cod(String comando) {

		count = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("pagamento_id");
				String pedido = resultSet.getString("pedido_id");
				String forma = resultSet.getString("pagamento_forma");
				String valor = resultSet.getString("pagamento_valor");
				count = (count + 1);

				ModeloJTPag.addRow(new Object[] { id, pedido, forma, valor });

			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}
	}

	public void Carregar_tela_pagamento() {

		String comando = "select pagamento_id, pedido_id, pagamento_forma, pagamento_valor from tbl_pagamento;";

		count = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("pagamento_id");
				String pedido = resultSet.getString("pedido_id");
				String forma = resultSet.getString("pagamento_forma");
				String valor = resultSet.getString("pagamento_valor");
				count = (count + 1);

				ModeloJTPag.addRow(new Object[] { id, pedido, forma, valor });

			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

	}
}
