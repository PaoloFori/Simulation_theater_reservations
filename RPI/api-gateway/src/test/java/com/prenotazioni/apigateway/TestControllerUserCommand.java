package com.prenotazioni.apigateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.apigateway.Controllers.UserControllerCommand;


public class TestControllerUserCommand extends ApiGatewayApplicationTests{
	@Autowired
	UserControllerCommand userControllerCommand;
	
	@Test
	public void deleteById() {
		String response = userControllerCommand.deleteUserById(4);
		assertEquals(response, "User delete correctly");
	}

	@Test
	public void insert() {
		String s = userControllerCommand.putNewUser("Padova", "Centro", 100);
		assertTrue(s.equals("User add correctly!"));
	}

	@Test
	public void upset() {
		String s = userControllerCommand.upsetUser(5, 100, "Paolo", "Fori");
		assertEquals(s, "User upset correctly!");
	}

	@Test
	public void removeCash() {
		String s = userControllerCommand.removeMoneybyUserId(1, 2);
		assertEquals("User has enogth money. The now money are: 18", s);
	}

	@Test
	public void addCash() {
		String s = userControllerCommand.addMoneyByUserId(2, 1);
		assertEquals("User money now are: 3", s);
	}
}
