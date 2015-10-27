package br.com.ambientinformatica.senai.universitario.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.senai.universitario.converter.DateConverter;
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.senai.universitario.entidade.PapelUsuario;
import br.com.ambientinformatica.senai.universitario.entidade.Pessoa;
import br.com.ambientinformatica.senai.universitario.entidade.Usuario;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperadoDao;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperativaDao;
import br.com.ambientinformatica.senai.universitario.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.UtilHash;
import br.com.ambientinformatica.util.UtilHash.Algoritimo;

@Controller("UsuarioLogadoControl")
@Scope("conversation")
public class UsuarioLogadoControl {

    private Usuario usuario;

    @Autowired
    private UsuarioDao usuarioDao;

    @SuppressWarnings("unused")
    @PostConstruct
    public void init(){
            String email = UtilFaces.getRequest().getUserPrincipal().getName();
            buscarUsuario();
    }
 
    private void buscarUsuario() {
        try {
            HttpServletRequest req = UtilFaces.getRequest();
            if (req.getUserPrincipal() != null) {
                String login = req.getUserPrincipal().getName();
                usuario = usuarioDao.consultarPorUsuario(login);
            }
        } catch (Exception e) {
            UtilFaces.addMensagemFaces(e);
        }
    }

    public boolean isLogado() {
        return getUsuarioLogado() != null;
    }

    public boolean isAdministrador() {
        for (PapelUsuario p : usuario.getPapeis()) {
            if (p.getPapel() == EnumPapelUsuario.ADMIN) {
                return true;
            }
        }
        return false;
    }

    public String getIp(){
        return UtilFaces.getRequest().getHeader("X-FORWARDED-FOR");
    }

    public static Usuario getUsuarioLogado() {
        return (Usuario) UtilFaces.getObjetoManagedBean("#{UsuarioLogadoControl.usuario}");
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
