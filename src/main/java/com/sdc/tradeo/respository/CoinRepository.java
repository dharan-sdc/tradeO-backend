package com.sdc.tradeo.respository;

import com.sdc.tradeo.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin,String> {
}
