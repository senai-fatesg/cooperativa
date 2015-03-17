package br.com.ambientinformatica.senai.universitario.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.com.ambientinformatica.util.Entidade;

@Entity
public class Assembleia extends Entidade{

	@Id
	@GeneratedValue(generator = "assembleia_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "assembleia_seq", sequenceName = "assembleia_seq", allocationSize = 1, initialValue = 1)
	private Integer id;

	private byte[] documento;
	
	private String nomeDoc;
	
	private String tipoDoc;
	
	private String descricao;
	
	private Date data;
	
	private Date horaIni;
	
	private Date horaFim;
	
	@OneToMany(mappedBy = "assembleia", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private List<Cooperado> cooperados = new ArrayList<Cooperado>();

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHoraIni() {
		return horaIni;
	}

	public void setHoraIni(Date horaIni) {
		this.horaIni = horaIni;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public List<Cooperado> getCooperados() {
		return cooperados;
	}

	public void setCooperados(List<Cooperado> cooperados) {
		this.cooperados = cooperados;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public String getNomeDoc() {
		return nomeDoc;
	}

	public void setNomeDoc(String nomeDoc) {
		this.nomeDoc = nomeDoc;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public void gerarCooperados(List<Adesao> adesoes) {
		if (adesoes != null) {
			for (Adesao a : adesoes) {
				a.setAprovado(true);
				Cooperado c = new Cooperado();				
				c.setDadosPessoais(a.getDadosPessoais());
				c.setAssembleia(this);
				c.setStatus(EnumStatus.A);
				this.getCooperados().add(c);
			}
		}
	}

	public String toString() {
		return String.format("%s - %S", this.id, this.getDescricao());
	}
}
