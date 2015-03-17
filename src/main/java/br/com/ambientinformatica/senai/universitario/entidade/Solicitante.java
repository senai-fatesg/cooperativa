package br.com.ambientinformatica.senai.universitario.entidade;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Solicitante  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idSolicitante;
	
	private Adesao adesao;

	public Solicitante() {
	}

	public Solicitante(Adesao adesao) {
		this.adesao = adesao;
	}

	@Id @GeneratedValue(strategy=IDENTITY)

	@Column(name="idSolicitante", unique=true, nullable=false)
	public Integer getIdSolicitante() {
		return this.idSolicitante;
	}

	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idAdesao", nullable=false)
	public Adesao getAdesao() {
		return this.adesao;
	}

	public void setAdesao(Adesao adesao) {
		this.adesao = adesao;
	}

}