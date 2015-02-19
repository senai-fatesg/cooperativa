package br.com.cooperativa.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumTipoTelefone implements IEnum{

   RESIDENCIAL("RESIDENCIAL", "RES"),
   COMERCIAL("COMERCIAL", "COM"),
   CELULAR("CELULAR", "CEL"),
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
