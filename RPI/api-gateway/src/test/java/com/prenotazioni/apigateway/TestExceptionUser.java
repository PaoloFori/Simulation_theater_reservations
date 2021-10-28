package com.prenotazioni.apigateway;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.apigateway.Controllers.UserControllerCommand;
import com.prenotazioni.apigateway.Controllers.UserControllerQuery;


public class TestExceptionUser extends ApiGatewayApplicationTests{
	@Autowired
	UserControllerCommand userControllerCommand;
	
	@Autowired
	UserControllerQuery userControllerQuery;
	
	@Test
	public void throwExceptionNoUserControllerQuery1() {
		String user = userControllerQuery.getUserById(10);
		assertEquals(user, "No user with id: 10");
	}
	
	@Test 
	public void throwExceptionNoUserControllerCommand1() {
		String response = userControllerCommand.deleteUserById(10);
		assertEquals(response, "no User with this id");
	}
	
	@Test 
	public void throwExceptionNoUserControllerCommand2() {
		String s = userControllerCommand.upsetUser(9, 100, "Paolo", "Fori");
		assertEquals(s, "No User with this id");
	}
	
	@Test 
	public void throwExceptionNoUserControllerCommand3() {
		String s = userControllerCommand.removeMoneybyUserId(9, 2);
		assertEquals("No user with this id", s);
	}
	
	@Test 
	public void throwExceptionNoUserControllerCommand4() {
		String s = userControllerCommand.addMoneyByUserId(9, 100);
		assertEquals("No user with this id", s);
	}
	
	@Test 
	public void throwExceptionNoEnoghMoneyControllerCommand1() {
		String s = userControllerCommand.removeMoneybyUserId(1, 100);
		assertEquals("No enough money", s);
	}
}
