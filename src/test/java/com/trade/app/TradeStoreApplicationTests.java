package com.trade.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trade.app.controller.TradeController;
import com.trade.app.exception.ValidationException;
import com.trade.app.model.Trade;
import com.trade.consants.Constant;

@SpringBootTest
class TradeStoreApplicationTests {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Test
	void contextLoads() {
	}
	
	 @Autowired
	    private TradeController tradeController;

	    @Test
	    public void successTradeStoreValidationTest() throws ValidationException, ParseException {
	        ResponseEntity<?> responseEntity = tradeController.tradeValidateStore(createTradesTest("T1", 1, "20/05/2024"));
	        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>(HttpStatus.CREATED);
	        Assertions.assertEquals(expectedResponseEntity.getStatusCodeValue(), responseEntity.getStatusCodeValue());
	        ResponseEntity<?> responseEntityTradesList = (ResponseEntity<?>) tradeController.findAllTrades();
	        ResponseEntity<?> expectedResponseEntityTradesList = new ResponseEntity<>(HttpStatus.OK);
	        Assertions.assertEquals(expectedResponseEntityTradesList.getStatusCodeValue(), responseEntityTradesList.getStatusCodeValue());
	    }

	    @Test
	    public void TradesMaturityValidationsFailed() throws ValidationException {
	        try {
	            ResponseEntity<?> responseEntityMaturityDateFailed = tradeController.tradeValidateStore(createTradesTest("T1", 1, "20/05/2021"));
	        } catch(ParseException maturityValidationFailed) {
	            Assertions.assertEquals(Constant.MATURITY_ERROR_MSG, maturityValidationFailed.getMessage());
	        }
	    }

	    @Test
	    public void TradesVersionValidationFailed() throws ValidationException {
	        try {
	        	tradeController.tradeValidateStore(createTradesTest("T1", 1, "20/05/2023"));
	            ResponseEntity<?> responseEntityVersionFailed = tradeController.tradeValidateStore(createTradesTest("T1", 0, "20/05/2023"));
	        } catch (ParseException versionValidationFailed) {
	            Assertions.assertEquals(Constant.VERSION_ERROR_MSG, versionValidationFailed.getMessage());
	        }
	    }



	    private Trade createTradesTest(String id, int version, String maturityDate) throws ParseException {
	        Trade trade = new Trade();
	        trade.setTradeId(id);
	        trade.setVersion(version);
	        trade.setExpiredFlag("N");
	        trade.setBookId("B"+id);
	        trade.setCounterParty("CP"+id);
	        trade.setMaturityDate(dateFormat.parse(maturityDate));
	        trade.setCreatedDate(dateFormat.parse("19/04/2022"));
	        return trade;
	    }

}
