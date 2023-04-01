package it.prova.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Compagnia {
	private Long id;
	private String ragioneSociale;
	private int fatturatoAnnuo;
	private LocalDate dataFondazione;
	
	public Compagnia () {
		
	}
	
	public Compagnia (String ragioneSociale, int fatturatoAnnuo, LocalDate dataFondazione) {
		this.ragioneSociale=ragioneSociale;
		this.fatturatoAnnuo=fatturatoAnnuo;
		this.dataFondazione=dataFondazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public int getFatturatoAnnuo() {
		return fatturatoAnnuo;
	}

	public void setFatturatoAnnuo(int fatturatoAnnuo) {
		this.fatturatoAnnuo = fatturatoAnnuo;
	}

	public LocalDate getDataFondazione() {
		return dataFondazione;
	}

	public void setDataFondazione(LocalDate dataFondazione) {
		this.dataFondazione = dataFondazione;
	}

	public String toString() {
		String dataFondazioneString = dataFondazione != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataFondazione)
				: " N.D.";

		return "Compagnia [id=" + id + ", ragioneSociale=" + ragioneSociale + ", fatturatoAnnuo=" + fatturatoAnnuo
				+ ", dataFondazione=" + dataFondazione + "]";
	}
	
	

}
