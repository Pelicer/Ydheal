package view.jDialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Cliente;
import view.jFrames.cadastros.JFCClientes;

@SuppressWarnings("serial")
public class JDSelecionarBuscaClientes extends JDialog {

	Cliente c = new Cliente();

	int cont = 0;

	private DefaultTableModel ModeloJTCli = new DefaultTableModel();
	private JPanel contentPane;
	private JTextField tfcodigo;
	private JTable tabletbl_clientes;
	private JTextField tfnome;
	private JTextField tfendereco;
	private JTextField tfrg;
	private JTextField tfcpf;

	public JDSelecionarBuscaClientes(Cliente c) {

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/cliente.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 992, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setLayout(null);
		contentPane.add(panel);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(0, 0, 966, 88);
		panel.add(panel_2);

		JLabel label_6 = new JLabel("C\u00F3digo:");
		label_6.setBounds(207, 11, 55, 14);
		panel_2.add(label_6);

		tfcodigo = new JTextField();
		tfcodigo.setColumns(10);
		tfcodigo.setBounds(280, 8, 86, 20);
		panel_2.add(tfcodigo);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(376, 11, 46, 14);
		panel_2.add(lblNome);

		tfnome = new JTextField();
		tfnome.setBounds(414, 8, 202, 20);
		panel_2.add(tfnome);
		tfnome.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(207, 45, 67, 14);
		panel_2.add(lblEndereo);

		tfendereco = new JTextField();
		tfendereco.setBounds(280, 42, 336, 20);
		panel_2.add(tfendereco);
		tfendereco.setColumns(10);

		JLabel lblClientes = new JLabel("Clientes");
		// Adicionando imagem.
		Image imgCliente = new ImageIcon(this.getClass().getResource(
				"/50px/cliente.png")).getImage();
		lblClientes.setIcon(new ImageIcon(imgCliente));
		lblClientes.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClientes.setBounds(10, 6, 343, 79);
		panel_2.add(lblClientes);

		JLabel lblRg = new JLabel("RG:");
		lblRg.setBounds(647, 11, 46, 14);
		panel_2.add(lblRg);

		tfrg = new JTextField();
		tfrg.setBounds(703, 8, 121, 20);
		panel_2.add(tfrg);
		tfrg.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(647, 45, 46, 14);
		panel_2.add(lblCpf);

		tfcpf = new JTextField();
		tfcpf.setBounds(703, 42, 121, 20);
		panel_2.add(tfcpf);
		tfcpf.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 88, 966, 592);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JButton Novo = new JButton("Novo");
		// Adicionando imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		Novo.setIcon(new ImageIcon(imgNovo));
		Novo.setBounds(208, 11, 116, 23);
		panel_1.add(Novo);
		Novo.setToolTipText("Criar Novo Registro");

		JButton Filtrar = new JButton("Filtrar");
		// Adicionando imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		Filtrar.setIcon(new ImageIcon(imgFiltrar));
		Filtrar.setBounds(334, 11, 116, 23);
		panel_1.add(Filtrar);
		Filtrar.setToolTipText("Filtrar Registros");

