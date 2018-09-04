package view.jFrames.cadastros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelHerancas.Pedido;
import modelSuplerclasses.Cliente;
import modelSuplerclasses.Instalacao;
import view.jDialogs.JDSelecionarBuscaClientes;
import view.jDialogs.JDSelecionarPedidoInstalacao;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JFCInstalacao extends JFrame {

	Pedido pedido = new Pedido();
	Cliente cliente = new Cliente();
	Instalacao instalacao = new Instalacao();

	private JPanel contentPane;
	private JTextField tfCliente;
	private JTextField tfPedido;
	private JTextField tfObservacao;

	public JFCInstalacao() {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro da instalação será cancelada. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/agendar.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Cadastro de Instala\u00E7\u00E3o");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 424, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCadastroInstalacao = new JLabel(
				"Cadastro de Instala\u00E7\u00E3o:");
		// Adicionando Imagem.
		Image imbInstalacao = new ImageIcon(this.getClass().getResource(
				"/50px/agendar.png")).getImage();
		lblCadastroInstalacao.setIcon(new ImageIcon(imbInstalacao));
		lblCadastroInstalacao.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastroInstalacao.setBounds(10, 11, 343, 50);
		contentPane.add(lblCadastroInstalacao);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 72, 408, 2);
		contentPane.add(separator);

		JLabel lblCamposObrigatorios = new JLabel(
				"* - Campos Obrigat\u00F3rios.");
		lblCamposObrigatorios.setForeground(Color.RED);
		lblCamposObrigatorios.setBackground(Color.RED);
		lblCamposObrigatorios.setBounds(278, 32, 214, 14);
		contentPane.add(lblCamposObrigatorios);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 89, 55, 14);
		contentPane.add(lblCliente);

		JDateChooser dataInstalacao = new JDateChooser();
		dataInstalacao.setBounds(110, 170, 288, 20);
		contentPane.add(dataInstalacao);

		JSpinner spinnerHora = new JSpinner();
		spinnerHora.setBounds(110, 139, 145, 20);
		contentPane.add(spinnerHora);

		JSpinner spinnerMinuto = new JSpinner();
		spinnerMinuto.setBounds(264, 139, 134, 20);
		contentPane.add(spinnerMinuto);

		JButton btnSalvar = new JButton("Salvar");
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfCliente.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Nenhum campo obrigatório pode estar vazio. Verifique os dados inseridos.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
				} else if (tfPedido.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Nenhum campo obrigatório pode estar vazio. Verifique os dados inseridos.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
				} else if (dataInstalacao.getJCalendar() == null) {
					JOptionPane
							.showMessageDialog(
									null,
									"Nenhum campo obrigatório pode estar vazio. Verifique os dados inseridos.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
				} else if (spinnerHora.getValue() == null) {
					JOptionPane
							.showMessageDialog(
									null,
									"Nenhum campo obrigatório pode estar vazio. Verifique os dados inseridos.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
				} else if (spinnerMinuto.getValue() == null) {
					JOptionPane
							.showMessageDialog(
									null,
									"Nenhum campo obrigatório pode estar vazio. Verifique os dados inseridos.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
				}
				try {
					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					// formata data para padrão americano
					SimpleDateFormat Formatodataam = new SimpleDateFormat(
							"yyyy/MM/dd");

					instalacao.setInstalacaoClienteId(cliente.getId());
					instalacao.setInstalacaoPedidoId(pedido.getId());
					instalacao.setInstalacaoObservacao(tfObservacao.getText());

					st.execute("insert into tbl_instalacao (instalacao_dia, instalacao_horario, instalacao_observacao, cliente_id, pedido_id) values ('"
							+ Formatodataam.format(dataInstalacao.getDate())
							+ "',  '"
							+ String.valueOf(spinnerHora.getValue())
							+ ":"
							+ String.valueOf(spinnerMinuto.getValue())
							+ "', '"
							+ instalacao.getInstalacaoObservacao()
							+ "',"
							+ instalacao.getInstalacaoClienteId()
							+ ", "
							+ instalacao.getInstalacaoPedidoId() + ");");

					// fala que executou com sucesso
					JOptionPane.showMessageDialog(null,
							"Instalação cadastrada com sucesso!");

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
		btnSalvar.setToolTipText("Salvar Dados");
		btnSalvar.setBounds(32, 295, 118, 20);
		contentPane.add(btnSalvar);

		JButton btnLimpar = new JButton("Limpar");
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfCliente.setText("");
				tfObservacao.setText("");
				tfPedido.setText("");
				dataInstalacao.setCalendar(null);
				spinnerHora.setValue(null);
				spinnerMinuto.setValue(null);
				tfCliente.grabFocus();
			}
		});
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setBounds(150, 295, 118, 20);
		contentPane.add(btnLimpar);

		JButton btnCancelar = new JButton("Cancelar");
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcao = JOptionPane.showConfirmDialog(null,
						"Certeza de que deseja cancelar o cadastro?",
						"Confirmação", JOptionPane.YES_NO_OPTION);

				if (opcao == JOptionPane.YES_OPTION) {
					dispose();
				} else if (opcao == JOptionPane.NO_OPTION) {
					// Não faz nada.
				}
			}
		});
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setBounds(268, 295, 118, 20);
		contentPane.add(btnCancelar);

		tfCliente = new JTextField();
		tfCliente.setColumns(10);
		tfCliente.setBounds(110, 86, 145, 20);
		contentPane.add(tfCliente);

		JLabel label_19 = new JLabel("*");
		label_19.setForeground(Color.RED);
		label_19.setBackground(Color.RED);
		label_19.setBounds(90, 89, 25, 14);
		contentPane.add(label_19);

		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JDSelecionarBuscaClientes frame = new JDSelecionarBuscaClientes(
							cliente);
					frame.setModal(true);
					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				tfCliente.setText(cliente.getNome());
			}
		});
		Image imgSelecionar = new ImageIcon(this.getClass().getResource(
				"/16px/cliente.png")).getImage();
		btnSelecionar.setIcon(new ImageIcon(imgSelecionar));
		btnSelecionar.setBounds(264, 85, 134, 23);
		contentPane.add(btnSelecionar);

		tfPedido = new JTextField();
		tfPedido.setBounds(110, 112, 145, 20);
		contentPane.add(tfPedido);
		tfPedido.setColumns(10);

		JLabel lblPedido = new JLabel("Pedido:");
		lblPedido.setBounds(10, 114, 77, 14);
		contentPane.add(lblPedido);

		JLabel label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setBackground(Color.RED);
		label.setBounds(90, 114, 25, 14);
		contentPane.add(label);

		JButton button_3 = new JButton("Selecionar");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Selecionando pedido.
				try {
					JDSelecionarPedidoInstalacao frame = new JDSelecionarPedidoInstalacao(
							pedido, cliente);
					frame.setModal(true);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}

				tfPedido.setText(String.valueOf(pedido.getId()));
			}
		});
		Image imgSelecionar2 = new ImageIcon(this.getClass().getResource(
				"/16px/pedidos.png")).getImage();
		button_3.setIcon(new ImageIcon(imgSelecionar2));
		button_3.setBounds(264, 111, 134, 23);
		contentPane.add(button_3);

		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setBackground(Color.RED);
		label_1.setBounds(90, 170, 25, 14);
		contentPane.add(label_1);

		JLabel lblHorario = new JLabel("Hor\u00E1ro:");
		lblHorario.setBounds(10, 141, 65, 14);
		contentPane.add(lblHorario);

		JLabel lblDataVisita = new JLabel("Instala\u00E7\u00E3o Dia:");
		lblDataVisita.setBounds(10, 170, 105, 14);
		contentPane.add(lblDataVisita);

		JLabel label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setBackground(Color.RED);
		label_4.setBounds(90, 145, 25, 14);
		contentPane.add(label_4);

		JLabel lblObservao = new JLabel("Observa\u00E7\u00E3o:");
		lblObservao.setBounds(10, 195, 105, 14);
		contentPane.add(lblObservao);

		tfObservacao = new JTextField();
		tfObservacao.setBounds(110, 195, 288, 89);
		contentPane.add(tfObservacao);
		tfObservacao.setColumns(10);
	}


}
