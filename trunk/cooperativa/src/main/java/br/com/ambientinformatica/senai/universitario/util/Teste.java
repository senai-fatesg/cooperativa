package br.com.ambientinformatica.senai.universitario.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;
import br.com.ambientinformatica.senai.universitario.entidade.Pessoa;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Teste {

	public static void main(String[] args) {
		// try {
		// Workbook workbook = Workbook
		// .getWorkbook(new File("C:\\3dsMax.xls"));
		// Sheet sheet = workbook.getSheet(0);
		// Integer linhas = sheet.getRows();
		// for (int i = 0; i < linhas; i++) {
		// Cell celula1 = sheet.getCell(0, i);
		// Cell celula2 = sheet.getCell(1, i);
		// Cell celula3 = sheet.getCell(2, i);
		// System.out.println(String.format("%s - %s - %s",
		// celula1.getContents(), celula2.getContents(),
		// celula3.getContents()));
		// }

		// Verifica_Idade ver = new Verifica_Idade();
		// JOptionPane.showMessageDialog(null, ver.verificaIdade(new
		// Date("11/02/1985")));
		
		Pessoa cooperativa = new Pessoa();
		Cooperado c = new Cooperado();
		// id cooperativa ficticia
		cooperativa.setId(new Long(1));
		c.setId(new Integer(1));
		GerarMatricula gm = new GerarMatricula(new Date(), c,
				cooperativa);
		System.out.println(gm.getMatricula());
	}
}
