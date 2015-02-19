package br.com.cooperativa.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Cidade{

	@Id
	@GeneratedValue(generator = "cidade_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "cidade_seq", sequenceName = "cidade_seq", allocationSize = 1, initialValue = 1)
	private Integer ibge;
	
	private String uf;
	
	private String nome;

	public Integer getIbge() {
		return ibge;
	}

	public void setIbge(Integer ibge) {
		this.ibge = ibge;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String toString() {
		return this.nome + " - " + this.uf;
	}

	public Boolean contem(String valor) {
		if (this.nome.contains(valor.toUpperCase())) {
			return true;
		}
		return false;
	}
}