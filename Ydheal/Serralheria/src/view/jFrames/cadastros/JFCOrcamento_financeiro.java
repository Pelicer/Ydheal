package view.jFrames.cadastros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;

import Model.DAO.ConexaoMySQL;
import modelHerancas.Pedido;
import modelSuplerclasses.Cliente;
import modelSuplerclasses.Orcamento;
import modelSuplerclasses.Visita;
import view.jDialogs.JDSelecionarCodigoCliente;

@SuppressWarnings("serial")
public class JFCOrcamento_financeiro extends JDialog {

	Pedido p = new Pedido();
	Cliente c = new Cliente();

	@SuppressWarnings("unused")
	private JPanel contentPane;
	DefaultTableModel modeloJtable = new DefaultTableModel();
	private JTable tabelaMateriais;
	private JTextField tfCustoMateriais;
	private JTextField tfMaoDeObra;
	private JTextField tfJuros;
	private JTextField tfValorFinal;
	private JTextField tfcustopormetroquadrado;
	private JTextField tfnome;

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public JFCOrcamento_financeiro(Orcamento o, Visita v) {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro do orçamento será cancelado. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/orcamento.png")).getImage();
		setLocationRelativeTo(null);
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Cadastro de Or\u00E7amento");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1090, 600);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(0, 64, 1084, 2);
			getContentPane().add(separator);
		}
		{
			JLabel lblCadastroDeOramento = new JLabel(
					"Cadastro de Or\u00E7amento - Financeiro");
			Image imgOrcamento = new ImageIcon(this.getClass().getResource(
					"/50px/orcamento.png")).getImage();
			lblCadastroDeOramento.setIcon(new ImageIcon(imgOrcamento));
			lblCadastroDeOramento.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblCadastroDeOramento.setBounds(31, 11, 1033, 50);
			getContentPane().add(lblCadastroDeOramento);
		}
		{

		}
		{
			JButton button = new JButton("Cancelar");
			Image imgButton = new ImageIcon(this.getClass().getResource(
					"/16px/cancelar.png")).getImage();
			button.setIcon(new ImageIcon(imgButton));
			button.setToolTipText("Cancelar");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando2 = "select max(orcamento_id) from tbl_orcamento;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							int id = resultSet2.getInt("max(orcamento_id)");

							o.setId(id);

						}

