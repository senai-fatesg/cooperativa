/**************************************************
 * Propriedade Intelectual da Ambient Informática 
 * (www.ambientinformatica.com.br). 
 * 
 * PROIBIDA A CÓPIA OU UTILIZAÇÃO POR TERCEIROS SEM
 * PRÉVIA AUTORIZAÇÃO.
 **************************************************
 */
package br.com.cooperativa.entidade;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Telefone implements java.io.Serializable{
 
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "geradorTelefone")
	@SequenceGenerator(name="geradorTelefone", sequenceName = "gerador_telefone", initialValue=1, allocationSize=1)
	private Integer id = 0;
	
	private String numero;
	
	@Enumerated(EnumType.STRING)
	private EnumTipoTelefone tipo;

	private String contato;
	
	public Integer getId() {
		return id;
	}

   public String getNumero() {
      return numero;
   }
   
   public String getNumeroTipoContato(){
      return (contato==null?"" : contato+ ": " ) + numero + " (" + (tipo == null ? "" : tipo.getAbreviacao())+ ")" ;
   }

   public void setNumero(String numero) {
      this.numero = numero;
   }

   public String getContato() {
      return contato;
   }

   public void setContato(String contato) {
      this.contato = contato;
   }

   public EnumTipoTelefone getTipo() {
	   return tipo;
   }

   public void setTipo(EnumTipoTelefone tipo) {
	   this.tipo = tipo;
   }
	   
//   public Pessoa getPessoa() {
//	   return pessoa;
//   }
//
//   public void setPessoa(Pessoa pessoa) {
//	   this.pessoa = pessoa;
//   }

@Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((numero == null) ? 0 : numero.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Telefone other = (Telefone) obj;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      if (numero == null) {
         if (other.numero != null)
            return false;
      } else if (!numero.equals(other.numero))
         return false;
      return true;
   }
}