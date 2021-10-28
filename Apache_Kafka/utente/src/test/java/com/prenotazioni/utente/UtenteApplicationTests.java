package com.prenotazioni.utente;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prenotazioni.utente.Entity.User;
import com.prenotazioni.utente.Repository.UserRepository;

@SpringBootTest
class UtenteApplicationTests {
	
	@Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {
	}
	
	@BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        saveAll();
    }
    
    public void saveAll(){
        User u1 = new User(1, "Gabriele", "Faro", 20, "Add New");
		User u2 = new User(2, "Gabriele", "Franco", 2, "Add New");
		User u3 = new User(3, "Gabriel", "Faro", 50, "Add New");
		User u4 = new User(4, "Gabriel", "Franco", 5, "Add New");
		User u5 = new User(5, "Gabriele", "Faro", 10, "Add New");

		//creo una lista e li inserisco
		List<User> users = Arrays.asList(u1,u2,u3,u4,u5);
		userRepository.saveAll(users);
    }

}
