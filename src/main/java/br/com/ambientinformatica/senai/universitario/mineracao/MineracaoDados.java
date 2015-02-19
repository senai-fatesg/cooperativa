package br.com.ambientinformatica.senai.universitario.mineracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.senai.universitario.converter.DateConverter;
import br.com.ambientinformatica.senai.universitario.entidade.Adesao;
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;
import br.com.ambientinformatica.senai.universitario.entidade.DadosPessoais;
import br.com.ambientinformatica.senai.universitario.entidade.Endereco;
import br.com.ambientinformatica.senai.universitario.entidade.EnumEstadoCivil;
import br.com.ambientinformatica.senai.universitario.entidade.EnumStatus;
import br.com.ambientinformatica.senai.universitario.entidade.EnumTipoPessoa;
import br.com.ambientinformatica.senai.universitario.entidade.Filiacao;
import br.com.ambientinformatica.senai.universitario.entidade.Pessoa;
import br.com.ambientinformatica.senai.universitario.persistencia.AdesaoDao;
import br.com.ambientinformatica.senai.universitario.persistencia.CidadeDao;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperadoDao;
import br.com.ambientinformatica.senai.universitario.persistencia.CooperativaDao;
import br.com.ambientinformatica.senai.universitario.util.GerarMatricula;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.faces.event.ActionEvent;
import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Controller("MineracaoControl")
@Scope("conversation")
public class MineracaoDados {
	
	@Autowired
	CooperativaDao cooperativaDao;
	@Autowired
	CooperadoDao cooperadoDao;
	@Autowired
	CidadeDao cidadeDao;
	@Autowired
	AdesaoDao adesaoDao;
	
	Cidade cidade = new Cidade();
	GerarMatricula matricula;
	
	
	public void cadastroCooperativa(ActionEvent evt)
	{
		try {
	        Workbook workbook = Workbook.getWorkbook(new File("C:\\Cooperativas.xls"));
	        
	        Sheet sheet = workbook.getSheet(0);
	        Integer linhas = sheet.getRows();
	        Integer colunas = sheet.getColumns();
	        Pessoa cooperativa = new Pessoa();
	        Endereco endereco = new Endereco();
	    	
	        
	        for (int i = 0; i < linhas; i++) 
	        {
	        	for(int c = 0; c < colunas; c++)
	        	{
	        		if(i == 0)
	        		{
	        			break;
	        		}
	        		else
	        		{
	        			Cell celula = sheet.getCell(c, i);
	        			
	        			switch (c) {
						case 0:
							cooperativa.setRazaoSocial(celula.getContents());
							break;
						case 1:
							cooperativa.setNomeFantasia(celula.getContents());
							break;
						case 2:
							cooperativa.setCnpj(celula.getContents());
							break;
						case 3:
							cooperativa.setCooperativa(true);
							break;
						case 4:
							if(celula.getContents().equals("Ativa"))
							{
								cooperativa.setStatus(EnumStatus.A);
							}
							else
							{
								cooperativa.setStatus(EnumStatus.I);
							}
							
							break;
						case 5:
							if(celula.getContents().equals("Juridica"))
							{
								cooperativa.setTipoPessoa(EnumTipoPessoa.PJ);
							}
							else
							{
								cooperativa.setTipoPessoa(EnumTipoPessoa.PF);
							}
							break;
						case 6:
							endereco.setLogradouro(celula.getContents());
							break;
						case 7:
							endereco.setNumero(celula.getContents());
							break;
						case 8:
							endereco.setComplemento(celula.getContents());
							break;
						case 9:
							endereco.setBairro(celula.getContents());
							break;
						case 10:
							cidade = consultaCidade(celula.getContents().toUpperCase());
							endereco.setCidade(cidade);
							cooperativa.setEndereco(endereco);
							cooperativaDao.salvar(cooperativa);
							cooperativa = new Pessoa();
					        endereco = new Endereco();
							break;
						}
	        		}
	        	}
	        }
	        UtilFaces.addMensagemFaces("Cooperativas Gravadas com sucesso!");

		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao Ler arquivo Excel: "+e.getMessage());
		}
	}
	
