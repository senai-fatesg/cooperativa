package br.com.ambientinformatica.senai.universitario.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumTipoTelefone implements IEnum{

	RESIDENCIAL("Residencial", "RES"),
	COMERCIAL("Comercial", "COM"),
	CELULAR("Celular", "CEL"),
	FAX("FAX", "FAX");

	private final String descricao;
	private final String abreviacao;

	private EnumTipoTelefone(String descricao, String abreviacao) {
		this.descricao = descricao;
		this.abreviacao = abreviacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getAbreviacao() {
		return abreviacao;
	}

}