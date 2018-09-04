package modelSuplerclasses;

public class Visita {
	// Atributos
	private int id;
	private int status;
	private String horarioVisita;
	private String dataVisita;
	private String nome;
	private String telefone;
	private String endereco;
	private String observacao;

	public Visita(int id, int status, String horarioVisita, String dataVisita,
			String nome, String telefone, String endereco, String observacao) {
		this.id = id;
		this.status = status;
		this.horarioVisita = horarioVisita;
		this.dataVisita = dataVisita;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.observacao = observacao;
	}

	// Metodos de acesso

	public Visita() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereço) {
		this.endereco = endereço;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) throws IllegalArgumentException,
			NumberFormatException {
		if (status > 0) {
			this.status = status;
		} else {
			throw new NumberFormatException();
		}
	}

	public String getHorarioVisita() {
		return horarioVisita;
	}

	public void setHorarioVisita(String horariovisita) {
		this.horarioVisita = horariovisita;
	}

	public String getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(String string) {
		this.dataVisita = string;
	}

}
