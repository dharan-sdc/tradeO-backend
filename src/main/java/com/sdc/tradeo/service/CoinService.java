package com.sdc.tradeo.service;

import com.sdc.tradeo.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page) throws Exception;

    //coin gecko api
    String getMarketChart(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    //this use to find in database
    Coin findById(String coinId) throws Exception;

    String searchCoins(String keyword) throws Exception;

    String getTop50CoinsByMarketCapRank() throws Exception;

    String getTrendingCoin() throws Exception;
}
