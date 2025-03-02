package com.sdc.tradeo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdc.tradeo.model.Coin;
import com.sdc.tradeo.respository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${coingecko.api.key}")
    private String API_KEY;

    @Cacheable(value = "coins")
    @Override
    public List<Coin> getCoinList(int page) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=inr&per_page=50&page=" + page;
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameter", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {});
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Cacheable(value = "marketChart")
    @Override
    public String getMarketChart(String coinId, int days) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=inr&days=" + days;
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameter", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Cacheable(value = "coinDetails", key = "#coinId")
    @Override
    public String getCoinDetails(String coinId) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId;
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameter", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            Coin coin = new Coin();
            coin.setId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData = jsonNode.path("market_data");

            coin.setCurrentPrice(marketData.path("current_price").path("inr").asDouble(0.0));
            coin.setMarketCap(marketData.path("market_cap").path("inr").asLong(0));
            coin.setMarketCapRank(marketData.path("market_cap_rank").asInt(0));
            coin.setTotalVolume(marketData.path("total_volume").path("inr").asLong(0));
            coin.setHigh24h(marketData.path("high_24h").path("inr").asDouble(0.0));
            coin.setLow24h(marketData.path("low_24h").path("inr").asDouble(0.0));
            coin.setPriceChange24h(marketData.path("price_change_24h").asDouble(0.0));
            coin.setPriceChangePercentage24h(marketData.path("price_change_percentage_24h").asDouble(0.0));
            coin.setMarketCapChange24h(marketData.path("market_cap_change_24h").asLong(0));
            coin.setMarketCapChangePercentage24h(marketData.path("market_cap_change_percentage_24h").asDouble(0.0));

            coin.setTotalSupply(marketData.path("total_supply").asLong(0));

            coinRepository.save(coin);

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Coin findById(String coinId) throws Exception {
        Optional<Coin> optionalCoin = coinRepository.findById(coinId);
        return optionalCoin.orElseThrow(() -> new Exception("Coin not found"));
    }

    @Cacheable(value = "searchResults")
    @Override
    public String searchCoins(String keyword) throws Exception {
        String url = "https://api.coingecko.com/api/v3/search?query=" + keyword;
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameter", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Cacheable(value = "topCoins")
    @Override
    public String getTop50CoinsByMarketCapRank() throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=inr&per_page=10&page=1";
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameter", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Cacheable(value = "trendingCoins")
    @Override
    public String getTrendingCoin() throws Exception {
        String url = "https://api.coingecko.com/api/v3/search/trending";
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameter", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }
}
