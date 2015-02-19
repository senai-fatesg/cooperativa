package br.com.cooperativa.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumTipoEstadoCivil implements IEnum{
    
	SOL("Solteiro"),
    CAS("Casado"),
    DIV("Divorciado"),
    VIU("Viuvo");

    private String descricao;
    
    EnumTipoEstadoCivil(String descricao){
    	this.descricao = descricao;
    }
    
	@Override
	public String getDescricao() {
		return descricao;
	}            
}
