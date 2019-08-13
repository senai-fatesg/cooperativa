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

import com.sun.faces.facelets.tag.jstl.fn.JstlFunction;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;
import br.com.ambientinformatica.senai.universitario.entidade.EnumEstadoCivil;
import br.com.ambientinformatica.senai.universitario.persistencia.CidadeDao;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperadoDao;
import br.com.ambientinformatica.senai.universitario.util.Mensagem;
import br.com.ambientinformatica.senai.universitario.util.Util;
import br.com.ambientinformatica.senai.universitario.util.WebServiceCep;
import br.com.ambientinformatica.util.UtilArquivo;
import br.com.ambientinformatica.util.UtilCpf;

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
			cooperado = (Cooperado) evt.getComponent().getAttributes().get("cooperado");
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
			cooperado = (Cooperado) evt.getComponent().getAttributes().get("cooperado");
			cooperado = cooperadoDao.consultar(cooperado.getId());
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
			if(cooperado.getDadosPessoais().getEndereco().getCep() != null) {
				if(!cooperado.getDadosPessoais().getEndereco().getCep().isEmpty()) {
					WebServiceCep loadCep = WebServiceCep.searchCep(cooperado.getDadosPessoais().getEndereco().getCep());
					cooperado.getDadosPessoais().getEndereco().setBairro(loadCep.getBairro());
					cooperado.getDadosPessoais().getEndereco().setLogradouro(loadCep.getLogradouroFull());
					String nomeCidade = Util.removeAcentos(loadCep.getCidade());
					String ufCidade = loadCep.getUf();
					Cidade c = cidadeDao.consultar(nomeCidade, ufCidade);
					cooperado.getDadosPessoais().getEndereco().setCidade(c);
				}
			}
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro("Cep inválido, tente novamente");
		}
	}

	public void validarDataNascimento() {
		try {
			Date dtAtual = new Date();

			if(cooperado.getDadosPessoais().getDataNascimento().after(dtAtual)){
				cooperado.getDadosPessoais().setDataNascimento(null);
				Mensagem.mostrarMensagemErro("A dada de nascimento deve ser inferior a data Atual");
			}

		} catch (Exception e) {
			Mensagem.mostrarMensagemErro("Necessário Informar a data de Nascimento");
		}
	}
		
	public void validarCpf() {
		
		if(!UtilCpf.validarCpf(cooperado.getDadosPessoais().getCpf())) {
			Mensagem.mostrarMensagemErro("Necessário Informar um CPF Valido.");
		}
		
	}

}
