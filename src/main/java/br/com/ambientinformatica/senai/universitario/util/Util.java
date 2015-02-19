package br.com.ambientinformatica.senai.universitario.util;

import java.text.Normalizer;

public class Util {

	public static String removeMascara(String parametro) {
		String retorno = parametro;
		retorno = retorno.replace(".", "");
		retorno = retorno.replace("/", "");
		retorno = retorno.replace("-", "");
		retorno = retorno.replace("_", "");
		retorno = retorno.replace("(", "");
		retorno = retorno.replace(")", "");
		return retorno;
	}

	public static String removeAcentos(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}
}
