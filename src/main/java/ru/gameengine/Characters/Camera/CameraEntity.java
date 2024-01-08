package ru.gameengine.Characters.Camera;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CameraEntity extends ShoulderRidingEntity implements IFlyingAnimal {
    //Cartar engine
    public CameraEntity(EntityType<? extends CameraEntity> p_29362_, World p_29363_) {
        super(p_29362_, p_29363_);
        this.moveControl = new FlyingMovementController(this, 10, true);
    }


    public static AttributeModifierMap.MutableAttribute setAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.FLYING_SPEED, 0.4f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f);
    }

    protected PathNavigator createNavigation(World p_29417_) {
        FlyingPathNavigator flyingpathnavigation = new FlyingPathNavigator(this, p_29417_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }


    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_146743_, AgeableEntity p_146744_) {
        return null;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void addAdditionalSaveData(CompoundNBT p_29422_) {
        super.addAdditionalSaveData(p_29422_);
    }

    public void readAdditionalSaveData(CompoundNBT p_29402_) {
        super.readAdditionalSaveData(p_29402_);
    }

    public boolean isFlying() {
        return !this.onGround;
    }

}