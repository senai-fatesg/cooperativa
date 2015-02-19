package br.com.cooperativa.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumStatus implements IEnum{
	
	AT("Ativo"),
	IN("Inativo");
	
	private String descricao;
	
	private EnumStatus(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getDescricao() {
		// TODO Auto-generated method stub
		return null;
	}
}
