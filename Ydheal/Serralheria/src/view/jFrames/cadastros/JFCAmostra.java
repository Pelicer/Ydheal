package view.jFrames.cadastros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.IllegalFormatException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Amostra;
import view.jDialogs.JDSelecionarCodigodeModelo;

@SuppressWarnings("serial")
public class JFCAmostra extends JDialog {

	private JPanel contentPane;
	private JTextField tfDescricao;
	private JTextField tfcodigomodelo;
	private JFormattedTextField tfPreco;

	String diretorio;

	public JFCAmostra() {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro da amostra será cancelada. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/amostra.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Amostra");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAmostra = new JLabel("Amostra");
		// Adicionando imagem.
		Image imgAmostra = new ImageIcon(this.getClass().getResource(
				"/50px/amostra.png")).getImage();
		lblAmostra.setIcon(new ImageIcon(imgAmostra));
		lblAmostra.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAmostra.setBounds(10, 0, 394, 58);
		contentPane.add(lblAmostra);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 69, 354, 2);
		contentPane.add(separator);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(10, 289, 81, 14);
		contentPane.add(lblDescrio);

		JLabel lblModelo = new JLabel("Cod.:");
		lblModelo.setBounds(10, 314, 103, 14);
		contentPane.add(lblModelo);

		JLabel lblPreo = new JLabel("Pre\u00E7o:");
		lblPreo.setBounds(10, 342, 46, 14);
		contentPane.add(lblPreo);

		tfDescricao = new JTextField();
		tfDescricao.setBounds(77, 286, 267, 20);
		contentPane.add(tfDescricao);
		tfDescricao.setColumns(10);

		tfcodigomodelo = new JTextField();
		tfcodigomodelo.setBounds(77, 314, 168, 20);
		contentPane.add(tfcodigomodelo);
		tfcodigomodelo.setColumns(10);

		tfPreco = new JFormattedTextField();
		tfPreco.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				String x = tfPreco.getText();

				MaskFormatter campoCustoUnidade = null;
				MaskFormatter campoCustoDezena = null;
				MaskFormatter campoCustoCentena = null;
				MaskFormatter campoCustoMilhar = null;
				try {
					campoCustoUnidade = new MaskFormatter("R$ #.##");
					campoCustoDezena = new MaskFormatter("R$ ##.##");
					campoCustoCentena = new MaskFormatter("R$ ###.##");
					campoCustoMilhar = new MaskFormatter("R$ ####.##");
				} catch (Exception erro) {

				}

				if (tfPreco.getText().length() == 1) {
					tfPreco = new JFormattedTextField(campoCustoUnidade);
					tfPreco.setText(x);
				} else if (tfPreco.getText().length() == 2) {
					tfPreco = new JFormattedTextField(campoCustoDezena);
					tfPreco.setText(x);
				} else if (tfPreco.getText().length() == 3) {
					tfPreco = new JFormattedTextField(campoCustoCentena);
					tfPreco.setText(x);
				} else if (tfPreco.getText().length() == 4) {
					tfPreco = new JFormattedTextField(campoCustoMilhar);
					tfPreco.setText(x);
				}

