package repository;

import domain.DeliveryPartner;
import enums.PartnerStatus;
import java.util.*;
import java.util.stream.Collectors;

public class PartnerRepository {
    private final Map<String, DeliveryPartner> store = new HashMap<>();

    public void save(DeliveryPartner partner) {
        store.put(partner.getPartnerId(), partner);
    }

    public List<DeliveryPartner> findAvailable() {
        return store.values().stream()
                .filter(DeliveryPartner::isAvailable)
                .collect(Collectors.toList());
    }

    public List<DeliveryPartner> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<DeliveryPartner> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}