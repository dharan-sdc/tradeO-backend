package com.sdc.tradeo.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdc.tradeo.model.Coin;
import com.sdc.tradeo.respository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class CoinSericeImpl implements CoinService {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Coin> getCoinList(int page) {
        String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=inr&per_page=15&page="+page;

        RestTemplate restTemplate = new RestTemplate();
        //4.09.44
        return List.of();
    }

    @Override
    public String getMarketChart(String coinId, int days) {
        return "";
    }

    @Override
    public String getCoinDetails(String coinId) {
        return "";
    }

    @Override
    public Coin findById(String coinId) {
        return null;
    }

    @Override
    public String searchCoins(String keyword) {
        return "";
    }

    @Override
    public String getTop50CoinsByMarketCapRank() {
        return "";
    }

    @Override
    public String GetTreadingCoins() {
        return "";
    }
}
