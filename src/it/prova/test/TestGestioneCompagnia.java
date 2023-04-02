package it.prova.test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import it.prova.connection.MyConnection;
import it.prova.dao.Constants;
import it.prova.dao.gestionecompagnia.CompagniaDAO;
import it.prova.dao.gestionecompagnia.CompagniaDAOImpl;
import it.prova.dao.gestionecompagnia.ImpiegatoDAO;
import it.prova.dao.gestionecompagnia.ImpiegatoDAOImpl;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;

public class TestGestioneCompagnia {

	public static void main(String[] args) {

		CompagniaDAO compagniaDAOInstance = null;
		ImpiegatoDAO impiegatoDAOInstance= null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance= new ImpiegatoDAOImpl(connection);

			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");
			System.out.println("in tabella impiegato sono presenti "+ impiegatoDAOInstance.list().size()+ " elementi.");

			
			testUpdateCompagnia (compagniaDAOInstance);
			System.out.println("in tabella sono presenti "+ compagniaDAOInstance.list().size()+ " elementi.");

			testInsertCompagnia(compagniaDAOInstance);
			System.out.println("in tabella sono presenti " + compagniaDAOInstance.list().size() + " elementi.");
			
			testDeleteCompagnia (compagniaDAOInstance);
			System.out.println("in tabella sono presenti " + compagniaDAOInstance.list().size() + " elementi.");
			
			testUpdateImpiegato(impiegatoDAOInstance);
			System.out.println("in tabella impiegato sono presenti "+ impiegatoDAOInstance.list().size()+" elementi.");
			
			testInsertImpiegato(impiegatoDAOInstance);
			System.out.println("in tabella sono presenti "+ impiegatoDAOInstance.list().size()+ " elementi.");
			
			testDeleteImpiegato(impiegatoDAOInstance);
			System.out.println("in tabella sono presenti "+ impiegatoDAOInstance.list().size()+ " elementi.");
			
			
			testFindAllByDataAssunzioneMaggioreDi(compagniaDAOInstance,impiegatoDAOInstance);
			System.out.println("In tabella compagnia ci sono "+compagniaDAOInstance.list().size()+" elementi.");

			
			testFindAllByRagioneSocialeContiene(compagniaDAOInstance);
			System.out.println("in tabella sono presenti "+ compagniaDAOInstance.list().size()+" elementi.");
			
			testFindAllByCodFisImpiegatoContiene(compagniaDAOInstance, impiegatoDAOInstance);
			System.out.println("in tabella sono presenti" + compagniaDAOInstance.list().size()+ " elementi.");
			
			
			testFindAllByCompagnia(compagniaDAOInstance, impiegatoDAOInstance);
			System.out.println("in tabella sono presenti" + impiegatoDAOInstance.list().size()+ " elementi.");


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// metodi per i test
	
	
	
	//CRUD COMPAGNIA
	
	//update
	
	private static void testUpdateCompagnia (CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".........testUpdateCompagnia inizio...........");
		List<Compagnia> elencoCompagnie=compagniaDAOInstance.list();
		if (elencoCompagnie.size()<1) {
			throw new RuntimeException ("errore: la lista di compagnie è vuota!!");
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
	
	
	//CRUD IMPIEGATO
	
	
	//update
	public static void testUpdateImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".........testUpdateImpiegato inizio...........");
		List<Impiegato> elencoImpiegati=impiegatoDAOInstance.list();
		if (elencoImpiegati.size()<1) {
			throw new RuntimeException ("errore: la lista di impiegati è vuota!!");
		
	}
	

	}
	
