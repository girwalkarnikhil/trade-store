package com.trade.app.repository;

import org.springframework.stereotype.Repository;

import com.trade.app.model.Trade;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TradeDaoImpl implements TradeDao{
    @Override
    public void saveTrade(Trade trade) {
        trade.setCreatedDate(new Date());
        tradeMap.put(trade.getTradeId(),trade);
    }

    @Override
    public List<Trade> findAllTrades() {
         return tradeMap.values().stream().
                 collect(Collectors.toList());
    }

    @Override
    public Trade findTrade(String tradeId) {
        return tradeMap.get(tradeId);
    }
}
