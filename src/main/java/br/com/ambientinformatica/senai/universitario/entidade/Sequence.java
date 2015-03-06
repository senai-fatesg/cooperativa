package br.com.ambientinformatica.senai.universitario.entidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sequence  {

     private String seqName;
     
     private BigDecimal seqCount;

    public Sequence() {
    }

	
    public Sequence(String seqName) {
        this.seqName = seqName;
    }
    public Sequence(String seqName, BigDecimal seqCount) {
       this.seqName = seqName;
       this.seqCount = seqCount;
    }
   
     @Id 
    @Column(name="SEQ_NAME", unique=true, nullable=false, length=50)
    public String getSeqName() {
        return this.seqName;
    }
    
    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    
    @Column(name="SEQ_COUNT", precision=38, scale=0)
    public BigDecimal getSeqCount() {
        return this.seqCount;
    }
    
    public void setSeqCount(BigDecimal seqCount) {
        this.seqCount = seqCount;
    }

}
