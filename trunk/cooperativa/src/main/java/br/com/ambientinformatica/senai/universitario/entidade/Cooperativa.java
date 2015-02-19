package br.com.ambientinformatica.senai.universitario.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ambientinformatica.util.Entidade;

@Entity
@Table(name = "cooperativa", catalog = "cooperativas")
public class Cooperativa extends Entidade {

	@Id
	@GeneratedValue(generator = "cooperativa_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "cooperativa_seq", sequenceName = "cooperativa_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	private Empresa empresa;
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataCadastro", nullable = false)
	private Date dataCadastro;

	public Cooperativa() {
	}

	public Cooperativa(Empresa empresa, String status, Date dataCadastro) {
		this.empresa = empresa;
		this.status = status;
		this.dataCadastro = dataCadastro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa", nullable = false)
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Column(name = "status", nullable = false, length = 11)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getId() {
		return id;
	}

}
