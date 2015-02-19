package br.com.cooperativa.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.util.UtilLog;
import br.com.cooperativa.entidade.Cidade;
import br.com.cooperativa.entidade.EnumPapelUsuario;
import br.com.cooperativa.entidade.Usuario;
import br.com.cooperativa.persistencia.CidadeDao;
import br.com.cooperativa.persistencia.UsuarioDao;

@Service("inicializadorSistema")
public class InicializadorSistema {

	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private CidadeDao cidadeDao;
	
	@PostConstruct
	public void iniciar(){
		inicializarUsuarioAdmin();
		//inserirCidades();
	}
	
	private void inicializarUsuarioAdmin(){
		try {
			List<Usuario> usuarios = usuarioDao.listar();
			if(usuarios.isEmpty()){
				Usuario usu = new Usuario();
				usu.setNome("admin");
				usu.setLogin("admin");
				usu.setSenhaNaoCriptografada("123456");
				usu.addPapel(EnumPapelUsuario.ADMIN);
				usu.addPapel(EnumPapelUsuario.USUARIO);
				usuarioDao.incluir(usu);
				UtilLog.getLog().info("*** USUARIO admin CRIADO com a senha 123456 ***");
			}
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
		}
	}
	
	
	private void inserirCidades() {
		try {
			List<Cidade> cidades = new ArrayList<Cidade>();
			cidades = cidadeDao.listar();

			if (cidades.size() == 0){
				
				String caminho = "scripts/cidades.sql";
				cidadeDao.incluirViaArquivo(caminho);
			}

		} catch (Exception e) {
			UtilLog.getLog().error("Erro na Inserção de Cidades: " + e.getMessage(), e);
		}
	}
	
}
