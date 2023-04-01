package it.prova.dao.gestionecompagnia;

import java.time.LocalDate;
import java.util.List;

import it.prova.dao.AbstractMySQLDAO;
import it.prova.dao.IBaseDAO;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO{

	public List<Impiegato> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Impiegato get(Long idInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public int update(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Impiegato> findByExample(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public int countByDataFondazioneCompagniaGreaterThan(LocalDate dataFondazione) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(int fatturatoInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Impiegato> findAllErroriAssunzione() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
