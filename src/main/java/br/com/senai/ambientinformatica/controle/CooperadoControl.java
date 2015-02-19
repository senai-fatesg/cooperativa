package br.com.senai.ambientinformatica.controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.cooperativa.entidade.Adesao;
import br.com.cooperativa.entidade.Cidade;
import br.com.cooperativa.entidade.Cooperado;
import br.com.cooperativa.entidade.EnumEstadoCivil;
import br.com.cooperativa.persistencia.CidadeDao;
import br.com.cooperativa.persistencia.CooperadoDao;
import br.com.cooperativa.util.Mensagem;
import br.com.cooperativa.util.Util;
import br.com.cooperativa.util.WebServiceCep;

@Controller("CooperadoControl")
@Scope("conversation")
public class CooperadoControl extends Control{

	private Cooperado cooperado = new Cooperado();
	private List<Cooperado> cooperados = new ArrayList<Cooperado>();

	@Autowired
	private CooperadoDao cooperadoDao;
	@Autowired
	private CidadeDao cidadeDao;

	public Cooperado getCooperado() {
		return cooperado;
	}

	public void setCooperado(Cooperado cooperado) {
		this.cooperado = cooperado;
	}

	public List<Cooperado> getCooperados() {
		return cooperados;
	}

	public void setCooperados(List<Cooperado> cooperados) {
		this.cooperados = cooperados;
	}

	public void preparaIncluir(ActionEvent evt) {
		this.cooperado = new Cooperado();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("cooperadoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmar(ActionEvent evt) {
		try {
			cooperadoDao.salvar(cooperado);
			listar(evt);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("cooperado.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			cooperados.clear();
			Integer id = new Integer(filtroGlobal);
			Cooperado c = cooperadoDao.consultar(id);
			if (c != null) {
				cooperados.add(c);
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
		cooperados = cooperadoDao.listarPorCpf(filtroGlobal);
		if (cooperados.isEmpty()) {
			cooperados = cooperadoDao.listarPorNome(filtroGlobal);
		}
	}

	public void preparaAlterar(ActionEvent evt) {
		try {
			cooperado = (Cooperado) evt.getComponent().getAttributes()
					.get("cooperado");
			cooperado = cooperadoDao.consultar(cooperado.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("cooperadoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public EnumEstadoCivil[] getEstadosCivis() {
		return EnumEstadoCivil.values();
	}

	public List<Cidade> completeCidades(String query) {
		List<Cidade> resultados = cidadeDao.listarPorNome(query);
		return resultados;
	}

	public Integer getTamanhoLista() {
		return this.cooperados.size();
	}

	public void prepararExcluir(ActionEvent evt) {
		try {
			cooperado = (Cooperado) evt.getComponent().getAttributes()
					.get("cooperado");
			cooperado = cooperadoDao.consultar(cooperado.getId());
			excluindo = true;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmarExcluir(ActionEvent evt) {
		try {
			cooperado = cooperadoDao.consultar(cooperado.getId());
			cooperado.setDtExclusao(new Date());
			cooperadoDao.alterar(cooperado);
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

	public void preencherEndereco() {
		try {
			if (cooperado.getDadosPessoais().getEndereco().getCep() != null) {
				if (!cooperado.getDadosPessoais().getEndereco().getCep()
						.isEmpty()) {
					WebServiceCep loadCep = WebServiceCep.searchCep(cooperado
							.getDadosPessoais().getEndereco().getCep());
					cooperado.getDadosPessoais().getEndereco()
							.setBairro(loadCep.getBairro());
					cooperado.getDadosPessoais().getEndereco()
							.setLogradouro(loadCep.getLogradouroFull());
					String nomeCidade = Util.removeAcentos(loadCep.getCidade());
					String ufCidade = loadCep.getUf();
					Cidade c = cidadeDao.consultar(nomeCidade, ufCidade);
					cooperado.getDadosPessoais().getEndereco().setCidade(c);
				}
			}
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro(e.getMessage());
		}
	}

	public void validarDtNasc() {
		try {
			if (cooperado.getDadosPessoais().getDataNascimento() != null) {
				Date dtAtual = new Date();
				if (cooperado.getDadosPessoais().getDataNascimento()
						.after(dtAtual)) {
					cooperado.getDadosPessoais().setDataNascimento(null);
					throw new Exception(
							"A dada de nascimento deve ser inferior a data Atual");
				}
			}
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro(e.getMessage());
		}
	}

}
