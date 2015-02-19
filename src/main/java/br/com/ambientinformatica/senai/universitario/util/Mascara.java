package br.com.ambientinformatica.senai.universitario.util;

public class Mascara {

    public static String remove(String parametro) {
        String retorno = parametro;
        retorno = retorno.replace(".", "");
        retorno = retorno.replace("/", "");
        retorno = retorno.replace("-", "");
        retorno = retorno.replace("_", "");
        retorno = retorno.replace("(", "");
        retorno = retorno.replace(")", "");
        return retorno;
    }
}
