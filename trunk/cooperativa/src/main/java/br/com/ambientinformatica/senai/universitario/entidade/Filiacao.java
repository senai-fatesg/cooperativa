package br.com.ambientinformatica.senai.universitario.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Filiacao implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "filiacao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "filiacao_seq", sequenceName = "filiacao_seq", allocationSize = 1, initialValue = 1)
	private Integer idFiliacao;

	private String nomePai;

	private String nomeMae;

	public Integer getIdFiliacao() {
		return idFiliacao;
	}

	public void setIdFiliacao(Integer idFiliacao) {
		this.idFiliacao = idFiliacao;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

}