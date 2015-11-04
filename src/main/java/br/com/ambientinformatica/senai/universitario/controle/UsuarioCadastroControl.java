package br.com.ambientinformatica.senai.universitario.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.senai.universitario.entidade.Pessoa;
import br.com.ambientinformatica.senai.universitario.entidade.Usuario;
import br.com.ambientinformatica.senai.universitario.persistencia.CidadeDao;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperativaDao;
import br.com.ambientinformatica.senai.universitario.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.UtilLog;

@Controller("UsuarioCadastroControl")
@Scope("conversation")
public class UsuarioCadastroControl {

    private Usuario usuario;
    private Cidade cidade;
    private String senha = "123456";
    private String status;
    public boolean ativo;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private CidadeDao cidadeDao;
    
    @Autowired
    private CooperativaDao cooperativaDao;

    private EnumPapelUsuario papel;

    private List<Cidade> cidades = new ArrayList<Cidade>();
    private List<Pessoa> cooperativas;

    @PostConstruct
    public void init(){
        usuario = new Usuario();
        carregarCidades(null);
        listarCooperativas();
        mostrarCooperativasPorCidadeSelecionada(null);
    }
    
    public List<SelectItem> getComboPermissoes(){
        return UtilFaces.getListEnum(EnumPapelUsuario.values());
    }

    public void addPapel(){
        usuario.addPapel(papel);
    }

    public  void carregarCidades(ActionEvent evt){
        try {
            cidades = cidadeDao.listar();
        } catch (Exception e) {
            UtilLog.getLog().error(e.getMessage(), e);
            UtilFaces.addMensagemFaces("Erro ao carregar a lista de cidades");
        }
    }

    public List<Cidade> autoCompletarCidades(String query){
        List<Cidade> resultados = new ArrayList<Cidade>();
        for (Cidade cidade : cidades) {
            if(cidade.contem(query)){
                resultados.add(cidade);
            }
        }
        return resultados;
    }
    
    public List<Pessoa> carregarCooperativa(ActionEvent evt){
        try {
            cooperativas = cooperativaDao.listar();
        } catch (Exception e) {
            UtilLog.getLog().error(e.getMessage(), e);
            UtilFaces.addMensagemFaces("Erro ao listar as cooperativas");
        }
        return cooperativas;
    }
    
    @SuppressWarnings("unchecked")
    public void mostrarCooperativasPorCidadeSelecionada(AjaxBehaviorEvent evt){
        cooperativas = cooperativaDao.consultarCooperativa(cidade);
    }
    
    @SuppressWarnings("unused")
    public void listarCooperativas( ){
        int i = 0;
        try {
            cooperativas = new ArrayList<Pessoa>();
            if(!(cidade.getNome().equals(null))){
                for(Pessoa p: cooperativaDao.listar()){
                    if(p.getEndereco().getCidade().equals(cidade)){
                        cooperativas.add(p);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            UtilFaces.addMensagemFaces("Erro ao Listar Cooperativas: " + e.getMessage());
        }
    }
    
    public void gravar(ActionEvent evt){
        List<Usuario> usuarios = new ArrayList<Usuario>();
    	try {
    		usuarios = usuarioDao.listar();
    		if(!usuarios.contains(usuario)){
    			usuario.setSenha(senha);
    		}
    		if(usuario != null && usuario.getCooperativa() != null && !usuario.getLogin().isEmpty() && !usuario.getPapeis().contains(papel)){
    			usuarioDao.alterar(usuario);
    			UtilFaces.addMensagemFaces("Operação realizada com sucesso");
    			usuario = new Usuario();
    			cidade = null;
    		}else{
    			UtilFaces.addMensagemFaces("Verificar campos não preenchidos ");
    		}

    	} catch (Exception e) {
    		UtilLog.getLog().error(e.getMessage(), e);
    		UtilFaces.addMensagemFaces("Erro ao incluir o usuário! ");
    	}
    }
    

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        cidade = usuario.getCooperativa().getEndereco().getCidade();
        mostrarCooperativasPorCidadeSelecionada(null);
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenhaConfirmacao(String senha) {
        this.senha = senha;
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

    public EnumPapelUsuario getPapel() {
        return papel;
    }

    public void setPapel(EnumPapelUsuario papel) {
        this.papel = papel;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public List<Pessoa> getCooperativas() {
        return cooperativas;
    }

}
