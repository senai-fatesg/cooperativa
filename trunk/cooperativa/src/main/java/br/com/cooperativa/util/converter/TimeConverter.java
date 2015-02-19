package br.com.cooperativa.util.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.cooperativa.util.Mensagem;

@FacesConverter(value = "timeConverter")
public class TimeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			if (arg2.equals("")) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			Date data = sdf.parse(arg2);
			return data;
		} catch (ParseException e) {
			Mensagem.mostrarMensagemErro("Formato de Data Invalido");
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		try {
			Date data = (Date) arg2;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			return sdf.format(data);
		} catch (ClassCastException e) {
			return null;
		}
	}
}
