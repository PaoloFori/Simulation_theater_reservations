package com.prenotazioni.utente;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.utente.Controller.UserControllerQuery;
import com.prenotazioni.utente.Entity.User;

public class TestControllerQuery extends UtenteApplicationTests {

	@Autowired
	UserControllerQuery userControllerQuery;

	@Test
	public void getUserById() {
		User user = userControllerQuery.getUserById(1);
		String userTest = "{\n id: 1\n name: Gabriele\n surname: Faro\n saldo: 20\n}";
		assertTrue(user.toString().equals(userTest));
	}

	@Test
	public void getAll() {
		List<User> users = userControllerQuery.getAllUser();
		List<Integer> ids = new ArrayList<>();
		for (User user : users) {
			ids.add(user.getId());
		}
		List<Integer> listIds = Arrays.asList(1, 2, 3, 4, 5); 
		List<Integer> listIds2 = Arrays.asList(1,2,3,5,6,4);
		assertTrue(ids.equals(listIds) || ids.equals(listIds2));
	}

	@Test
	public void getByName() {
		List<User> users = userControllerQuery.getAllUserByName("Gabriele");
		List<Integer> users1 = Arrays.asList(1, 2, 5);
		List<Integer> ids = new ArrayList<>();
		for (User user : users) {
			ids.add(user.getId());
		}

		assertTrue(ids.equals(users1));
	}
	
	@Test
    public void getBySurname(){
        List<User> users = userControllerQuery.getAllUserBySurname("Faro");
        List<Integer> users1 = Arrays.asList(1,3,5);
        List<Integer> ids = new ArrayList<>();
        for(User user : users){
            ids.add(user.getId());
        }
        assertTrue(ids.equals(users1));
    }
}
