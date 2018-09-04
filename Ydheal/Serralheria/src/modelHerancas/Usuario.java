package modelHerancas;

import java.util.Date;

public class Usuario {
	// Atributos da classe usuários, modifiquei a ordem dos atributos contidos
	// no diagrama para facilitar o desenvolvimento
	private int id;
	private int nivelAcesso;
	private Date dataNascimento;
	private String nome;
	private String sobrenome;
	private String telefone;
	private String email;
	private String endereco;
	private String cep;
	private String userName;
	private String senha;
	private String rg;
	private String cpf;

	// Construtores

	public Usuario(int id, int nivelAcesso, Date dataNascimento, String nome,
			String sobrenome, String telefone, String email, String endereco,
			String cep, String userName, String senha, String rg, String cpf) {
		this.id = id;
		this.nivelAcesso = nivelAcesso;
		this.dataNascimento = dataNascimento;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.cep = cep;
		this.userName = userName;
		this.senha = senha;
		this.rg = rg;
		this.cpf = cpf;
	}

	// Metodos de acesso

	public int getId() {
		return id;
	}

	public void setId(int id) throws IllegalArgumentException,
			NumberFormatException {
		if (id > 0) {
			this.id = id;
		} else {
			throw new NumberFormatException();
		}
	}

	public int getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(int nivelAcesso)
			throws IllegalArgumentException, NumberFormatException {
		if (nivelAcesso > 0) {
			this.nivelAcesso = nivelAcesso;
		} else {
			throw new NumberFormatException();
		}
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}