package ru.bananus.gameengine.Characters.Character;

import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import ru.bananus.gameengine.Characters.Utils.CharacterCreator;
import ru.bananus.gameengine.Characters.Utils.MovePlayerEntity;
import ru.bananus.gameengine.GamesAPI.JS.Event.EventManager;
import ru.bananus.gameengine.Items.ItemRegistry;
import ru.bananus.gameengine.Network.NBTStorage;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class CharacterEntity extends AnimalEntity implements IAnimatable, IAnimationTickable {

    private int ticks = 0;
    private NBTStorage nbtBank = new NBTStorage();

    public EventManager eventManager;

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);

    private static final DataParameter<Boolean> SLEEP =
            EntityDataManager.defineId(CharacterEntity.class, DataSerializers.BOOLEAN);

    private static final DataParameter<String> ANIMATION =
            EntityDataManager.defineId(CharacterEntity.class, DataSerializers.STRING);

    private static final DataParameter<String> WALK_ANIM =
            EntityDataManager.defineId(CharacterEntity.class, DataSerializers.STRING);

    private static final DataParameter<String> IDLE_ANIM =
            EntityDataManager.defineId(CharacterEntity.class, DataSerializers.STRING);

    private static final DataParameter<String> TEXTURE =
            EntityDataManager.defineId(CharacterEntity.class, DataSerializers.STRING);

    private static final DataParameter<String> MODEL =
            EntityDataManager.defineId(CharacterEntity.class, DataSerializers.STRING);

    private static final DataParameter<String> ANIMATION_FILE =
            EntityDataManager.defineId(CharacterEntity.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> MOVE =
            EntityDataManager.defineId(CharacterEntity.class, DataSerializers.BOOLEAN);

    public Entity focusedEntity;

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public CharacterEntity(EntityType<? extends AnimalEntity> p_i48568_1_, World p_i48568_2_) {
        super(p_i48568_1_, p_i48568_2_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
        super.registerGoals();
    }

    public static AttributeModifierMap setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f).build();
    }

    @Override
    public CharacterEntity getBreedOffspring(ServerWorld p_146743_, AgeableEntity p_146744_) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 15;
        String idleAnim = getIdleAnim();
        String walkAnim = getWalkAnim();
        if (event.isMoving()) {
            AnimationBuilder def = new AnimationBuilder()
                    .loop(walkAnim == "" ? "story.npc.walk" : walkAnim);
            event.getController().setAnimation(def);
            return PlayState.CONTINUE;
        }

        if (!this.getAnimation().equals("")) {
            event.getController().setAnimation((new AnimationBuilder()).addAnimation(this.getAnimation()));
            return PlayState.CONTINUE;
        }

        AnimationBuilder def = new AnimationBuilder()
                .loop(idleAnim == "" ? "story.npc.idle" : idleAnim);
        event.getController().setAnimation(def);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        setAnimation(tag.getString("animation"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        tag.putString("animation", this.getAnimation());
    }

    public void setTexturePath(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexturePath() {
        return this.entityData.get(TEXTURE);
    }

    public String getModelPath() {
        return this.entityData.get(MODEL);
    }

    public String getAnimationPath() {
        return this.entityData.get(ANIMATION_FILE);
    }
    public void setModelPath(String model) {
        this.entityData.set(MODEL, model);
    }
    public void setAnimationPath(String animationPath) {
        this.entityData.set(ANIMATION_FILE, animationPath);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEvents.PLAYER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PLAYER_DEATH;
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, "");
        this.entityData.define(TEXTURE, "");
        this.entityData.define(MODEL, "");
        this.entityData.define(ANIMATION_FILE, "");
        this.entityData.define(WALK_ANIM, "");
        this.entityData.define(IDLE_ANIM, "");
    }

    public void setSleep(boolean sitting) {
        this.entityData.set(SLEEP, sitting);
    }


    public void setMove(boolean sitting) {
        this.entityData.set(MOVE, sitting);
    }

    public void setAnimation(String animations) {
        this.entityData.set(ANIMATION, animations);
    }

    public String getAnimation() {
        return this.entityData.get(ANIMATION);
    }

    public void setWalkAnim(String animations) {
        this.entityData.set(WALK_ANIM, animations);
    }

    public String getWalkAnim() {
        return this.entityData.get(WALK_ANIM);
    }

    public void setIdleAnim(String animations) {
        this.entityData.set(IDLE_ANIM, animations);
    }

    public String getIdleAnim() {
        return this.entityData.get(IDLE_ANIM);
    }

    public boolean isMove() {
        return this.entityData.get(SLEEP);
    }


    @Override
    public boolean removeWhenFarAway(double p_213397_1_) {
        return false;
    }

    public boolean changeDimension() {
        return true;
    }

    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        if (player.level.isClientSide) return super.interactAt(player, vec, hand);
        if(player.getItemInHand(hand).getItem() == ItemRegistry.CHARACTER_DELETER.get())
            remove();
        if(player.getItemInHand(hand).getItem() == ItemRegistry.CHARACTER_CREATOR.get())
            Minecraft.getInstance().setScreen(new CharacterCreator(this));
        return super.interactAt(player, vec, hand);
    }


    @Override
    public ItemStack getItemBySlot(EquipmentSlotType arg0) {
        if (arg0 == EquipmentSlotType.MAINHAND) return inventory.get(0);
        if (arg0 == EquipmentSlotType.OFFHAND) return inventory.get(1);
        return ItemStack.EMPTY;
    }

    @Override
    public HandSide getMainArm() {
        return HandSide.RIGHT;
    }

    @Override
    public void tick() {
        super.tick();
        if (focusedEntity != null) {
            lookAt(EntityAnchorArgument.Type.EYES, new Vector3d(focusedEntity.getX(), focusedEntity.getEyeY(), focusedEntity.getZ()));
        }
        if(ticks % 10 == 0) {
            setTexturePath(getTexturePath());
            setModelPath(getModelPath());
            setAnimationPath(getAnimationPath());
        }
        ticks++;
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }

    public void moveEntity(double x, double y, double z, float speed){
        this.goalSelector.addGoal(1, new MovePlayerEntity(this, x, y, z, speed));
    }

}