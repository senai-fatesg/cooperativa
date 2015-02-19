package br.com.ambientinformatica.senai.universitario.entidade;

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

import br.com.ambientinformatica.util.Entidade;

@Entity
public class CursoTecnico extends Entidade {

	@Id
	@GeneratedValue(generator = "ctecnico_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ctecnico_seq", sequenceName = "ctecnico_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	private String nome;
	
	private String descricao;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "adesao")
	@Fetch(FetchMode.JOIN)
	
	private Adesao adesao;

	public CursoTecnico() {

	}

	public Adesao getAdesao() {
		return adesao;
	}

	public void setAdesao(Adesao adesao) {
		this.adesao = adesao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

}