		JButton Limpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		Limpar.setIcon(new ImageIcon(imgLimpar));
		Limpar.setBounds(460, 11, 116, 23);
		panel_1.add(Limpar);
		Limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfcodigo.setText("");
				tfcpf.setText("");
				tfendereco.setText("");
				tfnome.setText("");
				tfrg.setText("");
			}
		});
		Limpar.setToolTipText("Limpar Filtros");
		JLabel lblregistrosencontrados = new JLabel("0");
		lblregistrosencontrados.setBounds(740, 45, 46, 14);
		panel_1.add(lblregistrosencontrados);
		lblregistrosencontrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblregistrosencontrados.setForeground(new Color(0, 128, 0));

		Novo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setModal(false);

				try {
					JFCClientes frame = new JFCClientes();
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

				while (tabletbl_clientes.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_clientes.getModel())
							.removeRow(0);
				}

				Carregar_tela_cliente();

				lblregistrosencontrados.setText(String.valueOf(cont));

			}
		});
		JLabel label = new JLabel("Registros Encontrados");
		label.setBounds(793, 45, 141, 14);
		panel_1.add(label);

		Filtrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// ifs com um campo

				if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText() + ";";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_endereco like '%"
							+ tfendereco.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				// fim dos ifs com um campo

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_nome like '%"
							+ tfnome.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_rg like '"
							+ tfrg.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_endereco like '%"
							+ tfendereco.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_cpf like '"
							+ tfcpf.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_cpf like '"
							+ tfcpf.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_cpf like '"
							+ tfcpf.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%' and cliente_nome like '%"
							+ tfnome.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%'  and cliente_cpf like '"
							+ tfcpf.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%'  and cliente_endereco like '%"
							+ tfendereco.getText() + "%'  ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%' and cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_rg like '"
							+ tfrg.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%' and cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_nome like '%"
							+ tfnome.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%'  and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%'  and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%'  and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_cpf like '"
							+ tfcpf.getText()
							+ "%'  and cliente_nome like '%"
							+ tfnome.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_rg like '"
							+ tfrg.getText()
							+ "%'   and cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText()
							+ "%'   and cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText()
							+ "%'   and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'  and cliente_id = "
							+ tfcodigo.getText() + " ; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText()
							+ "%'    and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%' and cliente_id = "
							+ tfcodigo.getText() + " ; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& !(tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'  and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%' and cliente_id = "
							+ tfcodigo.getText() + " ; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& !(tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'  and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%' and cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_rg like '"
							+ tfrg.getText() + "%' ; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

			}// fim do evento

		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 946, 511);
		panel_1.add(scrollPane);

		// cria os titulos

		{
			ModeloJTCli.addColumn("Cod.");
			ModeloJTCli.addColumn("Nome.");
			ModeloJTCli.addColumn("Endereço");
			ModeloJTCli.addColumn("Número");
			ModeloJTCli.addColumn("Telefone");
			ModeloJTCli.addColumn("E-mail");
		}

		tabletbl_clientes = new JTable(ModeloJTCli) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		// impede que as colunas sejam reordemadas
		tabletbl_clientes.getTableHeader().setReorderingAllowed(false);

		scrollPane.setViewportView(tabletbl_clientes);

		// da os tamanhos para as colunas
		tabletbl_clientes.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabletbl_clientes.getColumnModel().getColumn(1).setPreferredWidth(1);
		tabletbl_clientes.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabletbl_clientes.getColumnModel().getColumn(3).setPreferredWidth(1);
		tabletbl_clientes.getColumnModel().getColumn(4).setPreferredWidth(1);
		tabletbl_clientes.getColumnModel().getColumn(5).setPreferredWidth(10);

		// Detecção de clique duplo.
		tabletbl_clientes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					String id = String.valueOf(ModeloJTCli.getValueAt(
							tabletbl_clientes.getSelectedRow(), 0));

					String nome = String.valueOf(ModeloJTCli.getValueAt(
							tabletbl_clientes.getSelectedRow(), 1));

					String endereco = String.valueOf(ModeloJTCli.getValueAt(
							tabletbl_clientes.getSelectedRow(), 2));

					String numero = String.valueOf(ModeloJTCli.getValueAt(
							tabletbl_clientes.getSelectedRow(), 3));

					String telefone = String.valueOf(ModeloJTCli.getValueAt(
							tabletbl_clientes.getSelectedRow(), 4));

					String comando = "select cliente_bairro from tbl_cliente where cliente_id = "
							+ id + ";";

					// Selecionando bairro do banco, pois não está na tabela.
					try {

						Class.forName("com.mysql.jdbc.Driver");
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();
						java.sql.Statement st = con.createStatement();
						ResultSet resultSet = st.executeQuery(comando);
						while (resultSet.next()) {

							String bairro = resultSet
									.getString("cliente_bairro");
							c.setBairro(bairro);
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

					c.setId(Integer.parseInt(id));
					c.setNome(nome);
					c.setEndereco(endereco);
					c.setNumero(numero);
					c.setTelefone(telefone);

					dispose();

				}
			}
		});

		// carrega a tela ao abrir

		Carregar_tela_cliente();

		lblregistrosencontrados.setText(String.valueOf(cont));

		JButton btnSelecionar = new JButton("Selecionar Cliente");
		Image imgSelecionar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSelecionar.setIcon(new ImageIcon(imgSelecionar));
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String id = String.valueOf(ModeloJTCli.getValueAt(
						tabletbl_clientes.getSelectedRow(), 0));

				String nome = String.valueOf(ModeloJTCli.getValueAt(
						tabletbl_clientes.getSelectedRow(), 1));

				String endereco = String.valueOf(ModeloJTCli.getValueAt(
						tabletbl_clientes.getSelectedRow(), 2));

				String numero = String.valueOf(ModeloJTCli.getValueAt(
						tabletbl_clientes.getSelectedRow(), 3));

				String telefone = String.valueOf(ModeloJTCli.getValueAt(
						tabletbl_clientes.getSelectedRow(), 4));

				String comando = "select cliente_bairro from tbl_cliente where cliente_id = "
						+ id + ";";

				// Selecionando 'bairro' do banco, pois não está na tabela.
				try {

					Class.forName("com.mysql.jdbc.Driver");
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					java.sql.Statement st = con.createStatement();
					ResultSet resultSet = st.executeQuery(comando);
					while (resultSet.next()) {

						String bairro = resultSet.getString("cliente_bairro");
						c.setBairro(bairro);
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

				c.setId(Integer.parseInt(id));
				c.setNome(nome);
				c.setEndereco(endereco);
				c.setNumero(numero);
				c.setTelefone(telefone);

				JOptionPane.showMessageDialog(null, "Cliente selecionado!");

				dispose();

			}
		});
		btnSelecionar.setBounds(624, 11, 151, 23);
		panel_1.add(btnSelecionar);

	}// fim da classe da tela

	public void Cod(String comando) {
		cont = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("cliente_id");
				String nome = resultSet.getString("cliente_nome");
				String sobrenome = resultSet.getString("cliente_sobrenome");

				String numero = resultSet.getString("cliente_numero");

				String endereco = resultSet.getString("cliente_endereco");

				String telefone1 = resultSet.getString("cliente_telefone1");

				String email = resultSet.getString("cliente_email");

				nome = (nome + " " + sobrenome);

				cont = (cont + 1);

				ModeloJTCli.addRow(new Object[] { id, nome, endereco, numero,
						telefone1, email });

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
		} catch (IllegalArgumentException illegalArgument) {
			JOptionPane
					.showMessageDialog(
							null,
							"Algo deu errado.. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
							"Erro!", JOptionPane.ERROR_MESSAGE);
		} catch (Exception eArgument) {
			JOptionPane
					.showMessageDialog(
							null,
							"Algo deu errado.. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
							"Erro!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void Carregar_tela_cliente() {

		String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
				+ "cliente_numero, cliente_endereco, "
				+ "cliente_telefone1, cliente_numero,"
				+ " cliente_email from tbl_cliente;";

		cont = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("cliente_id");
				String nome = resultSet.getString("cliente_nome");
				String sobrenome = resultSet.getString("cliente_sobrenome");

				String numero = resultSet.getString("cliente_numero");

				String endereco = resultSet.getString("cliente_endereco");

				String telefone1 = resultSet.getString("cliente_telefone1");

				String email = resultSet.getString("cliente_email");

				nome = (nome + " " + sobrenome);

				cont = (cont + 1);

				ModeloJTCli.addRow(new Object[] { id, nome, endereco, numero,
						telefone1, email });

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
