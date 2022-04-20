package pameas.incident.detection.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pameas.incident.detection.model.entity.LocationMonitor;

@Component
public class LocationMonitorRepository {

    @Autowired
    private RedisTemplate<String, LocationMonitor> redisTemplate;

    public void save(LocationMonitor locationMonitor) {
        redisTemplate.opsForValue().set(locationMonitor.getHashedMacAddress(), locationMonitor);
    }

    public LocationMonitor findByHashedMacAddress(String hashedMacAddress) {
        return redisTemplate.opsForValue().get(hashedMacAddress);
    }
}
