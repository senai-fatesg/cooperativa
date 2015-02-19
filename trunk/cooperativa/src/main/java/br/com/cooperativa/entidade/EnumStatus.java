package br.com.cooperativa.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumStatus implements IEnum{
	
	A("Ativo"),
	I("Inativo");
	
	private String descricao;
	
	private EnumStatus(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}
}
