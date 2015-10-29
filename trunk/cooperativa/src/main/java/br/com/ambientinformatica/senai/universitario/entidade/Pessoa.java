package br.com.ambientinformatica.senai.universitario.entidade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Pessoa {

	@Id
	@GeneratedValue(generator = "pessoa_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco = new Endereco();

	private String razaoSocial;

	private String nomeFantasia;

	private String nome;
	
	private String cnpj;

	private String cpf;
	
	@OneToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pessoa")
	private List<Telefone> telefones = new ArrayList<Telefone>();

	@Enumerated(EnumType.STRING)
	private EnumTipoPessoa tipoPessoa;

	@Enumerated(EnumType.STRING)
	private EnumStatus status;

	private boolean cooperativa;
	
	@OneToMany
	private List<Adesao> adesaos = new ArrayList<Adesao>();
	
	@OneToMany(fetch = FetchType.LAZY)
   @JoinColumn(name="usuario_id")
	private Set<Pessoa> parceiros = new HashSet<Pessoa>();

	public void removerParceiro(Pessoa parceiro) {
		parceiros.remove(parceiro);
	}

	public void adicionarParceiro(Pessoa parceiro) {
		parceiros.add(parceiro);
	}

	public void adicionarAllParceiro(Set<Pessoa> parceiros) {
		parceiros.addAll(parceiros);
	}

	
	public void addTelefone(Telefone tel) {
		telefones.add(tel);
	}
	
	public void removeTelefone(Telefone telefone) {
		telefones.remove(telefone);
	}

	public void adicionarAllEndereco(Set<Endereco> enderecos){

		enderecos.addAll(enderecos);
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

	public EnumTipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(EnumTipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	public boolean isCooperativa() {
		return cooperativa;
	}

	public void setCooperativa(boolean cooperativa) {
		this.cooperativa = cooperativa;
	}

	public Long getId() {
		return id;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public Set<Pessoa> getParceiros() {
		return parceiros;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setParceiros(Set<Pessoa> parceiros) {
		this.parceiros = parceiros;
	}

	public List<Adesao> getAdesaos() {
		return adesaos;
	}

	public void setAdesaos(List<Adesao> adesaos) {
		this.adesaos = adesaos;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
