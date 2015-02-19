/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ambientinformatica.senai.universitario.util;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 *
 * Alteração em: 26/10/2013<br>
 * 1-Criou classe Mensagem
 *
 * @author Kellerman
 */
public class Mensagem {

    private static ArrayList<String> lstMensagem = new ArrayList<String>();

    public static void addNovaMensagemAviso(String mensagem) {
        lstMensagem.add(mensagem);
    }

    /*Metodos Auxiliares*/
    public static void mostrarMensagemErro(String mensagem) {
        FacesContext.getCurrentInstance().
                addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                mensagem,
                                ""));
    }

    public static void mostrarMensagemAviso(String mensagem) {
        FacesContext.getCurrentInstance().
                addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                mensagem,
                                ""));
    }

    public static void mostrarMensagens() {
        for (int i = 0; i < lstMensagem.size(); i++) {
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    lstMensagem.get(i),
                                    ""));
        }
        lstMensagem.clear();
    }
}
