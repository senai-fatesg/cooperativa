package br.com.cooperativa.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import br.com.cooperativa.entidade.Cooperado;
import br.com.cooperativa.entidade.Cooperativa;
import br.com.cooperativa.entidade.Pessoa;

public class GerarMatricula {
	
	private Date data;
	private Cooperado cooperado;
	private Pessoa cooperativa;
	private String matricula;
	
	public GerarMatricula(Date d, Cooperado c, Pessoa cc) {
		this.data = d;
		this.cooperado = c;
		this.cooperativa = cc;
		
		gerarMatricula();
	}
	
	public GerarMatricula(Cooperado cooperado) {
		
		this.data = new Date();
		this.cooperado = cooperado;
		
		gerarMatricula();
	}

	private void gerarMatricula()
	{
		SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
		
		matricula = formato.format(data) + "." + cooperado.getCooperativa().getId().toString() + "." + cooperado.getId().toString();
	}

	public String getMatricula() {
		return matricula;
	}
}
