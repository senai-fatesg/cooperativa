package br.com.ambientinformatica.senai.universitario.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumEstadoCivil implements IEnum {

	CASADO("Casado(a)"), 
	SOLTEIRO("Solteiro(a)"), 
	DIVORCIADO("Divorciado(a)"), 
	VIUVO("Viuvo(a)");

	private String descricao;

	private EnumEstadoCivil(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}
}
