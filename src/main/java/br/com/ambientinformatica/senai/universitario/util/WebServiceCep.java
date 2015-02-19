/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ambientinformatica.senai.universitario.util;

/**
 *
 * @author davinc131
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WebServiceCep {
	private enum Xml {
		CIDADE {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setCidade(text);
			}
		},
		BAIRRO {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setBairro(text);
			}
		},
		TIPO_LOGRADOURO {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setLogradouroType(text);
			}
		},
		LOGRADOURO {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setLogradouro(text);
			}
		},
		RESULTADO {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setResulCode(Integer.parseInt(text));
			}
		},
		RESULTADO_TXT {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setResultText(text);
			}
		},
		UF {
			@Override
			public void setCep(String text, WebServiceCep webServiceCep) {
				webServiceCep.setUf(text);
			}
		};

		public abstract void setCep(String text, WebServiceCep webServiceCep);
	}

	private static final class IterableElement implements Iterable<Element> {
		private Iterator<Element> itr;

		@SuppressWarnings("unchecked")
		public IterableElement(Iterator<?> itr) {
			this.itr = (Iterator<Element>) itr;
		}

		@Override
		public Iterator<Element> iterator() {
			return itr;
		}
	}

	private static final class XmlEnums {
		private HashMap<String, Xml> enumsMap;

		/**
		 * Cria um {@link XmlEnums}
		 */
		public XmlEnums() {
			initializeEnums();
		}

		/**
		 * Inicializa este objeto, guardando os enumerations em um
		 * {@link HashMap}
		 */
		private void initializeEnums() {
			Xml[] enums = Xml.class.getEnumConstants();
			enumsMap = new HashMap<String, Xml>();
			for (int i = 0; i < enums.length; i++) {
				enumsMap.put(enums[i].name().toLowerCase(), enums[i]);
			}
		}

		public Xml getXml(String xmlName) {
			return enumsMap.get(xmlName.toLowerCase());
		}
	}

	private static final String URL_STRING = "http://cep.republicavirtual.com.br/web_cep.php?cep=%s&formato=xml?senai"; 

	private static Document getDocument(String cep) throws DocumentException,
			MalformedURLException {
		URL url = new URL(String.format(URL_STRING, cep));
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}

	private static Element getRootElement(String cep) throws DocumentException,
			MalformedURLException {
		return getDocument(cep).getRootElement();
	}

	private static IterableElement getElements(String cep)
			throws DocumentException, MalformedURLException {
		return new IterableElement(getRootElement(cep).elementIterator());
	}

	public static WebServiceCep searchCep(String cep) {
		cep = cep.replaceAll("\\D*", ""); // To numeric digits only
		if (cep.length() > 8)
			cep = cep.substring(0, 8);
		WebServiceCep loadCep = new WebServiceCep(cep);
		try {
			XmlEnums enums = new XmlEnums();
			for (Element e : getElements(cep))
				enums.getXml(e.getQualifiedName()).setCep(e.getText(), loadCep);
		} catch (DocumentException ex) {
			if (ex.getNestedException() != null
					&& ex.getNestedException() instanceof java.net.UnknownHostException) {
				loadCep.setResultText("Site não encontrado");
				loadCep.setResulCode(-14);
			} else {
				loadCep.setResultText("Não é possivel ler o arquivo XML");
				loadCep.setResulCode(-15);
			}
			loadCep.setExceptio(ex);
		} catch (MalformedURLException ex) {
			loadCep.setExceptio(ex);
			loadCep.setResultText("Erro na formatação da URL");
			loadCep.setResulCode(-16);
		} catch (Exception ex) {
			loadCep.setExceptio(ex);
			loadCep.setResultText("Erro inesperado.");
			loadCep.setResulCode(-17);
		}

		return loadCep;
	}

	private int resulCode = -1;
	private String resultText = "Busca não realizada";
	private String cep = null;
	private String bairro = null;
	private String cidade = null;
	private String logradouro = null;
	private String logradouroType = null;
	private String uf = null;
	private Exception exception;

	private WebServiceCep(String cep) {
		this.cep = cep;
	}

	private void setExceptio(Exception ex) {
		this.exception = ex;
	}

	private void setCidade(String cidade) {
		this.cidade = cidade;
	}

	private void setBairro(String bairro) {
		this.bairro = bairro;
	}

	private void setLogradouroType(String logradouroType) {
		this.logradouroType = logradouroType;
	}

	private void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	private void setResulCode(int resultado) {
		this.resulCode = resultado;
	}

	private void setResultText(String resultado_txt) {
		this.resultText = resultado_txt;
	}

	private void setUf(String uf) {
		this.uf = uf;
	}

	public int getResulCode() {
		return resulCode;
	}

	public String getResultText() {
		return resultText;
	}

	public boolean wasSuccessful() {
		return (resulCode == 1 && exception == null);
	}

	public boolean isCepNotFound() {
		return (resulCode == 0);
	}

	public boolean hasException() {
		return (exception != null);
	}

	public Exception getException() {
		return exception;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getUf() {
		return uf;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getLogradouroFull() {
		return (logradouro == null || logradouroType == null) ? null
				: logradouroType + " " + logradouro;
	}

	public String getLogradouroType() {
		return logradouroType;
	}

	public String getCep() {
		return cep;
	}
}
