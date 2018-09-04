package view.jFrames.visualizar_registros;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modelHerancas.Pedido;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JDMateriaisaPedir extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfClienteNome;
	private JTextField tfPedido;
	private JTextField tfAltura;
	private JTextField tfComprimento;
	private JTextField tfMaoDeObra;
	private JTextField tfValorTotal;
	private JTable jtablemateriais;

	DefaultTableModel modelo = new DefaultTableModel();
	private JTextField tfpedidoid;
	private JTextField tfdatadaentrega;

	public JDMateriaisaPedir(Pedido p) {

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
				"/16px/materiais.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Materiais a Encomendar");
		setBounds(100, 100, 871, 592);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblMateriaisAPedir = new JLabel("Materiais a Pedir");
		Image imgMaterial = new ImageIcon(this.getClass().getResource(
				"/50px/materiais.png")).getImage();
		lblMateriaisAPedir.setIcon(new ImageIcon(imgMaterial));
		lblMateriaisAPedir.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMateriaisAPedir.setBounds(20, 11, 394, 58);
		contentPanel.add(lblMateriaisAPedir);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 80, 855, 2);
		contentPanel.add(separator);

		JLabel lblNome = new JLabel("Cliente:");
		lblNome.setBounds(20, 123, 46, 14);
		contentPanel.add(lblNome);

		tfClienteNome = new JTextField();
		tfClienteNome.setEditable(false);
		tfClienteNome.setBounds(144, 117, 270, 20);
		contentPanel.add(tfClienteNome);
		tfClienteNome.setColumns(10);

		JLabel lblDataEntrega = new JLabel("Data Entrega:");
		lblDataEntrega.setBounds(20, 180, 89, 14);
		contentPanel.add(lblDataEntrega);

		JLabel lblPedido = new JLabel("Pedido:");
		lblPedido.setBounds(20, 148, 74, 14);
		contentPanel.add(lblPedido);

		tfPedido = new JTextField();
		tfPedido.setEditable(false);
		tfPedido.setBounds(144, 145, 270, 20);
		contentPanel.add(tfPedido);
		tfPedido.setColumns(10);

		JLabel lblOramento = new JLabel("Materiais:");
		lblOramento.setBounds(464, 96, 89, 14);
		contentPanel.add(lblOramento);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(431, 80, 1, 418);
		contentPanel.add(separator_1);

		JLabel lblDescrioOramento = new JLabel(
				"Descri\u00E7\u00E3o Or\u00E7amento:");
		lblDescrioOramento.setBounds(19, 302, 134, 14);
		contentPanel.add(lblDescrioOramento);

		JLabel lblAltura = new JLabel("Altura:");
		lblAltura.setBounds(20, 207, 46, 14);
		contentPanel.add(lblAltura);

		tfAltura = new JTextField();
		tfAltura.setEditable(false);
		tfAltura.setBounds(144, 204, 84, 20);
		contentPanel.add(tfAltura);
		tfAltura.setColumns(10);

		JLabel lblComprimento = new JLabel("Comprimento:");
		lblComprimento.setBounds(235, 207, 89, 14);
		contentPanel.add(lblComprimento);

		tfComprimento = new JTextField();
		tfComprimento.setEditable(false);
		tfComprimento.setBounds(319, 205, 95, 20);
		contentPanel.add(tfComprimento);
		tfComprimento.setColumns(10);

		JLabel lblDetalhes = new JLabel("Detalhes:");
		lblDetalhes.setBounds(20, 388, 89, 14);
		contentPanel.add(lblDetalhes);

		JLabel lblMoDeObra = new JLabel("M\u00E3o de Obra:");
		lblMoDeObra.setBounds(20, 233, 89, 14);
		contentPanel.add(lblMoDeObra);

		tfMaoDeObra = new JTextField();
		tfMaoDeObra.setEditable(false);
		tfMaoDeObra.setBounds(144, 230, 270, 20);
		contentPanel.add(tfMaoDeObra);
		tfMaoDeObra.setColumns(10);

		JLabel lblValorTotal = new JLabel("Valor Total:");
		lblValorTotal.setBounds(20, 264, 89, 14);
		contentPanel.add(lblValorTotal);

		tfValorTotal = new JTextField();
		tfValorTotal.setEditable(false);
		tfValorTotal.setBounds(144, 261, 270, 20);
		contentPanel.add(tfValorTotal);
		tfValorTotal.setColumns(10);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(-1, 502, 855, 2);
		contentPanel.add(separator_2);

		JDateChooser dateChooserdataencomenda = new JDateChooser();
		dateChooserdataencomenda.setBounds(144, 463, 270, 20);
		contentPanel.add(dateChooserdataencomenda);

		JButton btnencomendar = new JButton("Encomenda realizada");
		btnencomendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String comando = "UPDATE tbl_pedido SET pedido_materiaisencomendados = 'Sim', pedido_nivel = 3 WHERE pedido_id = "
						+ p.getId() + ";";
				try {

					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					st.execute(comando);

					// fala q executou co sucesso
					JOptionPane.showMessageDialog(null,
							"Materiais encomendados com sucesso");

					st.close();
					con.close();

					dispose();

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
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnencomendar.setIcon(new ImageIcon(imgSalvar));
		btnencomendar.setBounds(153, 515, 171, 23);
		contentPanel.add(btnencomendar);
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}
		});
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		btnCancelar.setBounds(517, 515, 171, 23);
		contentPanel.add(btnCancelar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(464, 120, 365, 320);
		contentPanel.add(scrollPane);

		modelo.addColumn("Cod.");
		modelo.addColumn("Nome");
		modelo.addColumn("QTD");

		jtablemateriais = new JTable(modelo) {

			// torno as céculas não editaveis da table acima /\
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		jtablemateriais.getColumnModel().getColumn(0).setPreferredWidth(1);
		jtablemateriais.getColumnModel().getColumn(1).setPreferredWidth(170);
		jtablemateriais.getColumnModel().getColumn(2).setPreferredWidth(1);

		scrollPane.setViewportView(jtablemateriais);

		JButton btnImprimirLista = new JButton("Imprimir Lista de materiais");
		Image imgImprimir = new ImageIcon(this.getClass().getResource(
				"/16px/imprimir.png")).getImage();
		btnImprimirLista.setIcon(new ImageIcon(imgImprimir));
		btnImprimirLista.setBounds(336, 515, 171, 23);
		contentPanel.add(btnImprimirLista);

		JTextArea tfdescricao = new JTextArea();
		tfdescricao.setEditable(false);
		tfdescricao.setBounds(144, 302, 270, 64);
		contentPanel.add(tfdescricao);

		JTextArea tfdetalhes = new JTextArea();
		tfdetalhes.setEditable(false);
		tfdetalhes.setBounds(144, 388, 270, 64);
		contentPanel.add(tfdetalhes);

		JLabel lblCodDoPedido = new JLabel("Cod. do pedido:");
		lblCodDoPedido.setBounds(20, 93, 110, 14);
		contentPanel.add(lblCodDoPedido);

		tfpedidoid = new JTextField();
		tfpedidoid.setEditable(false);
		tfpedidoid.setBounds(144, 93, 270, 20);
		contentPanel.add(tfpedidoid);
		tfpedidoid.setColumns(10);

		JLabel lblDataDaEncomenda = new JLabel("Data da encomenda:");
		lblDataDaEncomenda.setBounds(20, 467, 120, 14);
		contentPanel.add(lblDataDaEncomenda);

		tfdatadaentrega = new JTextField();
		tfdatadaentrega.setEditable(false);
		tfdatadaentrega.setBounds(144, 177, 270, 20);
		contentPanel.add(tfdatadaentrega);
		tfdatadaentrega.setColumns(10);

		JLabel lblR = new JLabel("R$");
		lblR.setBounds(124, 233, 29, 14);
		contentPanel.add(lblR);

		JLabel label = new JLabel("R$");
		label.setBounds(124, 264, 29, 14);
		contentPanel.add(label);

		// tfpedidoid.setText(p.);

		// carregar a tela

		try {

			SimpleDateFormat Formatodatabr = new SimpleDateFormat("dd/MM/yyyy");

			Connection con = ConexaoMySQL.getConexaoMySQL();

			java.sql.Statement st = con.createStatement();

			String comando = "select pedido_id, cliente_nome, cliente_sobrenome, pedido_dataentrega, pedido_nome, orcamento_listaMaterial, orcamento_listaqtd, orcamento_detalesadicionais,"
					+ "orcamento_descricaoproduto,orcamento_descricaopedido,orcamento_altura,orcamento_largura, orcamento_valorMaoDeObra, orcamento_valorTotal from tbl_pedido inner join tbl_orcamento on tbl_pedido.orcamento_id = tbl_orcamento.orcamento_id "
					+ "inner join tbl_cliente on tbl_pedido.cliente_id = tbl_cliente.cliente_id  where pedido_id = "
					+ p.getId() + "";

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				// carrega os valores para os tfs

				tfClienteNome.setText(resultSet.getString("cliente_nome")
						+ resultSet.getString("cliente_sobrenome"));

				tfPedido.setText(resultSet.getString("pedido_nome"));

				tfdatadaentrega.setText(String.valueOf(Formatodatabr
						.format(java.sql.Date.valueOf(resultSet
								.getString("pedido_dataentrega")))));

				tfpedidoid.setText(String.valueOf(p.getId()));

				tfAltura.setText(resultSet.getString("orcamento_altura"));
				tfComprimento.setText(resultSet.getString("orcamento_largura"));

				tfValorTotal.setText(resultSet
						.getString("orcamento_valorTotal"));

				tfMaoDeObra.setText(resultSet
						.getString("orcamento_valorMaoDeObra"));

				tfdetalhes.setText(resultSet
						.getString("orcamento_detalesadicionais"));
				tfdescricao.setText(resultSet
						.getString("orcamento_descricaopedido"));

				// carregou a string que veio com os nomes
				Scanner sc4 = new Scanner(
						resultSet.getString("orcamento_listamaterial"));
				Scanner sc3 = new Scanner(
						resultSet.getString("orcamento_listaqtd"));
				// da para a tabela
				while ((sc3.hasNextLine()) && (sc4.hasNextLine())) {

					modelo.addRow(new Object[] { 0, sc4.nextLine(),
							sc3.nextLine() });

				}

				sc3.close();
				sc4.close();

				// adiciona uma linha na tabela referente a busca
				// modelo.addRow(new Object[] { cod, nome });
			}
			// fim da busaca da tabela pedido

			st.close();
			con.close();
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null,
					"Falha no carregamento dos pedidos");
			erro.printStackTrace();
		}

	}
}
