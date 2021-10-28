package com.prenotazioni.apigateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.apigateway.Controllers.SpettacoloControllerQuery;
import com.prenotazioni.apigateway.Entity.Posto;
import com.prenotazioni.apigateway.Entity.Sala;
import com.prenotazioni.apigateway.Entity.Spettacolo;


public class TestControllerSpettacoloQuery extends ApiGatewayApplicationTests{
	
	@Autowired
	SpettacoloControllerQuery spettacoloControllerQuery;

	@Test
	public void getAll() {
		Spettacolo s1 = new Spettacolo("Titanic", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 5);
		Spettacolo s2 = new Spettacolo("Avatar", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 15);
		Spettacolo s3 = new Spettacolo("Aristogatti", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 7);
		Spettacolo s4 = new Spettacolo("Infinity War", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(3, Arrays.asList(new Posto("a",1,true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b", 2, true)))), 5);
		
		//parametri che si aggiungono o variano se pria viene eseguito il command
		Spettacolo s5 =  new Spettacolo("Pippo", 
				Arrays.asList	(new Sala(4, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,false), new Posto("b",2,true))),
						new Sala(5, Arrays.asList(new Posto("a", 1, false), new Posto("a",2,false), new Posto("b",1,true), new Posto("b",2,true)))), 3);
		Spettacolo s6 = new Spettacolo("Minnie", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 5);
		Spettacolo s11 = new Spettacolo("Titanic", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,false), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 5);
		Spettacolo s21 = new Spettacolo("Avatar", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(3, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(6, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 15);
		
		//sarà uguale a questo se prima viene eseguito il command
		List<String> allFromRepository = Arrays.asList(s11.toString(),s21.toString(),s4.toString(),s5.toString(),s6.toString());
		
		//sarà uguale a questo se prima viene eseguito la query e poi command
		List<String> allFromRepository1 = Arrays.asList(s1.toString(),s2.toString(),s3.toString(),s4.toString());
		
		List<String> allFromService = spettacoloControllerQuery.getAllShow();
		
		assertTrue(allFromRepository.equals(allFromService) || allFromRepository1.equals(allFromService));
	}

	@Test
	public void getShow() {
		Spettacolo showFromRepository = new Spettacolo("Titanic",
				Arrays.asList(
						new Sala(1,	Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true), new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true), new Posto("b", 2, true)))),
				5);
		String showFromService = spettacoloControllerQuery.getShow("Titanic", -1);
		
		//se prima viene eseguito il command divena così
		Spettacolo showFromRepository1 = new Spettacolo("Titanic",
				Arrays.asList(
						new Sala(1,	Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, false), new Posto("b", 1, true), new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true), new Posto("b", 2, true)))),
				5);
		
		assertTrue((showFromRepository.toString()).equals(showFromService) || (showFromRepository1.toString()).equals(showFromService));

	}

	@Test
	public void getShowBySpettacoloAndNumeroSala() {
		Spettacolo s1 = new Spettacolo("Titanic", Arrays.asList(new Sala(1, Arrays.asList(new Posto("a", 1, true),
				new Posto("a", 2, true), new Posto("b", 1, true), new Posto("b", 2, true)))), 5);
		
		//se prima viene eseguito il command diventa cosi
		Spettacolo s11 = new Spettacolo("Titanic", Arrays.asList(new Sala(1, Arrays.asList(new Posto("a", 1, true),
				new Posto("a", 2, false), new Posto("b", 1, true), new Posto("b", 2, true)))), 5);
		
		String test = spettacoloControllerQuery.getShow("Titanic", 1);
		assertTrue((s1.toString()).equals(test) || s11.toString().equals(test));
	}

	@Test
	public void getCostoBySpettacolo() {
		assertEquals(spettacoloControllerQuery.getCostByShow("Titanic"), "show: Titanic, costs: 5");

	}

	@Test
	public void getFreePostoByShow() {
		String show = spettacoloControllerQuery.getAllFreePostoByShowOrShowAndNumeroSala("Titanic", -1);
		Spettacolo showTest = new Spettacolo("Titanic",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
										new Posto("b", 1, true), new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(
								new Posto("a", 1, true),new Posto("a", 2, true),new Posto("b", 1, true), new Posto("b", 2, true)))),
				5);
		
		//se viene eseguito prima il command
		Spettacolo showTest1 = new Spettacolo("Titanic",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, true),
										new Posto("b", 1, true), new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(
								new Posto("a", 1, true),new Posto("a", 2, true),new Posto("b", 1, true), new Posto("b", 2, true)))),
				5);
		
		assertTrue(show.equals(showTest.toString()) || show.equals(showTest1.toString()));

	}

	@Test
	public void getAllFreePostoByShowAndNumeroSala() {
		String spettacolo = spettacoloControllerQuery.getAllFreePostoByShowOrShowAndNumeroSala("Titanic", 2);
		Spettacolo spettacoloTest = new Spettacolo("Titanic",
				Arrays.asList(
						new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true), new Posto("b", 2, true)))), 5);
		assertTrue(spettacolo.equals(spettacoloTest.toString()));

	}
}
