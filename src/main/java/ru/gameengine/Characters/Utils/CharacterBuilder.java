package ru.gameengine.Characters.Utils;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import ru.gameengine.Characters.Character.CharacterEntity;
import ru.gameengine.Characters.CharacterInit;

import java.util.Set;

public class CharacterBuilder {
    public static MobEntity entity;
    public static String name = null;
    public static String chatname = null;
    public static PlayerEntity connectedPlayer = null;
    
    public CharacterBuilder(String texture, BlockPos pos, World world){
        entity = new CharacterEntity(CharacterInit.CHARACTER.get(), world);
        entity.setPos(pos.getX(),pos.getY(),pos.getZ());
        entity.level.addFreshEntity(entity);
    }

    public void stopMoveEntity(){
        Set<PrioritizedGoal> goal = ObfuscationReflectionHelper.getPrivateValue(GoalSelector.class, entity.goalSelector, "goals");
        for (PrioritizedGoal availableGoal: goal) {
            if (availableGoal.getGoal().toString().equals("MovePlayerEntity")){
                entity.goalSelector.removeGoal(availableGoal.getGoal());
            }
        }
    }
    
    public void connectPlayer(PlayerEntity player) {
        connectedPlayer = player;
    }
    
    public void chatname(String name) {
        chatname = name;
    }
    
    
    public void sayFrom(String msg, PlayerEntity player) {
        TextComponent textComponent = new StringTextComponent("["+chatname+"] "+msg);
        player.sendMessage(textComponent, player.getUUID());
    }
    
    public void setAnim(String show){
        CharacterEntity npc = (CharacterEntity) entity;
        npc.setAnimation(show);
    }

    public void renderItem(ItemStack itemStack){
        CharacterEntity npc = (CharacterEntity) entity;
        npc.setItemSlot(EquipmentSlotType.MAINHAND, itemStack);
    }

    public void moveEntity(BlockPos vector3d, float speed){
        entity.goalSelector.addGoal(1, new MovePlayerEntity(entity, vector3d, speed));
    }

    public void rotate(float yaw){
        this.entity.yRotO = yaw;
    }

    public String getName() {
        return name;
    }

    public void remove() {
        entity.remove();
    }

    public void setPos(double x, double y, double z) {
        entity.setPos(x, y, z);
    }

    public void setTexture(String texture) {
        CharacterEntity npc = (CharacterEntity) entity;
        npc.setTexturePath(texture);
    }

    public void setModel(String model) {
        CharacterEntity npc = (CharacterEntity) entity;
        npc.setModelPath(model);
    }

    public void setAnimationPath(String animationPath) {
        CharacterEntity npc = (CharacterEntity) entity;
        npc.setAnimationPath(animationPath);
    }
}
