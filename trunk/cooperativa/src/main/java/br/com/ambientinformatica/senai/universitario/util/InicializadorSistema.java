package br.com.ambientinformatica.senai.universitario.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.senai.universitario.entidade.Usuario;
import br.com.ambientinformatica.senai.universitario.persistencia.CidadeDao;
import br.com.ambientinformatica.senai.universitario.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.UtilLog;

@Service("inicializadorSistema")
public class InicializadorSistema {

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private CidadeDao cidadeDao;
	
	@PostConstruct
	public void iniciar(){
		inicializarUsuarioAdmin();
		inserirCidades();
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
			
			final String fileName = "cidade.sql";
			List<Cidade> cidades = new ArrayList<Cidade>();
			cidades = cidadeDao.listar();
			if (cidades.isEmpty()){
				
				ClassLoader classLoader = getClass().getClassLoader();
				File file = new File(classLoader.getResource(fileName).getFile());
				cidadeDao.incluirViaArquivo(file.getPath());
			}
		} catch (Exception e) {
			UtilLog.getLog().error("Erro na Inserção de Cidades: " + e.getMessage(), e);
		}
	}
}