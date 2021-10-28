package com.prenotazioni.utente;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.prenotazioni.utente.Entity.User;
import com.prenotazioni.utente.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UtenteApplication implements CommandLineRunner{

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UtenteApplication.class, args);
	}

	@Override
	public void run(String...strings) throws Exception{

		//id atomico e intero, non creo confusionis
		AtomicInteger id = new AtomicInteger();

		//i miei utenti iniziali
		User u1 = new User(id.incrementAndGet(), "Paolo", "Forin", 20);
		User u2 = new User(id.incrementAndGet(), "Franco", "Forin", 2);
		User u3 = new User(id.incrementAndGet(), "Giovanni", "De Lazzari", 50);
		User u4 = new User(id.incrementAndGet(), "Mario", "Rossi", 5);
		User u5 = new User(id.incrementAndGet(), "Paolo", "Zambo", 10);

		//creo una lista e li inserisco
		List<User> users = Arrays.asList(u1,u2,u3,u4,u5);

		//elimino tutto quello che c'è prima e aggiungo i miei
		userRepository.deleteAll();
		userRepository.saveAll(users);
	}

}