						comando2 = "delete from tbl_orcamento where id = "
								+ o.getId() + ";";

					} catch (SQLException ee) {

						ee.printStackTrace();

					}

					dispose();
				}
			});
			button.setBounds(531, 530, 140, 20);
			getContentPane().add(button);
		}
		{
			JLabel lblMateriais = new JLabel("Materiais");
			lblMateriais.setBounds(10, 77, 201, 36);
			getContentPane().add(lblMateriais);
		}
		{
			JLabel lblNewLabel = new JLabel("Pedido");
			lblNewLabel.setBounds(252, 77, 120, 36);
			getContentPane().add(lblNewLabel);
		}
		{
			JLabel lblDescrioDoPedido = new JLabel(
					"Descri\u00E7\u00E3o do Produto:");
			lblDescrioDoPedido.setBounds(252, 119, 180, 36);
			getContentPane().add(lblDescrioDoPedido);
		}
		{
			JLabel lblCrusto = new JLabel("Custo");
			lblCrusto.setBounds(252, 205, 120, 20);
			getContentPane().add(lblCrusto);
		}
		{
			JLabel lblCustoTotalDos = new JLabel("Custo Total Dos Materiais:");
			lblCustoTotalDos.setBounds(252, 227, 150, 20);
			getContentPane().add(lblCustoTotalDos);
		}
		{
			tfCustoMateriais = new JTextField();
			tfCustoMateriais.addAncestorListener(new AncestorListener() {
				public void ancestorAdded(AncestorEvent arg0) {

					int x = 0;

					tfCustoMateriais.setText(String.valueOf(tabelaMateriais
							.getValueAt(x, 2)));

				}

				public void ancestorMoved(AncestorEvent arg0) {
				}

				public void ancestorRemoved(AncestorEvent arg0) {
				}
			});
			tfCustoMateriais.setText("0");
			tfCustoMateriais.setBounds(442, 227, 200, 20);
			getContentPane().add(tfCustoMateriais);
			tfCustoMateriais.setColumns(10);
		}
		{
			tfMaoDeObra = new JTextField();
			tfMaoDeObra.setText("0");
			tfMaoDeObra.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {

					tfcustopormetroquadrado.setText(String.valueOf((Double
							.parseDouble(tfCustoMateriais.getText()) + Double
							.parseDouble(tfMaoDeObra.getText()))
							/ o.getArea()));

				}
			});

			tfMaoDeObra.setBounds(442, 258, 200, 20);
			getContentPane().add(tfMaoDeObra);
			tfMaoDeObra.setColumns(10);
		}
		{
			JLabel lblMoDeObra = new JLabel("M\u00E3o de Obra:");
			lblMoDeObra.setBounds(252, 258, 75, 20);
			getContentPane().add(lblMoDeObra);
		}
		{
			JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o do Pedido");
			lblDescrio.setBounds(252, 333, 380, 20);
			getContentPane().add(lblDescrio);
		}
		{
			JLabel lblPagamento = new JLabel("Pagamento");
			lblPagamento.setBounds(664, 85, 90, 20);
			getContentPane().add(lblPagamento);
		}
		{
			JLabel lblFormaDePagamento = new JLabel("Forma de Pagamento:");
			lblFormaDePagamento.setBounds(664, 121, 130, 20);
			getContentPane().add(lblFormaDePagamento);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 122, 219, 343);
		getContentPane().add(scrollPane);
		{
			modeloJtable.addColumn("Nome");
			modeloJtable.addColumn("Qtd.");
			modeloJtable.addColumn("Preço");

		}
		tabelaMateriais = new JTable(modeloJtable) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		tabelaMateriais.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabelaMateriais.getColumnModel().getColumn(1).setPreferredWidth(1);
		tabelaMateriais.getColumnModel().getColumn(2).setPreferredWidth(1);

		tabelaMateriais.getTableHeader().setReorderingAllowed(false);

		scrollPane.setViewportView(tabelaMateriais);

		// carregar a lista de materiais

		try {

			// busca das informações
			String comando = "select orcamento_listaMaterial, orcamento_listaqtd from tbl_orcamento where orcamento_id = "
					+ o.getId() + "";

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			int cont = 0;

			// faz o select da lista de material

			// vai ser guardado os selectes com o nome do material
			// especifico

			// contador
			int i = 0;

			while (resultSet.next()) {

				String listamaterial = resultSet
						.getString("orcamento_listaMaterial");

				String listaqtd = resultSet.getString("orcamento_listaqtd");

				// carregou a string que veio com os nomes
				Scanner sc4 = new Scanner(listamaterial);

				Scanner sc5 = new Scanner(listaqtd);

				// adiciona na tabela central os itens dos modelo
				Scanner sc3 = new Scanner(listamaterial);

				while ((sc3.hasNextLine()) && (sc5.hasNextLine())) {

					modeloJtable.addRow(new Object[] { sc3.nextLine(),
							sc5.nextLine(), 0 });

					cont++;

				}
				ArrayList<String> comando2 = new ArrayList<String>();
				// cada linha, ele cria um select com UM nome de um
				// material da lista, guarda no vetor
				while ((sc4.hasNextLine())) {

					comando2.add("select material_custo from tbl_material inner join tbl_modelo on material_nome = '"
							+ sc4.nextLine() + "' group by material_id;");

					i++;
				}

				i = 0;

				for (String x : comando2) {

					resultSet = st.executeQuery(x.toString());

					while ((resultSet.next())) {

						modeloJtable.setValueAt(
								resultSet.getString("material_custo"), i, 2);

						i++;

					}

				}

				sc3.close();
				sc4.close();
				sc5.close();
			}

			st.close();
			con.close();

		} // fim do try
		catch (Exception item) {
			item.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Erro ao carregar a lista de material");
		}

		// calcula o custo dos materiais

		double x = 0;

		int i = 0;

		while (i != (modeloJtable.getRowCount())) {

			x += Double.parseDouble(String.valueOf(tabelaMateriais.getValueAt(
					i, 2)))
					* Double.parseDouble(String.valueOf(tabelaMateriais
							.getValueAt(i, 1)));

			i++;
		}

		JComboBox cbParcelamento = new JComboBox();
		cbParcelamento.setModel(new DefaultComboBoxModel(new String[] { "1",
				"2", "3", "4" }));
		cbParcelamento.setBounds(804, 151, 260, 20);
		getContentPane().add(cbParcelamento);

		JComboBox cbPagamento = new JComboBox();
		cbPagamento.setModel(new DefaultComboBoxModel(new String[] {
				"Dinheiro", "Cheque", "Cart\u00E3o" }));
		cbPagamento.setBounds(804, 119, 260, 20);
		getContentPane().add(cbPagamento);
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(252, 192, 400, 2);
			getContentPane().add(separator);
		}
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(252, 320, 402, 2);
			getContentPane().add(separator);
		}
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(652, 320, 402, 2);
			getContentPane().add(separator);
		}
		{
			JLabel lblParcelamento = new JLabel("Parcelamento:");
			lblParcelamento.setBounds(664, 151, 120, 20);
			getContentPane().add(lblParcelamento);
		}

		{
			JLabel lblDetalhes = new JLabel("Detalhes Adicionais");
			lblDetalhes.setBounds(664, 333, 120, 20);
			getContentPane().add(lblDetalhes);
		}
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(0, 502, 1074, 2);
			getContentPane().add(separator);
		}
		{
			JLabel lblJurosmensal = new JLabel("Juros (Total):");
			lblJurosmensal.setBounds(664, 182, 111, 20);
			getContentPane().add(lblJurosmensal);
		}
		{
			tfJuros = new JTextField();
			tfJuros.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

				}
			});
			tfJuros.setEditable(false);
			tfJuros.setBounds(804, 184, 260, 20);
			getContentPane().add(tfJuros);
			tfJuros.setColumns(10);
		}
		{
			JLabel lblValorFinal = new JLabel("Valor Final:");
			lblValorFinal.setBounds(664, 255, 90, 50);
			getContentPane().add(lblValorFinal);
		}

		{
			JSeparator separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setBounds(652, 71, 2, 433);
			getContentPane().add(separator);
		}

		JTextArea descricaoProduto = new JTextArea();

		descricaoProduto.setText(o.getNomedopedido());

		descricaoProduto.setBounds(396, 125, 219, 59);
		getContentPane().add(descricaoProduto);

		JTextArea descricaopedido = new JTextArea();
		descricaopedido.setBounds(262, 364, 360, 74);
		getContentPane().add(descricaopedido);

		{

		}

		tfValorFinal = new JTextField();
		tfValorFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		tfValorFinal.setEditable(false);
		tfValorFinal.setBounds(804, 268, 260, 20);
		getContentPane().add(tfValorFinal);
		tfValorFinal.setColumns(10);

		JButton btnFinalizarPedido = new JButton("Finalizar Pedido");

		JTextArea detalhesadicionais = new JTextArea();
		detalhesadicionais.setBounds(674, 364, 360, 127);
		getContentPane().add(detalhesadicionais);

		btnFinalizarPedido.setToolTipText("Finalizar Pedido");
		Image imgFinalizarPedido = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnFinalizarPedido.setIcon(new ImageIcon(imgFinalizarPedido));
		btnFinalizarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// formata data para padrão americano
				SimpleDateFormat Formatodataam = new SimpleDateFormat("yyyy/MM/dd");

				if (tfCustoMateriais.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Todos os campos obrigatórios devem ser preenchidos",
									"AVISO!", JOptionPane.WARNING_MESSAGE);
				} else if (tfcustopormetroquadrado.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Todos os campos obrigatórios devem ser preenchidos",
									"AVISO!", JOptionPane.WARNING_MESSAGE);

				} else if (tfValorFinal.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Todos os campos obrigatórios devem ser preenchidos",
									"AVISO!", JOptionPane.WARNING_MESSAGE);

				} else if (tfnome.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Todos os campos obrigatórios devem ser preenchidos",
									"AVISO!", JOptionPane.WARNING_MESSAGE);

				}

				try {


					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st;

					String comando = "UPDATE tbl_orcamento set cliente_id = "
							+ c.getId()
							+ " ,orcamento_descricaoproduto = '"
							+ descricaoProduto.getText()
							+ "', orcamento_detalesadicionais = '"
							+ detalhesadicionais.getText()
							+ "', orcamento_metodoPagamento = '"
							+ String.valueOf(cbPagamento.getSelectedItem())
							+ "', orcamento_precopormetroquadrado = "
							+ tfcustopormetroquadrado.getText()
							+ ", orcamento_valorMaoDeObra = "
							+ tfMaoDeObra.getText()
							+ ", orcamento_totalPrazo = "
							+ tfValorFinal.getText()
							+ ", orcamento_juros = "
							+ tfJuros.getText()
							+ ", orcamento_parcelamento = "
							+ Double.parseDouble(String.valueOf(cbParcelamento
									.getSelectedItem()))
							+ ", orcamento_valorTotal = "
							+ Double.parseDouble(tfValorFinal.getText())
							+ ", orcamento_descricaopedido = '"
							+ descricaopedido.getText()							
							+ "' where orcamento_id = " + o.getId() + ";";

					
					
					
					st = con.createStatement();
					st.execute(comando);

					JOptionPane.showMessageDialog(null,
							"Orçamento cadastrado com sucesso.");

					st.close();
					con.close();

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

			
			}

		});
		btnFinalizarPedido.setBounds(344, 530, 180, 20);
		getContentPane().add(btnFinalizarPedido);

		JButton btnCalcularValorFinal = new JButton("Calcular Valor Final");
		Image imgCalcular = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnCalcularValorFinal.setIcon(new ImageIcon(imgCalcular));
		btnCalcularValorFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Calculando o juros(simples).
				double m; // Montante
				double j; // Juros
				double p; // Capital
				double i; // Taxa de Juros Total (j(mensal) * número de
							// meses)
				double n; // Parcelas

				p = Double.parseDouble(tfMaoDeObra.getText())
						+ Double.parseDouble(tfCustoMateriais.getText());

				n = Double.parseDouble(String.valueOf(cbParcelamento
						.getSelectedItem()));

				// Juros de 10% ao mês.
				if (cbPagamento.getSelectedItem() == "Cheque") {
					i = 30 * n;

				} else if (cbPagamento.getSelectedItem() == "Cartão") {
					i = 20 * n;

				} else {
					i = 10 * n;

				}

				j = (p + i);

				m = p + j;

				if (cbParcelamento.getSelectedItem() == "1") {
					tfJuros.setText("0");
				} else {

					tfJuros.setText(String.valueOf(i));

				}
				// A vista, sem juros.

				p = Double.parseDouble(tfCustoMateriais.getText())
						+ Double.parseDouble(tfMaoDeObra.getText());
				m = p + Double.parseDouble(tfJuros.getText());

				if (cbParcelamento.getSelectedItem() == "1") {
					tfValorFinal.setText(String.valueOf(p));

				} else {
					// Com juros de parcelamento.
					tfValorFinal.setText(String.valueOf(m));

				}

			}
		});
		btnCalcularValorFinal.setBounds(804, 215, 260, 44);
		getContentPane().add(btnCalcularValorFinal);
		{
			JLabel lblCustoPorM = new JLabel("Custo por m\u00B2:");
			lblCustoPorM.setBounds(252, 291, 120, 14);
			getContentPane().add(lblCustoPorM);
		}
		{
			tfcustopormetroquadrado = new JTextField();
			tfcustopormetroquadrado.setEditable(false);

			tfcustopormetroquadrado.setBounds(442, 289, 200, 20);
			getContentPane().add(tfcustopormetroquadrado);
			tfcustopormetroquadrado.setColumns(10);
		}
		{
			JLabel lblCliente = new JLabel("Cliente");
			lblCliente.setBounds(252, 449, 46, 14);
			getContentPane().add(lblCliente);
		}
		{
			JLabel lblNome = new JLabel("Nome:");
			lblNome.setBounds(252, 471, 46, 14);
			getContentPane().add(lblNome);
		}
		{
			tfnome = new JTextField();
			// aqui!
			tfnome.setEditable(false);
			tfnome.setBounds(308, 471, 216, 20);
			getContentPane().add(tfnome);
			tfnome.setColumns(10);
		}

		JButton btnSelecionar = new JButton("Selecionar");
		Image imgSelecionar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSelecionar.setIcon(new ImageIcon(imgSelecionar));
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					JDSelecionarCodigoCliente dialog = new JDSelecionarCodigoCliente(
							c);
					dialog.setModal(true);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				tfnome.setText(c.getNome());

			}
		});
		btnSelecionar.setBounds(531, 468, 89, 23);
		getContentPane().add(btnSelecionar);

		JLabel lblNewLabel_1 = new JLabel("* - Campos Obrigat\u00F3rios");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(396, 32, 219, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("*");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBounds(292, 471, 18, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setBounds(396, 230, 18, 14);
		getContentPane().add(label_1);

		JLabel label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setBounds(396, 261, 18, 14);
		getContentPane().add(label_2);

		JLabel label_3 = new JLabel("*");
		label_3.setForeground(Color.RED);
		label_3.setBounds(785, 119, 18, 14);
		getContentPane().add(label_3);

		JLabel label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setBounds(785, 152, 18, 14);
		getContentPane().add(label_4);
		{
			JLabel lblR = new JLabel("R$");
			lblR.setHorizontalAlignment(SwingConstants.RIGHT);
			lblR.setBounds(406, 291, 34, 14);
			getContentPane().add(lblR);
		}
		{
			JLabel label_5 = new JLabel("R$");
			label_5.setHorizontalAlignment(SwingConstants.RIGHT);
			label_5.setBounds(406, 258, 34, 14);
			getContentPane().add(label_5);
		}
		{
			JLabel label_5 = new JLabel("R$");
			label_5.setHorizontalAlignment(SwingConstants.RIGHT);
			label_5.setBounds(406, 230, 34, 14);
			getContentPane().add(label_5);
		}
		{
			JLabel label_5 = new JLabel("R$");
			label_5.setHorizontalAlignment(SwingConstants.RIGHT);
			label_5.setBounds(769, 188, 34, 14);
			getContentPane().add(label_5);
		}
		{
			JLabel label_5 = new JLabel("R$");
			label_5.setHorizontalAlignment(SwingConstants.RIGHT);
			label_5.setBounds(769, 273, 34, 14);
			getContentPane().add(label_5);
		}

	}
}
