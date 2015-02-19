package br.com.cooperativa.entidade;

import br.com.ambientinformatica.util.IEnum;


public enum EnumTipoPessoa implements IEnum{
	
	PF("Pessoa Física"),
	PJ("Pessoa Juridica");
	
	private String descricao;
	
	private EnumTipoPessoa(String descricao) {
		this.descricao = descricao;
	}

	
	@Override
	public String getDescricao() {
		return descricao;

	}

}
