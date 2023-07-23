package ru.bananus.gameengine.GamesAPI.JS;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import ru.bananus.gameengine.Annotations.Documentate;
import ru.bananus.gameengine.Characters.Character.CharacterEntity;
import ru.bananus.gameengine.Characters.CharacterInit;
import ru.bananus.gameengine.Dialogue.Bench;
import ru.bananus.gameengine.Dialogue.Dialog;
import ru.bananus.gameengine.GamesAPI.*;
import ru.bananus.gameengine.GamesAPI.Instances.SceneInstance;
import ru.bananus.gameengine.GamesAPI.data.Action;
import ru.bananus.gameengine.GamesAPI.data.ActionPacketData;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Consumer;

public class SceneJS {
    private final SceneInstance sceneInstance;

    private final HashMap<String,NpcJS> localNpcs = new HashMap<>();

    public SceneJS(SceneInstance scInstance) {
        sceneInstance = scInstance;
    }

    @Documentate(desc = "Ends the scene")
    public void endScene() { sceneInstance.getScene().story.endScene(sceneInstance.getPlayer()); }

    @Documentate(desc = "Adds action to the scene.")
    public SceneJS addAction(Consumer<ActionPacketData> cb, ActionEvent ev) {
        Action act = new Action(cb,ev);
        sceneInstance.actsJs.add(act);
        return this;
    }

    @Documentate(desc = "Enters a cutscene.")
    public void enterCutscene(ApiJS.CameraMode camMode) {
        sceneInstance.inCutscene = true;
        sceneInstance.cutsceneCam = camMode;
    }

    @Documentate(desc = "Exits a cutscene.")
    public void exitCutscene() {
        sceneInstance.inCutscene = false;
        sceneInstance.getPlayer().setGameMode(GameType.SURVIVAL);
    }

    @Documentate(desc = "Create and show dialog")
    public void showDialog(ApiJS.DialogueBuilder dialog) {
        Dialog dialogue = new Dialog(dialog.question, new Bench[] {
                new Bench(dialog.answer1,
                            new Dialog(1, (Serializable & Runnable) () -> {
                                File file = new File(sceneInstance.getScene().story.storyFolder + "/scene/" + dialog.scene_id1 + ".json");
                                String f = file.getName();
                                sceneInstance.getPlayer().sendMessage(new StringTextComponent(f), sceneInstance.getPlayer().getUUID());
                            })
                        ),
                new Bench(dialog.answer2,
                        new Dialog(2, (Serializable & Runnable) () -> {
                            File file = new File(sceneInstance.getScene().story.storyFolder + "/scene/" + dialog.scene_id2 + ".json");
                            sceneInstance.getScene().story.addScene(file);
                            sceneInstance.endScene();
                            sceneInstance.startScene();
                        })
                ),
        });
        dialogue.show((PlayerEntity) sceneInstance.getPlayer());
    }

    @Documentate(desc = "Creates an npc.")
    public void createNpc(World world, ApiJS.NpcBuilder npc, double[] pos) {
        EntityType<CharacterEntity> npcReg = CharacterInit.CHARACTER.get();
        CharacterEntity npcEntity = (CharacterEntity) npcReg.spawn((ServerWorld) world, ItemStack.EMPTY, null, new BlockPos(pos[0],pos[1],pos[2]),
                SpawnReason.EVENT, false, false);
        npcEntity.setTexturePath(npc.texturePath);
        npcEntity.setModelPath(npc.modelPath);
        npcEntity.setAnimationPath(npc.animationPath);
        npcEntity.focusedEntity = sceneInstance.getPlayer();
        npcEntity.moveEntity(pos[0], pos[1], pos[2], 1f);
        npcEntity.getPersistentData().putBoolean("immortal", true);
        NpcJS npcJS = new NpcJS(npcEntity);
        localNpcs.put(npc.id, npcJS);
    }

    @Documentate(desc = "Creates an npc.")
    public void createNpc(WorldJS world, ApiJS.NpcBuilder npc, double[] pos)  {
        createNpc((World) world.getNative(),npc,pos);                           }

    @Documentate(desc = "Gets npc by its id.")
    public NpcJS getNpc(String id) {
        return localNpcs.get(id);
    }


    public Object getNative() { return sceneInstance; }
    public String getResourceId() { return "scene"; }
    public boolean isClient() { return true; }

}
