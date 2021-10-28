package com.prenotazioni.apigateway;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.apigateway.Controllers.SpettacoloControllerCommand;
import com.prenotazioni.apigateway.Entity.Posto;
import com.prenotazioni.apigateway.Entity.Sala;
import com.prenotazioni.apigateway.Entity.Spettacolo;

public class TestControllerSpettacoloCommand extends ApiGatewayApplicationTests{
	
	@Autowired 
	SpettacoloControllerCommand spettacoloControllerCommand;
	
	@Test
	public void deleteByShow() {
		String response = spettacoloControllerCommand.deleteByShow("Aristogatti");
		assertTrue(response.equals("Show: Aristogatti deleted correctly"));
	}

	@Test
	public void deleteSala() {
		String response = spettacoloControllerCommand.deleteSalaByShowAndNumeroSala("Avatar", 2);
		assertTrue(response.equals("Deleted correctly\nSala: 2 the show: Avatar"));

	}

	@Test
	public void insertNewShowAndSala() {
		Spettacolo s1 = new Spettacolo("Pippo",
				Arrays.asList(
						new Sala(4,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
										new Posto("b", 1, false), new Posto("b", 2, true))),
						new Sala(5, Arrays.asList(new Posto("a", 1, false), new Posto("a", 2, false),
								new Posto("b", 1, true), new Posto("b", 2, true)))),
				3);
		String response = spettacoloControllerCommand.insertNewShowAndNewSala(s1);
		assertTrue(response.equals("New Show added correctly"));
	}

	@Test
	public void insertNewShowExistsSala() {
		String response = spettacoloControllerCommand.insertNewShowKnowSala("Minnie", 5, Arrays.asList(1));
		assertTrue(response.equals("New Show added correctly"));
	}

	@Test
	public void insertExistsSala() {
		String response = spettacoloControllerCommand.addExistsSalaByShow("Avatar", Arrays.asList(3));
		Spettacolo s2 = new Spettacolo("Avatar",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true),
										new Posto("b", 2, true))),
						new Sala(2,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true),
										new Posto("b", 2, true))),
						new Sala(3, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
								new Posto("b", 1, true), new Posto("b", 2, true)))),
				15);
		assertTrue(response.equals(s2.toString()));

	}
	
	@Test
	public void insertNewSala() {
		String response = spettacoloControllerCommand.addNewSalaByShow("Avatar",
				new Sala(6, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true),
						new Posto("b", 2, true))));
		assertTrue(response.equals("New Sala added correctly for the show Avatar"));

	}

	@Test
	public void setPosto() {
		String spettacolo = spettacoloControllerCommand.setPosto("Titanic", 1,false, "a", 2);
		Spettacolo spettacoloTest1 = new Spettacolo("Titanic",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, false),
										new Posto("b", 1, true), new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
								new Posto("b", 1, true), new Posto("b", 2, true)))), 5);
		
		//se viene eseguito dopo le exception
		Spettacolo spettacoloTest2 = new Spettacolo("Titanic",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, false), new Posto("a", 2, false),
										new Posto("b", 1, true), new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
								new Posto("b", 1, true), new Posto("b", 2, true)))), 5);
		assertTrue(spettacolo.equals(spettacoloTest1.toString()) || spettacolo.equals(spettacoloTest2.toString()));

	}
}
