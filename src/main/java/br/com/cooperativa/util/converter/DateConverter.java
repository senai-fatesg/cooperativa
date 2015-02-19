package br.com.cooperativa.util.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.cooperativa.util.Mensagem;

@FacesConverter(value = "dateConverter")
public class DateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			if (arg2.equals("")) {
				return "";
			}
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			Date data = df.parse(arg2);
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
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			return df.format(data);
		} catch (ClassCastException e) {
			return null;
		}
	}
}
