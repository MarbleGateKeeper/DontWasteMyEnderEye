package plus.dragons.homingendereye.misc;

import javax.annotation.Nullable;
import java.util.*;

public class EyeThrowCache {
    Queue<UUID> cache = new LinkedList<>();

    public void putThrowRecord(UUID uuid){
        cache.add(uuid);
    }

    @Nullable
    public UUID retrieveThrowerRecord(){
        return cache.poll();
    }

    public UUID peek(){
        return cache.peek();
    }

}
