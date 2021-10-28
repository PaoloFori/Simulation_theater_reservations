package com.prenotazioni.apigateway;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prenotazioni.apigateway.Controllers.BigliettoController;
import com.prenotazioni.apigateway.Controllers.SpettacoloControllerCommand;
import com.prenotazioni.apigateway.Controllers.SpettacoloControllerQuery;

public class TestExceptionSpettacolo extends ApiGatewayApplicationTests{
	
	@Autowired
	SpettacoloControllerQuery spettacoloControllerQuery;
	
	@Autowired
	BigliettoController bigliettoController;
	
	@Autowired
	SpettacoloControllerCommand spettacoloControllerCommand;
	
	@Test
	public void throwExceptionNoSpettacolo1() {
		String show = spettacoloControllerQuery.getShow("Topolino", 1);
		assertEquals("No show with name: Topolino", show);
	}
	
	@Test
	public void throwExceptionNoSpettacolo2() {
		String show = spettacoloControllerQuery.getAllFreePostoByShowOrShowAndNumeroSala("Topolino", 1);
		assertEquals("No show with name: Topolino", show);
	}
	
	@Test
	public void throwExceptionNoSpettacolo3() {
		String prenota = bigliettoController.prenotaPostoAndCreateTicket("Topolino", 1, 1, "a", 1);
		assertEquals("No show with name: Topolino", prenota);
	}
	
	@Test
	public void throwExceptionNoSala1() {
		String show = spettacoloControllerQuery.getShow("Titanic", 4);
		assertEquals("No sala: 4 for show: Titanic", show);
	}
	
	@Test
	public void throwExceptionNoSala2() {
		String show = spettacoloControllerQuery.getAllFreePostoByShowOrShowAndNumeroSala("Titanic", 4);
		assertEquals("No sala: 4 for show: Titanic", show);
	}
	
	@Test
	public void throwExceptionNoSala3() {
		String prenota = bigliettoController.prenotaPostoAndCreateTicket("Titanic", 4, 1, "a", 1);
		assertEquals("No sala: 4 for show: Titanic", prenota);
	}
	
	@Test
	public void throwExceptionNoEnoughMoney() {
		String prenota = bigliettoController.prenotaPostoAndCreateTicket("Titanic", 1, 2, "b", 1);
		assertEquals("User 2 haven't got enough money", prenota);
	}
	
	@Test
	public void throwExceptionNoPosto() {
		String prenota = bigliettoController.prenotaPostoAndCreateTicket("Titanic", 1, 1, "a", 4);
		assertEquals("Not existx posto: 4A for sala: 1", prenota);
	}
	
	@Test
	public void throwEceptionNoFreePosto() {
		spettacoloControllerCommand.setPosto("Titanic", 1, false, "a", 1);
		String prenota = bigliettoController.prenotaPostoAndCreateTicket("Titanic", 1, 1, "a", 1);
		assertEquals("Posto: 1A already busy", prenota);
	}
	
}
