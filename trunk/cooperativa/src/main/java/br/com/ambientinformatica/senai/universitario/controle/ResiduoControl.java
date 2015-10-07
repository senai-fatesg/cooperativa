package br.com.ambientinformatica.senai.universitario.controle;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;
import br.com.ambientinformatica.senai.universitario.entidade.Residuo;
import br.com.ambientinformatica.senai.universitario.persistencia.ResiduoDao;

@Controller("residuoControl")
@Scope("conversation")
public class ResiduoControl extends Control {

	private Residuo residuo = new Residuo();

	private List<Residuo> residuos = new ArrayList<Residuo>();

	@Autowired
	private ResiduoDao residuoDao;

	public void preparaIncluir(ActionEvent evt) {
		this.residuo = new Residuo();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("residuoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmar(ActionEvent evt) {
		try {
			residuoDao.salvar(residuo);
			listar(evt);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("residuo.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public Residuo getContato() {
		return residuo;
	}

	public void setResiduo(Residuo residuo) {
		this.residuo = residuo;
	}

	public ResiduoDao getResiduoDao() {
		return residuoDao;
	}

	public void setResiduoDao(ResiduoDao residuoDao) {
		this.residuoDao = residuoDao;
	}

	public Residuo getResiduo() {
		return residuo;
	}

	public void setResiduos(List<Residuo> residuos) {
		this.residuos = residuos;
	}

	public List<Residuo> getResiduos() {
		return residuos;
	}

	// public String retornaDataAtual() {
	// DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	// Date date = new Date();
	// return dateFormat.format(date);
	//
	// }

	public void listar(ActionEvent evt) {
		try {
			residuos.clear();

			Integer id = new Integer(filtroGlobal);
			Residuo r = residuoDao.consultar(id);
			if (r != null) {
				residuos.add(r);
			} else {
				filtrarPorDescricao();
			}
		} catch (NumberFormatException e) {
			filtrarPorDescricao();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void filtrarPorDescricao() {
		residuos = residuoDao.listarPorDescricao(filtroGlobal);

	}

	public Integer getTamanhoLista() {
		return this.residuos.size();
	}

	public void preparaAlterar(ActionEvent evt) {
		try {
			residuo = (Residuo) evt.getComponent().getAttributes().get("residuo");
			residuo = residuoDao.consultar(residuo.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("residuoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void prepararExcluir(ActionEvent evt) {
		try {
			residuo = (Residuo) evt.getComponent().getAttributes().get("residuo");
			residuo = residuoDao.consultar(residuo.getId());
			excluindo = true;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmarExcluir(ActionEvent evt) {
		try {
			residuo = residuoDao.consultar(residuo.getId());
			residuo.setDtExclusao(new Date());
			residuoDao.alterar(residuo);
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
}
