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

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {
	// iniezione della conessione
	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
	}

	public List<Impiegato> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from impiegato")) {

			while (rs.next()) {
				Impiegato impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("nome"));
				impiegatoTemp.setCognome(rs.getString("cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTemp.setDataNascita(
						rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
				impiegatoTemp.setDataAssunzione(
						rs.getDate("dataassunzione") != null ? rs.getDate("dataassunzione").toLocalDate() : null);
				impiegatoTemp.setId(rs.getLong("id"));
				result.add(impiegatoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// inizio del CRUD

	// get

	public Impiegato get(Long idInput) throws Exception {
		// verifica della disponibilità della connessione
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
					result.setDataAssunzione(
							rs.getDate("dataassunzione") != null ? rs.getDate("dataassunzione").toLocalDate() : null);
					result.setId(rs.getLong("id"));
				} else {
					result = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}

	// update

	public int update(Impiegato input) throws Exception {
		// verifica della connessione
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
			ps.setLong(6, input.getId());
			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// insert

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

	// delete

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

	
	//findByExample
	public List<Impiegato> findByExample(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input == null)
			throw new RuntimeException("Impossibile caricare Compagnia: id mancante!");

		List<Impiegato> result=new ArrayList<Impiegato>();
		Impiegato impiegatoTemp= null;
		
		String query= "select * from impiegato where  id is not null";
		if (input.getNome()!= null && ! input.getNome().isEmpty()) {
			query+="and nome like ' "+ input.getNome()+ " %' ";
		}
		if (input.getCognome()!= null && ! input.getCognome().isEmpty()) {
			query+="and cognome like ' " +input.getCognome()+ " %'";
		}
		if(input.getCodiceFiscale()!= null && !input.getCodiceFiscale().isEmpty()) {
			query+="and codicefiscale = ' "+ input.getCodiceFiscale()+ " %' ";
		}
		if (input.getDataNascita()!= null) {
			query+= "and datanascita =" + input.getDataNascita()+ " '";
		}
		if( input.getDataAssunzione()!= null) {
			query+="and dataassunzione ="+input.getDataAssunzione()+ " ' ";
		}
		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("nome"));
				impiegatoTemp.setCognome(rs.getString("cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTemp.setDataNascita(
						rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
				impiegatoTemp.setDataAssunzione(rs.getDate("dataassunzione")!= null? rs.getDate("dataassunzione").toLocalDate(): null);
				impiegatoTemp.setId(rs.getLong("id"));
				result.add(impiegatoTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (compagniaInput == null) {
			throw new Exception("errore: non è stato inserito alcuna compagnia!");
		}
		List<Impiegato> elencoImpiegatiDellaCompagnia = new ArrayList<Impiegato>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from impiegato i inner join compagnia c on i.id_compagnia=c.id where c.id like ?");) {

			ps.setLong(1, compagniaInput.getId());
			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Impiegato temp = new Impiegato();
					temp.setId(rs.getLong("id"));
					temp.setNome(rs.getString("nome"));
					temp.setCognome(rs.getString("cognome"));
					temp.setCodiceFiscale(rs.getString("codicefiscale"));
					temp.setDataNascita(
							rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
					temp.setDataAssunzione(
							rs.getDate("dataassunzione") != null ? rs.getDate("dataassunzione").toLocalDate() : null);
					elencoImpiegatiDellaCompagnia.add(temp);

				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return elencoImpiegatiDellaCompagnia;
	}

	public int countByDataFondazioneCompagniaGreaterThan(LocalDate dataFondazione) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (dataFondazione == null)
			throw new Exception("Valore di input non ammesso.");

		int count = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(datafondazione) from compagnia c inner join impiegato i on c.id=i.id_compagnia where datafondazione > ? ;")) {
			ps.setDate(1, java.sql.Date.valueOf(dataFondazione));

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					count = rs.getInt("count(datafondazione)");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return count;
	}

	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(int fatturatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (fatturatoInput == 0)
			throw new Exception("Valore non ammesso.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from compagnia c inner join impiegato i on c.id=i.id_compagnia where c.fatturatoannuo > ? ;")) {
			ps.setInt(1, fatturatoInput);

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					Compagnia compagniaTemp = new Compagnia();
					compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
					compagniaTemp.setId(rs.getLong("id"));

					Impiegato impiegatoTemp = new Impiegato();
					impiegatoTemp.setId(rs.getLong("id"));
					impiegatoTemp.setNome(rs.getString("nome"));
					impiegatoTemp.setCognome(rs.getString("cognome"));
					impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
					impiegatoTemp.setDataNascita(
							rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
					impiegatoTemp.setDataAssunzione(
							rs.getDate("dataassunzione") != null ? rs.getDate("dataassunzione").toLocalDate() : null);
					impiegatoTemp.setCompagnia(compagniaTemp);

					result.add(impiegatoTemp);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return result;
		}
	}

	public List<Impiegato> findAllErroriAssunzione() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from compagnia c inner join impiegato i on c.id=i.id_compagnia where datafondazione > dataassunzione ;")) {

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					Compagnia compagniaTemp = new Compagnia();
					compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
					compagniaTemp.setId(rs.getLong("id"));

					Impiegato impiegatoTemp = new Impiegato();
					impiegatoTemp.setId(rs.getLong("id"));
					impiegatoTemp.setNome(rs.getString("nome"));
					impiegatoTemp.setCognome(rs.getString("cognome"));
					impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
					impiegatoTemp.setDataNascita(
							rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
					impiegatoTemp.setDataAssunzione(
							rs.getDate("dataassunzione") != null ? rs.getDate("dataassunzione").toLocalDate() : null);

					result.add(impiegatoTemp);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return result;
		}
	}

}
