package br.com.ambientinformatica.senai.universitario.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.senai.universitario.entidade.Residuo;

public interface ResiduoDao extends Persistencia<Residuo> {

	void salvar(Residuo r) throws PersistenciaException;

	List<Residuo> listarPorDescricao(String descricao);

	Residuo consultar(Integer id);

}