	//insert
	public static void testInsertImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testInsertImpiegato inizio.............");
		int quantiElementiInseriti = impiegatoDAOInstance.insert(new Impiegato("carola", "carolina", "CRLN3", LocalDate.of(2002,11,20) , LocalDate.now()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("TestInsertImpiegato : FAILED");

		System.out.println(".......testInsertImpiegato fine: PASSED.............");
	}
	
	//delete 
	
	public static void testDeleteImpiegato (ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testDeleteImpiegato inizio.............");
		int quantiElementiInseriti = impiegatoDAOInstance.insert(new Impiegato("rosa", "rosina","RSRS4", LocalDate.of(1967, 02, 15), LocalDate.of(2003, 07, 02)));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteImpiegato : FAILED, user da rimuovere non inserito");

		List<Impiegato> elencoVociPresenti = impiegatoDAOInstance.list();
		int numeroElementiPresentiPrimaDellaRimozione = elencoVociPresenti.size();
		if (numeroElementiPresentiPrimaDellaRimozione < 1)
			throw new RuntimeException("testDeleteImpiegato : FAILED, non ci sono voci sul DB");

		Impiegato ultimoDellaLista = elencoVociPresenti.get(numeroElementiPresentiPrimaDellaRimozione - 1);
		impiegatoDAOInstance.delete(ultimoDellaLista);

		int numeroElementiPresentiDopoDellaRimozione = impiegatoDAOInstance.list().size();
		if (numeroElementiPresentiDopoDellaRimozione != numeroElementiPresentiPrimaDellaRimozione - 1)
			throw new RuntimeException("testDeleteImpiegato : FAILED, la rimozione non è avvenuta");

		System.out.println(".......testDeleteImpiegato fine: PASSED.............");
	}
	
	
	
	
	//definizione metodi Compagnia
	
	//testFindAllByDataAssunzioneMaggioreDi
	public static void testFindAllByDataAssunzioneMaggioreDi (CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance)throws Exception {
		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi inizio......");
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		if (elencoCompagniePresenti.size() < 1)
			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi : FAILED, non ci sono compagnia sul DB");
		List<Impiegato> elencoImpiegatiPresenti = impiegatoDAOInstance.list();
		if (elencoImpiegatiPresenti.size() < 1)
			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi : FAILED, non ci sono impiegati sul DB");

		LocalDate dataDaRicercare = LocalDate.parse("2000-01-01");
		List<Compagnia> listaCompagniaLikeExample = compagniaDAOInstance.findAllByDataAssunzioneMaggioreDi(dataDaRicercare);
		if (listaCompagniaLikeExample.size() < 1) {
			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi : FAILED, non ci sono voci sul DB");
		}
		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi fine: PASSED.............");
	}

	
	//testFindAllByRagioneSocialeContiene
	private static void testFindAllByRagioneSocialeContiene (CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("......testFindAllByRagioneSocialeContiene inizio......");
		List<Compagnia> compagnieEsistenti= compagniaDAOInstance.list();
		String parteNomeRagioneSociale= "a";
		List<Compagnia> result=compagniaDAOInstance.findAllByRagioneSocialeContiene(parteNomeRagioneSociale);
		System.out.println(result);
		System.out.println("......testFindAllByRagioneSocialeContiene fine......");

		
	}
	
	
	//testFindAllByCodFisImpiegatoContiene
	private static void testFindAllByCodFisImpiegatoContiene (CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println(".......testFindAllByCodFisImpiegatoContiene inizio......");
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		if (elencoCompagniePresenti.size() < 1)
			throw new RuntimeException("testFindAllByCodFisImpiegatoContiene : FAILED, non ci sono compagnia sul DB");
		List<Impiegato> elencoImpiegatiPresenti = impiegatoDAOInstance.list();
		if (elencoImpiegatiPresenti.size() < 1)
			throw new RuntimeException("testFindAllByCodFisImpiegatoContiene : FAILED, non ci sono impiegati sul DB");

		String codFisDaCercare = "h501";
		List<Compagnia> listaCompagniaLikeExample = compagniaDAOInstance.findAllBYCodFisImpiegatoContiene(codFisDaCercare);
		if (listaCompagniaLikeExample.size() < 1) {
			throw new RuntimeException("testFindAllByCodFisImpiegatoContiene : FAILED, non ci sono voci sul DB");
		}
		System.out.println("Gli elementi della lista sono: " + listaCompagniaLikeExample.size());
		System.out.println(listaCompagniaLikeExample);
		System.out.println(".......testFindAllByCodFisImpiegatoContiene fine: PASSED.............");
	}
	
	
	//testFindAllByCompagnia
	public static void testFindAllByCompagnia(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println("......testFindAllByCompagnia inizio.........");
		List<Compagnia> elencoCompagnie=compagniaDAOInstance.list();
		if(elencoCompagnie.size()<1) {
			throw new RuntimeException("errore: non sono presenti compagnie sul db.");
		}
		List<Impiegato> elencoImpiegati= impiegatoDAOInstance.list();
		if (elencoImpiegati.size()<1) {
			throw new RuntimeException ("errore: non sono presenti impiegati sul db.");
		}
		Compagnia compagnieDaRicercare= elencoCompagnie.get(0);
		List<Impiegato> impiegatiDellaCompagnia= impiegatoDAOInstance.findAllByCompagnia(compagnieDaRicercare);
		if (impiegatiDellaCompagnia.size()<1) {
			throw new RuntimeException("non è stato trovato nulla");
		}
		System.out.println("gli elementi che corrispondono sono "+impiegatiDellaCompagnia.size() );
		System.out.println(impiegatiDellaCompagnia);
		System.out.println("..........testFindAllByCompagnia fine......");
		
	}


	}
	
