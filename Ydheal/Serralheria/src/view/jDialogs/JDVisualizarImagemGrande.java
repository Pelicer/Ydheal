package view.jDialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.IllegalFormatException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Amostra;

@SuppressWarnings("serial")
public class JDVisualizarImagemGrande extends JDialog {

	private JPanel contentPane;

	int id = 0;
	private JTextField tfdescricao;
	private JTextField tfcusto;
	private JTextField tfcodigodomodelo;

	String diretorio;

	public JDVisualizarImagemGrande(Amostra a) {

		id = (a.getId());

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/amostra.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Visualizar Imagem");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 753, 506);

		tfcusto = new JTextField();
		tfdescricao = new JTextField();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmExcluirAmostra = new JMenuItem("Excluir amostra");
		menuBar.add(mntmExcluirAmostra);

		JButton btnPrxima = new JButton("Pr\u00F3xima");

		JMenuItem mntmAlterarImagem = new JMenuItem("Alterar dados");

		menuBar.add(mntmAlterarImagem);
		mntmExcluirAmostra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"Deseja realmente excluir a amostra que está visualizando??",
								"Confirmar exclusões ",
								JOptionPane.YES_NO_OPTION);

				// chaca resposta e faz o de acordo
				if (resposta == JOptionPane.YES_OPTION) {

					String comando = "DELETE FROM tbl_amostra WHERE amostra_id = "
							+ id + ";";

					try {

						Class.forName("com.mysql.jdbc.Driver");

						// Criando conexão.
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();
						java.sql.Statement st = con.createStatement();

						st.execute(comando);

						st.close();
						con.close();

						JOptionPane.showMessageDialog(null,
								"Deletado com sucesso");

						dispose();

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

					catch (Exception error) {
						error.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

				}

			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btnAnterior = new JButton("Anterior");
		Image imgAnterior = new ImageIcon(this.getClass().getResource(
				"/16px/previous.png")).getImage();
		btnAnterior.setIcon(new ImageIcon(imgAnterior));
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new Thread() {

					public void run() {

						// carrega a imagem pelo id recebido

						id--;

						String comando = "select amostra_preco , amostra_descricao, amostra_imagem from tbl_amostra where amostra_id = "
								+ id + ";";

						try {

							Class.forName("com.mysql.jdbc.Driver");

							// Criando conexão.
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();
							java.sql.Statement st = con.createStatement();

							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {

								ImageIcon img = new ImageIcon(resultSet
										.getString("amostra_imagem"));

								tfcusto.setText(resultSet
										.getString("amostra_preco"));

								tfdescricao.setText(resultSet
										.getString("amostra_descricao"));

								new Thread() {

									public void run() {

										img.setImage(img.getImage()
												.getScaledInstance(
														label.getWidth(),
														label.getHeight(), 100));

										label.setIcon(img);

									}
								}.start();

							}

							st.close();
							con.close();

						}// fim do try
						catch (NumberFormatException erroNumero) {

							JOptionPane
									.showMessageDialog(
											null,
											"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
											"Aviso!",
											JOptionPane.WARNING_MESSAGE);

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
											"Aviso!",
											JOptionPane.WARNING_MESSAGE);
						}

						catch (Exception error) {
							error.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Falha na consulta");
						}

					}
				}.start();

			}
		});
		btnAnterior.setBounds(9, 357, 89, 23);
		contentPane.add(btnAnterior);

		btnPrxima.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				new Thread() {

					public void run() {

						// carrega a imagem pelo id recebido

						id++;

						String comando = "select amostra_preco , amostra_descricao, amostra_imagem from tbl_amostra where amostra_id = "
								+ id + ";";

						try {

							Class.forName("com.mysql.jdbc.Driver");

							// Criando conexão.
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();
							java.sql.Statement st = con.createStatement();

							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {

								ImageIcon img = new ImageIcon(resultSet
										.getString("amostra_imagem"));

								tfcusto.setText(resultSet
										.getString("amostra_preco"));

								tfdescricao.setText(resultSet
										.getString("amostra_descricao"));

								new Thread() {

									public void run() {

										img.setImage(img.getImage()
												.getScaledInstance(
														label.getWidth(),
														label.getHeight(), 100));

										label.setIcon(img);

									}
								}.start();

							}

							st.close();
							con.close();

						}// fim do try
						catch (NumberFormatException erroNumero) {

							JOptionPane
									.showMessageDialog(
											null,
											"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
											"Aviso!",
											JOptionPane.WARNING_MESSAGE);

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
											"Aviso!",
											JOptionPane.WARNING_MESSAGE);
						}

						catch (Exception error) {
							error.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Falha na consulta");
						}

					}
				}.start();

			}
		});
		Image imgNext = new ImageIcon(this.getClass().getResource(
				"/16px/next.png")).getImage();
		btnPrxima.setIcon(new ImageIcon(imgNext));
		btnPrxima.setBounds(646, 357, 89, 23);
		contentPane.add(btnPrxima);

		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setBounds(7, 5, 730, 377);
		contentPane.add(label);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(197, 397, 74, 14);
		contentPane.add(lblDescrio);

		JButton btnCarregarNoOramento = new JButton(
				"Carregar no or\u00E7amento");
		Image imgOrcamento = new ImageIcon(this.getClass().getResource(
				"/16px/orcamento.png")).getImage();
		btnCarregarNoOramento.setIcon(new ImageIcon(imgOrcamento));
		btnCarregarNoOramento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// carregar no orçamento assim q estiver prontorsrs

			}
		});
		btnCarregarNoOramento.setBounds(543, 391, 192, 23);
		contentPane.add(btnCarregarNoOramento);

		JLabel lblCusto = new JLabel("Custo:");
		lblCusto.setBounds(197, 426, 58, 14);
		contentPane.add(lblCusto);
		setLocationRelativeTo(null);

		tfdescricao.setEditable(false);
		tfdescricao.setBounds(262, 391, 144, 20);
		contentPane.add(tfdescricao);
		tfdescricao.setColumns(10);

		tfcusto.setEditable(false);
		tfcusto.setBounds(265, 423, 86, 20);
		contentPane.add(tfcusto);
		tfcusto.setColumns(10);

		JLabel lblCodDoModelo = new JLabel("Cod. do modelo:");
		lblCodDoModelo.setBounds(9, 397, 91, 14);
		contentPane.add(lblCodDoModelo);

		tfcodigodomodelo = new JTextField();
		tfcodigodomodelo.setEditable(false);
		tfcodigodomodelo.setBounds(110, 393, 58, 20);
		contentPane.add(tfcodigodomodelo);
		tfcodigodomodelo.setColumns(10);

		JButton btnselecionarmodelo = new JButton("Selecionar Cod.");
		Image imgModelo = new ImageIcon(this.getClass().getResource(
				"/16px/modelo.png")).getImage();
		btnselecionarmodelo.setIcon(new ImageIcon(imgModelo));
		btnselecionarmodelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

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

				tfcodigodomodelo.setText(String.valueOf(m.getId()));

			}
		});
		btnselecionarmodelo.setEnabled(false);
		btnselecionarmodelo.setBounds(9, 422, 159, 23);
		contentPane.add(btnselecionarmodelo);

		mntmAlterarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// ativa os campos
				tfcusto.setEditable(true);
				tfdescricao.setEditable(true);
				tfcodigodomodelo.setEditable(true);

				// ativa o bnt de seleção de um outro modelo
				btnselecionarmodelo.setEnabled(true);

				// zera o lbl da imagem e torna possivel carregar outra ao
				// clicar
				label.setIcon(null);

				label.setText("Carregar outra imagem");

				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {

						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Importar imagem");
						FileFilter imageFilter = new FileNameExtensionFilter(
								"Image files", ImageIO.getReaderFileSuffixes());
						fileChooser.addChoosableFileFilter(imageFilter);

						fileChooser
								.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

						if (fileChooser.showOpenDialog(label) == JFileChooser.APPROVE_OPTION) {

							ImageIcon img = new ImageIcon(fileChooser
									.getSelectedFile().getPath());

							// Largura, altura, não lembro, mas deixa o numero
							// 100 que
							// funciona
							img.setImage(img.getImage().getScaledInstance(
									label.getWidth(), label.getHeight(), 100));

							diretorio = fileChooser.getSelectedFile().getPath();

							StringBuilder sb = new StringBuilder(diretorio);

							String teste = diretorio;
							// aqui ele pega a quantidade de carcteres que tem
							// uma
							// determinada variável
							// e armazena numa INT para usá-la de contador
							int contador = teste.length();

							// cria um for( para fazer uma varredura letra por
							// letra até
							// encontrar
							for (int i = 0; i < contador; i++) {
								// usamos substring pra pegar um caractere,
								// passando
								// como parâmetro,
								// o primeiro caractere a ser pega, até a
								// ultima.
								// fiz um if para verificar se o caractere é
								// igual a "_"
								if (teste.substring(i, i + 1).equals("\\")) {
									int posicao = i + 1;

									sb.setCharAt((posicao - 1), '/');

									// JOptionPane.showMessageDialog(null,"Está na posição "
									// + posicao ,"TITULO",1);
								}

							}

							diretorio = sb.toString();

							label.setIcon(img);

						}

					}

				});

				// o bnt de carregar para o orçamento torna-se um btn salvar
				btnCarregarNoOramento.setText("Salvar alterações");
				btnCarregarNoOramento.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String comando = "update tbl_amostra set amostra_descricao = '"
								+ tfdescricao.getText()
								+ "' , amostra_preco = "
								+ tfcusto.getText()
								+ " , amostra_imagem = '"
								+ diretorio
								+ "', tbl_amostra.modelo_id = "
								+ tfcodigodomodelo.getText()
								+ " where amostra_id = " + a.getId() + ";";

						try {

							Class.forName("com.mysql.jdbc.Driver");

							// Criando conexão.
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();
							java.sql.Statement st = con.createStatement();

							st.execute(comando);

							st.close();
							con.close();

							JOptionPane.showMessageDialog(null,
									"Amostra alterada com sucesso!");

						}

						// fim do try
						catch (NumberFormatException erroNumero) {

							JOptionPane
									.showMessageDialog(
											null,
											"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
											"Aviso!",
											JOptionPane.WARNING_MESSAGE);

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
											"Aviso!",
											JOptionPane.WARNING_MESSAGE);
						}

						catch (Exception error) {
							error.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Falha na consulta");
						}

						tfcusto.setEditable(false);
						tfdescricao.setEditable(false);
						tfcodigodomodelo.setEditable(false);

						// ativa o bnt de seleção de um outro modelo
						btnselecionarmodelo.setEnabled(false);

						// zera o lbl da imagem e torna possivel carregar outra
						// ao clicar

						label.setText("");

						label.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent arg0) {

							}
						});

					}
				});

			}
		});

		new Thread() {

			public void run() {
				String comando = "select modelo_id, amostra_preco , amostra_descricao, amostra_imagem from tbl_amostra where amostra_id = "
						+ a.getId() + ";";

				try {

					Class.forName("com.mysql.jdbc.Driver");

					// Criando conexão.
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						tfcodigodomodelo.setText(resultSet
								.getString("modelo_id"));

						ImageIcon img = new ImageIcon(
								resultSet.getString("amostra_imagem"));

						tfcusto.setText(resultSet.getString("amostra_preco"));

						tfdescricao.setText(resultSet
								.getString("amostra_descricao"));

						new Thread() {

							public void run() {

								img.setImage(img.getImage().getScaledInstance(
										label.getWidth(), label.getHeight(),
										100));

								label.setIcon(img);

							}
						}.start();
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

				catch (Exception error) {
					error.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}
			}
		}.start();
	}

	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}