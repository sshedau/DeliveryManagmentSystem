package repository;

import domain.Order;
import enums.OrderStatus;
import java.util.*;
import java.util.stream.Collectors;

public class OrderRepository {
    private final Map<String, Order> store = new HashMap<>();

    public void save(Order order) {
        store.put(order.getOrderId(), order);
    }

    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(store.get(orderId));
    }

    public List<Order> findByStatus(OrderStatus status) {
        return store.values().stream()
                .filter(o -> o.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Order> findAll() {
        return new ArrayList<>(store.values());
    }
}