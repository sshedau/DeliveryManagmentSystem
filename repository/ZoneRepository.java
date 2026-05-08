package repository;

import domain.Zone;
import java.util.*;

public class ZoneRepository {
    private final Map<String, Zone> store = new HashMap<>();

    public void save(Zone zone) {
        store.put(zone.getZoneId(), zone);
    }

    public Optional<Zone> findById(String zoneId) {
        return Optional.ofNullable(store.get(zoneId));
    }

    public List<Zone> findAll() {
        return new ArrayList<>(store.values());
    }
}