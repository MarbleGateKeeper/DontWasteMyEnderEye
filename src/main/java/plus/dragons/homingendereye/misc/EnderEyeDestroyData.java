package plus.dragons.homingendereye.misc;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnderEyeDestroyData extends SavedData {
    private final Gson gson;
    private final Type type;
    private final boolean shared;
    private int count;
    private Map<UUID,Integer> countMap;

    public EnderEyeDestroyData() {
        shared = !Configuration.INDIVIDUAL_MODE.get();
        count = 0;
        gson = new Gson();
        countMap = new HashMap<>();
        type = new TypeToken<HashMap<UUID,Integer>>() {}.getType();
    }

    public static EnderEyeDestroyData get(Level world){
        if (!(world instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }

        ServerLevel serverWorld = world.getServer().overworld();
        DimensionDataStorage dimensionSavedDataManager = serverWorld.getDataStorage();
        return dimensionSavedDataManager.computeIfAbsent(EnderEyeDestroyData::load,EnderEyeDestroyData::new, "endereyedestroy");
    }

    public int getCount(@Nullable UUID uuid) {
        if(shared){
            return count;
        } else {
            return uuid == null?0:countMap.getOrDefault(uuid,0);
        }
    }

    public void setCount(@Nullable UUID uuid, int count) {
        count = Math.max(count, 0);
        if(shared) {
            this.count = count;
            setDirty();
        }
        else{
            if(uuid!=null){
                countMap.put(uuid,count);
                setDirty();
            }
        }
    }

    public void increaseCount(@Nullable UUID uuid){
        if(shared) {
            count += 1;
            setDirty();
        }
        else{
            if(uuid!=null){
                if(countMap.containsKey(uuid)){
                    countMap.put(uuid,countMap.get(uuid)+1);
                } else {
                    countMap.put(uuid,1);
                }
                setDirty();
            }
        }
    }

    public void decreaseCount(@Nullable UUID uuid){
        if(shared) {
            count = Math.max(0,count - 1);
            setDirty();
        }
        else{
            if(uuid!=null){
                countMap.put(uuid,Math.max(0,countMap.get(uuid)-1));
                setDirty();
            }
        }
    }

    public static EnderEyeDestroyData load(CompoundTag compoundNBT){
        EnderEyeDestroyData enderEyeDestroyData = new EnderEyeDestroyData();
        if(compoundNBT.contains("shared_destroy_count"))
            enderEyeDestroyData.count = compoundNBT.getInt("shared_destroy_count");
        if(compoundNBT.contains("individual_destroy_count"))
            enderEyeDestroyData.countMap = enderEyeDestroyData.gson.fromJson(compoundNBT.getString("individual_destroy_count"),enderEyeDestroyData.type);
        return enderEyeDestroyData;
    }

    @Override
    public CompoundTag save(CompoundTag compoundNBT) {
        compoundNBT.putInt("shared_destroy_count",count);
        compoundNBT.putString("individual_destroy_count",gson.toJson(countMap,type));
        return compoundNBT;
    }
}
