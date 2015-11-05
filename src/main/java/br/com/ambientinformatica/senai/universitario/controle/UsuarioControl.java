package br.com.ambientinformatica.senai.universitario.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.senai.universitario.entidade.PapelUsuario;
import br.com.ambientinformatica.senai.universitario.entidade.Usuario;
import br.com.ambientinformatica.senai.universitario.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.UtilLog;

@Controller("UsuarioControl")
@Scope("conversation")
public class UsuarioControl {
    
    private Usuario usuario; 
    private Usuario usuarioExcluir;
    private String nome;
    private List<PapelUsuario>papeis;
    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();

    @Autowired
    private UsuarioDao usuarioDao;


    @PostConstruct
    public void init(){
        usuario = new Usuario();
    }

    public List<Usuario> listar(){
        try {
        	listaUsuarios = usuarioDao.listar();
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Nenhum dado encontrado");
        }
        return listaUsuarios;
    }
    
    public List<Usuario> listarPorNomeOuTodos(){
        try {
            listaUsuarios = usuarioDao.listarPorNome(usuario);
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Nenhum dado encontrado");
        }
        return listaUsuarios;
    }

    public List<PapelUsuario> mostrarPapeisDoUsuario(){
        papeis = usuario.getPapeis();
        return papeis;
    }

    public void excluirUsuario(ActionEvent evt){
        try {
            usuarioDao.excluirPorId(usuarioExcluir.getId());
            UtilFaces.addMensagemFaces("Usuário excluido com sucesso");
        } catch (Exception e) {
            UtilLog.getLog().error(e.getMessage());
            UtilFaces.addMensagemFaces("Erro ao excluir o usuário");
        }
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioExcluir() {
        return usuarioExcluir;
    }

    public void setUsuarioExcluir(Usuario usuarioExcluir) {
        this.usuarioExcluir = usuarioExcluir;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public List<PapelUsuario> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<PapelUsuario> papeis) {
        this.papeis = papeis;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	} 
    
}
