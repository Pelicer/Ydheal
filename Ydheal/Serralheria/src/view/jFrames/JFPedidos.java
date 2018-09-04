package view.jFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

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

import modelHerancas.Pedido;
import view.jFrames.cadastros.JFCPedido;
import view.jFrames.visualizar_registros.JFVisualizarPedido;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JFPedidos extends JFrame {

	int cont = 0;
	private DefaultTableModel ModeloJTPedido = new DefaultTableModel();
	private JPanel contentPane;
	private JTextField tfcliente;
	private JTable tabletbl_pedido;
	private JTextField tfpedido;

	public JFPedidos() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				repaint();
			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/pedidos.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Pedidos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 992, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// formata a data para ficar igual no padrão americano
		SimpleDateFormat Formatodataam = new SimpleDateFormat("yyyy/MM/dd");

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel painelFiltro = new JPanel();
		painelFiltro.setLayout(null);
		painelFiltro.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelFiltro.setBounds(0, 0, 966, 88);
		panel.add(painelFiltro);

		JLabel lblPedidos = new JLabel("Pedidos");
		// Adicionando imagem.
		Image imgPedidos = new ImageIcon(this.getClass().getResource(
				"/50px/pedidos.png")).getImage();
		lblPedidos.setIcon(new ImageIcon(imgPedidos));
		lblPedidos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPedidos.setBounds(10, 0, 215, 79);
		painelFiltro.add(lblPedidos);

		JLabel lblOramento = new JLabel("Nome do cliente:");
		lblOramento.setBounds(236, 14, 116, 14);
		painelFiltro.add(lblOramento);

		tfcliente = new JTextField();
		tfcliente.setBounds(350, 14, 146, 20);
		painelFiltro.add(tfcliente);
		tfcliente.setColumns(10);

		JLabel lblNomeDoPedido = new JLabel("Nome do pedido:");
		lblNomeDoPedido.setBounds(530, 14, 98, 14);
		painelFiltro.add(lblNomeDoPedido);

		tfpedido = new JTextField();
		tfpedido.setColumns(10);
		tfpedido.setBounds(638, 14, 146, 20);
		painelFiltro.add(tfpedido);

		JLabel lblDataDaEntrega = new JLabel("Data da entrega:");
		lblDataDaEntrega.setBounds(530, 54, 108, 14);
		painelFiltro.add(lblDataDaEntrega);

		JDateChooser dcentrega = new JDateChooser();
		dcentrega.setBounds(638, 48, 146, 20);
		painelFiltro.add(dcentrega);

		JLabel lblDataDeEmi = new JLabel("Data de emiss\u00E3o:");
		lblDataDeEmi.setBounds(235, 54, 117, 14);
		painelFiltro.add(lblDataDeEmi);

		JDateChooser dcemissao = new JDateChooser();
		dcemissao.setBounds(350, 48, 146, 20);
		painelFiltro.add(dcemissao);

		JPanel painelTabela = new JPanel();
		painelTabela.setLayout(null);
		painelTabela.setBounds(0, 88, 966, 592);
		panel.add(painelTabela);

		JLabel registrosEncontrados = new JLabel("0");
		registrosEncontrados.setHorizontalAlignment(SwingConstants.CENTER);
		registrosEncontrados.setForeground(new Color(0, 128, 0));
		registrosEncontrados.setBounds(740, 45, 46, 14);
		painelTabela.add(registrosEncontrados);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 81, 946, 511);
		painelTabela.add(scrollPane);
		{
			// Nome das colunas.
			ModeloJTPedido.addColumn("Cod.");
			ModeloJTPedido.addColumn("Nome do cliente");
			ModeloJTPedido.addColumn("Nome do pedido");
			ModeloJTPedido.addColumn("Data de emissão");
			ModeloJTPedido.addColumn("Data de entrega");
			ModeloJTPedido.addColumn("Materiais encomendados");

		}

		tabletbl_pedido = new JTable(ModeloJTPedido) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane.setViewportView(tabletbl_pedido);

		// Detecção de clique duplo.
		tabletbl_pedido.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Pedido p = new Pedido();

					String comando = "select pedido_diaencomendamateriais, pedido_materiaisencomendados, visita_id, orcamento_id, pedido_historico, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, tbl_pedido.cliente_id from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id where pedido_id = "
							+ ModeloJTPedido.getValueAt(
									tabletbl_pedido.getSelectedRow(), 0) + ";";

					try {

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						// não sei oq isso faz kk
						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {

							p.setDataentrega(resultSet
									.getDate("pedido_dataentrega"));
							p.setDiagerado(resultSet.getDate("pedido_gerado"));
							p.setPedidonome(resultSet.getString("pedido_nome"));
							p.setClienteId(resultSet.getInt("cliente_id"));
							p.setId(resultSet.getInt("pedido_id"));
							p.setHistorico(resultSet
									.getString("pedido_historico"));
							p.setVisitaid(resultSet.getInt("visita_id"));
							p.setOrcamentoId(resultSet.getInt("orcamento_id"));
							p.setMateriaisencomendados(resultSet
									.getString("pedido_materiaisencomendados"));
							p.setDiaencomendamateriais(resultSet
									.getDate("pedido_diaencomendamateriais"));

						}

						st.close();
						con.close();

					} // fim do try
					catch (Exception ee) {
						ee.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

					try {
						JFVisualizarPedido frame = new JFVisualizarPedido(p);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
					} catch (Exception erro) {
						erro.printStackTrace();
					}

					while (tabletbl_pedido.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_pedido.getModel())
								.removeRow(0);
					}

					Carregartela();
					registrosEncontrados.setText(String.valueOf(cont));

				}
			}
		});

		// Carrendo os valores nas linhas.

		// Carregar_tela_pedido();

		registrosEncontrados.setText(String.valueOf(cont));

		JButton btnNovo = new JButton("Novo");
		// Adicionando imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnNovo.setIcon(new ImageIcon(imgNovo));
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre o formulário de cadastro de pedidos.
				try {
					JFCPedido frame = new JFCPedido();
					frame.setModal(true);
					frame.setVisible(true);				
				} catch (Exception error) {
					error.printStackTrace();
				}

				while (tabletbl_pedido.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_pedido.getModel())
							.removeRow(0);
				}
				Carregartela();
				registrosEncontrados.setText(String.valueOf(cont));

			}
		});
		btnNovo.setToolTipText("Criar Novo Registro");
		btnNovo.setBounds(99, 11, 116, 23);
		painelTabela.add(btnNovo);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfcliente.setText("");
				tfpedido.setText("");

				dcemissao.cleanup();
				dcentrega.cleanup();

			}
		});
		btnLimpar.setToolTipText("Limpar Filtros");
		btnLimpar.setBounds(351, 11, 116, 23);
		painelTabela.add(btnLimpar);

		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// API de impressão.
				MessageFormat footer = new MessageFormat("Página {0}");
				MessageFormat header = new MessageFormat("Lista de Pedidos");

				try {
					boolean complete = tabletbl_pedido.print(
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
		btnImprimir.setIcon(new ImageIcon(imgImprimir));
		btnImprimir.setToolTipText("Imprimir");
		btnImprimir.setBounds(477, 11, 116, 23);
		painelTabela.add(btnImprimir);

		JButton btnAlterar = new JButton("Alterar");
		// Adicionando imagem.
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imgAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Pedido p = new Pedido();

				String comando = "select pedido_diaencomendamateriais, pedido_materiaisencomendados, visita_id, orcamento_id, pedido_historico, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, tbl_pedido.cliente_id from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id where pedido_id = "
						+ ModeloJTPedido.getValueAt(
								tabletbl_pedido.getSelectedRow(), 0) + ";";

				try {

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					// não sei oq isso faz kk
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						p.setDataentrega(resultSet
								.getDate("pedido_dataentrega"));
						p.setDiagerado(resultSet.getDate("pedido_gerado"));
						p.setPedidonome(resultSet.getString("pedido_nome"));
						p.setClienteId(resultSet.getInt("cliente_id"));
						p.setId(resultSet.getInt("pedido_id"));
						p.setHistorico(resultSet.getString("pedido_historico"));
						p.setVisitaid(resultSet.getInt("visita_id"));
						p.setOrcamentoId(resultSet.getInt("orcamento_id"));
						p.setMateriaisencomendados(resultSet
								.getString("pedido_materiaisencomendados"));
						p.setDiaencomendamateriais(resultSet
								.getDate("pedido_diaencomendamateriais"));

					}

					st.close();
					con.close();

				} // fim do try
				catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				try {
					JFVisualizarPedido frame = new JFVisualizarPedido(p);
					//frame.setModal(true);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception erro) {
					erro.printStackTrace();
				}

				while (tabletbl_pedido.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_pedido.getModel())
							.removeRow(0);
				}

				Carregartela();
				registrosEncontrados.setText(String.valueOf(cont));

			}
		});
		btnAlterar.setToolTipText("Alterar Registro");
		btnAlterar.setBounds(603, 11, 116, 23);
		painelTabela.add(btnAlterar);
		{

			JLabel lblregistros = new JLabel("Registros Encontrados");
			lblregistros.setBounds(793, 45, 141, 14);
			painelTabela.add(lblregistros);

			JButton btnFiltrar = new JButton("Filtrar");
			// Adicionando imagem.
			Image imgFiltrar = new ImageIcon(this.getClass().getResource(
					"/16px/search.png")).getImage();
			btnFiltrar.setIcon(new ImageIcon(imgFiltrar));
			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					while (tabletbl_pedido.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_pedido.getModel())
								.removeRow(0);
					}

					// se todos os registro forem vazios
					if ((tfcliente.getText().equals(""))
							&& (tfpedido.getText().equals(""))
							&& (dcemissao.getDate() == null)
							&& (dcentrega.getDate() == null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id;";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((!tfcliente.getText().equals(""))
							&& (tfpedido.getText().equals(""))
							&& (dcemissao.getDate() == null)
							&& (dcentrega.getDate() == null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " cliente_nome like '"
								+ tfcliente.getText() + "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((tfcliente.getText().equals(""))
							&& (!tfpedido.getText().equals(""))
							&& (dcemissao.getDate() == null)
							&& (dcentrega.getDate() == null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_nome like '"
								+ tfpedido.getText() + "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((tfcliente.getText().equals(""))
							&& (tfpedido.getText().equals(""))
							&& (dcemissao.getDate() != null)
							&& (dcentrega.getDate() == null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_gerado = '"
								+ Formatodataam.format(dcemissao.getDate())
								+ "';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((tfcliente.getText().equals(""))
							&& (tfpedido.getText().equals(""))
							&& (dcemissao.getDate() == null)
							&& (dcentrega.getDate() != null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_dataentrega = '"
								+ Formatodataam.format(dcentrega.getDate())
								+ "';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((!tfcliente.getText().equals(""))
							&& (!tfpedido.getText().equals(""))
							&& (dcemissao.getDate() == null)
							&& (dcentrega.getDate() == null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_nome like '"
								+ tfpedido.getText()
								+ "%' and "
								+ " cliente_nome like '"
								+ tfcliente.getText()
								+ "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((!tfcliente.getText().equals(""))
							&& (tfpedido.getText().equals(""))
							&& (dcemissao.getDate() != null)
							&& (dcentrega.getDate() == null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_gerado = '"
								+ Formatodataam.format(dcemissao.getDate())
								+ "' and "
								+ " cliente_nome like '"
								+ tfcliente.getText() + "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((!tfcliente.getText().equals(""))
							&& (tfpedido.getText().equals(""))
							&& (dcemissao.getDate() == null)
							&& (dcentrega.getDate() != null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_dataentrega = '"
								+ Formatodataam.format(dcentrega.getDate())
								+ "' and "
								+ " cliente_nome like '"
								+ tfcliente.getText() + "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((tfcliente.getText().equals(""))
							&& (!tfpedido.getText().equals(""))
							&& (dcemissao.getDate() != null)
							&& (dcentrega.getDate() == null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_gerado = '"
								+ Formatodataam.format(dcemissao.getDate())
								+ "' and "
								+ " pedido_nome like '"
								+ tfpedido.getText() + "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					} else if ((tfcliente.getText().equals(""))
							&& (tfpedido.getText().equals(""))
							&& (dcemissao.getDate() != null)
							&& (dcentrega.getDate() != null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_dataentrega = '"
								+ Formatodataam.format(dcentrega.getDate())
								+ "' and "
								+ " pedido_gerado = '"
								+ Formatodataam.format(dcemissao.getDate())
								+ "';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((tfcliente.getText().equals(""))
							&& (!tfpedido.getText().equals(""))
							&& (dcemissao.getDate() == null)
							&& (dcentrega.getDate() != null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_dataentrega = '"
								+ Formatodataam.format(dcentrega.getDate())
								+ "' and "
								+ " pedido_nome like '"
								+ tfpedido.getText() + "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					} else if ((!tfcliente.getText().equals(""))
							&& (!tfpedido.getText().equals(""))
							&& (dcemissao.getDate() != null)
							&& (dcentrega.getDate() == null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_gerado = '"
								+ Formatodataam.format(dcemissao.getDate())
								+ "' and "
								+ " pedido_nome like '"
								+ tfpedido.getText()
								+ "%' and "
								+ " cliente_nome like '"
								+ tfcliente.getText()
								+ "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((tfcliente.getText().equals(""))
							&& (!tfpedido.getText().equals(""))
							&& (dcemissao.getDate() != null)
							&& (dcentrega.getDate() != null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_dataentrega = '"
								+ Formatodataam.format(dcentrega.getDate())
								+ "' and "
								+ " pedido_gerado = '"
								+ Formatodataam.format(dcemissao.getDate())
								+ "' and "
								+ " pedido_nome like '"
								+ tfpedido.getText() + "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

					else if ((!tfcliente.getText().equals(""))
							&& (!tfpedido.getText().equals(""))
							&& (dcemissao.getDate() != null)
							&& (dcentrega.getDate() != null)) {

						String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
								+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id "
								+ "where"
								+ " pedido_dataentrega = '"
								+ Formatodataam.format(dcentrega.getDate())
								+ "' and "
								+ " pedido_gerado = '"
								+ Formatodataam.format(dcemissao.getDate())
								+ "' and "
								+ " pedido_nome like '"
								+ tfpedido.getText()
								+ "%' and "
								+ " cliente_nome like '"
								+ tfcliente.getText()
								+ "%';";

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(cont));

					}

				}
			});
			btnFiltrar.setToolTipText("Filtrar Registros");
			btnFiltrar.setBounds(225, 11, 116, 23);
			painelTabela.add(btnFiltrar);

			JButton button_11 = new JButton("Excluir");
			// Adicionando imagem.
			Image imgExcluir = new ImageIcon(this.getClass().getResource(
					"/16px/cancelar.png")).getImage();
			button_11.setIcon(new ImageIcon(imgExcluir));
			button_11.setToolTipText("Excluir Registro");
			button_11.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int resposta = JOptionPane
							.showConfirmDialog(
									null,
									"Deseja realmente excluir os clientes selecionados?",
									"Confirmar exclusões ",
									JOptionPane.YES_NO_OPTION);

					// chaca resposta e faz o de acordo
					if (resposta == JOptionPane.YES_OPTION) {

						// pega as linhas selecionadas
						int x[] = tabletbl_pedido.getSelectedRows();

						// exclui as linhas selecionadas

						int id = 0;

						for (int i = x.length - 1; i >= 0; i--) {

							// obtem o id da linha selecionada
							id = Integer.parseInt(String.valueOf(ModeloJTPedido
									.getValueAt(x[i], 0)));

							ModeloJTPedido.removeRow(x[i]);

							// Comando para excluir cliente.
							String comandoCliente = "DELETE FROM tbl_pedido WHERE pedido_id = "
									+ id + ";";
							try {

								Class.forName("com.mysql.jdbc.Driver");

								// cria a conecxão
								java.sql.Connection con = ConexaoMySQL
										.getConexaoMySQL();

								java.sql.Statement st = con.createStatement();

								// Excluindo cliente...
								st.execute(comandoCliente);

								st.close();
								con.close();

							}// fim do try
							catch (Exception ee) {
								ee.printStackTrace();
								JOptionPane
										.showMessageDialog(
												null,
												"Falha na exclusão. Não será possível excluir o pedido caso exista pendências do mesmo."
														+ "\nPor favor, exclua as pendências antes de excluir o pedido.",
												"AVISO!",
												JOptionPane.WARNING_MESSAGE);
							}

							registrosEncontrados.setText(String.valueOf(Integer
									.parseInt(registrosEncontrados.getText()) - 1));

						}// fecha o for de exclusão

					} else if (resposta == JOptionPane.NO_OPTION) {
						// Usuário clicou em não. Executar o código
						// correspondente.

					}

				}
			});
			button_11.setBounds(729, 11, 116, 23);
			painelTabela.add(button_11);
		}

		// carrega os registros ao abrir a tela

		Carregartela();
		registrosEncontrados.setText(String.valueOf(cont));

	}

	public void Cod(String comando) {

		// formata a data para ficar igual no padrão brasileiro
		SimpleDateFormat Formatodatabr = new SimpleDateFormat("dd/MM/yyyy");

		cont = 0;

		try {

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String entrega = resultSet.getString("pedido_dataentrega");
				String gerado = resultSet.getString("pedido_gerado");
				String pedido = resultSet.getString("pedido_nome");
				String cliente = resultSet.getString("cliente_nome");
				String id = resultSet.getString("pedido_id");
				String materiais = resultSet
						.getString("pedido_materiaisencomendados");

				cont = (cont + 1);
				ModeloJTPedido
						.addRow(new Object[] { id, cliente, pedido,
								Formatodatabr.format(Date.valueOf(gerado)),
								Formatodatabr.format(Date.valueOf(entrega)),
								materiais });

			}

			st.close();
			con.close();

		} // fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

	}

	public void Carregartela() {

		// formata a data para ficar igual no padrão brasileiro
		SimpleDateFormat Formatodatabr = new SimpleDateFormat("dd/MM/yyyy");

		cont = 0;

		String comando = "select pedido_materiaisencomendados, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, cliente_nome "
				+ "from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id;";
		try {

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String entrega = resultSet.getString("pedido_dataentrega");
				String gerado = resultSet.getString("pedido_gerado");
				String pedido = resultSet.getString("pedido_nome");
				String cliente = resultSet.getString("cliente_nome");
				String id = resultSet.getString("pedido_id");
				String materiais = resultSet
						.getString("pedido_materiaisencomendados");

				cont = (cont + 1);
				ModeloJTPedido
						.addRow(new Object[] { id, cliente, pedido,
								Formatodatabr.format(Date.valueOf(gerado)),
								Formatodatabr.format(Date.valueOf(entrega)),
								materiais });

			}

			st.close();
			con.close();

		} // fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na condfdsulta");
		}

	}
}
