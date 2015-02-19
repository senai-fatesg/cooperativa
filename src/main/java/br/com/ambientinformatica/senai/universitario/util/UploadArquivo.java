package br.com.ambientinformatica.senai.universitario.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;

import org.primefaces.event.FileUploadEvent;

/**
 * Método que faz o upload de arquivos para o ambiente web
 * Ele irá carregar as imagens das atas das assembléias
 *@author davinc131
 */

public class UploadArquivo {

	public void carregaArquivo(FileUploadEvent event) throws FileNotFoundException, IOException
	{
		try
		{
			FacesMessage msg = new FacesMessage("Sucesso", event.getFile().getFileName() + " foi carregado.");  
	        FacesContext.getCurrentInstance().addMessage("teste", msg);  
	  
	        String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("" + "\\fotos\\" + event.getFile().getFileName());  
	  
	        byte[] conteudo = event.getFile().getContents();  
	        FileOutputStream fos = new FileOutputStream("caminho");  
	        fos.write(conteudo);  
	        fos.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Erro ao Carregar Arquivo: " + e.getMessage());
		}
	}
	
}
