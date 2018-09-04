package modelSuplerclasses;

public class Pagamento {

	private int idPagamento;
	private int pedidoId;
	private String formaPagamento;
	private double valorPagamento;
	private int parcelamentoPagamento;
	private String status;
	private double pagamentoValorPago;
	private double pagamentoValorPendente;
	private double pagamentoValorPrazo;
	private double pagamentoJuros;

	public Pagamento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pagamento(int idPagamento, int pedidoId, String formaPagamento,
			double valorPagamento, int parcelamentoPagamento, String status,
			double pagamentoValorPago, double pagamentoValorPendente,
			double pagamentoValorPrazo, double pagamentoJuros) {
		this.idPagamento = idPagamento;
		this.pedidoId = pedidoId;
		this.formaPagamento = formaPagamento;
		this.valorPagamento = valorPagamento;
		this.parcelamentoPagamento = parcelamentoPagamento;
		this.status = status;
		this.pagamentoValorPago = pagamentoValorPago;
		this.pagamentoValorPendente = pagamentoValorPendente;
		this.pagamentoValorPrazo = pagamentoValorPrazo;
		this.pagamentoJuros = pagamentoJuros;
	}

	public int getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(int idPagamento) {
		this.idPagamento = idPagamento;
	}

	public int getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(int pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public double getValorPagamento() {
		return valorPagamento;
	}

	public void setValorPagamento(double valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

	public int getParcelamentoPagamento() {
		return parcelamentoPagamento;
	}

	public void setParcelamentoPagamento(int parcelamentoPagamento) {
		this.parcelamentoPagamento = parcelamentoPagamento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getPagamentoValorPago() {
		return pagamentoValorPago;
	}

	public void setPagamentoValorPago(double pagamentoValorPago) {
		this.pagamentoValorPago = pagamentoValorPago;
	}

	public double getPagamentoValorPendente() {
		return pagamentoValorPendente;
	}

	public void setPagamentoValorPendente(double pagamentoValorPendente) {
		this.pagamentoValorPendente = pagamentoValorPendente;
	}

	public double getPagamentoValorPrazo() {
		return pagamentoValorPrazo;
	}

	public void setPagamentoValorPrazo(double pagamentoValorPrazo) {
		this.pagamentoValorPrazo = pagamentoValorPrazo;
	}

	public double getPagamentoJuros() {
		return pagamentoJuros;
	}

	public void setPagamentoJuros(double pagamentoJuros) {
		this.pagamentoJuros = pagamentoJuros;
	}

}