package com.prenotazioni.spettacolo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.spettacolo.Controller.SpettacoloControllerCommand;
import com.prenotazioni.spettacolo.Entity.Posto;
import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;

public class TestSpettacoloControllerCommand extends SpettacoloApplicationTests{
	@Autowired 
	SpettacoloControllerCommand spettacoloControllerCommand;
	
	@Test
	public void deleteByShow() {
		String response = spettacoloControllerCommand.deleteEverythingByShow("Rapunzel");
		assertTrue(response.equals("Show: Rapunzel deleted correctly"));
	}
	
	@Test 
	public void deleteSala() {
		String response = spettacoloControllerCommand.deleteSalaByShowAndNumeroSala("Aladin", 2);
		assertTrue(response.equals("Deleted correctly\nSala: 2 the show: Aladin"));
	}
	
	@Test
	public void insertNewShowAndSala() {
		Spettacolo s1 = new Spettacolo("Pippo", 
				Arrays.asList	(new Sala(4, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,false), new Posto("b",2,true))),
						new Sala(5, Arrays.asList(new Posto("a", 1, false), new Posto("a",2,false), new Posto("b",1,true), new Posto("b",2,true)))), 3, "Add New");
		String response = spettacoloControllerCommand.insertNewShow(s1);
		assertTrue(response.equals("New Show added correctly"));
	}
	
	@Test
	public void insertNewShowExistsSala() {
		String response = spettacoloControllerCommand.insertNewShowKnowSala("Minnie", 5, Arrays.asList(1));
		assertTrue(response.equals("New Show added correctly"));
	}
	
	@Test
	public void insertNewSala() {
		String response = spettacoloControllerCommand.addNewSalaByShow("Aladin", new Sala(6, Arrays.asList(new Posto("a", 1, false), new Posto("a",2,false), new Posto("b",1,true), new Posto("b",2,true))));
		assertTrue(response.equals("New Sala added correctly for the show Aladin"));
	}
	
	@Test
	public void insertExistsSala() {
		String response = spettacoloControllerCommand.addSalaAlreadyExistByShow("Aladin", Arrays.asList(3));
		Spettacolo s2 = new Spettacolo("Aladin", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(3, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 10, "Add New");
		assertTrue(response.equals(s2.toString()));
	}
	
	@Test
	public void setPosto() {
		String spettacolo = spettacoloControllerCommand.upsetPostoByShowAndNumeroSala("Aladin", 1, 2, "a", false);
		String spettacoloTest = "{\"spettacolo\":\"Aladin\",\"costo\":10,\"sala\":"
				+ "[{\"numeroSala\":1,\"posto\":[{\"riga\":\"a\",\"colonna\":1,\"free\":true}, {\"riga\":\"a\",\"colonna\":2,\"free\":false}, "
					+ "{\"riga\":\"b\",\"colonna\":1,\"free\":true}, {\"riga\":\"b\",\"colonna\":2,\"free\":true}]}, "
				+ "{\"numeroSala\":2,\"posto\":[{\"riga\":\"a\",\"colonna\":1,\"free\":true}, {\"riga\":\"a\",\"colonna\":2,\"free\":true}, "
					+ "{\"riga\":\"b\",\"colonna\":1,\"free\":true}, {\"riga\":\"b\",\"colonna\":2,\"free\":true}]}]}";
		assertTrue(spettacolo.equals(spettacoloTest));
	}
}
