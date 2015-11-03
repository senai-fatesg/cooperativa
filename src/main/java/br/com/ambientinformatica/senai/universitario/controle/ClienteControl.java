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
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.entidade.Cliente;
import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;
import br.com.ambientinformatica.senai.universitario.entidade.EnumEstadoCivil;
import br.com.ambientinformatica.senai.universitario.persistencia.CidadeDao;
import br.com.ambientinformatica.senai.universitario.persistencia.ClienteDao;
import br.com.ambientinformatica.senai.universitario.util.Mensagem;
import br.com.ambientinformatica.senai.universitario.util.Util;
import br.com.ambientinformatica.senai.universitario.util.WebServiceCep;

@Controller("clienteControl")
@Scope("conversation")
public class ClienteControl extends Control {

	private Cliente cliente = new Cliente();

	private List<Cliente> clientes = new ArrayList<Cliente>();

	@Autowired
	private ClienteDao clienteDao;

	@Autowired
	private CidadeDao cidadeDao;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	public CidadeDao getCidadeDao() {
		return cidadeDao;
	}

	public void setCidadeDao(CidadeDao cidadeDao) {
		this.cidadeDao = cidadeDao;
	}

	public void preparaIncluir(ActionEvent evt) {
		this.cliente = new Cliente();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("clienteDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmar(ActionEvent evt) {
		try {
			clienteDao.salvar(cliente);
			listar(evt);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("cliente.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			clientes.clear();
			Integer id = new Integer(filtroGlobal);
			Cliente c = clienteDao.consultar(id);
			if (c != null) {
				clientes.add(c);
			} else {
				filtrarPorNome();
			}
		} catch (NumberFormatException e) {
			filtrarPorNome();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void filtrarPorNome() {
		if (clientes.isEmpty()) {
			clientes = clienteDao.listarPorNome(filtroGlobal);
		}
	}

	public void preparaAlterar(ActionEvent evt) {
		try {
			cliente = (Cliente) evt.getComponent().getAttributes()
					.get("cliente");
			cliente = clienteDao.consultar(cliente.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("clienteDetalhes.jsf");
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
		return this.clientes.size();
	}

	public void prepararExcluir(ActionEvent evt) {
		try {
			cliente = (Cliente) evt.getComponent().getAttributes()
					.get("cliente");
			cliente = clienteDao.consultar(cliente.getId());
			excluindo = true;
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void confirmarExcluir(ActionEvent evt) {
		try {
			cliente = clienteDao.consultar(cliente.getId());
			cliente.setDtExclusao(new Date());
			clienteDao.alterar(cliente);
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
			if (cliente.getEndereco().getCep() != null) {
				if (!cliente.getEndereco().getCep().isEmpty()) {
					WebServiceCep loadCep = WebServiceCep.searchCep(cliente
							.getEndereco().getCep());
					cliente.getEndereco().setBairro(loadCep.getBairro());
					cliente.getEndereco().setLogradouro(loadCep.getLogradouroFull());
					String nomeCidade = Util.removeAcentos(loadCep.getCidade());
					String ufCidade = loadCep.getUf();
					Cidade c = cidadeDao.consultar(nomeCidade, ufCidade);
					cliente.getEndereco().setCidade(c);
				}
			}
		} catch (Exception e) {
			Mensagem.mostrarMensagemErro("Cep inválido, tente novamente");
		}
	}

}
