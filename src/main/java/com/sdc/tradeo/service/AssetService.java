package com.sdc.tradeo.service;

import com.sdc.tradeo.model.Asset;
import com.sdc.tradeo.model.Coin;
import com.sdc.tradeo.model.User;

import java.util.List;

public interface AssetService {

    Asset createAsset(User user, Coin coin, double quantity);

    Asset getAssetbyId(Long assetId) throws Exception;

    Asset getAssetByUserIdAndId(Long userId, Long assetId);

    List<Asset> getUsersAssets(Long userId);

    Asset updateAsset(Long assetId, double quantity) throws Exception;

    Asset findAssetbyUserIdAndCoinId(Long userId, String coinId);

    void deleteAsset(Long assetId);

}
