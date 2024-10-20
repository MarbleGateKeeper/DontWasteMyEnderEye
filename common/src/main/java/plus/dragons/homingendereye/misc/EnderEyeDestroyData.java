package plus.dragons.homingendereye.misc;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import plus.dragons.homingendereye.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnderEyeDestroyData extends PersistentState {
    private final boolean shared;
    private int count;
    private final Map<UUID,Integer> countMap;

    public EnderEyeDestroyData() {
        shared = !Configuration.isIndividualMode();
        count = 0;
        countMap = new HashMap<>();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("SharedCount",count);
        var individualNbt = new NbtCompound();
        var listNbt = new NbtList();
        countMap.forEach((key, value) -> {
            var entryNbt = new NbtCompound();
            entryNbt.putUuid("Id",key);
            entryNbt.putInt("Count",value);
            listNbt.add(entryNbt);
        });
        individualNbt.put("Data",listNbt);
        nbt.put("IndividualCount",individualNbt);
        return nbt;
    }

    public static EnderEyeDestroyData readNbt(NbtCompound compoundNBT){
        EnderEyeDestroyData enderEyeDestroyData = new EnderEyeDestroyData();
        if(compoundNBT.contains("SharedCount"))
            enderEyeDestroyData.count = compoundNBT.getInt("SharedCount");
        if(compoundNBT.contains("IndividualCount")){
            var individualNbt = compoundNBT.getCompound("IndividualCount");
            var datas = (NbtList) individualNbt.get("Data");
            datas.forEach(nbtElement -> {
                var nbt = (NbtCompound) nbtElement;
                enderEyeDestroyData.countMap.put(nbt.getUuid("Id"),nbt.getInt("Count"));
            });
        }
        return enderEyeDestroyData;
    }

    public static PersistentState.Type<EnderEyeDestroyData> getPersistentStateType() {
        return new PersistentState.Type<>(EnderEyeDestroyData::new, (tag,lookup) -> EnderEyeDestroyData.readNbt(tag), null);
    }

    public static EnderEyeDestroyData get(World world){
        if (!(world instanceof ServerWorld)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }

        ServerWorld serverWorld = world.getServer().getOverworld();
        PersistentStateManager manager = serverWorld.getPersistentStateManager();
        return manager.getOrCreate(EnderEyeDestroyData.getPersistentStateType(), "EnderEyeDestroyData");
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
            markDirty();
        }
        else{
            if(uuid!=null){
                countMap.put(uuid,count);
                markDirty();
            }
        }
    }

    public void increaseCount(@Nullable UUID uuid){
        if(shared) {
            count += 1;
            markDirty();
        }
        else{
            if(uuid!=null){
                if(countMap.containsKey(uuid)){
                    countMap.put(uuid,countMap.get(uuid)+1);
                } else {
                    countMap.put(uuid,1);
                }
                markDirty();
            }
        }
    }

    public void decreaseCount(@Nullable UUID uuid){
        if(shared) {
            count = Math.max(0,count - 1);
            markDirty();
        }
        else{
            if(uuid!=null){
                countMap.put(uuid,Math.max(0,countMap.get(uuid)-1));
                markDirty();
            }
        }
    }

}
