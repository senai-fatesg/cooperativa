package br.com.ambientinformatica.senai.universitario.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.jpa.util.FabricaAbstrata;
import br.com.ambientinformatica.senai.universitario.entidade.Pessoa;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperativaDao;

@FacesConverter(forClass = Pessoa.class, value = "pessoaConverter")
public class CooperativaConverter implements Converter{

	public static List<Pessoa> listaCooperativa;
	Pessoa pessoa = new Pessoa();
	private CooperativaDao cooperativaDao = (CooperativaDao) FabricaAbstrata.criarObjeto("cooperativaDao");

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null && !value.trim().equals("")) {
			try {
				String idString[] = value.split("-");
				String idSt = idString[0].trim();
				Long id = Long.parseLong(idSt);
				pessoa.setId(id);

				pessoa = cooperativaDao.consultar(pessoa.getId());
			} catch (Exception e) {
				return null;
			}
		}
		return pessoa;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		try {
			if (value == null || value.equals("")) {
				return "";
			}
			else
			{
				return String.valueOf(((Pessoa)value).getId() + " - " + ((Pessoa)value).getNomeFantasia());
			}
		} catch (Exception e) {
			return null;
		}
	}
}
