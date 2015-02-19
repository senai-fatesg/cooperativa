package br.com.ambientinformatica.senai.universitario.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumEscolaridade implements IEnum{

    FC("Fundamental_Completo"),
	FI("Fundamental_Incompleto"),
	MC("Medio_Completo"),
	MI("Medio_Incompleto"),
	SC("Superior_Completo"),
	SI("Superior_Incompleto");

    private String descricao;
    
    EnumEscolaridade(String descricao){
    	this.descricao = descricao;
    }
    
	@Override
	public String getDescricao() {
		return descricao;
	}


}
