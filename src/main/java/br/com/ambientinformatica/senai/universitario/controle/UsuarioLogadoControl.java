package br.com.ambientinformatica.senai.universitario.controle;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.senai.universitario.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.senai.universitario.entidade.PapelUsuario;
import br.com.ambientinformatica.senai.universitario.entidade.Usuario;
import br.com.ambientinformatica.senai.universitario.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.UtilHash;
import br.com.ambientinformatica.util.UtilLog;
import br.com.ambientinformatica.util.UtilHash.Algoritimo;

@Controller("UsuarioLogadoControl")
@Scope("conversation")
public class UsuarioLogadoControl {

    private Usuario usuario;
    private String senhaAlteracao;
    private String senhaAlteracaoNovamente;
    private String senhaAtual;

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
    
    public void alterarSenhaDoUsuario(){
        try {
            if(!usuario.getSenha().equals(UtilHash.gerarStringHash(senhaAtual, Algoritimo.MD5)) 
                    || senhaAlteracao.isEmpty() || senhaAlteracaoNovamente.isEmpty()){
                UtilFaces.addMensagemFaces("Campos não preenchidos, ou senha atual inválida ");    
            }else if(senhaAlteracao.equals(senhaAlteracaoNovamente)){
                usuario.setSenhaNaoCriptografada(senhaAlteracao);
                usuarioDao.alterar(usuario);
                UtilFaces.addMensagemFaces("Senha alterada com sucesso ");
            }else{
                UtilFaces.addMensagemFaces("As senhas digitadas não conferem, digite novamente");
            }
        } catch (PersistenciaException e) {
            UtilLog.getLog().error(e.getMessage(), e);
            UtilFaces.addMensagemFaces("A senha não foi alterada");
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

    public String getSenhaAlteracao() {
        return senhaAlteracao;
    }

    public void setSenhaAlteracao(String senhaAlteracao) {
        this.senhaAlteracao = senhaAlteracao;
    }

    public String getSenhaAlteracaoNovamente() {
        return senhaAlteracaoNovamente;
    }

    public void setSenhaAlteracaoNovamente(String senhaAlteracaoNovamente) {
        this.senhaAlteracaoNovamente = senhaAlteracaoNovamente;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }
    

}
