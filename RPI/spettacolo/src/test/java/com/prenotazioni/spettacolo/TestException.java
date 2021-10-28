package com.prenotazioni.spettacolo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.spettacolo.Controller.SpettacoloControllerCommand;
import com.prenotazioni.spettacolo.Controller.SpettacoloControllerQuery;
import com.prenotazioni.spettacolo.Entity.Posto;
import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;
import com.prenotazioni.spettacolo.Exception.ExceptionNoPosto;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSala;
import com.prenotazioni.spettacolo.Exception.ExceptionNoSpettacolo;
import com.prenotazioni.spettacolo.Services.SpettacoloServiceCommand;
import com.prenotazioni.spettacolo.Services.SpettacoloServiceQuery;

public class TestException extends SpettacoloApplicationTests{
	
	@Autowired 
	SpettacoloServiceQuery spettacoloServiceQuery;
	
	@Autowired 
	SpettacoloServiceCommand spettacoloServiceCommand;
	
	@Autowired 
	SpettacoloControllerCommand spettacoloControllerCommand;
	
	@Autowired
	SpettacoloControllerQuery spettacoloControllerQuery;
	
	@Test
	public void throwExceptionNoPosto() {
		try {
			Spettacolo spettacolo = spettacoloServiceCommand.setPostoByTitoloAnNumeroSalaAndPosto("Aladin", 1, new Posto("c",2,false));
			assertTrue(spettacolo.toString().equals(""));
		}catch(ExceptionNoPosto e) {
			assertTrue(true);
		}catch(Exception e1) {
			assertTrue(false);
		}
	}
	
	@Test
	public void throwExceptionNoSpettacolo1() {
		try {
			Spettacolo spettacolo = spettacoloServiceQuery.getSpettacolo("Pippo");
			assertTrue(spettacolo.toString().equals(null));
		}catch(ExceptionNoSpettacolo e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void throwExceptionNoSpettacolo2() {
		try {
			int cost = spettacoloServiceQuery.getCostoBySpettacolo("Pippo");
			assertTrue(cost == 0);
		}catch(ExceptionNoSpettacolo e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void throwExceptionNoSpettacolo3() {
		try {
			Spettacolo spettacolo = spettacoloServiceQuery.getAllFreePostoPerAllSalaByShow("Pippo");
			assertTrue(spettacolo.toString().equals(null));
		}catch(ExceptionNoSpettacolo e) {
			assertTrue(true);
		}
	}
	
	@Test 
	public void throwExceptionNoSpettacolo4() {
		try {
			List<Sala> spettacolo = spettacoloServiceQuery.getSaleBySpettacolo("Pippo");
			assertTrue(spettacolo.toString().equals(""));
		}catch(ExceptionNoSpettacolo e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void throwExceptionNoSala1() {
		try {
			Spettacolo spettacolo = spettacoloServiceQuery.getSpettacoloBySpettacoloAndNumeroSala("Aladin", 5);
			assertTrue(spettacolo.toString().equals(""));
		}catch(ExceptionNoSpettacolo e) {
			assertTrue(false);
		}catch(ExceptionNoSala e1) {
			assertTrue(true);
		}
	}
	
	@Test
	public void throwExceptionNoSala2() {
		try {
			Spettacolo spettacolo = spettacoloServiceQuery.getAllFreePostoBySpettacoloAndNumeroSala("Aladin", 10);
			assertTrue(spettacolo.toString().equals(""));
		}catch(ExceptionNoSpettacolo e) {
			assertTrue(false);
		}catch(ExceptionNoSala e1) {
			assertTrue(true);
		}
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerQuery1() {
		Spettacolo spettacolo = spettacoloControllerQuery.getShow("La Bella e la Bestia");
		assertEquals(spettacolo, null);	
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerQuery2() {
		Spettacolo spettacolo = spettacoloControllerQuery.getShowByShowAndNumeroSala("La Bella e la Bestia", 1);
		assertEquals(spettacolo, null);	
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerQuery4() {
		String spettacolo = spettacoloControllerQuery.getCostoBySpettacolo("La Bella e la Bestia");
		assertEquals(spettacolo, "No show with name: La Bella e la Bestia");	
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerQuery5() {
		List<Sala> listSala = spettacoloControllerQuery.getAllSalaBySpettacolo("La Bella e la Bestia");
		assertEquals(listSala, null);	
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerQuery6() {
		Spettacolo spettacolo = spettacoloControllerQuery.getAllFreePostoPerAllSalaByShow("La Bella e la Bestia");
		assertEquals(spettacolo, null);	
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerQuery7() {
		Spettacolo spettacolo = spettacoloControllerQuery.getAllFreePostoByShowAndNumeroSala("La Bella e la Bestia", 1);
		assertEquals(spettacolo, null);	
	}
	
	@Test
	public void throwExceptionNoSalaControllerQuery1() {
		Spettacolo spettacolo = spettacoloControllerQuery.getShowByShowAndNumeroSala("Aladin", 6);
		assertEquals(spettacolo, null);	
	}
	
	@Test
	public void throwExceptionNoSalaControllerQuery2() {
		Spettacolo spettacolo = spettacoloControllerQuery.getAllFreePostoByShowAndNumeroSala("Aladin", 10);
		assertEquals(spettacolo, null);	
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerCommand1() {
		String response = spettacoloControllerCommand.addSalaByShow("La Bella e la Bestia", new Sala(5, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))));
		assertEquals(response, "No Show with this name La Bella e la Bestia");
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerCommand2() {
		String response = spettacoloControllerCommand.addExistsSalaByShow("La Bella e la Bestia", Arrays.asList(1,2));
		assertEquals(response, "No Show with this name La Bella e la Bestia");
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerCommand3() {
		String response = spettacoloControllerCommand.deleteEverythingByShow("La Bella e la Bestia");
		assertEquals(response, "No Show with this name La Bella e la Bestia");
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerCommand4() {
		String response = spettacoloControllerCommand.deleteSalaByShowAndNumeroSala("La Bella e la Bestia", 1);
		assertEquals(response, "No Show with this name La Bella e la Bestia");
	}
	
	@Test
	public void throwExceptionNoSpettacoloControllerCommand5() {
		String response = spettacoloControllerCommand.upsetPostoByShowAndNumeroSala("La Bella e la Bestia", 1, 1, "a", false);
		assertEquals(response, "No show with name: La Bella e la Bestia");
	}
	
	@Test
	public void throwExceptionNoSalaControllerCommand1() {
		String response = spettacoloControllerCommand.deleteSalaByShowAndNumeroSala("Aladin", 6);
		assertEquals(response, "No Sala: 6 for show: Aladin");
	}
	
	@Test
	public void throwExceptionNoSalaControllerCommand2() {
		String response = spettacoloControllerCommand.upsetPostoByShowAndNumeroSala("Aladin", 9, 1, "a", false);
		assertEquals(response, "No sala with numeroSala: 9");
	}
	
	@Test
	public void throwExceptionNoPostoControllerCommand2() {
		String response = spettacoloControllerCommand.upsetPostoByShowAndNumeroSala("Aladin", 1, 1, "d", false);
		assertEquals(response, "Not exists posto: D1 for sala: 1");
	}
}
