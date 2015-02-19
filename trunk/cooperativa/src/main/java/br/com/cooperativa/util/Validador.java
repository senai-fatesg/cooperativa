package br.com.cooperativa.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.cooperativa.entidade.*;

public class Validador {

    public static Boolean eDataNascValida(Date dataNasc) throws Exception {
        Boolean retorno = true;
        if (dataNasc != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicial = format.parse("01/01/1940");
            Date dataFinal = format.parse("01/01/2000");
            if (dataNasc.getTime() > dataFinal.getTime()
                    || dataNasc.getTime() < dataInicial.getTime()) {
                retorno = false;
            } else {
                retorno = true;
            }
        }
        return retorno;
    }

    public static Boolean isCPF(String cpf) {
        String strCpf = cpf;
        if (strCpf.equals("")) {
            return false;
        }

        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.   
            d1 = d1 + (11 - nCount) * digitoCPF;

            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.   
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        //Primeiro resto da divisão por 11.   
        resto = (d1 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.   
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        //Segundo resto da divisão por 11.   
        resto = (d2 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.   
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        //Digito verificador do CPF que está sendo validado.   
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

        //Concatenando o primeiro resto com o segundo.   
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.   
        return nDigVerific.equals(nDigResult);
    }
    
    //Método para Geração de Matricula de Cooperado(id da cooperativa.Data de Aceite.id Cooperado)
    //Exemplo: 001.02122013.095
    public String GeraMatricula(Solicitante solicitante)//Vai pegar o id do solicitante
    {
        String matricula = "";
        
        Adesao adesao = new Adesao();
        
        adesao.getIdAdesao();
        
        return matricula;
        
    }
}
