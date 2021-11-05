package love.marblegate.dontwastemyendereye.misc;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraft.nbt.CompoundNBT;
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

    public int getCount(@Nullable UUID uuid) {
        if(shared){
            return count;
        } else {
            return uuid == null?0:countMap.getOrDefault(uuid,0);
        }
    }

    public void setCount(@Nullable UUID uuid, int count) {
        if(shared) this.count = count;
        else{
            if(uuid!=null){
                countMap.put(uuid,count);
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
