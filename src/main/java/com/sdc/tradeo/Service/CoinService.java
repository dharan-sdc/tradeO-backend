package com.sdc.tradeo.Service;

import com.sdc.tradeo.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page);

    //coin gecko api
    String getMarketChart(String coinId, int days);

    String getCoinDetails(String coinId);

    //this use to find in database
    Coin findById(String coinId);

    String searchCoins(String keyword);

    String getTop50CoinsByMarketCapRank();

    String GetTreadingCoins();
}
