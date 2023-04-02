package it.prova.dao.gestionecompagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.prova.dao.AbstractMySQLDAO;
import it.prova.dao.IBaseDAO;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO{
	//iniezione della conessione
		public ImpiegatoDAOImpl(Connection connection) {
			super(connection);
		}


	public List<Impiegato> list() throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		List<Impiegato> result = new ArrayList<>();
		try (Statement ps = connection.createStatement();
				ResultSet rs = ps
						.executeQuery("select * from impiegato i inner join compagnia c on c.id=i.id_compagnia;")) {
			while (rs.next()) {
				Impiegato impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("nome"));
				impiegatoTemp.setCognome(rs.getString("cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTemp.setDataNascita(
						rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
				impiegatoTemp.setDataAssunzione(
						rs.getDate("dataassunzione") != null ? rs.getDate("dataassunzione").toLocalDate() : null);
				impiegatoTemp.setId(rs.getLong("i.ID"));
				Compagnia compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDataFondazione(
						rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
				compagniaTemp.setId(rs.getLong("c.ID"));
				impiegatoTemp.setCompagnia(compagniaTemp);
				result.add(impiegatoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	
	
	//inizio del CRUD
	
	
	//get

	public Impiegato get(Long idInput) throws Exception {
		//verifica della disponibilità della connessione
				if (isNotActive())
					throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

				if (idInput == null || idInput < 1)
					throw new Exception("Valore di input non ammesso.");

				Impiegato result = null;
				try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where id=?")) {

					ps.setLong(1, idInput);
					try (ResultSet rs = ps.executeQuery()) {
						if (rs.next()) {
							result = new Impiegato();
							result.setNome(rs.getString("nome"));
							result.setCognome(rs.getString("cognome"));
							result.setCodiceFiscale(rs.getString("codicefiscale"));
							result.setDataNascita(
									rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
							result.setDataAssunzione(rs.getDate("dataassunzione")!= null? rs.getDate("dataassunzione").toLocalDate():null);
							result.setId(rs.getLong("id"));
						} 
						else {
							result = null;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return result;
						
	}
	
	
	
	//update

	public int update(Impiegato input) throws Exception {
		//verifica della connessione
				if (isNotActive())
					throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

				if (input == null || input.getId() == null || input.getId() < 1)
					throw new Exception("Valore di input non ammesso.");

				int result = 0;
				try (PreparedStatement ps = connection.prepareStatement(
						"UPDATE impiegato SET nome=?, cognome=?, codicefiscale=?, datanascita=?, dataassunzione=? where id=?;")) {
					ps.setString(1, input.getNome());
					ps.setString(2, input.getCognome());
					ps.setString(3, input.getCodiceFiscale());
					ps.setDate(4, java.sql.Date.valueOf(input.getDataNascita()));
					ps.setDate(5, java.sql.Date.valueOf(input.getDataAssunzione()));
					ps.setLong(5, input.getId());
					result = ps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return result;
	}
	
	
	//insert

	public int insert(Impiegato input) throws Exception {
		// verifica della connessione
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO impiegato (nome, cognome, codicefiscale, datanascita, dataassunzione) VALUES (?, ?, ?, ?, ?);")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setDate(4, java.sql.Date.valueOf(input.getDataNascita()));
			ps.setDate(5, java.sql.Date.valueOf(input.getDataAssunzione()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	
	//delete

	public int delete(Impiegato input) throws Exception {
		// verifica della connessione
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM impiegato WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
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
