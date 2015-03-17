package br.com.ambientinformatica.senai.universitario.controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.senai.universitario.entidade.Adesao;
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;
import br.com.ambientinformatica.senai.universitario.entidade.CursoTecnico;
import br.com.ambientinformatica.senai.universitario.entidade.Dependente;
import br.com.ambientinformatica.senai.universitario.entidade.EnumEstadoCivil;
import br.com.ambientinformatica.senai.universitario.persistencia.AdesaoDao;
import br.com.ambientinformatica.senai.universitario.persistencia.CidadeDao;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperadoDao;
import br.com.ambientinformatica.senai.universitario.util.Mensagem;
import br.com.ambientinformatica.senai.universitario.util.Util;
import br.com.ambientinformatica.senai.universitario.util.WebServiceCep;

@Controller("AdesaoControl")
@Scope("conversation")
public class AdesaoControl extends Control {

	private Adesao adesao = new Adesao();

	private CursoTecnico curso = new CursoTecnico();

	private Dependente dependente = new Dependente();

	private List<Adesao> adesoes = new ArrayList<Adesao>();

	private Boolean aprovado = false;

	@Autowired
	private AdesaoDao adesaoDao;

	@Autowired
	private CidadeDao cidadeDao;

	@Autowired
	private CooperadoDao cooperadoDao;

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	public CursoTecnico getCurso() {
		return curso;
	}

	public void setCurso(CursoTecnico curso) {
		this.curso = curso;
	}

	public Dependente getDependente() {
		return dependente;
	}

	public void setDependente(Dependente dependente) {
		this.dependente = dependente;
	}

	public Adesao getAdesao() {
		return adesao;
	}

	public void setAdesao(Adesao adesao) {
		this.adesao = adesao;
	}

	public List<Adesao> getAdesoes() {
		return adesoes;
	}

	public void setAdesoes(List<Adesao> adesaos) {
		this.adesoes = adesaos;
	}

	public void preparaIncluir(ActionEvent evt) {
		this.adesao = new Adesao();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("adesaoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmar(ActionEvent evt) {
		try {
			adesao.corrigirRefListas();
			adesaoDao.salvar(adesao);
			listar(evt);
			adesao = new Adesao();
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("adesao.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			adesoes.clear();
			Integer id = new Integer(filtroGlobal);
			Adesao a = adesaoDao.consultar(id);
			if (a != null) {
				adesoes.add(a);
			} else {
				filtrarPorNomeOuCpf();
			}
		} catch (NumberFormatException e) {
			filtrarPorNomeOuCpf();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void filtrarPorNomeOuCpf() {
		adesoes = adesaoDao.listarPorCpf(filtroGlobal, aprovado);
		if (adesoes.isEmpty()) {
			adesoes = adesaoDao.listarPorNome(filtroGlobal, aprovado);
		}
	}

	public void preparaAlterar(ActionEvent evt) {
		try {
			adesao = (Adesao) evt.getComponent().getAttributes().get("adesao");
			adesao = adesaoDao.consultar(adesao.getId());
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("adesaoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void prepararVisualizar(ActionEvent evt) {
		try {
			adesao = (Adesao) evt.getComponent().getAttributes().get("adesao");
			adesao = adesaoDao.consultar(adesao.getId());
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("adesaoVisualizacao.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void prepararExcluir(ActionEvent evt) {
		try {
			adesao = (Adesao) evt.getComponent().getAttributes().get("adesao");
			adesao = adesaoDao.consultar(adesao.getId());
			excluindo = true;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmarExcluir(ActionEvent evt) {
		try {
			adesao = adesaoDao.consultar(adesao.getId());
			adesao.setDtExclusao(new Date());
			adesaoDao.alterar(adesao);
			listar(null);
			excluindo = false;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void cancelarExcluir(ActionEvent evt) {
		try {
			excluindo = false;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public String reiniciarDependente() {
		this.dependente = new Dependente();
		return null;
	}

	public String reiniciarCurso() {
		this.curso = new CursoTecnico();
		return null;
	}

	public EnumEstadoCivil[] getEstadosCivis() {
		return EnumEstadoCivil.values();
	}

	public List<Cidade> completeCidades(String query) {
		List<Cidade> resultados = cidadeDao.listarPorNome(query);
		return resultados;
	}

	public List<Cooperado> completeCooperados(String query) {
		List<Cooperado> resultados = cooperadoDao.listarPorNome(query);
		return resultados;
	}

	public Integer getTamanhoLista() {
		return this.adesoes.size();
	}

	public void preencherEndereco() {
		try {
			if (adesao.getDadosPessoais().getEndereco().getCep() != null) {
				if (!adesao.getDadosPessoais().getEndereco().getCep().isEmpty()) {
					WebServiceCep loadCep = WebServiceCep.searchCep(adesao
							.getDadosPessoais().getEndereco().getCep());
					adesao.getDadosPessoais().getEndereco()
					.setBairro(loadCep.getBairro());
					adesao.getDadosPessoais().getEndereco()
					.setLogradouro(loadCep.getLogradouroFull());
					String nomeCidade = Util.removeAcentos(loadCep.getCidade());
					String ufCidade = loadCep.getUf();
					Cidade c = cidadeDao.consultar(nomeCidade, ufCidade);
					adesao.getDadosPessoais().getEndereco().setCidade(c);
				}
			}
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro(e.getMessage());
		}
	}

	public void validarDtNasc() {
		try {
			if (adesao.getDadosPessoais().getDataNascimento() != null) {
				Date dtAtual = new Date();
				if (adesao.getDadosPessoais().getDataNascimento()
						.after(dtAtual)) {
					adesao.getDadosPessoais().setDataNascimento(null);
					throw new Exception(
							"A dada de nascimento deve ser inferior a data Atual");
				}
			}
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro(e.getMessage());
		}
	}
}