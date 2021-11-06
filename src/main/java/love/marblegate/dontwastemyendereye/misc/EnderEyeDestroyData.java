package love.marblegate.dontwastemyendereye.misc;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import love.marblegate.dontwastemyendereye.DontWatseMyEnderEye;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnderEyeDestroyData extends WorldSavedData {
    private Gson gson;
    private Type type;
    private boolean shared;
    private int count;
    private Map<UUID,Integer> countMap;

    public static EnderEyeDestroyData get(World world){
        if (!(world instanceof ServerWorld)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }

        ServerWorld serverWorld = world.getServer().overworld();
        DimensionSavedDataManager dimensionSavedDataManager = serverWorld.getDataStorage();
        return dimensionSavedDataManager.computeIfAbsent(EnderEyeDestroyData::new, "endereyedestroy");
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

            DontWatseMyEnderEye.LOGGER.warn("Shared-mode EnderEyeDestroyData has been modified:" + count);
        }
        else{
            if(uuid!=null){
                if(countMap.containsKey(uuid)){
                    countMap.put(uuid,countMap.get(uuid)+1);
                } else {
                    countMap.put(uuid,1);
                }
                setDirty();

                DontWatseMyEnderEye.LOGGER.warn("Individual-mode EnderEyeDestroyData has been modified:" + uuid + " - " + countMap.get(uuid));
            }
        }
    }

    public void decreaseCount(@Nullable UUID uuid){
        if(shared) {
            count = Math.max(0,count - 1);
            setDirty();

            DontWatseMyEnderEye.LOGGER.warn("Shared-mode EnderEyeDestroyData has been modified:" + count);
        }
        else{
            if(uuid!=null){
                countMap.put(uuid,Math.max(0,countMap.get(uuid)-1));
                setDirty();

                DontWatseMyEnderEye.LOGGER.warn("Individual-mode EnderEyeDestroyData has been modified:" + uuid + " - " + countMap.get(uuid));
            }
        }
    }

    public EnderEyeDestroyData() {
        super("endereyedestroy");
        shared = !Configuration.INDIVIDUAL_MODE.get();
        if(shared) count = 0;
        else{
            gson = new Gson();
            countMap = new HashMap<>();
            type = new TypeToken<HashMap<UUID,Integer>>() {}.getType();
        }
    }

    @Override
    public void load(CompoundNBT compoundNBT) {
        if(shared){
            if(compoundNBT.contains("shared_destroy_count"))
                count = compoundNBT.getInt("shared_destroy_count");
            else count = 0;
        } else {
            if(compoundNBT.contains("individual_destroy_count"))
                countMap = gson.fromJson(compoundNBT.getString("individual_destroy_count"),type);
            else countMap = new HashMap<>();
        }

    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        if(shared) compoundNBT.putInt("shared_destroy_count",count);
        else{
            String serialized = gson.toJson(countMap,type);
            compoundNBT.putString("individual_destroy_count",serialized);
        }
        return compoundNBT;
    }
}
