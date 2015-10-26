package br.com.ambientinformatica.senai.universitario.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumStatus implements IEnum{
	
	A("Ativo"),
	I("Inativo"),
	C("Cancelado");
	
	private String descricao;
	
	private EnumStatus(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}
}
