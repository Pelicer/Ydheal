package modelSuplerclasses;

import java.sql.Date;

public class Instalacao {

	private int instalacaoId;
	private Date instalacaoData;
	private int instalacaoClienteId;
	private int instalacaoPedidoId;
	private String instalacaoObservacao;
	private String instalacaoHorario;

	public Instalacao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Instalacao(int instalacaoId, Date instalacaoData,
			int instalacaoClienteId, int instalacaoPedidoId,
			String instalacaoObservacao, String instalacaoHorario) {
		this.instalacaoId = instalacaoId;
		this.instalacaoData = instalacaoData;
		this.instalacaoClienteId = instalacaoClienteId;
		this.instalacaoPedidoId = instalacaoPedidoId;
		this.instalacaoObservacao = instalacaoObservacao;
		this.instalacaoHorario = instalacaoHorario;
	}

	public int getInstalacaoId() {
		return instalacaoId;
	}

	public void setInstalacaoId(int instalacaoId)
			throws IllegalArgumentException, NumberFormatException {
		if (instalacaoId > 0) {
			this.instalacaoId = instalacaoId;
		} else {
			throw new NumberFormatException();
		}
	}

	public Date getInstalacaoData() {
		return instalacaoData;
	}

	public void setInstalacaoData(Date instalacaoData) {
		this.instalacaoData = instalacaoData;
	}

	public int getInstalacaoClienteId() {
		return instalacaoClienteId;
	}

	public void setInstalacaoClienteId(int instalacaoClienteId)
			throws IllegalArgumentException, NumberFormatException {
		if (instalacaoClienteId > 0) {
			this.instalacaoClienteId = instalacaoClienteId;
		} else {
			throw new NumberFormatException();
		}
	}

	public int getInstalacaoPedidoId() {
		return instalacaoPedidoId;
	}

	public void setInstalacaoPedidoId(int instalacaoPedidoId)
			throws IllegalArgumentException, NumberFormatException {
		if (instalacaoClienteId > 0) {
			this.instalacaoPedidoId = instalacaoPedidoId;
		} else {
			throw new NumberFormatException();
		}
	}

	public String getInstalacaoObservacao() {
		return instalacaoObservacao;
	}

	public void setInstalacaoObservacao(String instalacaoObservacao) {
		this.instalacaoObservacao = instalacaoObservacao;
	}

	public String getInstalacaoHorario() {
		return instalacaoHorario;
	}

	public void setInstalacaoHorario(String instalacaoHorario) {
		this.instalacaoHorario = instalacaoHorario;
	}
}
