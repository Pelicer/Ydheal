package view.jDialogs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Orcamento;
import modelSuplerclasses.Visita;
import view.jFrames.cadastros.JFCOrcamento_materiais;

@SuppressWarnings("serial")
public class JDSelecionarBuscarOrcamentos extends JDialog {

	int count = 0;
	private DefaultTableModel ModeloJTOrc = new DefaultTableModel();
	private JPanel contentPane;
	private JTextField tfClienteId;
	private JTextField tfOrcamentoId;
	private JTable tabletbl_orcamento;
	@SuppressWarnings("unused")
	private JScrollPane scrollPane;
	private JTextField tfdatacriacao;
	private JTextField tfclientenome;

	public JDSelecionarBuscarOrcamentos(Orcamento o) {

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

		JLabel lblCodCliente = new JLabel("Cod. cliente:");
		lblCodCliente.setBounds(235, 11, 80, 14);
		panel.add(lblCodCliente);

		tfClienteId = new JTextField();
		tfClienteId.setColumns(10);
		tfClienteId.setBounds(325, 8, 69, 20);
		panel.add(tfClienteId);

		JLabel lblCodOr = new JLabel("Cod. Or\u00E7.:");
		lblCodOr.setBounds(235, 42, 91, 14);
		panel.add(lblCodOr);

		tfOrcamentoId = new JTextField();
		tfOrcamentoId.setColumns(10);
		tfOrcamentoId.setBounds(325, 39, 69, 20);
		panel.add(tfOrcamentoId);

		JLabel lblDataCriaa = new JLabel("Data Cria\u00E7\u00E3o:");
		lblDataCriaa.setBounds(747, 11, 80, 14);
		panel.add(lblDataCriaa);

		tfdatacriacao = new JTextField();
		tfdatacriacao.setBounds(837, 8, 86, 20);
		panel.add(tfdatacriacao);
		tfdatacriacao.setColumns(10);

		JLabel lblNomeCliente = new JLabel("Nome cliente:");
		lblNomeCliente.setBounds(418, 11, 86, 14);
		panel.add(lblNomeCliente);

		tfclientenome = new JTextField();
		tfclientenome.setBounds(514, 8, 208, 20);
		panel.add(tfclientenome);
		tfclientenome.setColumns(10);

		JButton btnFiltrar = new JButton("Filtrar");
		// Adicionando imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		btnFiltrar.setIcon(new ImageIcon(imgFiltrar));
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFiltrar.setBounds(469, 119, 116, 23);
		contentPane.add(btnFiltrar);
		JButton btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfClienteId.setText("");

				tfOrcamentoId.setText("");
				tfClienteId.grabFocus();
			}
		});
		btnLimpar.setBounds(343, 119, 116, 23);
		contentPane.add(btnLimpar);
		JLabel labelregistrosencontrados = new JLabel("0");
		labelregistrosencontrados.setBounds(737, 153, 46, 14);
		contentPane.add(labelregistrosencontrados);
		labelregistrosencontrados.setHorizontalAlignment(SwingConstants.CENTER);
		labelregistrosencontrados.setForeground(new Color(0, 128, 0));

		JButton btnNovo = new JButton("Novo");
		// Adicionando imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnNovo.setIcon(new ImageIcon(imgNovo));
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setModal(false);

				Visita v = new Visita();

				// Abre o formulário de cadastro de orçamentos.
				try {
					JFCOrcamento_materiais frame = new JFCOrcamento_materiais(v);
					frame.setModal(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);

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

				while (tabletbl_orcamento.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_orcamento.getModel())
							.removeRow(0);
				}

				Carregar_tela_orcamento();

				labelregistrosencontrados.setText(String.valueOf(count));

			}
		});

		btnNovo.setBounds(217, 119, 116, 23);
		contentPane.add(btnNovo);
		btnNovo.setToolTipText("Criar Novo Registro");
		// Adicionando imagem.
		JLabel label_7 = new JLabel("Registros Encontrados");
		label_7.setBounds(793, 150, 141, 14);
		contentPane.add(label_7);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 175, 946, 511);
		contentPane.add(scrollPane);
		// Criando os títulos.

		{
			ModeloJTOrc.addColumn("Cod.");
			ModeloJTOrc.addColumn("Cod. cliente");
			ModeloJTOrc.addColumn("Nome cliente");
			ModeloJTOrc.addColumn("Modo Pagamento");
			ModeloJTOrc.addColumn("Custo Total");
		}

		tabletbl_orcamento = new JTable(ModeloJTOrc) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		// Impede a reordenação das colunas.
		tabletbl_orcamento.getTableHeader().setReorderingAllowed(false);

		scrollPane.setViewportView(tabletbl_orcamento);

		// Detecção de clique duplo.
		tabletbl_orcamento.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					// pego o codigo
					o.setId(Integer.parseInt(String.valueOf(ModeloJTOrc
							.getValueAt(tabletbl_orcamento.getSelectedRow(), 0))));

					try {
						Class.forName("com.mysql.jdbc.Driver");

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando = "select orcamento_descricaoproduto, orcamento_metodoPagamento, orcamento_valorTotal,  orcamento_totalPrazo, orcamento_juros from tbl_orcamento where orcamento_id = "
								+ o.getId() + "; ";

						ResultSet resultSet = st.executeQuery(comando);

						String nome = "";
						String metodoPagamento = null;
						double valorTotal = 0;
						double valorPrazo = 0;
						double valorJuros = 0;
						while (resultSet.next()) {

							nome = resultSet
									.getString("orcamento_descricaoproduto");
							metodoPagamento = resultSet
									.getString("orcamento_metodoPagamento");
							valorTotal = resultSet
									.getDouble("orcamento_valorTotal");
							valorPrazo = resultSet
									.getDouble("orcamento_totalPrazo");
							valorJuros = resultSet.getDouble("orcamento_juros");

						}

						o.setDescricaoproduto(nome);
						o.setFormaPagamento(metodoPagamento);
						o.setValorTotal(valorTotal);
						o.setJuros(valorJuros);
						o.setTotalPrazo(valorPrazo);

						st.close();
						con.close();

					} catch (NumberFormatException erroNumero) {

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

					catch (Exception erro) {
						erro.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

					JOptionPane.showMessageDialog(null,
							"Orçamento selecionado!");

					dispose();
				}
			}
		});

		// Settando os tamanhos de coluna.
		tabletbl_orcamento.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabletbl_orcamento.getColumnModel().getColumn(1).setPreferredWidth(10);
		tabletbl_orcamento.getColumnModel().getColumn(2).setPreferredWidth(1);
		tabletbl_orcamento.getColumnModel().getColumn(3).setPreferredWidth(10);
		tabletbl_orcamento.getColumnModel().getColumn(3).setPreferredWidth(1);

		// Carregando a tela...
		Carregar_tela_orcamento();

		labelregistrosencontrados.setText(String.valueOf(count));

		JButton btnSelecionarOramento = new JButton("Selecionar Or\u00E7amento");
		Image imgSelecionar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSelecionarOramento.setIcon(new ImageIcon(imgSelecionar));
		btnSelecionarOramento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// pego o codigo
				o.setId(Integer.parseInt(String.valueOf(ModeloJTOrc.getValueAt(
						tabletbl_orcamento.getSelectedRow(), 0))));

				try {
					Class.forName("com.mysql.jdbc.Driver");

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					String comando = "select orcamento_descricaoproduto, orcamento_metodoPagamento, orcamento_valorTotal,  orcamento_totalPrazo, orcamento_juros, from tbl_orcamento where orcamento_id = "
							+ o.getId() + "; ";

					ResultSet resultSet = st.executeQuery(comando);

					String nome = "";
					String metodoPagamento = null;
					double valorTotal = 0;
					double valorPrazo = 0;
					double valorJuros = 0;
					while (resultSet.next()) {

						nome = resultSet
								.getString("orcamento_descricaoproduto");
						metodoPagamento = resultSet
								.getString("orcamento_dataentrega");
						valorTotal = resultSet
								.getDouble("orcamento_valorTotal");
						valorPrazo = resultSet
								.getDouble("orcamento_totalPrazo");
						valorJuros = resultSet.getDouble("orcamento_juros");

					}

					o.setDescricaoproduto(nome);
					o.setFormaPagamento(metodoPagamento);
					o.setValorTotal(valorTotal);
					o.setJuros(valorJuros);
					o.setTotalPrazo(valorPrazo);

					st.close();
					con.close();

				} catch (NumberFormatException erroNumero) {

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

				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				JOptionPane.showMessageDialog(null, "Orçamento selecionado!");

				dispose();

			}
		});
		btnSelecionarOramento.setBounds(620, 119, 163, 23);
		contentPane.add(btnSelecionarOramento);

	} // Fim da tela inicial.

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

				String id = resultSet.getString("orcamento_id");
				String modeloId = resultSet.getString("modeloId");
				String visitaId = resultSet.getString("visita_id");
				String valorTotal = resultSet.getString("orcamento_valorTotal");

				// cont = (cont + 1);

				ModeloJTOrc.addRow(new Object[] { id, modeloId, visitaId,
						valorTotal, });

			}

			st.close();
			con.close();

		}// fim do try

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

		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}
	}

	public void Carregar_tela_orcamento() {

		String comando = "select cliente_nome, orcamento_id, tbl_orcamento.cliente_id, orcamento_valorTotal, orcamento_metodoPagamento, orcamento_valorTotal, modelo_id, visita_id from tbl_orcamento inner join tbl_cliente on tbl_orcamento.cliente_id = tbl_cliente.cliente_id;";

		count = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

			
				count = (count + 1);

				ModeloJTOrc.addRow(new Object[] { resultSet.getString("orcamento_id"), resultSet.getString("tbl_orcamento.cliente_id"), resultSet.getString("cliente_nome"),
						resultSet.getString("orcamento_metodoPagamento"), resultSet.getString("orcamento_valorTotal") });
				
			}

			st.close();
			con.close();

		}// fim do try
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

		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

	}
}
