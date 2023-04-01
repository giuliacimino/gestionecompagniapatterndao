package it.prova.test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import it.prova.connection.MyConnection;
import it.prova.dao.Constants;
import it.prova.dao.gestionecompagnia.CompagniaDAO;
import it.prova.dao.gestionecompagnia.CompagniaDAOImpl;
import it.prova.dao.gestionecompagnia.CompagniaDAO;
import it.prova.model.Compagnia;

public class TestGestioneCompagnia {

	public static void main(String[] args) {

		CompagniaDAO compagniaDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			compagniaDAOInstance = new CompagniaDAOImpl(connection);

			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");
			
			testUpdateCompagnia (compagniaDAOInstance);
			System.out.println("in tabella sono presenti "+ compagniaDAOInstance.list().size()+ " elementi.");

			testInsertCompagnia(compagniaDAOInstance);
			System.out.println("in tabella sono presenti " + compagniaDAOInstance.list().size() + " elementi.");
			
			testDeleteCompagnia (compagniaDAOInstance);
			System.out.println("in tabella sono presenti " + compagniaDAOInstance.list().size() + " elementi.");


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// metodi per i test
	
	
	
	//CRUD
	
	//update
	
	private static void testUpdateCompagnia (CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".........testUpdateCompagnia inizio...........");
		List<Compagnia> elencoCompagnie=compagniaDAOInstance.list();
		if (elencoCompagnie.size()<1) {
			throw new RuntimeException ("errore: la lista di negozi è vuota!!");
		}
	}

	
	//insert
	private static void testInsertCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testInsertCompagnia inizio.............");
		int quantiElementiInseriti = compagniaDAOInstance.insert(new Compagnia("ikea", 150, LocalDate.now()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertCompagnia : FAILED");

		System.out.println(".......testInsertCompagnia fine: PASSED.............");
	}
	
	//delete
	public static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testDeleteCompagnia inizio.............");
		int quantiElementiInseriti = compagniaDAOInstance.insert(new Compagnia("Dell", 200, LocalDate.now()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, user da rimuovere non inserito");

		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		int numeroElementiPresentiPrimaDellaRimozione = elencoVociPresenti.size();
		if (numeroElementiPresentiPrimaDellaRimozione < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, non ci sono voci sul DB");

		Compagnia ultimoDellaLista = elencoVociPresenti.get(numeroElementiPresentiPrimaDellaRimozione - 1);
		compagniaDAOInstance.delete(ultimoDellaLista);

		int numeroElementiPresentiDopoDellaRimozione = compagniaDAOInstance.list().size();
		if (numeroElementiPresentiDopoDellaRimozione != numeroElementiPresentiPrimaDellaRimozione - 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, la rimozione non è avvenuta");

		System.out.println(".......testDeleteCompagnia fine: PASSED.............");
	}
	
	

}
