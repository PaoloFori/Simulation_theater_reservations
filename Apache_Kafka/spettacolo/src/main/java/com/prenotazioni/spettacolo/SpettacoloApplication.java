package com.prenotazioni.spettacolo;

import java.util.Arrays;
import java.util.List;

import com.prenotazioni.spettacolo.Entity.Posto;
import com.prenotazioni.spettacolo.Entity.Sala;
import com.prenotazioni.spettacolo.Entity.Spettacolo;
import com.prenotazioni.spettacolo.Repository.SpettacoloRepository;
import com.prenotazioni.spettacolo.Services.SpettacoloServiceCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpettacoloApplication implements CommandLineRunner{

	@Autowired
	SpettacoloRepository spettacoloRepository;

	@Autowired
	SpettacoloServiceCommand spettacoloServiceCommand;

	public static void main(String[] args) {
		SpringApplication.run(SpettacoloApplication.class, args);
	}

	@Override
	public void run(String...strings) throws Exception{
		//creo i miei spettacoli con sala e posti
		Spettacolo s1 = new Spettacolo("Titanic", 
			Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
							new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 5, "Add New");
		spettacoloServiceCommand.publishMessage(s1);
		Spettacolo s2 = new Spettacolo("Avatar", 
			Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
							new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 15, "Add New");
		spettacoloServiceCommand.publishMessage(s2);
		Spettacolo s3 = new Spettacolo("Aristogatti", 
			Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
							new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true)))), 7, "Add New");
		spettacoloServiceCommand.publishMessage(s3);
		Spettacolo s4 = new Spettacolo("Infinity War", 
			Arrays.asList	(new Sala(1, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
							new Sala(2, Arrays.asList(new Posto("a", 1, true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b",2,true))),
							new Sala(3, Arrays.asList(new Posto("a",1,true), new Posto("a",2,true), new Posto("b",1,true), new Posto("b", 2, true)))), 5, "Add New");
		spettacoloServiceCommand.publishMessage(s4);

		//unisco gli spettacoli
		List<Spettacolo> spett = Arrays.asList(s1,s2,s3,s4);

		//elimino gli spettacoli vecchi e aggiungo questi nuovi
		spettacoloRepository.deleteAll();
		spettacoloRepository.saveAll(spett);
	}
}
