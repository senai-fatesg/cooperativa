package br.com.ambientinformatica.senai.universitario.entidade;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Dependente implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "dependente_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "dependente_seq", sequenceName = "dependente_seq", allocationSize = 1, initialValue = 1)
	private Integer idDependente;

	private Date dataNascimento;
	
	private String situacao;
	
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "adesao")
	@Fetch(FetchMode.JOIN)
	private Adesao adesao;

	public Dependente() {
	}

	public Adesao getAdesao() {
		return adesao;
	}

	public void setAdesao(Adesao adesao) {
		this.adesao = adesao;
	}

	public Integer getIdDependente() {
		return idDependente;
	}

	public void setIdDependente(Integer idDependente) {
		this.idDependente = idDependente;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return this.nome;
	}
}