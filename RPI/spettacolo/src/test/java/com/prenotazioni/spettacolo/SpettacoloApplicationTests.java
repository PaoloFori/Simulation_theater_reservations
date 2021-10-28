package com.prenotazioni.spettacolo;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prenotazioni.spettacolo.Entity.Posto;
import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;
import com.prenotazioni.spettacolo.Repository.SpettacoloRepository;

@SpringBootTest
class SpettacoloApplicationTests {

	@Autowired
	SpettacoloRepository spettacoloRepository;

	@Test
	void contextLoads() {
	}

	@BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        saveAll();
    }
	
	public void saveAll() {
		Spettacolo s1 = new Spettacolo("Rapunzel", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 5);
			Spettacolo s2 = new Spettacolo("Aladin", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 10);
			Spettacolo s3 = new Spettacolo("Topolino", 
				Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,false), new Posto("b",2,true))),
								new Sala(2, Arrays.asList(new Posto("a", 1, false), new Posto("a",2,false), new Posto("b",1,true), new Posto("b",2,true))),
								new Sala(3, Arrays.asList(new Posto("a", 1, false), new Posto("a",2,false), new Posto("b",1,true), new Posto("b",2,true)))), 3);
			List<Spettacolo> spett = Arrays.asList(s1,s2,s3);
			
			spettacoloRepository.deleteAll();
			spettacoloRepository.saveAll(spett);
	}
}
