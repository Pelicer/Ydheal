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
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTable.PrintMode;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;

import modelSuplerclasses.Orcamento;
import view.jFrames.cadastros.JFCOrcamento_materiais;
import view.jFrames.visualizar_registros.JFVisualizarOrcamento;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JFOrcamento extends JFrame {

	int cont = 0;
	private DefaultTableModel ModeloJTOrc = new DefaultTableModel();
	private JPanel contentPane;
	private JTextField tfcodigo;
	private JTextField tfdescricao;
	private JTextField tfvalortotal;
	private JTable tabletbl_orcamento;
	@SuppressWarnings("unused")
	private JScrollPane scrollPane;

	public JFOrcamento() {

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/orcamento.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Or\u00E7amento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 990, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 22, 966, 88);
		contentPane.add(panel);

		JLabel lblOrcamento = new JLabel("Or\u00E7amento");
		// Adicionando imagem.
		Image imgOrcamento = new ImageIcon(this.getClass().getResource(
				"/50px/orcamento.png")).getImage();
		lblOrcamento.setIcon(new ImageIcon(imgOrcamento));
		lblOrcamento.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent arg0) {
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		lblOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOrcamento.setBounds(10, 6, 215, 79);
		panel.add(lblOrcamento);

		JLabel lblCodigo = new JLabel("Codigo:");
		lblCodigo.setBounds(213, 26, 80, 14);
		panel.add(lblCodigo);

		tfcodigo = new JTextField();
		tfcodigo.setColumns(10);
		tfcodigo.setBounds(271, 23, 80, 20);
		panel.add(tfcodigo);

		JLabel lblDescrioDoProduto = new JLabel(
				"Descri\u00E7\u00E3o do produto:");
		lblDescrioDoProduto.setBounds(510, 29, 160, 14);
		panel.add(lblDescrioDoProduto);

		tfdescricao = new JTextField();
		tfdescricao.setColumns(10);
		tfdescricao.setBounds(645, 26, 200, 20);
		panel.add(tfdescricao);

		JLabel labelregistrosencontrados = new JLabel("0");
		labelregistrosencontrados.setBounds(737, 153, 46, 14);
		contentPane.add(labelregistrosencontrados);
		labelregistrosencontrados.setHorizontalAlignment(SwingConstants.CENTER);
		labelregistrosencontrados.setForeground(new Color(0, 128, 0));

		JLabel lblModelo = new JLabel("Valor total:");
		lblModelo.setBounds(589, 57, 70, 14);
		panel.add(lblModelo);

		tfvalortotal = new JTextField();
		tfvalortotal.setBounds(661, 54, 105, 20);
		panel.add(tfvalortotal);
		tfvalortotal.setColumns(10);

		JLabel lblDataDeCriao = new JLabel("Data de cria\u00E7\u00E3o:");
		lblDataDeCriao.setBounds(213, 51, 109, 14);
		panel.add(lblDataDeCriao);

		JDateChooser tfdata = new JDateChooser();
		tfdata.setBounds(316, 51, 160, 20);
		panel.add(tfdata);

		JButton btnFiltrar = new JButton("Filtrar");
		// Adicionando imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		btnFiltrar.setIcon(new ImageIcon(imgFiltrar));
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				while (tabletbl_orcamento.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_orcamento.getModel())
							.removeRow(0);
				}

				if ((tfdata.getDate() == null) && (tfcodigo.equals(""))
						&& (tfdescricao.equals(""))
						&& (tfvalortotal.equals(""))) {

					String comando = "select orcamento_id, orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal from tbl_orcamento;";
					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

					JOptionPane.showMessageDialog(null, "aki");
				}

				// um
				else if (tfcodigo.equals("") && (tfdata.getDate() == null)
						&& (tfdescricao.equals(""))
						&& (!tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_valorTotal >= "
							+ tfvalortotal.getText() + "';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (tfcodigo.equals("") && (tfdata.getDate() == null)
						&& (!tfdescricao.equals(""))
						&& (tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_descricaoproduto like '"
							+ tfdescricao.getText() + "%';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (tfcodigo.equals("") && (tfdata.getDate() != null)
						&& (tfdescricao.equals(""))
						&& (tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_datagerado  = '"
							+ tfdata.getDate()
							+ "';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (!tfcodigo.equals("") && (tfdata.getDate() == null)
						&& (tfdescricao.equals(""))
						&& (tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_id = "
							+ tfcodigo.getText() + ";";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				}

				// 2
				else if (!tfcodigo.equals("") && (tfdata.getDate() == null)
						&& (tfdescricao.equals(""))
						&& (!tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where orcamento_id = "
							+ tfcodigo.getText()
							+ " and "
							+ "orcamento_datagerado  = '"
							+ tfdata.getDate()
							+ "';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (!tfcodigo.equals("") && (tfdata.getDate() == null)
						&& (!tfdescricao.equals(""))
						&& (tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_id = "
							+ tfcodigo.getText()
							+ " and "
							+ "orcamento_descricaoproduto like '"
							+ tfdescricao.getText() + "%';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (!tfcodigo.equals("") && (tfdata.getDate() != null)
						&& (tfdescricao.equals(""))
						&& (tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_id = "
							+ tfcodigo.getText()
							+ " and "
							+ "orcamento_datagerado  = '"
							+ tfdata.getDate()
							+ "';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (tfcodigo.equals("") && (tfdata.getDate() == null)
						&& (!tfdescricao.equals(""))
						&& (!tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_valorTotal >= "
							+ tfvalortotal.getText()
							+ " and "
							+ "orcamento_descricaoproduto like '"
							+ tfdescricao.getText() + "%';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (tfcodigo.equals("") && (tfdata.getDate() != null)
						&& (tfdescricao.equals(""))
						&& (!tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_valorTotal >= "
							+ tfvalortotal.getText()
							+ " and "
							+ "orcamento_datagerado  = '"
							+ tfdata.getDate()
							+ "';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (tfcodigo.equals("") && (tfdata.getDate() != null)
						&& (!tfdescricao.equals(""))
						&& (tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_descricaoproduto like '"
							+ tfdescricao.getText()
							+ "%' and "
							+ "orcamento_valorTotal >= "
							+ tfvalortotal.getText() + ";";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				}
				// 3
				 else if (!tfcodigo.equals("") && (tfdata.getDate() != null)
						&& (!tfdescricao.equals(""))
						&& (tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_id = "
							+ tfcodigo.getText()
							+ " and "
							+ "orcamento_datagerado  = '"
							+ tfdata.getDate()
							+ "' and "
							+ "orcamento_descricaoproduto like '"
							+ tfdescricao.getText() + "%';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (tfcodigo.equals("") && (tfdata.getDate() != null)
						&& (!tfdescricao.equals(""))
						&& (!tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_datagerado  = '"
							+ tfdata.getDate()
							+ "' and "
							+ "orcamento_descricaoproduto like '"
							+ tfdescricao.getText()
							+ "%' and "
							+ "orcamento_valorTotal >= "
							+ tfvalortotal.getText() + ";";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (!tfcodigo.equals("") && (tfdata.getDate() != null)
						&& (tfdescricao.equals(""))
						&& (!tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_id = "
							+ tfcodigo.getText()
							+ " and "
							+ "orcamento_datagerado  = '"
							+ tfdata.getDate()
							+ "' and "
							+ "orcamento_valorTotal >= "
							+ tfvalortotal.getText() + ";";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				} else if (!tfcodigo.equals("") && (tfdata.getDate() != null)
						&& (!tfdescricao.equals(""))
						&& (tfvalortotal.equals(""))) {
					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where "
							+ "orcamento_id = "
							+ tfcodigo.getText()
							+ " and "
							+ "orcamento_datagerado  = '"
							+ tfdata.getDate()
							+ "' and "
							+ "orcamento_descricaoproduto like '"
							+ tfdescricao.getText() + "%';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				}
				// 4
				else if ((tfdata.getDate() != null) && (!tfcodigo.equals(""))
						&& (!tfdescricao.equals(""))
						&& (!tfvalortotal.equals(""))) {

					String comando = "select orcamento_id, orcamento_precopormetroquadrado,orcamento_datagerado , orcamento_descricaoproduto, orcamento_valorTotal "
							+ "from tbl_orcamento where orcamento_id = "
							+ tfcodigo.getText()
							+ " and "
							+ "orcamento_descricaoproduto like '"
							+ tfdescricao.getText()
							+ "%' and "
							+ "orcamento_valorTotal >= "
							+ tfvalortotal.getText()
							+ " and "
							+ "orcamento_datagerado  = '"
							+ tfdata.getDate()
							+ "';";

					Cod(comando);
					labelregistrosencontrados.setText(String.valueOf(cont));

				}

			}
		});
		btnFiltrar.setBounds(349, 121, 116, 23);
		contentPane.add(btnFiltrar);

		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// API de impressão.
				MessageFormat footer = new MessageFormat("Página {0}");
				MessageFormat header = new MessageFormat("Lista de Orçamentos");

				try {
					boolean complete = tabletbl_orcamento.print(
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
		btnImprimir.setBounds(475, 121, 116, 23);
		contentPane.add(btnImprimir);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfcodigo.setText("");
				tfvalortotal.setText("");
				tfdescricao.setText("");
				tfcodigo.grabFocus();
			}
		});
		btnLimpar.setBounds(223, 121, 116, 23);
		contentPane.add(btnLimpar);

		JButton btnAlterar = new JButton("Alterar");
		// Adicionando imagem.
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imgAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Orcamento o = new Orcamento();

				String comando = "select orcamento_detalesadicionais, orcamento_altura, orcamento_largura, orcamento_precopormetroquadrado,"
						+ " orcamento_metodoPagamento, orcamento_parcelamento, orcamento_juros, orcamento_valorTotal, orcamento_valorMaoDeObra,"
						+ " orcamento_listaMaterial, orcamento_listaqtd, orcamento_descricaoproduto, orcamento_detalesadicionais from tbl_orcamento where orcamento_id = "
						+ ModeloJTOrc.getValueAt(
								tabletbl_orcamento.getSelectedRow(), 0) + ";";

				o.setId(Integer.parseInt(String.valueOf(ModeloJTOrc.getValueAt(
						tabletbl_orcamento.getSelectedRow(), 0))));

				try {

					// cria a conecxão

					Class.forName("com.mysql.jdbc.Driver");

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						o.setDetalesadicionais(resultSet
								.getString("orcamento_detalesadicionais"));
						o.setAltura(resultSet.getDouble("orcamento_altura"));
						o.setLargura(resultSet.getDouble("orcamento_largura"));
						o.setPrecopormetroquadrado(resultSet
								.getDouble("orcamento_precopormetroquadrado"));
						o.setFormaPagamento(resultSet
								.getString("orcamento_metodoPagamento"));
						o.setParcelamento(resultSet
								.getDouble("orcamento_parcelamento"));
						o.setJuros(resultSet.getDouble("orcamento_juros"));
						o.setValorTotal(resultSet
								.getDouble("orcamento_valorTotal"));
						o.setMaoDeObra(resultSet
								.getDouble("orcamento_valorMaoDeObra"));
						o.setListaMaterial(resultSet
								.getString("orcamento_listaMaterial"));
						o.setListaqtd(resultSet.getString("orcamento_listaqtd"));
						o.setDescricaoproduto(resultSet
								.getString("orcamento_descricaoproduto"));
						o.setDetalesadicionais(resultSet
								.getString("orcamento_detalesadicionais"));

					}

					st.close();
					con.close();

				}// fim do try
				catch (SQLException erroBanco) {
					JOptionPane
							.showMessageDialog(
									null,
									"Falha na conexão com o banco de dados. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
									"Erro!", JOptionPane.ERROR_MESSAGE);
					erroBanco.printStackTrace();
				}

				catch (ClassNotFoundException erroJDBC) {
					JOptionPane
							.showMessageDialog(
									null,
									"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
				}

				try {
					JFVisualizarOrcamento viewOrcamento = new JFVisualizarOrcamento(
							o);
					// viewOrcamento.setModal(true);
					viewOrcamento.setVisible(true);
					viewOrcamento.setLocationRelativeTo(null);

				} catch (Exception eee) {
					eee.printStackTrace();
				}

			}
		});
		btnAlterar.setBounds(601, 121, 116, 23);
		contentPane.add(btnAlterar);
		btnAlterar.setToolTipText("Alterar Registro");

		JButton btnNovo = new JButton("Novo");
		// Adicionando imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnNovo.setIcon(new ImageIcon(imgNovo));
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Abre o formulário de cadastro de orçamentos.
				try {
					JFCOrcamento_materiais frame = new JFCOrcamento_materiais(
							null);
					frame.setModal(true);
					frame.setVisible(true);
				} catch (Exception error) {
					error.printStackTrace();
				}

				while (tabletbl_orcamento.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_orcamento.getModel())
							.removeRow(0);
				}

				Carregar_tela_orcamento();

				labelregistrosencontrados.setText(String.valueOf(cont));

			}
		});

		btnNovo.setBounds(97, 121, 116, 23);
		contentPane.add(btnNovo);
		btnNovo.setToolTipText("Criar Novo Registro");

		JButton btnExcluir = new JButton("Excluir");
		// Adicionando imagem.
		Image imgExcluir = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnExcluir.setIcon(new ImageIcon(imgExcluir));
		btnExcluir.setBounds(727, 121, 116, 23);
		contentPane.add(btnExcluir);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// pede para confirmar a resposta
				int resposta = JOptionPane.showConfirmDialog(null,
						"Deseja realmente excluir os clientes selecionados?",
						"Confirmar exclusões ", JOptionPane.YES_NO_OPTION);

				// chaca resposta e faz o de acordo
				if (resposta == JOptionPane.YES_OPTION) {

					// pega as linhas selecionadas
					int x[] = tabletbl_orcamento.getSelectedRows();

					// exclui as linhas selecionadas

					int id = 0;

					for (int i = x.length - 1; i >= 0; i--) {

						// obtem o id da linha selecionada
						id = Integer.parseInt(String.valueOf(ModeloJTOrc
								.getValueAt(x[i], 0)));

						ModeloJTOrc.removeRow(x[i]);

						String comando = "DELETE FROM tbl_orcamento WHERE orcamento_id = "
								+ id + ";";

						try {

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
											"Falha na exclusão. Não será possível excluir o orçamento caso exista pendências do mesmo."
													+ "\nPor favor, exclua pedidos referentes a este antes de retirá-lo do sistema.",
											"AVISO!",
											JOptionPane.WARNING_MESSAGE);

						}

					}// fecha o for de exclusão

				} else if (resposta == JOptionPane.NO_OPTION) {
					// Usuário clicou em não. Executar o código correspondente.

				}

			}

		});
		btnExcluir.setToolTipText("Excluir Registro");

		JLabel label_7 = new JLabel("Registros Encontrados");
		label_7.setBounds(793, 150, 141, 14);
		contentPane.add(label_7);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 175, 946, 511);
		contentPane.add(scrollPane);
		// Criando os títulos.

		{
			ModeloJTOrc.addColumn("Cod.");
			ModeloJTOrc.addColumn("Descrição");
			ModeloJTOrc.addColumn("Preço por M²");
			ModeloJTOrc.addColumn("Forma de Pagamento");
			ModeloJTOrc.addColumn("Valor Total");
			ModeloJTOrc.addColumn("Criado em");
		}

		tabletbl_orcamento = new JTable(ModeloJTOrc) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		// Settando os tamanhos de coluna.
		tabletbl_orcamento.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabletbl_orcamento.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabletbl_orcamento.getColumnModel().getColumn(2).setPreferredWidth(1);
		tabletbl_orcamento.getColumnModel().getColumn(3).setPreferredWidth(1);
		tabletbl_orcamento.getColumnModel().getColumn(4).setPreferredWidth(1);
		tabletbl_orcamento.getColumnModel().getColumn(5).setPreferredWidth(1);

		// Impede a reordenação das colunas.
		tabletbl_orcamento.getTableHeader().setReorderingAllowed(false);

		scrollPane.setViewportView(tabletbl_orcamento);

		// Verificação de click duplo.
		tabletbl_orcamento.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					Orcamento o = new Orcamento();

					String comando = "select orcamento_detalesadicionais, orcamento_altura, orcamento_largura, orcamento_precopormetroquadrado,"
							+ " orcamento_metodoPagamento, orcamento_parcelamento, orcamento_juros, orcamento_valorTotal, orcamento_valorMaoDeObra,"
							+ " orcamento_listaMaterial, orcamento_listaqtd, orcamento_descricaoproduto, orcamento_detalesadicionais from tbl_orcamento where orcamento_id = "
							+ ModeloJTOrc.getValueAt(
									tabletbl_orcamento.getSelectedRow(), 0)
							+ ";";

					o.setId(Integer.parseInt(String.valueOf(ModeloJTOrc
							.getValueAt(tabletbl_orcamento.getSelectedRow(), 0))));

					try {

						// cria a conecxão

						Class.forName("com.mysql.jdbc.Driver");

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {

							o.setDetalesadicionais(resultSet
									.getString("orcamento_detalesadicionais"));
							o.setAltura(resultSet.getDouble("orcamento_altura"));
							o.setLargura(resultSet
									.getDouble("orcamento_largura"));
							o.setPrecopormetroquadrado(resultSet
									.getDouble("orcamento_precopormetroquadrado"));
							o.setFormaPagamento(resultSet
									.getString("orcamento_metodoPagamento"));
							o.setParcelamento(resultSet
									.getDouble("orcamento_parcelamento"));
							o.setJuros(resultSet.getDouble("orcamento_juros"));
							o.setValorTotal(resultSet
									.getDouble("orcamento_valorTotal"));
							o.setMaoDeObra(resultSet
									.getDouble("orcamento_valorMaoDeObra"));
							o.setListaMaterial(resultSet
									.getString("orcamento_listaMaterial"));
							o.setListaqtd(resultSet
									.getString("orcamento_listaqtd"));
							o.setDescricaoproduto(resultSet
									.getString("orcamento_descricaoproduto"));
							o.setDetalesadicionais(resultSet
									.getString("orcamento_detalesadicionais"));

						}

						st.close();
						con.close();

					}// fim do try
					catch (SQLException erroBanco) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha na conexão com o banco de dados. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						erroBanco.printStackTrace();
					}

					catch (ClassNotFoundException erroJDBC) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);
					}

					try {
						JFVisualizarOrcamento viewOrcamento = new JFVisualizarOrcamento(
								o);
						viewOrcamento.setVisible(true);
						viewOrcamento.setLocationRelativeTo(null);

					} catch (Exception eee) {
						eee.printStackTrace();
					}
				}
			}
		});

		// Carregando a tela...
		Carregar_tela_orcamento();

		labelregistrosencontrados.setText(String.valueOf(cont));

	} // Fim da tela inicial.

	public void Cod(String comando) {

		cont = 0;

		try {

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				cont = (cont + 1);

				ModeloJTOrc.addRow(new Object[] { resultSet.getString("orcamento_id"), resultSet.getString("orcamento_descricaoproduto"),
						resultSet.getString("orcamento_precopormetroquadrado"), resultSet.getString("orcamento_metodoPagamento"), resultSet.getString("orcamento_valorTotal"), resultSet.getString("orcamento_datagerado") });
			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}
	}

	public void Carregar_tela_orcamento() {

		String comando = "select orcamento_id, orcamento_precopormetroquadrado, orcamento_descricaoproduto, orcamento_valorTotal, orcamento_metodoPagamento, orcamento_datagerado from tbl_orcamento;";

		cont = 0;

		try {

			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			// formata data para padrão americano
			SimpleDateFormat Formatodatabr = new SimpleDateFormat(
					"dd/MM/yyyy");
			
			while (resultSet.next()) {

			
				cont = (cont + 1);
				
				String dia = resultSet.getString("orcamento_datagerado");
				
				ModeloJTOrc.addRow(new Object[] { resultSet.getString("orcamento_id"), resultSet.getString("orcamento_descricaoproduto"),
						resultSet.getString("orcamento_precopormetroquadrado"), resultSet.getString("orcamento_metodoPagamento"), resultSet.getString("orcamento_valorTotal"), Formatodatabr.format(java.sql.Date.valueOf(dia)) });
		
			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(
							null,
							"Falha na consulta. Houve um erro ao carregar alguns dos orçamentos",
							"ERRO!", JOptionPane.ERROR_MESSAGE);
		}

	}
}
