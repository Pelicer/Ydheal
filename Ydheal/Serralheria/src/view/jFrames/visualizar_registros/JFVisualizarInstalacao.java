package view.jFrames.visualizar_registros;

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
import view.jDialogs.JDSelecionarCodigoPedido;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class JFVisualizarInstalacao extends JFrame {

	Cliente cliente = new Cliente();
	Pedido pedido = new Pedido();

	private JPanel contentPane;
	private JTextField tfObservacao;
	private JTextField tfPedido;
	private JTextField tfCliente;

	public JFVisualizarInstalacao(final Instalacao i) {
		
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
				"/16px/agendar.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Instala\u00E7\u00F5es");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 420, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblVisualizarInstalacao = new JLabel(
				"Visualizar Instala\u00E7\u00E3o");
		Image imgVisualizacao = new ImageIcon(this.getClass().getResource(
				"/50px/agendar.png")).getImage();
		lblVisualizarInstalacao.setIcon(new ImageIcon(imgVisualizacao));
		lblVisualizarInstalacao.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVisualizarInstalacao.setBounds(10, 0, 343, 50);
		contentPane.add(lblVisualizarInstalacao);

		JLabel lblCamposObrigatorios = new JLabel(
				"* - Campos Obrigat\u00F3rios.");
		lblCamposObrigatorios.setForeground(Color.RED);
		lblCamposObrigatorios.setBackground(Color.RED);
		lblCamposObrigatorios.setBounds(244, 21, 160, 14);
		contentPane.add(lblCamposObrigatorios);

		JButton btnCancelar = new JButton("Cancelar");
		// Adicionando Imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int resposta = JOptionPane.showConfirmDialog(null,
						"Deseja manter os dados como estão?",
						"Confirmar exclusões ", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					// Não faz nada.
				}
			}
		});
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setBounds(309, 284, 89, 20);
		contentPane.add(btnCancelar);

		JDateChooser dataInstalacao = new JDateChooser();
		dataInstalacao.getCalendarButton().setEnabled(false);
		dataInstalacao.setBounds(110, 159, 288, 20);
		contentPane.add(dataInstalacao);

		JSpinner spinnerMinuto = new JSpinner();
		spinnerMinuto.setEnabled(false);
		spinnerMinuto.setBounds(264, 128, 134, 20);
		contentPane.add(spinnerMinuto);

		JSpinner spinnerHora = new JSpinner();
		spinnerHora.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spinnerHora.setEnabled(false);
		spinnerHora.setBounds(110, 128, 145, 20);
		contentPane.add(spinnerHora);

		JButton btnSelecionarPedido = new JButton("Selecionar");
		btnSelecionarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JDSelecionarCodigoPedido frame = new JDSelecionarCodigoPedido(
							pedido);
					frame.setModal(true);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				} catch (Exception erroSelecionarPedido) {
					erroSelecionarPedido.printStackTrace();
				}

				tfPedido.setText(String.valueOf(pedido.getId()));
			}
		});
		// Adicionando Imagem.
		Image imgSelecionarPedido = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSelecionarPedido.setIcon(new ImageIcon(imgSelecionarPedido));
		btnSelecionarPedido.setEnabled(false);
		btnSelecionarPedido.setBounds(264, 100, 134, 23);
		contentPane.add(btnSelecionarPedido);

		JButton btnSelecionarCliente = new JButton("Selecionar");
		// Adicionando Imagem.
		Image imgSelecionarCliente = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSelecionarCliente.setIcon(new ImageIcon(imgSelecionarCliente));
		btnSelecionarCliente.addActionListener(new ActionListener() {
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
		btnSelecionarCliente.setEnabled(false);
		btnSelecionarCliente.setBounds(264, 74, 134, 23);
		contentPane.add(btnSelecionarCliente);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando Imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.setEnabled(false);
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
		btnLimpar.setBounds(210, 284, 89, 20);
		contentPane.add(btnLimpar);

		JButton btnSalvar = new JButton("Salvar");
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
					
					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					// formata data para padrão americano
					SimpleDateFormat Formatodataam = new SimpleDateFormat(
							"yyyy/MM/dd");

					i.setInstalacaoClienteId(cliente.getId());
					i.setInstalacaoPedidoId(pedido.getId());
					i.setInstalacaoObservacao(tfObservacao.getText());

					st.execute("UPDATE tbl_instalacao set instalacao_dia = '"
							+ Formatodataam.format(dataInstalacao.getDate())
							+ "', instalacao_horario = '"
							+ String.valueOf(spinnerHora.getValue()) + ":"
							+ String.valueOf(spinnerMinuto.getValue())
							+ "', instalacao_observacao = '"
							+ i.getInstalacaoObservacao() + "', cliente_id = "
							+ +i.getInstalacaoClienteId() + ", pedido_id = "
							+ i.getInstalacaoPedidoId()
							+ " WHERE instalacao_id = " + i.getInstalacaoId()
							+ ";");

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

			

			}
		});
		// Adicionando Imagem.
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.setEnabled(false);
		btnSalvar.setBounds(12, 284, 89, 20);
		contentPane.add(btnSalvar);

		JButton btnAlterar = new JButton("Alterar");
		// Adicionando Imagem.
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imgAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfCliente.setEditable(true);
				tfObservacao.setEditable(true);
				tfPedido.setEditable(true);
				spinnerHora.setEnabled(true);
				spinnerMinuto.setEnabled(true);
				dataInstalacao.setEnabled(true);
				btnSelecionarCliente.setEnabled(true);
				btnSelecionarPedido.setEnabled(true);
				btnLimpar.setEnabled(true);
				btnSalvar.setEnabled(true);
				btnAlterar.setEnabled(false);

			}
		});
		btnAlterar.setToolTipText("Alterar Dados");
		btnAlterar.setBounds(111, 284, 89, 20);
		contentPane.add(btnAlterar);

		tfObservacao = new JTextField();
		tfObservacao.setEditable(false);
		tfObservacao.setColumns(10);
		tfObservacao.setBounds(110, 184, 288, 89);
		contentPane.add(tfObservacao);

		tfPedido = new JTextField();
		tfPedido.setEditable(false);
		tfPedido.setColumns(10);
		tfPedido.setBounds(110, 101, 145, 20);
		contentPane.add(tfPedido);

		tfCliente = new JTextField();
		tfCliente.setEditable(false);
		tfCliente.setColumns(10);
		tfCliente.setBounds(110, 75, 145, 20);
		contentPane.add(tfCliente);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 61, 408, 2);
		contentPane.add(separator);

		JLabel label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setBackground(Color.RED);
		label_2.setBounds(90, 159, 25, 14);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("*");
		label_3.setForeground(Color.RED);
		label_3.setBackground(Color.RED);
		label_3.setBounds(90, 134, 25, 14);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setBackground(Color.RED);
		label_4.setBounds(90, 103, 25, 14);
		contentPane.add(label_4);

		JLabel label_5 = new JLabel("*");
		label_5.setForeground(Color.RED);
		label_5.setBackground(Color.RED);
		label_5.setBounds(90, 78, 25, 14);
		contentPane.add(label_5);

		JLabel lblObservacao = new JLabel("Observa\u00E7\u00E3o:");
		lblObservacao.setBounds(10, 184, 105, 14);
		contentPane.add(lblObservacao);

		JLabel lblDiaVisita = new JLabel("Dia da Visita:");
		lblDiaVisita.setBounds(10, 159, 80, 14);
		contentPane.add(lblDiaVisita);

		JLabel lblHorario = new JLabel("Hor\u00E1ro:");
		lblHorario.setBounds(10, 130, 65, 14);
		contentPane.add(lblHorario);

		JLabel lblPedido = new JLabel("Pedido:");
		lblPedido.setBounds(10, 103, 77, 14);
		contentPane.add(lblPedido);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 78, 55, 14);
		contentPane.add(lblCliente);

		// da os valores para os campos ao abrir a tela
		tfCliente.setText(String.valueOf(i.getInstalacaoClienteId()));
		tfObservacao.setText(String.valueOf(i.getInstalacaoObservacao()));
		tfPedido.setText(String.valueOf(i.getInstalacaoPedidoId()));
		dataInstalacao.setDate(i.getInstalacaoData());

		// falta o horario vadia Will

	}
}
