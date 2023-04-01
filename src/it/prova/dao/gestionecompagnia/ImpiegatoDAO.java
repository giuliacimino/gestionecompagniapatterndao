package it.prova.dao.gestionecompagnia;

import java.time.LocalDate;
import java.util.List;

import it.prova.dao.IBaseDAO;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;

public interface ImpiegatoDAO extends IBaseDAO<Impiegato> {
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception;
	public int countByDataFondazioneCompagniaGreaterThan(LocalDate dataFondazione) throws Exception;
	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(int fatturatoInput) throws Exception;
	public List<Impiegato> findAllErroriAssunzione() throws Exception;
	
	

}
