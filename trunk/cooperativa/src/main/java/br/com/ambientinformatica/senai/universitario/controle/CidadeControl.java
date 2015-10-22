package br.com.ambientinformatica.senai.universitario.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.persistencia.CidadeDao;

@Controller("CidadeControl")
@Scope("conversation")
public class CidadeControl {

	@Autowired
	private CidadeDao cidadeDao;

	private List<Cidade> cidades = new ArrayList<Cidade>();

	@PostConstruct
	private void init(){
		carregarListaLeitura(null);
	}

	public void carregarListaLeitura(ActionEvent evt) {
		try {
			this.cidades = cidadeDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public List<Cidade> completeCidades(String query) {
		List<Cidade> resultados = new ArrayList<Cidade>();
		for (Cidade cidade : cidades) {
			if (cidade.contem(query)) {
				resultados.add(cidade);
			}
		}
		return resultados;
	}
}
