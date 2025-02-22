package com.sdc.tradeo.service;

import com.sdc.tradeo.domain.OrderType;
import com.sdc.tradeo.model.Coin;
import com.sdc.tradeo.model.Order;
import com.sdc.tradeo.model.OrderItem;
import com.sdc.tradeo.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assestSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
