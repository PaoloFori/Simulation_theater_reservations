package com.prenotazioni.spettacolo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.spettacolo.Controller.SpettacoloControllerQuery;
import com.prenotazioni.spettacolo.Entity.Posto;
import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;

public class TestControllerQuery extends SpettacoloApplicationTests {

	@Autowired
	SpettacoloControllerQuery spettacoloControllerQuery;

	@Test
	public void getAll() {
		Spettacolo s1 = new Spettacolo("Rapunzel",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true),
										new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
								new Posto("b", 1, true), new Posto("b", 2, true)))),
				5);
		Spettacolo s2 = new Spettacolo("Aladin",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true),
										new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
								new Posto("b", 1, true), new Posto("b", 2, true)))),
				10);
		Spettacolo s3 = new Spettacolo("Topolino",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
										new Posto("b", 1, false), new Posto("b", 2, true))),
						new Sala(2,
								Arrays.asList(new Posto("a", 1, false), new Posto("a", 2, false),
										new Posto("b", 1, true), new Posto("b", 2, true))),
						new Sala(3, Arrays.asList(new Posto("a", 1, false), new Posto("a", 2, false),
								new Posto("b", 1, true), new Posto("b", 2, true)))),
				3);
		List<Spettacolo> allFromRepository = Arrays.asList(s1, s2, s3);
		List<Spettacolo> allFromService = spettacoloControllerQuery.getAll();
		int cont = 0;

		if (allFromRepository.size() == allFromService.size()) {
			for (int i = 0; i < allFromRepository.size(); i++) {
				Spettacolo s11 = allFromRepository.get(i);
				Spettacolo s22 = allFromService.get(i);
				if ((s11.toString()).equals(s22.toString())) {
					cont = cont + 1;
				}
			}
		}
		assertTrue(cont == allFromService.size() && cont == allFromRepository.size());
	}

	@Test
	public void getShow() {
		Spettacolo showFromRepository = new Spettacolo("Rapunzel",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true),
										new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
								new Posto("b", 1, true), new Posto("b", 2, true)))),
				5);
		Spettacolo showFromService = spettacoloControllerQuery.getShow("Rapunzel");
		assertTrue((showFromRepository.toString()).equals(showFromService.toString()));

	}

	@Test
	public void getShowBySpettacoloAndNumeroSala() {
		Spettacolo s1 = new Spettacolo("Rapunzel", Arrays.asList(new Sala(1, Arrays.asList(new Posto("a", 1, true),
				new Posto("a", 2, true), new Posto("b", 1, true), new Posto("b", 2, true)))), 5);
		assertTrue(
				(s1.toString()).equals(spettacoloControllerQuery.getShowByShowAndNumeroSala("Rapunzel", 1).toString()));

	}

	@Test
	public void getCostoBySpettacolo() {
		assertEquals(spettacoloControllerQuery.getCostoBySpettacolo("Aladin"), "show: Aladin, costs: " + 10);

	}

	@Test
	public void getSaleBySpettacolo() {
		List<Sala> saleTest1 = spettacoloControllerQuery.getAllSalaBySpettacolo("Aladin");
		List<String> saleTest = new ArrayList<>();
		for (Sala sala : saleTest1) {
			saleTest.add(sala.toString());
		}
		List<String> sale = Arrays.asList(
				new Sala(1,
						Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true),
								new Posto("b", 2, true))).toString(),
				new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true), new Posto("b", 1, true),
						new Posto("b", 2, true))).toString());
		assertTrue(sale.equals(saleTest));

	}

	@Test
	public void getFreePostoByShow() {
		Spettacolo show = spettacoloControllerQuery.getAllFreePostoPerAllSalaByShow("Topolino");
		Spettacolo showTest = new Spettacolo("Topolino",
				Arrays.asList(
						new Sala(1,
								Arrays.asList(new Posto("a", 1, true), new Posto("a", 2, true),
										new Posto("b", 2, true))),
						new Sala(2, Arrays.asList(new Posto("b", 1, true), new Posto("b", 2, true))),
						new Sala(3, Arrays.asList(new Posto("b", 1, true), new Posto("b", 2, true)))),
				3);
		assertTrue((show.toString()).equals(showTest.toString()));

	}

	@Test
	public void getAllFreePostoByShowAndNumeroSala() {
		Spettacolo spettacolo = spettacoloControllerQuery.getAllFreePostoByShowAndNumeroSala("Topolino", 2);
		Spettacolo spettacoloTest = new Spettacolo("Topolino",
				Arrays.asList(new Sala(2, Arrays.asList(new Posto("b", 1, true), new Posto("b", 2, true)))), 3);
		assertTrue(spettacolo.toString().equals(spettacoloTest.toString()));

	}

}
