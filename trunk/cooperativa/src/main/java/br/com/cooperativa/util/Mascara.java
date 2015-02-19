package br.com.cooperativa.util;

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
