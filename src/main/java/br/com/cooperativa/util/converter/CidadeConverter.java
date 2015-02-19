package br.com.cooperativa.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;
import br.com.cooperativa.entidade.Cidade;
import br.com.cooperativa.persistencia.CidadeDao;

@FacesConverter("cidadeConverter")
public class CidadeConverter implements Converter {

	private CidadeDao cidadeDao = (CidadeDao) FabricaAbstrata
			.criarObjeto("cidadeDao");

	@Override
	public Object getAsObject(FacesContext facesContext,
			UIComponent uiComponent, String value) {
		if (value != null && !value.trim().equals("")) {
			Cidade cidade = new Cidade();
			try {
				int id = Integer.parseInt(value);

				try {
					cidade = cidadeDao.consultar(id);
				} catch (PersistenciaException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException exception) {
				// throw new ConverterException(new
				// FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error",
				// "Funcionario escolhido não é válido"));
				return null;
			}
			return cidade;
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext,
			UIComponent uiComponent, Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Cidade) value).getIbge());
		}
	}
}
