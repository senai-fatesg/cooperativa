package br.com.senai.ambient.controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.cooperativa.entidade.Cidade;
import br.com.cooperativa.entidade.EnumTipoTelefone;
import br.com.cooperativa.entidade.Pessoa;
import br.com.cooperativa.entidade.Telefone;
import br.com.cooperativa.persistencia.CidadeDao;
import br.com.cooperativa.persistencia.CooperativaDao;

@Controller("cooperativaControl")
@Scope("conversation")
public class CooperativaControl {
	
	//FAZER CONSULTA DA LISTA DE TELEFONE LIGADO A PESSOA CONSULTADA PELA TABELA DE PESSOAS
	//APÓS A PRIMEIRA CONSULTA A LISTA DE TELEFONE DA PESSOA É ELIMINADA DA MEMÓRIA
	
	private Pessoa cooperativa = new Pessoa();

	private List<Pessoa> lista = new ArrayList<Pessoa>(); 
	
	private String filtro = new String();
	private boolean rSocial = false;
	private boolean nFantasia = false;
	private Telefone telefone = new Telefone();
	private List<String> listString = new ArrayList<String>();
	private List<Pessoa> listPessoa = new ArrayList<Pessoa>();
	private List<Cidade> cidades = new ArrayList<Cidade>();

	@Autowired
	private CooperativaDao cooperativaDao;
	
	@Autowired
	private CidadeDao cidadeDao;
	
	@PostConstruct
	public void init(){
		cooperativa = new Pessoa();
		listString = new ArrayList<String>();
		listPessoa = new ArrayList<Pessoa>();
		cidades = new ArrayList<Cidade>();
	}

	// ---------------Métodos de Servico-------------//
	// ----- Redireciona para pagina de cadastro-----//
	public void preparaIncluir(ActionEvent evt) {
		this.cooperativa = new Pessoa();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("empresaDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	
	public void prepararEditar(ActionEvent evt) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("empresaDetalhes.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro: "+e.getMessage());
		}
	}
	
	public Pessoa consultaCooperativa(ActionEvent evt) {
		try {
			cooperativa = cooperativaDao.consultar(cooperativa.getId());
			return cooperativa;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro na consulta de dados: "+e.getMessage());
			return null;
		}
	}

	public void tipoListagem(Number i) {
		if (this.rSocial == true) {
			consultarPorRazao(this.filtro);
		} else if (this.nFantasia == true) {
			consultarPorFantasia(filtro);
		} else {
			UtilFaces.addMensagemFaces("O filtro deve ser selecionado!");
		}
	}

	public void incluir(ActionEvent evt) {
		try {
			cooperativaDao.incluir(cooperativa);
			cooperativa = new Pessoa();
			UtilFaces.addMensagemFaces("Cadastro efetuado com sucesso!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao Inserir dados: "+e.getMessage());
		}
	}
	
	public void alterar(ActionEvent evt) {
		try {
			cooperativaDao.alterar(cooperativa);
			cooperativa = new Pessoa();
			UtilFaces.addMensagemFaces("Alteração efetuada com sucesso!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao Alterar dados: "+e.getMessage());
		}
	}
	
	public List<Pessoa> listarPessoa()
	{
		try {
			return listPessoa = cooperativaDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao Listar: "+e.getMessage());
			return null;
		}
	}

	public Pessoa consultarPorRazao(String r) {
		try {
			if (cooperativa.getRazaoSocial().equals(null)) {
				UtilFaces.addMensagemFaces("O Campo Razão Social da empresa precisa ser preenchido!");
				return null;
			} else {
				cooperativa = cooperativaDao.consultarPorRazao(r);
				return cooperativa;
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
			return null;
		}
	}

	public Pessoa consultarPorFantasia(String f) {
		try {
			if (cooperativa.getNomeFantasia().equals(null)) {
				UtilFaces.addMensagemFaces("O Campo Nome Fantasia da empresa precisa ser preenchido!");
				return null;
			} else {
				cooperativa = cooperativaDao.consultarPorFantasia(f);
				return cooperativa;
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
			return null;
		}
	}

	public void excluirCooperativa(int id) {
		try {
			cooperativaDao.excluirPorId(id);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}

	}

	public List<Pessoa> listar() {
		try {
			return null;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
			return null;
		}
	}

	public void addTelefone(ActionEvent evt) {
		try {
			cooperativa.addTelefone(telefone);
			telefone = new Telefone();
			UtilFaces.addMensagemFaces("Telefone adicionado com sucesso.");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro: " + e);
		}
	}

	public void setRemoveTelefone(Telefone tel) {
		try {
			cooperativa.removeTelefone(tel);
			UtilFaces.addMensagemFaces("Telefone removido com sucesso.");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro: " + e);
		}
	}

	// Retorna uma lista do enumerador TipoTelefone
	public List<SelectItem> getTipoTelefone() {
		return UtilFaces.getListEnum(EnumTipoTelefone.values());
	}
	
	
	//--------------------------GETS E SETS--------------------------//
	
	public List<Pessoa> getLista() {
		return lista;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<String> getListString() {
		return listString;
	}

	public void setListString(List<String> listString) {
		this.listString = listString;
	}

	public List<Pessoa> getListPessoa() {
		return listPessoa;
	}

	public void setListPessoa(List<Pessoa> listPessoa) {
		this.listPessoa = listPessoa;
	}

	public void setLista(List<Pessoa> lista) {
		this.lista = lista;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public boolean isrSocial() {
		return rSocial;
	}

	public void setrSocial(boolean rSocial) {
		this.rSocial = rSocial;
	}

	public boolean isnFantasia() {
		return nFantasia;
	}

	public void setnFantasia(boolean nFantasia) {
		this.nFantasia = nFantasia;
	}

	public Pessoa getCooperativa() {
		return cooperativa;
	}

	public void setCooperativa(Pessoa cooperativa) {
		this.cooperativa = cooperativa;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

}
