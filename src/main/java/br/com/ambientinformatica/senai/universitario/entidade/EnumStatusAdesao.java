package br.com.ambientinformatica.senai.universitario.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumStatusAdesao implements IEnum{

    P("Pendente"),
    A("Aprovado");
    
    private String descricao;
	
	private EnumStatusAdesao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}
}
