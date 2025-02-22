package com.sdc.tradeo.service;

import com.sdc.tradeo.model.Coin;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.WatchList;

public interface WatchListService {
    WatchList findUserWatchList(Long userId) throws Exception;

    WatchList createWatchList(User user);

    WatchList findById(Long id) throws Exception;

    Coin addItemToWatchList(Coin coin, User user) throws Exception;
}
