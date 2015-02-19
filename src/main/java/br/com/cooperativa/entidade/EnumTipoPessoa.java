package br.com.cooperativa.entidade;

import br.com.ambientinformatica.util.IEnum;
import br.com.ambientinformatica.util.IEnumRestritivo;


public enum EnumTipoPessoa implements IEnum{
	
	PF("Pessoa Fisica"),
	PJ("Pessoa Juridica");
	
	private String descricao;
	
	private EnumTipoPessoa(String descricao) {
		this.descricao = descricao;
	}

	
	@Override
	public String getDescricao() {
		return this.descricao;

	}

}
