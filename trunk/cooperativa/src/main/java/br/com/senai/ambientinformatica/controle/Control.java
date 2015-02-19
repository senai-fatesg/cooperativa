package br.com.senai.ambientinformatica.controle;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

public abstract class Control {
	protected String filtroGlobal = "";
	protected Boolean excluindo = false;

	@PostConstruct
	public void init() {
		listar(null);
	}

	public Boolean getExcluindo() {
		return excluindo;
	}

	public void setExcluindo(Boolean excluindo) {
		this.excluindo = excluindo;
	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}

	public abstract void listar(ActionEvent evt);
}
