package br.com.ambientinformatica.senai.universitario.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class DadosPessoais {

	@Id
	@GeneratedValue(generator = "dpessoais_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "dpessoais_seq", sequenceName = "dpessoais_seq", allocationSize = 1, initialValue = 1)
	private Integer idDadosPessoais;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Filiacao filiacao = new Filiacao();
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Endereco endereco = new Endereco();
	
	private String nome;
	
	private Date dataNascimento;
	
	@Enumerated(EnumType.STRING)
	private EnumEstadoCivil estadoCivil;
	
	private String rg;
	
	private String cpf;
	
	private String carteiraTrabalho;
	
	private String telResidencial;
	
	private String telCelular;
	
	private String nacionalidade;

	public EnumEstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EnumEstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Integer getIdDadosPessoais() {
		return idDadosPessoais;
	}

	public void setIdDadosPessoais(Integer idDadosPessoais) {
		this.idDadosPessoais = idDadosPessoais;
	}

	public Filiacao getFiliacao() {
		return filiacao;
	}

	public void setFiliacao(Filiacao filiacao) {
		this.filiacao = filiacao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCarteiraTrabalho() {
		return carteiraTrabalho;
	}

	public void setCarteiraTrabalho(String carteiraTrabalho) {
		this.carteiraTrabalho = carteiraTrabalho;
	}

	public String getTelResidencial() {
		return telResidencial;
	}

	public void setTelResidencial(String telResidencial) {
		this.telResidencial = telResidencial;
	}

	public String getTelCelular() {
		return telCelular;
	}

	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String toString() {
		return String.format("%s", this.nome);
	}
}
