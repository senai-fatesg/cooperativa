package br.com.ambientinformatica.senai.universitario.controle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.senai.universitario.entidade.Adesao;
import br.com.ambientinformatica.senai.universitario.entidade.Assembleia;
import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;
import br.com.ambientinformatica.senai.universitario.entidade.Pessoa;
import br.com.ambientinformatica.senai.universitario.persistencia.AdesaoDao;
import br.com.ambientinformatica.senai.universitario.persistencia.AssembleiaDao;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperadoDao;
import br.com.ambientinformatica.senai.universitario.util.GerarMatricula;
import br.com.ambientinformatica.senai.universitario.util.Mensagem;

@Controller("AssembleiaControl")
@Scope("conversation")
public class AssembleiaControl extends Control{

	private Assembleia assembleia = new Assembleia();
	private List<Assembleia> assembleias = new ArrayList<Assembleia>();
	private List<Adesao> adesoes = new ArrayList<Adesao>();
	private Integer navegador = 1;
	private String tituloAdesCoop = "";
	private Date dtIni = new Date();
	private Date dtFim = new Date();

	@Autowired
	private AssembleiaDao assembleiaDao;

	@Autowired
	private AdesaoDao adesaoDao;

	public Date getDtIni() {
		return dtIni;
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public String getTituloAdesCoop() {
		return tituloAdesCoop;
	}

	public void setTituloAdesCoop(String tituloAdesCoop) {
		this.tituloAdesCoop = tituloAdesCoop;
	}

	public List<Adesao> getAdesoes() {
		return adesoes;
	}

	public void setAdesoes(List<Adesao> adesoes) {
		this.adesoes = adesoes;
	}

	public Integer getNavegador() {
		return navegador;
	}

	public void setNavegador(Integer navegador) {
		this.navegador = navegador;
	}

	public Assembleia getAssembleia() {
		return assembleia;
	}

	public void setAssembleia(Assembleia assembleia) {
		this.assembleia = assembleia;
	}

	public List<Assembleia> getAssembleias() {
		return assembleias;
	}

	public void setAssembleias(List<Assembleia> assembleias) {
		this.assembleias = assembleias;
	}

	public void preparaIncluir(ActionEvent evt) {
		this.assembleia = new Assembleia();
		this.assembleia.setData(new Date());
		try {
			navegador = 1;
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("assembleiaDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmar(ActionEvent evt) {
		try {
			if (assembleia.getId() == null) {
				if (adesoes.isEmpty()) {
					throw new Exception(
							"Não é possivel gerar um registro de Assembléia sem propostas de adesão pendentes");
				}
				assembleia.gerarCooperados(adesoes);
			}
			assembleiaDao.salvar(assembleia);
			adesaoDao.alterar(getAdesoes());
			listar(evt);
			assembleia = new Assembleia();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("assembleia.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void preparaAlterar(ActionEvent evt) {
		try {
			navegador = 1;
			assembleia = (Assembleia) evt.getComponent().getAttributes()
					.get("assembleia");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("assembleiaDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public StreamedContent getDocumento() {
		InputStream is = new ByteArrayInputStream(assembleia.getDocumento());
		StreamedContent sc = new DefaultStreamedContent(is,
				assembleia.getTipoDoc(), assembleia.getNomeDoc());
		return sc;
	}

	public void fazerUpload(FileUploadEvent event) {
		try {
			FacesMessage msg = new FacesMessage("O arquivo: "
					+ event.getFile().getFileName() + " foi salvo com sucesso!");
			assembleia.setDocumento(event.getFile().getContents());
			assembleia.setNomeDoc(event.getFile().getFileName());
			assembleia.setTipoDoc(event.getFile().getContentType());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro(e.getMessage());
		}

	}

	public void listar(ActionEvent evt) {
		try {
			assembleias.clear();
			Integer id = new Integer(filtroGlobal);
			Assembleia a = assembleiaDao.consultar(id, dtIni, dtFim);
			if (a != null) {
				assembleias.add(a);
			} else {
				assembleias = assembleiaDao.listar(dtIni, dtFim);
			}
		} catch (NumberFormatException e) {
			assembleias = assembleiaDao.listar(dtIni, dtFim);
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro(e.getMessage());
		}
	}

	public void listarAdesoes() {
		try {
			setAdesoes(adesaoDao.listarPorDtAssembleia(
					this.assembleia.getData(), false));
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro(e.getMessage());
		}
	}

	public void avancar() {
		try {
			validarHoraIniHoraFim();
			validarDocumento();
			if (assembleia.getId() == null) {
				tituloAdesCoop = "Propostas de Adesão Pendentes";
				listarAdesoes();
			} else {
				tituloAdesCoop = "Cooperados Aprovados";
				assembleia = assembleiaDao.consultar(assembleia.getId());
			}
			navegador++;
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro(e.getMessage());
		}
	}

	public void voltar() {
		navegador--;
	}

	public Integer getTamanhoLista() {
		return this.assembleias.size();
	}

	public void validarHoraIniHoraFim() throws Exception {
		Integer horaIni = assembleia.getHoraIni().getHours();
		Integer horaFim = assembleia.getHoraFim().getHours();
		if (horaIni >= horaFim) {
			throw new Exception(
					"A hora inicial deve ser menor que a hora final");
		}
	}

	public void validarDocumento() throws Exception {
		if (assembleia.getDocumento() == null) {
			throw new Exception("É necessário informar um documento");
		}
	}
}
