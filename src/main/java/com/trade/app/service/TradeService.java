package com.trade.app.service;

import java.util.List;

import com.trade.app.model.Trade;

public interface TradeService {
	public boolean isValid(Trade trade);

	public void persist(Trade trade);

	public List<Trade> findAllTrades();

	public void updateExpiryFlagOfTrade();

}
