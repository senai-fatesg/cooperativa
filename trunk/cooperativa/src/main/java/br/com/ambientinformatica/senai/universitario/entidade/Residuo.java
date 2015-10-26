package br.com.ambientinformatica.senai.universitario.entidade;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class Residuo extends Entidade {

	@Id
	@GeneratedValue(generator = "residuo_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "residuo_seq", sequenceName = "residuo_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	private String descricao;

	// @Temporal(TemporalType.DATE)
	// private Date dataCadastro;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro = new Date();

	private String usuario;

	private float precoMedio;

	private String status;

	@Temporal(TemporalType.DATE)
	private Date dtExclusao;

	public boolean equals(Object obj) {
		try {
			Residuo r = (Residuo) obj;
			return r.getDescricao().equals(descricao) && r.getId() == id;
		} catch (Exception e) {
			return false;
		}
	}

	public Date getDtExclusao() {
		return dtExclusao;
	}

	public void setDtExclusao(Date dtExclusao) {
		this.dtExclusao = dtExclusao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao.toUpperCase();
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario.toUpperCase();
	}

	public float getPrecoMedio() {
		return precoMedio;
	}

	public void setPrecoMedio(float precoMedio) {
		this.precoMedio = precoMedio;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String toString() {

		return this.getDescricao();

	}

}
