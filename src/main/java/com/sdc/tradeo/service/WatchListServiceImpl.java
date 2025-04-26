package com.sdc.tradeo.service;

import com.sdc.tradeo.model.Coin;
import com.sdc.tradeo.model.User;
import com.sdc.tradeo.model.WatchList;
import com.sdc.tradeo.respository.UserRepository;
import com.sdc.tradeo.respository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WatchListRepository watchListRepository;

    @Override
    public WatchList findUserWatchList(Long userId) throws Exception {
        WatchList watchList = watchListRepository.findByUserId(userId);
        if (watchList == null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            watchList = createWatchList(user);
            //throw new Exception("WatchList not found");
        }
        return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList = new WatchList();
        watchList.setUser(user);

        return watchListRepository.save(watchList);
    }

    @Override
    public WatchList findById(Long id) throws Exception {
        Optional<WatchList> watchListOptional = watchListRepository.findById(id);
        if (watchListOptional.isEmpty()) {
            throw new Exception("Watch list not found");
        }
        return watchListOptional.get();
    }

    @Override
    public Coin addItemToWatchList(Coin coin, User user) throws Exception {
        WatchList watchList = findUserWatchList(user.getId());

        if (watchList.getCoins().contains(coin)) {
            watchList.getCoins().remove(coin);
        } else watchList.getCoins().add(coin);
        watchListRepository.save(watchList);
        return coin;
    }
}
