package it.prova.dao.gestionecompagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.prova.dao.AbstractMySQLDAO;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;



public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	// iniezione della conessione
	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	public List<Compagnia> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {
			while (rs.next()) {
				Compagnia compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDataFondazione(
						rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
				compagniaTemp.setId(rs.getLong("id"));
				result.add(compagniaTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// INIZIO DEL CRUD

	// definizione metodo get
	public Compagnia get(Long idInput) throws Exception {
		// verifica della disponibilità della connessione
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setRagioneSociale(rs.getString("ragionesociale"));
					result.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					result.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
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

	// definizione metodo update

	public int update(Compagnia input) throws Exception {

		// verifica della connessione
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE compagnia SET ragionesociale=?, fatturatoannuo=?, datafondazione=? where id=?;")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, java.sql.Date.valueOf(input.getDataFondazione()));
			ps.setLong(4, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// definizione metodo insert

	public int insert(Compagnia input) throws Exception {
		// verifica della connessione
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO compagnia (ragionesociale, fatturatoannuo, datafondazione) VALUES (?, ?, ?);")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(3, java.sql.Date.valueOf(input.getDataFondazione()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	// definizione metodo delete

	public int delete(Compagnia input) throws Exception {
		// verifica della connessione
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM compagnia WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	//findByExample

	public List<Compagnia> findByExample(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input == null)
			throw new RuntimeException("Impossibile caricare Compagnia: id mancante!");

		List<Compagnia> result=new ArrayList<Compagnia>();
		Compagnia compagniaTemp= null;
		
		String query= "select * from compagnia where  id is not null";
		if (input.getRagioneSociale()!= null && ! input.getRagioneSociale().isEmpty()) {
			query+="and ragionesociale like ' "+ input.getRagioneSociale() + " %' ";
		}
		if (input.getFatturatoAnnuo()!= 0) {
			query+="and fatturatoannuo > ' " +input.getFatturatoAnnuo() + " ' ";
		}
		if(input.getDataFondazione()!= null) {
			query+="and datafondazione = ' "+ input.getDataFondazione()+ " ' ";
		}
		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDataFondazione(
						rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
				compagniaTemp.setId(rs.getLong("id"));
				result.add(compagniaTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}


	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(LocalDate dataAssunzione) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (dataAssunzione == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select distinct * from compagnia c inner join impiegato i on c.id=i.id_compagnia where dataassunzione > ? ;")) {
			ps.setDate(1, java.sql.Date.valueOf(dataAssunzione));

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

					result.add(compagniaTemp);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return result;
		}
	}

	public List<Compagnia> findAllByRagioneSocialeContiene(String ragioneSocialeInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (ragioneSocialeInput == null) {
			throw new Exception("errore: non è stato inserito alcuna ragione sociale!");
		}
		List<Compagnia> elencoCompagnieConRagioneSocialeInput = new ArrayList<Compagnia>();

		try (PreparedStatement ps = connection
				.prepareStatement("select * from compagnia c where c.ragionesociale like ?");) {

			ps.setString(1, '%' + ragioneSocialeInput + '%');
			try (ResultSet rs = ps.executeQuery()) {
				;

				while (rs.next()) {
					Compagnia temp = new Compagnia();
					temp.setId(rs.getLong("id"));
					temp.setRagioneSociale(rs.getString("ragionesociale"));
					temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					temp.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
					elencoCompagnieConRagioneSocialeInput.add(temp);

				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return elencoCompagnieConRagioneSocialeInput;
	}

	public List<Compagnia> findAllBYCodFisImpiegatoContiene(String codiceFiscaleInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (codiceFiscaleInput == null || codiceFiscaleInput.isBlank())
			throw new Exception("Valore di input non ammesso.");
		List<Compagnia> result = new ArrayList<>();
		try (PreparedStatement ps = connection
				.prepareStatement("select distinct c.id,ragionesociale,fatturatoannuo,datafondazione from compagnia c "
						+ "inner join impiegato i on c.id=i.id_compagnia where i.codicefiscale like ?;")) {

			ps.setString(1, '%' + codiceFiscaleInput + '%');

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Compagnia compagniaTemp = new Compagnia();
					compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
					compagniaTemp.setId(rs.getLong("c.ID"));
					result.add(compagniaTemp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

}
