package com.prenotazioni.utente;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.utente.Controller.UserControllerCommand;

public class TestUserControllerCommand extends UtenteApplicationTests {

	@Autowired
	UserControllerCommand userControllerCommand;
	
	@Test
	public void deleteById() {
		String response = userControllerCommand.deleteUser(4);
		assertEquals(response, "User delete correctly");
	}

	@Test
	public void insert() {
		String s = userControllerCommand.insert("Padova", "Centro", 100);
		assertTrue(s.equals("User add correctly!"));
	}

	@Test
	public void upset() {
		String s = userControllerCommand.upset(5, "Paolo", "Fori", 100);
		assertEquals(s, "User upset correctly!");
	}

	@Test
	public void removeCash() {
		String s = userControllerCommand.removeMoney(1, 2);
		assertEquals("User has enogth money. The now money are: 18", s);
	}

	@Test
	public void addCash() {
		String s = userControllerCommand.addMoney(2, 100);
		assertEquals("User money now are: 102", s);
	}
}
