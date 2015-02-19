package br.com.ambientinformatica.senai.universitario.entidade;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.ambientinformatica.senai.universitario.util.GerarMatricula;
import br.com.ambientinformatica.util.Entidade;

@Entity
public class Cooperado extends Entidade{

	@Id
	@GeneratedValue(generator = "cooperado_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "cooperado_seq", sequenceName = "cooperado_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dadosPessoais")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private DadosPessoais dadosPessoais = new DadosPessoais();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assembleia")
	@Fetch(FetchMode.JOIN)
	private Assembleia assembleia;
	
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	
	@Temporal(TemporalType.DATE)
	private Date dtExclusao;
	
	private String matricula;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private Pessoa cooperativa;

	public Pessoa getCooperativa() {
		return cooperativa;
	}

	public void setCooperativa(Pessoa cooperativa) {
		this.cooperativa = cooperativa;
	}

	public EnumStatus getStatus() {
		return status;
	}

	public Integer getId() {
		return id;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}

	public void setDadosPessoais(DadosPessoais dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}

	public Assembleia getAssembleia() {
		return assembleia;
	}

	public void setAssembleia(Assembleia assembleia) {
		this.assembleia = assembleia;
	}

	public Date getDtExclusao() {
		return dtExclusao;
	}

	public void setDtExclusao(Date dtExclusao) {
		this.dtExclusao = dtExclusao;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Boolean contem(String valor) {
		if (this.dadosPessoais != null) {
			if (this.dadosPessoais.getNome().contains(valor)) {
				return true;
			}
		}
		return false;
	}

	public void gerarMatricula() {
		if (this.matricula == null && this.id != null) {
			Pessoa cooperativa = new Pessoa();
			// id cooperativa ficticia
			cooperativa.setId(new Long(1));
			this.cooperativa = cooperativa;
			GerarMatricula gm = new GerarMatricula(new Date(), this,
					cooperativa);
			this.matricula = gm.getMatricula();
		}
	}

	public String toString() {
		if (this.getDadosPessoais() != null) {
			return this.getId() + " - " + this.getDadosPessoais().getNome();
		}
		return this.getId().toString();
	}

}
