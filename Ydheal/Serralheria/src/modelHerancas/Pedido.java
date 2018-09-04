package modelHerancas;

import java.sql.Date;

public class Pedido {
	// Atributos
	private int id;
	private int clienteId;
	private int orcamentoId;
	private java.util.Date dataentrega;
	private Date diagerado;
	private String pedidonome;
	private int visitaid;
	private int nivel;
	private String historico;
	private String materiaisencomendados;
	private Date diaencomendamateriais;

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

	public java.util.Date getDataentrega() {
		return dataentrega;
	}

	public void setDataentrega(java.util.Date dataentrega) {
		this.dataentrega = dataentrega;
	}

	public Date getDiagerado() {
		return diagerado;
	}

	public void setDiagerado(Date diagerado) {
		this.diagerado = diagerado;
	}

	public String getPedidonome() {
		return pedidonome;
	}

	public void setPedidonome(String pedidonome) {
		this.pedidonome = pedidonome;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) throws NumberFormatException {
		if (nivel > 0) {
			this.nivel = nivel;
		} else {
			throw new NumberFormatException();
		}
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getMateriaisencomendados() {
		return materiaisencomendados;
	}

	public void setMateriaisencomendados(String materiaisencomendados) {
		this.materiaisencomendados = materiaisencomendados;
	}

	public Date getDiaencomendamateriais() {
		return diaencomendamateriais;
	}

	public void setDiaencomendamateriais(Date diaencomendamateriais) {
		this.diaencomendamateriais = diaencomendamateriais;
	}

	public int getClienteId() {
		return clienteId;
	}

	public int getOrcamentoId() {
		return orcamentoId;
	}

	public int getVisitaid() {
		return visitaid;
	}

	public void setClienteId(int clienteId) throws IllegalArgumentException {

		if (clienteId > 0) {
			this.clienteId = clienteId;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void setOrcamentoId(int orcamentoId)
			throws IllegalArgumentException, NumberFormatException {
		if (orcamentoId > 0) {
			this.orcamentoId = orcamentoId;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void setVisitaid(int visitaid) throws IllegalArgumentException,
			NumberFormatException {
		if (visitaid > 0) {
			this.visitaid = visitaid;
		} else {
			throw new IllegalArgumentException();
		}
	}

}