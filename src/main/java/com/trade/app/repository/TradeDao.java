package com.trade.app.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.trade.app.model.Trade;

public interface TradeDao {


    public  static Map<String,Trade> tradeMap =new ConcurrentHashMap<>();


    public void saveTrade(Trade trade);

    List<Trade> findAllTrades();

    Trade findTrade(String tradeId);
}
