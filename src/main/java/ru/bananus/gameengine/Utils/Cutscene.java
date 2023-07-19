package ru.bananus.gameengine.Utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.bananus.gameengine.Characters.Camera.CameraEntity;
import ru.bananus.gameengine.Characters.CharacterInit;
import ru.bananus.gameengine.Characters.Utils.MovePlayerEntity;
import ru.bananus.gameengine.Network.Network;
import ru.bananus.gameengine.Network.Packets.SCutscene;

public class Cutscene {
    CameraEntity entity;
    public Cutscene(World level){
        entity = new CameraEntity(CharacterInit.CAMERA.get(), level);
        entity.setNoGravity(true);
        entity.setPos(0,-50,0);
        level.addFreshEntity(entity);
    }

    public void show(PlayerEntity player){
        ServerSaveData.isCutScene = true;
        Network.sendToPlayer(new SCutscene(entity.getId()), player);
    }

    public void moveCamera(BlockPos blockPos, float speed){
        entity.setSpeed(speed);
        entity.goalSelector.addGoal(1, new MovePlayerEntity(entity, blockPos, 1));
    }

    public void stopCameraMove(){
        entity.goalSelector.getRunningGoals().distinct().forEach(prioritizedGoal -> {
            if (prioritizedGoal.getGoal() instanceof MovePlayerEntity){
                entity.goalSelector.removeGoal(prioritizedGoal.getGoal());
            }
        });
    }

    public void exitCutscene(PlayerEntity player){
        Network.sendToPlayer(new SCutscene(player.getId()), player);
        ServerSaveData.isCutScene = false;
    }
}
