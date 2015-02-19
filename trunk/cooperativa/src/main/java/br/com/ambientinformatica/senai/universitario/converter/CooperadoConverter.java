package br.com.ambientinformatica.senai.universitario.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.jpa.util.FabricaAbstrata;
import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperadoDao;

@FacesConverter(forClass = Cooperado.class, value = "cooperadoConverter")
public class CooperadoConverter implements Converter {
	private CooperadoDao cooperadoDao = (CooperadoDao) FabricaAbstrata
			.criarObjeto("cooperadoDao");

	@Override
	public Object getAsObject(FacesContext facesContext,
			UIComponent uiComponent, String value) {
		if (value.trim().equals("")) {
			return null;
		} else {
			try {
				Integer number = Integer.parseInt(value);
				Cooperado c = cooperadoDao.consultar(number);
				return c;

			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Erro de Conversao",
						"Cooperado inválido"));
			} catch (Exception e) {

			}
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext,
			UIComponent uiComponent, Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Cooperado) value).getId());
		}
	}
}