				tfPreco.setBounds(100, 171, 204, 20);
				tfPreco.setColumns(10);
				contentPane.add(tfPreco);
			}
		});
		tfPreco.setBounds(77, 339, 168, 20);
		contentPane.add(tfPreco);
		tfPreco.setColumns(10);

		JLabel lblimagem = new JLabel("");
		lblimagem.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimagem.setBounds(10, 77, 334, 178);
		contentPane.add(lblimagem);

		Panel imagePanel = new Panel();
		imagePanel.setBounds(10, 77, 334, 178);
		contentPane.add(imagePanel);

		JButton btnSalvar = new JButton("Salvar");
		// Adicionando imagem.
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.setBounds(10, 385, 106, 23);
		contentPane.add(btnSalvar);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				tfDescricao.setText("");
				tfDescricao.grabFocus();
				tfcodigomodelo.setText("");
				tfPreco.setText("");

			}
		});
		btnLimpar.setBounds(126, 385, 102, 23);
		contentPane.add(btnLimpar);

		JButton btnCancelar = new JButton("Cancelar");
		// Adicionando imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(238, 385, 106, 23);
		contentPane.add(btnCancelar);

		JButton bntselecionarimagem = new JButton("Carregar Imagem");
		bntselecionarimagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Importar imagem");
				FileFilter imageFilter = new FileNameExtensionFilter(
						"Image files", ImageIO.getReaderFileSuffixes());
				fileChooser.addChoosableFileFilter(imageFilter);

				fileChooser
						.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				if (fileChooser.showOpenDialog(lblimagem) == JFileChooser.APPROVE_OPTION) {

					ImageIcon img = new ImageIcon(fileChooser.getSelectedFile()
							.getPath());

					// Largura, altura, não lembro, mas deixa o numero 100 que
					// funciona
					img.setImage(img.getImage().getScaledInstance(
							lblimagem.getWidth(), lblimagem.getHeight(), 100));

					diretorio = fileChooser.getSelectedFile().getPath();

					StringBuilder sb = new StringBuilder(diretorio);

					String teste = diretorio;
					// aqui ele pega a quantidade de carcteres que tem uma
					// determinada variável
					// e armazena numa INT para usá-la de contador
					int contador = teste.length();

					// cria um for( para fazer uma varredura letra por letra até
					// encontrar
					for (int i = 0; i < contador; i++) {
						// usamos substring pra pegar um caractere, passando
						// como parâmetro,
						// o primeiro caractere a ser pega, até a ultima.
						// fiz um if para verificar se o caractere é igual a "_"
						if (teste.substring(i, i + 1).equals("\\")) {
							int posicao = i + 1;

							sb.setCharAt((posicao - 1), '/');

							// JOptionPane.showMessageDialog(null,"Está na posição "
							// + posicao ,"TITULO",1);
						}

					}

					diretorio = sb.toString();

					lblimagem.setIcon(img);

				}

			}
		});
		bntselecionarimagem.setFont(new Font("Tahoma", Font.PLAIN, 10));
		bntselecionarimagem.setBounds(10, 261, 334, 14);
		contentPane.add(bntselecionarimagem);

		JButton btnBuscar = new JButton("");
		// Adicionando imagem.
		Image imgBuscar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		btnBuscar.setIcon(new ImageIcon(imgBuscar));
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Amostra m = new Amostra();

				try {
					JDSelecionarCodigodeModelo dialog = new JDSelecionarCodigodeModelo(
							m);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setModal(true);
					dialog.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				tfcodigomodelo.setText(String.valueOf(m.getId()));
				tfPreco.setText(String.valueOf(m.getAmostraPreco()));

			}
		});
		btnBuscar.setBounds(255, 310, 23, 23);
		contentPane.add(btnBuscar);

		JLabel label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setBackground(Color.RED);
		label.setBounds(288, 314, 25, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setBackground(Color.RED);
		label_1.setBounds(288, 345, 25, 14);
		contentPane.add(label_1);

		JLabel lblCampos = new JLabel("* - Campos obrigat\u00F3rios.");
		lblCampos.setForeground(Color.RED);
		lblCampos.setBackground(Color.RED);
		lblCampos.setBounds(160, 26, 184, 14);
		contentPane.add(lblCampos);

		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfcodigomodelo.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);
				} else if (tfPreco.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else {
					try {
						Class.forName("com.mysql.jdbc.Driver");

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						Amostra amostra = new Amostra();
						amostra.setAmostraPreco(Double.parseDouble(tfPreco
								.getText()));
						amostra.setId(Integer.parseInt(tfcodigomodelo.getText()));
						amostra.setDescricao(tfDescricao.getText());

						st.execute("insert into tbl_amostra(amostra_descricao, amostra_preco, modelo_id, amostra_imagem) values ('"
								+ amostra.getDescricao()
								+ "',  "
								+ amostra.getAmostraPreco()
								+ ", "
								+ amostra.getId() + ", '" + diretorio + "');");

						// Amostra cadastrada.
						JOptionPane.showMessageDialog(null,
								"Amostra cadastrada com sucesso!");

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
			}

		});

	}

}
