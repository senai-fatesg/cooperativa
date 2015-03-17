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
public class Usuariosistema implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idUsuarioSistema;
	
	private Cooperado cooperado;
	
	private String senha;
	
	private String tipo;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idUsuarioSistema", unique = true, nullable = false)
	public Integer getIdUsuarioSistema() {
		return this.idUsuarioSistema;
	}

	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCoopr", nullable = false)
	public Cooperado getCooperado() {
		return this.cooperado;
	}

	public void setCooperado(Cooperado cooperado) {
		this.cooperado = cooperado;
	}

	@Column(name = "senha", nullable = false, length = 45)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Column(name = "tipo", nullable = false, length = 2)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
