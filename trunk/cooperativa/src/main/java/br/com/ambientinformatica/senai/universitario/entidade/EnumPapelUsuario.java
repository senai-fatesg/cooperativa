package br.com.ambientinformatica.senai.universitario.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumPapelUsuario implements IEnum {

	INCUBADORA("INCUBADORA", "IN"),

	INCUBADORA_MUNICIPAL("INCUBADORA_MUNICIPAL", "IN_MU"),

	ADMIN("ADMIN", "AD"),

	USUARIO("USUARIO", "USO");

	private final String descricao;
	private final String abreviacao;

	private EnumPapelUsuario(String descricao, String abreviacao) {
		this.abreviacao = abreviacao;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public String getAbreviacao() {
		return this.abreviacao;
	}

}
