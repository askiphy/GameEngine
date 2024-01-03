package ru.gameengine.GamesAPI.JS;

import net.minecraft.entity.Entity;
import ru.gameengine.Annotations.Documentate;
import ru.gameengine.Characters.Character.CharacterEntity;
import ru.gameengine.GamesAPI.JS.Event.EventManager;

public class NpcJS extends JSResource {
    private CharacterEntity npc;
    @Documentate(desc="Npc's event manager.")
    public final EventManager evManager;

    public NpcJS(CharacterEntity npc) {
        this.npc = npc;
        this.evManager = npc.eventManager;
    }

    // Head controlls
    @Documentate(desc = "Rotates NPC's head by Y/yaw.")
    public void setHeadRotation(float yaw) { npc.yHeadRot = yaw; setEntityFocused(); }

    @Documentate(desc = "Focuses npc on another entity.")
    public void setEntityFocused(Entity entity) { npc.focusedEntity = entity; }

    @Documentate(desc = "Focuses npc on another entity.")
    public void setEntityFocused(JSResource res) {
        Object nativeObj = res.getNative();
        if(nativeObj instanceof Entity) {
            setEntityFocused((Entity) nativeObj);
        };
    }

    @Documentate(desc = "Sets if npc can be hurt or not. True if can, false if not.")
    public void setCanDie(boolean can) {npc.getPersistentData().putBoolean("immortal", !can);}

    @Documentate(desc = "Makes npc don't focus on anything.")
    public void setEntityFocused() { npc.focusedEntity = null; }

    // Positions
    @Documentate(desc = "Gets NPC's position.")
    public double[] getPosition() { return new double[] { getX(),getY(),getZ() };}

    @Documentate(desc = "Gets NPC's X position.")
    public double getX() { return npc.getX(); }

    @Documentate(desc = "Gets NPC's Y position.")
    public double getY() { return npc.getY(); }

    @Documentate(desc = "Gets NPC's Z position.")
    public double getZ() { return npc.getZ(); }

    //Teleports and Movement
    @Documentate(desc = "Sets NPC's position.")
    public void setPosition(double[] pos)  { npc.setPos(pos[0],pos[1],pos[2]); npc.moveEntity(pos[0], pos[1], pos[2], 1f); };

    @Documentate(desc = "Sets NPC's position.")
    public void setPosition(double x, double y, double z)  { setPosition( new double[] {x,y,z} ); };

    @Documentate(desc = "Sets NPC's X position.")
    public void setX(double x) { setPosition(x,getY(),getZ()); };

    @Documentate(desc = "Sets NPC's Y position.")
    public void setY(double y) { setPosition(getX(),y,getZ()); };

    @Documentate(desc = "Sets NPC's Z position.")
    public void setZ(double z) { setPosition(getX(),getY(),z); };

    @Documentate(desc = "Moves NPC to position with preset speed.")
    public void moveToPosition(double[] pos, double speed) { npc.moveEntity(pos[0], pos[1], pos[2], Float.parseFloat(String.valueOf(speed))); }

    @Documentate(desc = "Moves NPC to position with default speed.")
    public void moveToPosition(double[] pos) { moveToPosition(pos,1D); }

    @Documentate(desc = "Moves NPC to position with preset speed.")
    public void moveToPosition(double x, double y, double z, double speed) { moveToPosition( new double[] {x,y,z} , speed); }

    @Documentate(desc = "Moves NPC to position with default speed.")
    public void moveToPosition(double x, double y, double z) { moveToPosition(x,y,z,1D); }

    //Despawning
    @Documentate(desc = "Despawns/Removes npc from world.")
    public void despawnSelf() { npc.remove(); }

    //JS
    @Override public Object getNative() { return npc; }
    @Override public String getResourceId() { return "npc"; }
    @Override public boolean isClient() { return npc.level.isClientSide; }
}
