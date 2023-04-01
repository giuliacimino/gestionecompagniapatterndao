package it.prova.dao.gestionecompagnia;

import java.time.LocalDate;
import java.util.List;

import it.prova.dao.IBaseDAO;
import it.prova.model.Compagnia;

public interface CompagniaDAO extends IBaseDAO<Compagnia> {
	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(LocalDate dataFondazione) throws Exception;
	public List<Compagnia> findAllByRagioneSocialeContiene(String ragioneSocialeInput) throws Exception;
	public List<Compagnia> findAllBYCodFisImpiegatoContiene(String codiceFiscaleInput) throws Exception;	
}
