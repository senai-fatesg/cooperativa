package br.com.cooperativa.entidade;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class Cidade extends Entidade{

	@Id
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

	@Override
	public Object getId() {
		return this.ibge;
	}
}