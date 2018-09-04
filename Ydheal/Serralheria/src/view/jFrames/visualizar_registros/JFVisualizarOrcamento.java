package view.jFrames.visualizar_registros;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.IllegalFormatException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Orcamento;

@SuppressWarnings("serial")
public class JFVisualizarOrcamento extends JFrame {

	private JPanel contentPane;
	private JTextField tfCliente;
	private JTextField tfValorTotal;
	private JTextField tfModelo;
	private JTextField tfMaoDeObra;
	private JTable tabletbl_materiais;

	DefaultTableModel modelo = new DefaultTableModel();
	private JTextField tfpagamento;
	private JTextField tfcustometroquadrado;
	private JTextField tflargura;
	private JTextField tfaltura;
	private JTextField tfParcelamento;

	@SuppressWarnings("resource")
	public JFVisualizarOrcamento(Orcamento o) {

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
				"/16px/orcamento.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Visualiza\u00E7\u00E3o do Or\u00E7amento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 809, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(481, 79, 299, 261);
		contentPane.add(scrollPane);

		modelo.addColumn("Nome");
		modelo.addColumn("Quantidade");

		tabletbl_materiais = new JTable(modelo) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane.setViewportView(tabletbl_materiais);

		JLabel lblOramento = new JLabel("Or\u00E7amento");
		// Adicionando imagem.
		Image imgOrcamento = new ImageIcon(this.getClass().getResource(
				"/50px/orcamento.png")).getImage();
		lblOramento.setIcon(new ImageIcon(imgOrcamento));
		lblOramento.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOramento.setBounds(10, 0, 394, 58);
		contentPane.add(lblOramento);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 69, 793, 2);
		contentPane.add(separator);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 82, 71, 14);
		contentPane.add(lblCliente);

		tfCliente = new JTextField();
		tfCliente.setEditable(false);
		tfCliente.setBounds(113, 79, 277, 20);
		contentPane.add(tfCliente);
		tfCliente.setColumns(10);

		JLabel lblValorTotal = new JLabel("Valor Total:");
		lblValorTotal.setBounds(211, 311, 71, 14);
		contentPane.add(lblValorTotal);

		tfValorTotal = new JTextField();
		tfValorTotal.setEditable(false);
		tfValorTotal.setBounds(298, 308, 92, 20);
		contentPane.add(tfValorTotal);
		tfValorTotal.setColumns(10);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(10, 110, 93, 14);
		contentPane.add(lblModelo);

		tfModelo = new JTextField();
		tfModelo.setEditable(false);
		tfModelo.setBounds(113, 107, 277, 20);
		contentPane.add(tfModelo);
		tfModelo.setColumns(10);

		JTextArea tfdetalhesdopedido = new JTextArea();
		tfdetalhesdopedido.setEditable(false);
		tfdetalhesdopedido.setBounds(113, 138, 277, 66);
		contentPane.add(tfdetalhesdopedido);

		JTextArea tfdetalhesadicionais = new JTextArea();
		tfdetalhesadicionais.setEditable(false);
		tfdetalhesadicionais.setBounds(113, 210, 277, 66);
		contentPane.add(tfdetalhesadicionais);

		JLabel lblListaDeMateriais = new JLabel("Materiais:");
		lblListaDeMateriais.setBounds(400, 76, 76, 14);
		contentPane.add(lblListaDeMateriais);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setEnabled(false);
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Orcamento o = new Orcamento();

				String comando = "UPDATE tbl_orcamento set orcamento_valorTotal ="
						+ tfValorTotal.getText()
						+ ", orcamento_metodoPagamento = '"
						+ tfpagamento.getText()
						+ "', orcamento_valorMaoDeObra ="
						+ tfMaoDeObra.getText()
						+ ", orcamento_parcelamento ="
						+ tfParcelamento.getText()
						+ ", orcamento_largura="
						+ tflargura.getText()
						+ ", orcamento_altura="
						+ tfaltura.getText()
						+ ", orcamento_area="
						+ Double.parseDouble(tfaltura.getText())
								* Double.parseDouble(tflargura.getText())
						+ ", orcamento_precopormetroquadrado ="
						+ tfcustometroquadrado.getText()
						+ ", orcamento_descricaopedido ='"
						+ tfdetalhesdopedido.getText()
						+ "', orcamento_detalesadicionais = '"
						+ tfdetalhesadicionais.getText()
						+ "' WHERE orcamento_id =" + o.getId() + ";";
				try {
					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					st.execute(comando);

					// fala q executou co sucesso
					JOptionPane.showMessageDialog(null,
							"Dados alterados com sucesso!");

					st.close(); // fecha a concxão c
					con.close();

					// Campos não editáveis.

					dispose();

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
					erroFormato.printStackTrace();
				}

				catch (ClassNotFoundException erroJDBC) {
					JOptionPane
							.showMessageDialog(
									null,
									"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
					erroJDBC.printStackTrace();
				} catch (IllegalArgumentException illegalArgument) {
					JOptionPane
							.showMessageDialog(
									null,
									"Algo deu errado.. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
									"Erro!", JOptionPane.ERROR_MESSAGE);
					illegalArgument.printStackTrace();
				} catch (Exception eArgument) {
					JOptionPane
							.showMessageDialog(
									null,
									"Algo deu errado. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
									"Erro!", JOptionPane.ERROR_MESSAGE);
					eArgument.printStackTrace();
				}

			}

		});
		btnSalvar.setBounds(10, 431, 234, 23);
		contentPane.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setEnabled(false);
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();

			}
		});
		btnCancelar.setBounds(242, 431, 234, 23);
		contentPane.add(btnCancelar);

		JButton btnAlterar = new JButton("Alterar");
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imgAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnSalvar.setEnabled(true);
				btnCancelar.setEnabled(true);
				tfaltura.setEditable(true);
				tfcustometroquadrado.setEditable(true);
				tflargura.setEditable(true);
				tfMaoDeObra.setEditable(true);
				tfModelo.setEditable(true);
				tfpagamento.setEditable(true);
				tfParcelamento.setEditable(true);
				tfValorTotal.setEditable(true);

			}
		});
		btnAlterar.setBounds(10, 453, 466, 23);
		contentPane.add(btnAlterar);

		JLabel lblMoDeObra = new JLabel("M\u00E3o de Obra:");
		lblMoDeObra.setBounds(10, 314, 93, 14);
		contentPane.add(lblMoDeObra);

		tfMaoDeObra = new JTextField();
		tfMaoDeObra.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				tfValorTotal.setText(String.valueOf(o.getValorTotal()
						- o.getMaoDeObra()
						+ Integer.parseInt(tfMaoDeObra.getText())));
				o.setMaoDeObra(Double.parseDouble(tfMaoDeObra.getText()));
			}
		});
		tfMaoDeObra.setEditable(false);
		tfMaoDeObra.setBounds(105, 311, 96, 20);
		contentPane.add(tfMaoDeObra);
		tfMaoDeObra.setColumns(10);

		JLabel lblMtodoDePagamento = new JLabel("Pagamento:");
		lblMtodoDePagamento.setBounds(10, 342, 71, 14);
		contentPane.add(lblMtodoDePagamento);

		tfpagamento = new JTextField();
		tfpagamento.setEditable(false);
		tfpagamento.setBounds(105, 339, 96, 20);
		contentPane.add(tfpagamento);
		tfpagamento.setColumns(10);

		JLabel lblHistrico = new JLabel("Hist\u00F3rico:");
		lblHistrico.setBounds(400, 351, 89, 14);
		contentPane.add(lblHistrico);

		JTextArea tfhistorico = new JTextArea();
		tfhistorico.setEditable(false);
		tfhistorico.setBounds(481, 351, 299, 125);
		contentPane.add(tfhistorico);

		JLabel lblDetalhesDoPedido = new JLabel("Detalhes do pedido:");
		lblDetalhesDoPedido.setBounds(10, 135, 107, 14);
		contentPane.add(lblDetalhesDoPedido);

		JLabel lblDetalhesAdicionais = new JLabel("Detalhes Adicionais:");
		lblDetalhesAdicionais.setBounds(7, 215, 110, 14);
		contentPane.add(lblDetalhesAdicionais);

		JLabel lblCustoPorM = new JLabel("Custo por m\u00B2:");
		lblCustoPorM.setBounds(10, 370, 93, 14);
		contentPane.add(lblCustoPorM);

		tfcustometroquadrado = new JTextField();
		tfcustometroquadrado.setEditable(false);
		tfcustometroquadrado.setBounds(105, 367, 285, 20);
		contentPane.add(tfcustometroquadrado);
		tfcustometroquadrado.setColumns(10);

		JLabel lblLargura = new JLabel("Largura:");
		lblLargura.setBounds(10, 406, 71, 14);
		contentPane.add(lblLargura);

		tflargura = new JTextField();
		tflargura.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				tfcustometroquadrado.setText(String.valueOf(Integer
						.parseInt(tfaltura.getText())
						* Integer.parseInt(tflargura.getText())
						/ Integer.parseInt(tfValorTotal.getText())));
			}
		});
		tflargura.setEditable(false);
		tflargura.setBounds(105, 400, 96, 20);
		contentPane.add(tflargura);
		tflargura.setColumns(10);

		JLabel lblAltura = new JLabel("Altura:");
		lblAltura.setBounds(211, 406, 46, 14);
		contentPane.add(lblAltura);

		tfaltura = new JTextField();
		tfaltura.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				tfcustometroquadrado.setText(String.valueOf(Integer
						.parseInt(tfaltura.getText())
						* Integer.parseInt(tflargura.getText())
						/ Integer.parseInt(tfValorTotal.getText())));
			}
		});
		tfaltura.setEditable(false);
		tfaltura.setColumns(10);
		tfaltura.setBounds(298, 403, 92, 20);
		contentPane.add(tfaltura);

		tfParcelamento = new JTextField();
		tfParcelamento.setEditable(false);
		tfParcelamento.setBounds(298, 339, 92, 20);
		contentPane.add(tfParcelamento);
		tfParcelamento.setColumns(10);

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			java.sql.Statement st = con.createStatement();

			String comando = "select pedido_historico, pedido_nome, pedido_dataentrega from tbl_pedido where orcamento_id = "
					+ o.getId() + ";";

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				tfhistorico.setText(resultSet.getString("pedido_historico"));
				tfModelo.setText(resultSet.getString("pedido_nome"));
			}

			// Seleção de ID do cliente.
			String comandoCliente = "select cliente_id from tbl_orcamento where orcamento_id = "
					+ o.getId() + ";";

			resultSet = st.executeQuery(comandoCliente);

			while (resultSet.next()) {
				o.setClienteId(resultSet.getInt("cliente_id"));
			}

			comando = "SELECT cliente_nome, cliente_sobrenome FROM tbl_cliente WHERE cliente_id = "
					+ o.getClienteId() + ";";

			// Selecionando nome do cliente.
			resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String nome = resultSet.getString("cliente_nome");

				String sobrenome = resultSet.getString("cliente_sobrenome");

				tfCliente.setText(nome + " " + sobrenome);

			}

			// Parcelamento.
			String parcelamento = "select orcamento_parcelamento from tbl_orcamento where orcamento_id = "
					+ o.getId() + ";";

			resultSet = st.executeQuery(parcelamento);

			String p = "";

			while (resultSet.next()) {

				p = String.valueOf(resultSet
						.getDouble("orcamento_parcelamento"));

			}

			// Modelo.
			String modelo = "select modelo_id from tbl_orcamento where orcamento_id = "
					+ o.getId() + ";";

			resultSet = st.executeQuery(modelo);

			int m = 0;

			while (resultSet.next()) {

				m = resultSet.getInt("modelo_id");

			}

			tfModelo.setText(String.valueOf(m));
			tfParcelamento.setText(p);
			st.close();
			con.close();

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
			erroFormato.printStackTrace();
		}

		catch (ClassNotFoundException erroJDBC) {
			JOptionPane
					.showMessageDialog(
							null,
							"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
							"Aviso!", JOptionPane.WARNING_MESSAGE);
			erroJDBC.printStackTrace();
		} catch (IllegalArgumentException illegalArgument) {
			JOptionPane
					.showMessageDialog(
							null,
							"Algo deu errado.. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
							"Erro!", JOptionPane.ERROR_MESSAGE);
			illegalArgument.printStackTrace();
		} catch (Exception eArgument) {
			JOptionPane
					.showMessageDialog(
							null,
							"Algo deu errado. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
							"Erro!", JOptionPane.ERROR_MESSAGE);
			eArgument.printStackTrace();
		}

		// carregas o objeto modelos nos campos.

		tfdetalhesadicionais.setText(o.getDetalesadicionais());
		tfdetalhesdopedido.setText(o.getDescricaoproduto());
		tfaltura.setText(String.valueOf(o.getAltura()));
		tfcustometroquadrado.setText(String.valueOf(o
				.getPrecopormetroquadrado()));
		tflargura.setText(String.valueOf(o.getLargura()));
		tfMaoDeObra.setText(String.valueOf(o.getMaoDeObra()));
		tfValorTotal.setText(String.valueOf(o.getValorTotal()));
		tfpagamento.setText(o.getFormaPagamento());

		JLabel label = new JLabel("R$");
		label.setBounds(83, 370, 29, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("R$");
		label_1.setBounds(83, 314, 29, 14);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("R$");
		label_2.setBounds(276, 311, 24, 14);
		contentPane.add(label_2);

		JLabel lblParcelamento = new JLabel("Parcelamento:");
		lblParcelamento.setBounds(211, 342, 89, 14);
		contentPane.add(lblParcelamento);

		Scanner sc = new Scanner(o.getListaMaterial());
		Scanner sc2 = new Scanner(o.getListaqtd());

		while ((sc2.hasNextLine()) && (sc.hasNextLine())) {

			modelo.addRow(new Object[] { sc.nextLine(), sc2.nextLine(), });

		}

	}
}
