package br.com.ambientinformatica.senai.universitario.entidade;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class Adesao extends Entidade{

	@Id
	@GeneratedValue(generator = "adesao_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "adesao_seq", sequenceName = "adesao_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

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

	public void corrigirRefListas() {
		for (CursoTecnico c : cursos) {
			c.setAdesao(this);
		}

		for (Dependente d : dependentes) {
			d.setAdesao(this);
		}
	}

	@Override
	public String toString() {
		return String.format("%s - %s", this.id, this.dadosPessoais);
	}

	@Override
	public Object getId() {
		return this.id;
	}
}
