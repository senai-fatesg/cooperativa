package br.com.cooperativa.persistencia;

import java.util.Date;
import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.cooperativa.entidade.Assembleia;
import br.com.cooperativa.entidade.Cooperado;

public interface AssembleiaDao extends Persistencia<Assembleia> {
	Assembleia consultar(Integer id, Date dtIni, Date dtFim);

	List<Assembleia> listar(Date dtIni, Date dtFim);

	void salvar(Assembleia a) throws PersistenciaException;
}