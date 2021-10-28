package com.prenotazioni.utente;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.utente.Controller.UserControllerCommand;
import com.prenotazioni.utente.Controller.UserControllerQuery;

public class TestUserException extends UtenteApplicationTests{
	@Autowired
	UserControllerCommand userControllerCommand;
	
	@Autowired
	UserControllerQuery userControllerQuery;
	
	@Test
	public void throwExceptionNoUserControllerQuery1() {
		String user = userControllerQuery.getUserById(10);
		assertEquals(user, "No user with this id");
	}
	
	@Test 
	public void throwExceptionNoUserControllerCommand1() {
		String response = userControllerCommand.deleteUser(10);
		assertEquals(response, "no User with this id");
	}
	
	@Test 
	public void throwExceptionNoUserControllerCommand2() {
		String s = userControllerCommand.upset(9, "Paolo", "Fori", 100);
		assertEquals(s, "No User with this id");
	}
	
	@Test 
	public void throwExceptionNoUserControllerCommand3() {
		String s = userControllerCommand.removeMoney(9, 2);
		assertEquals("No user with this id", s);
	}
	
	@Test 
	public void throwExceptionNoUserControllerCommand4() {
		String s = userControllerCommand.addMoney(9, 100);
		assertEquals("No user with this id", s);
	}
	
	@Test 
	public void throwExceptionNoEnoghMoneyControllerCommand1() {
		String s = userControllerCommand.removeMoney(1, 100);
		assertEquals("No enough money", s);
	}
}
