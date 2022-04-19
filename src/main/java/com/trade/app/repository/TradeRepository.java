package com.trade.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trade.app.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade,String> {
}
