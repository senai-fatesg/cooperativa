package br.com.ambientinformatica.senai.universitario.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.util.AlfaNumerico;
import br.com.ambientinformatica.util.AmbientValidator;
import br.com.ambientinformatica.util.Entidade;
import br.com.ambientinformatica.util.UtilHash;
import br.com.ambientinformatica.util.UtilHash.Algoritimo;

@Entity
public class Usuario extends Entidade {

	@Id
	@Column(nullable = false, unique = true)
	@NotNull(message = "Login do usuário é obrigatório", groups = AmbientValidator.class)
	@NotEmpty(message = "Login do usuário é obrigatório", groups = AmbientValidator.class)
	@AlfaNumerico(message = "O login do usuário não pode conter caracteres especiais, acentos ou espaços", semAcentos = true, semEspacos = true, groups = AmbientValidator.class)
	private String login;

	@ManyToOne(fetch = FetchType.EAGER)
	private Pessoa cooperativa;
	
	private String senha;
	
	@NotEmpty(message="O preenchimento do campo nome é obrigatorio", groups=AmbientValidator.class)
	private String nome;
	
	private boolean ativo;

	@Temporal(TemporalType.DATE)
	private Date dataAlteracaoSenha = new Date();

	@Temporal(TemporalType.DATE)
	private Date dataCriacao = new Date();

	@Temporal(TemporalType.DATE)
	private Date dataUltimoAcesso = new Date();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<PapelUsuario> papeis = new ArrayList<PapelUsuario>();

	public void addPapel(EnumPapelUsuario papel) {
		try {
			if (!isContemPapel(papel)) {
				PapelUsuario pu = new PapelUsuario();
				pu.setPapel(papel);
				papeis.add(pu);
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao adicionar papel da lista: "
					+ e.getMessage());
		}
	}

	public void removerPapel(EnumPapelUsuario papel) {
		try {
			PapelUsuario pu = new PapelUsuario();
			pu.setPapel(papel);
			papeis.remove(pu);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao excluir papel da lista: "
					+ e.getMessage());
		}
	}

	private boolean isContemPapel(EnumPapelUsuario papel) {
		try {
			for (PapelUsuario p : papeis) {
				if (p.getPapel().getDescricao().equals(papel.getDescricao())) {
					UtilFaces
					.addMensagemFaces("Perfil de usuário já existe no contexto!");
					return true;
				}
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao Verificar Lista: "
					+ e.getMessage());
		}
		return false;
	}
	
	public String getListaPapeis(){
	    String papeis = new String();
	    for (PapelUsuario papelUsuario : this.papeis) {
            papeis = papeis.concat(papelUsuario.getPapel().getDescricao()).concat(", ");
        }
	    if(!papeis.isEmpty()){
	        papeis = papeis.substring(0, papeis.length()-2);
	    }
        return papeis;
	}

	public String getStatusUsuario(){
        return this.ativo == true ? "ATIVO" : "INATIVO";
    }
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenhaNaoCriptografada(String senha) {
		this.senha = UtilHash.gerarStringHash(senha, Algoritimo.MD5);
	}

	public void setSenha(String senha) {
		setSenhaNaoCriptografada(senha);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataAlteracaoSenha() {
		return dataAlteracaoSenha;
	}

	public void setDataAlteracaoSenha(Date dataAlteracaoSenha) {
		this.dataAlteracaoSenha = dataAlteracaoSenha;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Object getId() {
		return login;
	}

	public Pessoa getCooperativa() {
		return cooperativa;
	}

	public void setCooperativa(Pessoa cooperativa) {
		this.cooperativa = cooperativa;
	}

	public List<PapelUsuario> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<PapelUsuario> papeis) {
		this.papeis = papeis;
	}

    @Override
    public String toString() {
        return String.format("%s", nome);
    }

	
}