	public void cadastroCooperado()
	{
		try {
			Workbook workbook = Workbook.getWorkbook(new File("C:\\Cooperados.xls"));

			Sheet sheet = workbook.getSheet(0);
			Integer linhas = sheet.getRows();
			Integer colunas = sheet.getColumns();
			Pessoa cooperativa = new Pessoa();
			Adesao adesao = new Adesao();
			Endereco endereco = new Endereco();
			DadosPessoais dadosP = new DadosPessoais();
			Cooperado cooperado = new Cooperado();
			DateConverter dateConverter = new DateConverter();
			Date data = new Date();
			Filiacao filiacao = new Filiacao();
			
			for(int l = 0; l < linhas; l++)
			{
				//Para cada linha ele vai limpar os dados carregados anteriormente
				//para não haver redundância de dados
				filiacao = new Filiacao();
				endereco = new Endereco();
				dadosP = new DadosPessoais();
				cooperado = new Cooperado();
				cooperativa = new Pessoa();
				
				for(int c = 0; c < colunas; c++)
				{
					if(l == 0)
					{
						break;
					}
					else
					{
						Cell celula = sheet.getCell(c, l);
						
						switch (c) {
						case 0:
							dadosP.setNome(celula.getContents());
							break;
						case 1:
							dadosP.setRg(celula.getContents());
							break;
						case 2:
							dadosP.setCpf(celula.getContents());
							break;
						case 3:
							data = (Date)dateConverter.getAsObject(null, null, celula.getContents());
							dadosP.setDataNascimento(data);
							break;
						case 4:
							if(celula.getContents().equals("Viuvo(a)"))
							{
								dadosP.setEstadoCivil(EnumEstadoCivil.VIUVO);
							}
							else if(celula.getContents().equals("Divorciado(a)"))
							{
								dadosP.setEstadoCivil(EnumEstadoCivil.DIVORCIADO);
							}
							else if(celula.getContents().equals("Casado(a)"))
							{
								dadosP.setEstadoCivil(EnumEstadoCivil.CASADO);
							}
							else
							{
								dadosP.setEstadoCivil(EnumEstadoCivil.SOLTEIRO);
							}
							break;
						case 5:
							dadosP.setCarteiraTrabalho(celula.getContents());
							break;
						case 6:
							dadosP.setTelCelular(celula.getContents());
							break;
						case 7:
							dadosP.setTelResidencial(celula.getContents());
							break;
						case 8:
							filiacao.setNomePai(celula.getContents());
							break;
						case 9:
							filiacao.setNomeMae(celula.getContents());
							dadosP.setFiliacao(filiacao);
							break;
						case 10:
							endereco.setLogradouro(celula.getContents());
							break;
						case 11:
							endereco.setNumero(celula.getContents());
							break;
						case 12:
							endereco.setComplemento(celula.getContents());
							break;
						case 13:
							endereco.setBairro(celula.getContents());
							break;
						case 14:
							cidade = consultaCidade(celula.getContents().toUpperCase());
							endereco.setCidade(cidade);
							dadosP.setEndereco(endereco);
							break;
						case 15:
							dadosP.setNacionalidade(celula.getContents());
							break;
						case 16:
							cooperativa = cooperativaDao.consultarPorFantasia(celula.getContents());
							
//							adesao.setCooperativa(cooperativa);
//							adesao.setDadosPessoais(dadosP);
//							adesaoDao.salvar(adesao);
							
							cooperado.setDadosPessoais(dadosP);
							cooperado.setCooperativa(cooperativa);

							break;
						case 17:
							if(celula.getContents().equals("Ativo(a)"))
							{
								cooperado.setStatus(EnumStatus.A);
							}
							else
							{
								cooperado.setStatus(EnumStatus.I);
							}
							
//							cooperadoDao.salvar(cooperado);
							cooperado = cooperadoDao.alterar(cooperado);
//							int id = cooperadoDao.ultimoRegistro();
							matricula = new GerarMatricula(cooperado);							
							cooperado.setMatricula(matricula.getMatricula());
							cooperadoDao.alterar(cooperado);
							break;
						}
					}
				}
			}
			UtilFaces.addMensagemFaces("Cooperados Gravados com sucesso!");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces("Erro ao gravar cooperado: "+e.getMessage());
		}
	}
	
	private Cidade consultaCidade(String c)
	{
		cidade = cidadeDao.consultarPorNome(c);

		if(cidade.equals(null))
		{
			UtilFaces.addMensagemFaces("Erro ao consultar Município ");
			return null;
		}
		else
		{
			return cidade;
		}
	}
}
