package com.prenotazioni.apigateway;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.apigateway.Controllers.UserControllerQuery;
import com.prenotazioni.apigateway.Entity.User;


public class TestControllerUserQuery extends ApiGatewayApplicationTests {
	@Autowired
	UserControllerQuery userControllerQuery;

	@Test
	public void getUserById() {
		String user = userControllerQuery.getUserById(1);
		
		//viene eseguito prima del command
		String userTest = "{\n id: 1\n name: Paolo\n surname: Forin\n saldo: 20\n}";
		
		//viene eseguito dopo del command
		
		String userTest1 = "{\n id: 1\n name: Paolo\n surname: Forin\n saldo: 18\n}";
		assertTrue(user.equals(userTest) || user.equals(userTest1));
	}

	@Test
	public void getAll() {
		List<String> users = userControllerQuery.getAllUser();
		
		//viene eseguito prima del command
		User u1 = new User(1, "Paolo", "Forin", 20);
		User u2 = new User(2, "Franco", "Forin", 2);
		User u3 = new User(3, "Giovanni", "De Lazzari", 50);
		User u4 = new User(4, "Mario", "Rossi", 5);
		User u5 = new User(5, "Paolo", "Zambo", 10);
		List<User> listUser = Arrays.asList(u1,u2,u3,u4,u5);
		List<String> listOfString = new ArrayList<>();
		for(User user : listUser) {
			listOfString.add(user.toString());
		}
		
		//se viene eseguito dopo il command
		User u11 = new User(1, "Paolo", "Forin", 18);
		User u21 = new User(2, "Franco", "Forin", 3);
		User u31 = new User(3, "Giovanni", "De Lazzari", 50);
		User u41 = new User(5, "Paolo", "Fori", 100);
		User u51 = new User(6, "Padova", "Centro", 100);
		List<User> listUser1 = Arrays.asList(u11,u21,u31,u41,u51);
		List<String> listOfString1 = new ArrayList<>();
		for(User user : listUser1) {
			listOfString1.add(user.toString());
		}
		
		assertTrue(users.equals(listOfString) || users.equals(listOfString1));
	}

	@Test
	public void getByName() {
		List<String> users = userControllerQuery.getUserByNameOrSurname("Paolo", "");
		
		//viene eseguito prima del command
		String s1 = "{\n id: 1\n name: Paolo\n surname: Forin\n saldo: 20\n}";
		String s2 = "{\n id: 5\n name: Paolo\n surname: Zambo\n saldo: 10\n}";
		List<String> userTest = Arrays.asList(s1,s2);
		
		//eseguito dopo il command
		String s11 = "{\n id: 1\n name: Paolo\n surname: Forin\n saldo: 18\n}";
		String s21 = "{\n id: 5\n name: Paolo\n surname: Fori\n saldo: 100\n}";
		List<String> userTest1 = Arrays.asList(s11,s21);

		assertTrue(users.equals(userTest) || users.equals(userTest1));
	}
	
	@Test
    public void getBySurname(){
        List<String> users = userControllerQuery.getUserByNameOrSurname("","Forin");
        
        //viene eseguito prima del command
        String s1 = "{\n id: 1\n name: Paolo\n surname: Forin\n saldo: 20\n}";
		String s2 = "{\n id: 2\n name: Franco\n surname: Forin\n saldo: 2\n}";
		List<String> userTest = Arrays.asList(s1,s2);
		
		//eseguito dopo il command
		String s11 = "{\n id: 1\n name: Paolo\n surname: Forin\n saldo: 18\n}";
		String s21 = "{\n id: 2\n name: Franco\n surname: Forin\n saldo: 3\n}";
		List<String> userTest1 = Arrays.asList(s11,s21);
		
		assertTrue(users.equals(userTest) || users.equals(userTest1));
    }
}
