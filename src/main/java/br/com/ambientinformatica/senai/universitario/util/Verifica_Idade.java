package br.com.ambientinformatica.senai.universitario.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author davinc131
 */
public class Verifica_Idade
{
    //Retorna um inteiro como resultado da verificação
    public Integer verificaIdade(Date data)
    {   
        Calendar dataNascimento = Calendar.getInstance();  
        dataNascimento.setTime(data);  
        Calendar dataAtual = Calendar.getInstance();  

        Integer diferencaMes = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);  
        Integer diferencaDia = dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH);  
        Integer idade = (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));  

        if(diferencaMes < 0  || (diferencaMes == 0 && diferencaDia < 0)) {  
            idade--;  
        }  

        return idade;
    }
}
