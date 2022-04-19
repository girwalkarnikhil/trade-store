package com.trade.app.service;

import com.trade.app.model.Trade;
import com.trade.app.repository.TradeDao;
import com.trade.app.repository.TradeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

	private static final Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);

	@Autowired
	TradeDao tradeDao;

	@Autowired
	TradeRepository tradeRepository;

	public boolean isValid(Trade trade) {
		if (validateMaturityDate(trade)) {

			Optional<Trade> exsitingTrade = tradeRepository.findById(trade.getTradeId());
			if (exsitingTrade.isPresent()) {
				return validateVersion(trade, exsitingTrade.get());
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean validateVersion(Trade trade, Trade oldTrade) {

		if (trade.getVersion() >= oldTrade.getVersion()) {
			return true;
		}
		return false;
	}

	private boolean validateMaturityDate(Trade trade) {
		return trade.getMaturityDate().before(new Date()) ? false : true;
	}

	public void persist(Trade trade) {

		trade.setCreatedDate(new Date());
		tradeRepository.save(trade);
	}

	public List<Trade> findAllTrades() {
		return tradeRepository.findAll();

	}

	public void updateExpiryFlagOfTrade() {

		tradeRepository.findAll().stream().forEach(t -> {
			if (!validateMaturityDate(t)) {
				t.setExpiredFlag("Y");
				log.info("Trade which needs to updated {}", t);
				tradeRepository.save(t);
			}
		});
	}

}
