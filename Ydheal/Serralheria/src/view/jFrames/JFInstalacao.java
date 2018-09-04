package view.jFrames;

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
import modelSuplerclasses.Instalacao;
import view.jFrames.cadastros.JFCInstalacao;
import view.jFrames.visualizar_registros.JFVisualizarInstalacao;

@SuppressWarnings("serial")
public class JFInstalacao extends JFrame {

	int cont = 0;

	private DefaultTableModel ModeloJTCli = new DefaultTableModel();
	private JTable tabletbl_instalacao;
	private JPanel contentPane;
	private JTextField tfCodigo;
	private JTextField tfCliente;
	private JTextField tfCodPedido;

	public JFInstalacao() {

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/agendar.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Instala\u00E7\u00E3o");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 730);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 966, 88);
		contentPane.add(panel);

		JLabel lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setBounds(193, 40, 55, 14);
		panel.add(lblCodigo);

		tfCodigo = new JTextField();
		tfCodigo.setColumns(10);
		tfCodigo.setBounds(241, 37, 86, 20);
		panel.add(tfCodigo);

		JLabel lblCliente = new JLabel("Cod. Cliente:");
		lblCliente.setBounds(337, 40, 86, 14);
		panel.add(lblCliente);

		tfCliente = new JTextField();
		tfCliente.setColumns(10);
		tfCliente.setBounds(422, 37, 195, 20);
		panel.add(tfCliente);

		JLabel lblCodPedido = new JLabel("Cod. Pedido:");
		lblCodPedido.setBounds(627, 40, 92, 14);
		panel.add(lblCodPedido);

		tfCodPedido = new JTextField();
		tfCodPedido.setColumns(10);
		tfCodPedido.setBounds(703, 37, 195, 20);
		panel.add(tfCodPedido);

		JLabel lblInstalao = new JLabel("Instala\u00E7\u00E3o");
		// Adicionando imagem.
		Image imgInstalacao = new ImageIcon(this.getClass().getResource(
				"/50px/agendar.png")).getImage();
		lblInstalao.setIcon(new ImageIcon(imgInstalacao));
		lblInstalao.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInstalao.setBounds(10, 6, 343, 79);
		panel.add(lblInstalao);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(10, 99, 966, 592);
		contentPane.add(panel_1);

		JButton btnNovo = new JButton("Novo");
		// Adicionando Imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnNovo.setIcon(new ImageIcon(imgNovo));
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFCInstalacao frame = new JFCInstalacao();
					//frame.setModal(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception erroInstalacao) {
					erroInstalacao.printStackTrace();
				}
			}
		});
		btnNovo.setToolTipText("Criar Novo Registro");
		btnNovo.setBounds(108, 11, 116, 23);
		panel_1.add(btnNovo);

		JLabel lblContagem = new JLabel("0");
		lblContagem.setHorizontalAlignment(SwingConstants.CENTER);
		lblContagem.setForeground(new Color(0, 128, 0));
		lblContagem.setBounds(740, 45, 46, 14);
		panel_1.add(lblContagem);

		JButton btnFiltrar = new JButton("Filtrar");
		// Adicionando Imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		btnFiltrar.setIcon(new ImageIcon(imgFiltrar));
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				while (tabletbl_instalacao.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_instalacao.getModel())
							.removeRow(0);
				}
				
				if ((tfCliente.getText() == "") && (tfCodigo.getText() == "") && (tfCodPedido.getText() == ""))  {
					
					String comando = "select instalacao_id, cliente_id, instalacao_dia,instalacao_horario, instalacao_observacao, pedido_id from tbl_instalacao;";

					Cod(comando);

					lblContagem.setText(String.valueOf(cont));

				} 				
				else if (tfCliente.getText().isEmpty()
						&& tfCodPedido.getText().isEmpty()) {		

					String comando = "select instalacao_id, cliente_id, instalacao_dia, "
							+ "instalacao_horario, instalacao_observacao, pedido_id from tbl_instalacao where instalacao_id = "
							+ tfCodigo.getText() + ";";

					Cod(comando);

					lblContagem.setText(String.valueOf(cont));

				} else if (tfCodigo.getText().isEmpty()
						&& tfCodPedido.getText().isEmpty()) {

					String comando = "select instalacao_id, cliente_id, instalacao_dia, "
							+ "instalacao_horario, instalacao_observacao, pedido_id from tbl_instalacao where cliente_id = "
							+ tfCliente.getText() + ";";

					Cod(comando);

					lblContagem.setText(String.valueOf(cont));

				}
			}
		});
		btnFiltrar.setToolTipText("Filtrar Registros");
		btnFiltrar.setBounds(234, 11, 116, 23);
		panel_1.add(btnFiltrar);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando Imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfCodigo.setText("");
				tfCliente.setText("");
				tfCodPedido.setText("");
				tfCodigo.grabFocus();
			}
		});
		btnLimpar.setToolTipText("Limpar Filtros");
		btnLimpar.setBounds(360, 11, 116, 23);
		panel_1.add(btnLimpar);

		JButton btnImprimir = new JButton("Imprimir");
		// Adicionando Imagem.
		Image imgImprimir = new ImageIcon(this.getClass().getResource(
				"/16px/imprimir.png")).getImage();
		btnImprimir.setIcon(new ImageIcon(imgImprimir));
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// API de impressão.
				MessageFormat footer = new MessageFormat("Página {0}");
				MessageFormat header = new MessageFormat("Lista de Instalações");

				try {
					boolean complete = tabletbl_instalacao.print(
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
		btnImprimir.setToolTipText("Imprimir");
		btnImprimir.setBounds(486, 11, 116, 23);
		panel_1.add(btnImprimir);

		JButton btnAlterar = new JButton("Alterar");
		// Adicionando Imagem.
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imgAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Instalacao i = new Instalacao();

				String comando = "select instalacao_id, instalacao_dia, instalacao_horario, instalacao_observacao, cliente_id, pedido_id from tbl_instalacao where instalacao_id = "
						+ ModeloJTCli.getValueAt(
								tabletbl_instalacao.getSelectedRow(), 0) + ";";

				cont = 0;

				try {

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						i.setInstalacaoId(resultSet.getInt("instalacao_id"));
						i.setInstalacaoClienteId(resultSet.getInt("cliente_id"));
						i.setInstalacaoPedidoId((resultSet.getInt("pedido_id")));
						i.setInstalacaoData(resultSet.getDate("instalacao_dia"));
						i.setInstalacaoObservacao(resultSet
								.getString("instalacao_observacao"));
						i.setInstalacaoHorario(resultSet
								.getString("instalacao_horario"));

					}

					st.close();
					con.close();

				}// fim do try
				catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				try {
					try {
						JFVisualizarInstalacao frame = new JFVisualizarInstalacao(
								i);
						//frame.setModal(true);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
					} catch (Exception erroViewInstalacao) {
						erroViewInstalacao.printStackTrace();
					}
				} catch (Exception eee) {
					eee.printStackTrace();
				}
			}
		});
		btnAlterar.setToolTipText("Alterar Registro");
		btnAlterar.setBounds(612, 11, 116, 23);
		panel_1.add(btnAlterar);

		JButton btnExcluir = new JButton("Excluir");
		// Adicionando Imagem.
		Image imgExcluir = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnExcluir.setIcon(new ImageIcon(imgExcluir));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pede para confirmar a resposta
				int resposta = JOptionPane.showConfirmDialog(null,
						"Deseja realmente excluir os clientes selecionados?",
						"Confirmar exclusões ", JOptionPane.YES_NO_OPTION);

				// chaca resposta e faz o de acordo
				if (resposta == JOptionPane.YES_OPTION) {

					// pega as linhas selecionadas
					int x[] = tabletbl_instalacao.getSelectedRows();

					// exclui as linhas selecionadas

					int id = 0;

					for (int i = x.length - 1; i >= 0; i--) {

						// obtem o id da linha selecionada
						id = Integer.parseInt(String.valueOf(ModeloJTCli
								.getValueAt(x[i], 0)));

						ModeloJTCli.removeRow(x[i]);

						String comando = "DELETE FROM tbl_instalacao WHERE instalacao_id = "
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
							JOptionPane.showMessageDialog(null,
									"Falha na exclusão");
						}

						lblContagem.setText(String.valueOf(Integer
								.parseInt(lblContagem.getText()) - 1));

					}// fecha o for de exclusão

				} else if (resposta == JOptionPane.NO_OPTION) {
					// Usuário clicou em não. Executar o código correspondente.

				}

			}
		});
		btnExcluir.setToolTipText("Excluir Registro");
		btnExcluir.setBounds(738, 11, 116, 23);
		panel_1.add(btnExcluir);

		JLabel lblRegistros = new JLabel("Registros Encontrados");
		lblRegistros.setBounds(793, 45, 141, 14);
		panel_1.add(lblRegistros);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 946, 511);
		panel_1.add(scrollPane);

		// cria os titulos

		{
			ModeloJTCli.addColumn("Cod.");
			ModeloJTCli.addColumn("Cliente.");
			ModeloJTCli.addColumn("Pedido");
			ModeloJTCli.addColumn("Dia da Instalação");
			ModeloJTCli.addColumn("Horário");
			ModeloJTCli.addColumn("Observação");
		}

		tabletbl_instalacao = new JTable(ModeloJTCli) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		// impede que as colunas sejam reordemadas
		tabletbl_instalacao.getTableHeader().setReorderingAllowed(false);

		scrollPane.setViewportView(tabletbl_instalacao);// impede que as colunas
														// sejam reordemadas
		tabletbl_instalacao.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(tabletbl_instalacao);

		// no fim da moddificação da célula editavel, vem a deteqção de evento
		// de cliq dublo em um registro da jtable.
		tabletbl_instalacao.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					Instalacao i = new Instalacao();

					String comando = "select instalacao_id, instalacao_dia, instalacao_horario, instalacao_observacao, cliente_id, pedido_id from tbl_instalacao where instalacao_id = "
							+ ModeloJTCli.getValueAt(
									tabletbl_instalacao.getSelectedRow(), 0)
							+ ";";

					cont = 0;

					try {

						Class.forName("com.mysql.jdbc.Driver");

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						
						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {

							i.setInstalacaoId(resultSet.getInt("instalacao_id"));
							i.setInstalacaoClienteId(resultSet
									.getInt("cliente_id"));
							i.setInstalacaoPedidoId((resultSet
									.getInt("pedido_id")));
							i.setInstalacaoData(resultSet
									.getDate("instalacao_dia"));
							i.setInstalacaoObservacao(resultSet
									.getString("instalacao_observacao"));
							i.setInstalacaoHorario(resultSet
									.getString("instalacao_horario"));

						}

						st.close();
						con.close();

					}// fim do try
					catch (Exception ee) {
						ee.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

					try {
						try {
							JFVisualizarInstalacao frame = new JFVisualizarInstalacao(
									i);
							frame.setVisible(true);
							frame.setLocationRelativeTo(null);
						} catch (Exception erroViewInstalacao) {
							erroViewInstalacao.printStackTrace();
						}
					} catch (Exception eee) {
						eee.printStackTrace();
					}

				}
			}
		});

		// da os tamanhos para as colunas
		tabletbl_instalacao.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabletbl_instalacao.getColumnModel().getColumn(1).setPreferredWidth(1);
		tabletbl_instalacao.getColumnModel().getColumn(2)
				.setPreferredWidth(100);
		tabletbl_instalacao.getColumnModel().getColumn(3).setPreferredWidth(1);
		tabletbl_instalacao.getColumnModel().getColumn(4).setPreferredWidth(1);
		tabletbl_instalacao.getColumnModel().getColumn(5).setPreferredWidth(10);

		// carrega a tela ao abrir

		Carregar_tela_instalacao();

		lblContagem.setText(String.valueOf(cont));

	}// fim da classe da tela

	public void Cod(String comando) {

		cont = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("instalacao_id");
				String clienteId = resultSet.getString("cliente_id");
				String observacao = resultSet
						.getString("instalacao_observacao");
				String pedidoId = resultSet.getString("pedido_id");
				String instalacaoDia = resultSet.getString("instalacao_dia");
				String instalacaoHorario = resultSet
						.getString("instalacao_horario");

				cont = (cont + 1);

				ModeloJTCli.addRow(new Object[] { id, clienteId, pedidoId,
						instalacaoDia, instalacaoHorario, observacao });

			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}
	}

	public void Carregar_tela_instalacao() {

		String comando = "select instalacao_id, cliente_id, pedido_id, instalacao_dia, instalacao_horario, instalacao_observacao from tbl_instalacao;";

		cont = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("instalacao_Id");
				String clienteId = resultSet.getString("cliente_id");
				String observacao = resultSet
						.getString("instalacao_observacao");
				String pedidoId = resultSet.getString("pedido_id");
				String instalacaoDia = resultSet.getString("instalacao_dia");
				String instalacaoHorario = resultSet
						.getString("instalacao_horario");

				cont = (cont + 1);

				ModeloJTCli.addRow(new Object[] { id, clienteId, pedidoId,
						instalacaoDia, instalacaoHorario, observacao });

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
