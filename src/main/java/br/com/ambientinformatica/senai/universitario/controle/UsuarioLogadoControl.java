package br.com.ambientinformatica.senai.universitario.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.senai.universitario.converter.DateConverter;
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.entidade.EnumPapelUsuario;
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

    private List<Usuario> listaUsuario;

    DateConverter dateConverter;

    private EnumPapelUsuario papel;

    private Cidade cidade;

    private List<Pessoa> lista;
    
    private boolean ativo;
    
    private String status;
    
    private String senhaConfirmacao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private CooperativaDao cooperativaDao;

    @Autowired
    private CooperadoDao cooperadoDao;

    @SuppressWarnings("unused")
    @PostConstruct
    public void init(){
        try {
            usuario = new Usuario();
            listaUsuario = new ArrayList<Usuario>();
            String email = UtilFaces.getRequest().getUserPrincipal().getName();
            dateConverter = new DateConverter();
            cidade = new Cidade();
            cidade.setNome("Goiania");
            cidade.setUf("GO");
            lista = new ArrayList<Pessoa>();
            listarCooperativas( );
            setRemoverPapel(null);
            mostrarStatusDoUsuario();
        } catch (Exception e) {
            UtilFaces.addMensagemFaces(e);
        }
    }
    

    public void prepararIncluir(ActionEvent evt) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("usuarioDetalhes.jsf");
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Erro ao Navegar para Detalhes: "+e.getMessage());
        }
    }

    public void incluir(ActionEvent evt) {
        try {

            int cont = 0;

            for(int i = 0; i < usuario.getPapeis().size(); i++){
                if(usuario.getPapeis().get(i).getPapel().equals("")){
                    cont++;
                }
            }

            
            if(cont > 0){
                UtilFaces.addMensagemFaces("Advertência: Papel de usuário nulo!");
            }
            if(usuario.getPapeis().size() == 0){
                UtilFaces.addMensagemFaces("Advertência: É necessário adcionar ao menos um papel para o usuário!");
            }
            if(usuario.getLogin().isEmpty()){
                UtilFaces.addMensagemFaces("Advertência: Por favor, preenha o campo login!");
            }
            if(usuario.getSenha().isEmpty()){
                UtilFaces.addMensagemFaces("Advertência: O campo senha precisa ser preenchido!");
            }
            if(!usuario.getSenha().equals(senhaConfirmacao)){
                UtilFaces.addMensagemFaces("Advertência: As senhas não conferem!");
            }
            else{
                usuarioDao.incluir(usuario);

                UtilFaces.addMensagemFaces("Usuário Cadastrado com Sucesso!");

                usuario = new Usuario();
            }
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Erro ao Incluir: "+e.getMessage());
            usuario = new Usuario();
        }
    }

    public void alterar(ActionEvent evt) {
        try {
            usuarioDao.alterar(usuario);
            UtilFaces.addMensagemFaces("Usuário Alterado com Sucesso!");
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Erro ao Alterar: "+e.getMessage());
        }
    }

    public void excluir(ActionEvent evt) {
        try {
            usuarioDao.excluirPorId(usuario.getId());
            UtilFaces.addMensagemFaces("Usuário Excluido com Sucesso!");
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Erro ao Excluir: "+e.getMessage());
        }
    }

    public List<Usuario> listar() {
        try {
            return listaUsuario = usuarioDao.listar();
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Erro ao Listar: "+e.getMessage());
            return null;
        }
    }

    public List<SelectItem> getTipoUsuario() {
        return UtilFaces.getListEnum(EnumPapelUsuario.values());
    }


    public void addPapel( ){
        try {
            usuario.addPapel(papel);
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Erro ao Adcionar à lista: "+e.getMessage());
        }
    }

    public void setRemoverPapel(EnumPapelUsuario p){
        try {
            usuario.removerPapel(papel);
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Erro ao Remover da Lista: "+e.getMessage());
        }
    }

    @SuppressWarnings("unused")
    public void listarCooperativas( ){
        int i = 0;
        try {
            lista = new ArrayList<Pessoa>();
            if(!(cidade.getNome().equals(null))){
                for(Pessoa p: cooperativaDao.listar()){
                    if(p.getEndereco().getCidade().equals(cidade)){
                        lista.add(p);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Erro ao Listar Cooperativas: " + e.getMessage());
        }
    }
    
    public void alterarStatusDoUsuario(){
        if(!isAtivo()){
            usuario.setAtivo(true);
            setStatus("Ativo");
        }else{
            usuario.setAtivo(false);
            setStatus("Inativo");
        }
    }
    
    public void mostrarStatusDoUsuario(){
        if(!isAtivo()){
            setStatus("Ativo");
        }else{
            setStatus("Inativo");
        }
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public List<Pessoa> getLista() {
        return lista;
    }

    public void setLista(List<Pessoa> lista) {
        this.lista = lista;
    }

    public Cidade getCidade() {
        return cidade = new Cidade();
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public EnumPapelUsuario getPapel() {
        return papel;
    }

    public void setPapel(EnumPapelUsuario papel) {
        this.papel = papel;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSenhaConfirmacao() {
        return senhaConfirmacao;
    }

    public void setSenhaConfirmacao(String senhaConfirmacao) {
        this.senhaConfirmacao = UtilHash.gerarStringHash(senhaConfirmacao, Algoritimo.MD5);
    }
    
}
