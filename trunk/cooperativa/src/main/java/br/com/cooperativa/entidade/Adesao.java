package br.com.cooperativa.entidade;

// Generated 21/11/2013 13:33:34 by Hibernate Tools 3.6.0

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Adesao generated by hbm2java
 */
@Entity
public class Adesao {
	@Id
	@GeneratedValue(generator = "adesao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "adesao_seq", sequenceName = "adesao_seq", allocationSize = 1, initialValue = 1)
	private Integer idAdesao;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dados_pessoais")
	@Fetch(FetchMode.JOIN)
	@Cascade(CascadeType.ALL)
	private DadosPessoais dadosPessoais = new DadosPessoais();
	private boolean bolsaFamilia;
	private boolean aprovado;
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	@Temporal(TemporalType.DATE)
	private Date dtAssembleia;
	@Temporal(TemporalType.DATE)
	private Date dtExclusao;
	private BigDecimal rendaFamiliar;
	private int qtdPessoasRenda;
	@ManyToOne(fetch = FetchType.EAGER)
	private Cooperado coopResponsavel;
	private boolean principalAtivProdutiva;
	private boolean estudaAtualmente;
	private String motivo;
	@OneToMany(mappedBy = "adesao", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private List<CursoTecnico> cursos = new ArrayList<CursoTecnico>();
	@OneToMany(mappedBy = "adesao", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private List<Dependente> dependentes = new ArrayList<Dependente>();

	public Cooperado getCoopResponsavel() {
		return coopResponsavel;
	}

	public Date getDtExclusao() {
		return dtExclusao;
	}

	public void setDtExclusao(Date dtExclusao) {
		this.dtExclusao = dtExclusao;
	}

	public void setCoopResponsavel(Cooperado coopResponsavel) {
		this.coopResponsavel = coopResponsavel;
	}

	public Integer getIdAdesao() {
		return idAdesao;
	}

	public void setIdAdesao(Integer idAdesao) {
		this.idAdesao = idAdesao;
	}

	public boolean isBolsaFamilia() {
		return bolsaFamilia;
	}

	public void setBolsaFamilia(boolean bolsaFamilia) {
		this.bolsaFamilia = bolsaFamilia;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public BigDecimal getRendaFamiliar() {
		return rendaFamiliar;
	}

	public void setRendaFamiliar(BigDecimal rendaFamiliar) {
		this.rendaFamiliar = rendaFamiliar;
	}

	public int getQtdPessoasRenda() {
		return qtdPessoasRenda;
	}

	public void setQtdPessoasRenda(int qtdPessoasRenda) {
		this.qtdPessoasRenda = qtdPessoasRenda;
	}

	public boolean isPrincipalAtivProdutiva() {
		return principalAtivProdutiva;
	}

	public void setPrincipalAtivProdutiva(boolean principalAtivProdutiva) {
		this.principalAtivProdutiva = principalAtivProdutiva;
	}

	public boolean isEstudaAtualmente() {
		return estudaAtualmente;
	}

	public void setEstudaAtualmente(boolean estudaAtualmente) {
		this.estudaAtualmente = estudaAtualmente;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public List<CursoTecnico> getCursos() {
		return cursos;
	}

	public void setCursos(List<CursoTecnico> cursos) {
		this.cursos = cursos;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}

	public void setDadosPessoais(DadosPessoais dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}

	public Date getDtAssembleia() {
		return dtAssembleia;
	}

	public void setDtAssembleia(Date dtAssembleia) {
		this.dtAssembleia = dtAssembleia;
	}

	/* Metodos Auxiliares */
	public void corrigirRefListas() {
		for (CursoTecnico c : cursos) {
			c.setAdesao(this);
		}

		for (Dependente d : dependentes) {
			d.setAdesao(this);
		}
	}

	// /* Overrride */
	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result
	// + ((idAdesao == null) ? 0 : idAdesao.hashCode());
	// return result;
	// }
	//
	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// Adesao other = (Adesao) obj;
	// if (idAdesao == null) {
	// if (other.idAdesao != null)
	// return false;
	// } else if (!idAdesao.equals(other.idAdesao))
	// return false;
	// return true;
	// }

	@Override
	public String toString() {
		return String.format("%s - %s", this.idAdesao, this.dadosPessoais);
	}
}
