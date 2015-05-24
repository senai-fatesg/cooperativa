package br.com.ambientinformatica.senai.universitario.entidade;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Empresa {

	@Id
	@GeneratedValue(generator = "empresa_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "empresa_seq", sequenceName = "empresa_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name = "endereco", nullable = false)
	private Endereco endereco;

	@Column(nullable = false)
	private String razaoSocial;

	@Column(nullable = false)
	private String nomeFantasia;

	@Column(nullable = false)
	private String cnpj;

	@Column(nullable = false)
	private String telefone;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	private Set<Cooperativa> cooperativas = new HashSet<Cooperativa>();

	public Empresa(){
		
	}
	
	public Empresa(Endereco endereco, String razaoSocial, String nomeFantasia, String cnpj, String telefone) {
		this.endereco = endereco;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.telefone = telefone;
	}

	public Empresa(Endereco endereco, String razaoSocial, String nomeFantasia,
			String cnpj, String telefone, Set<Cooperativa> cooperativas) {
		this.endereco = endereco;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.cooperativas = cooperativas;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Set<Cooperativa> getCooperativas() {
		return cooperativas;
	}

	public void setCooperativas(Set<Cooperativa> cooperativas) {
		this.cooperativas = cooperativas;
	}

	public Integer getId() {
		return id;
	}
}