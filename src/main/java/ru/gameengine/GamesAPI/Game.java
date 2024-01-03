package ru.gameengine.GamesAPI;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import ru.gameengine.FSC;
import ru.gameengine.GamesAPI.Instances.SceneInstance;
import ru.gameengine.GamesAPI.JS.JSResource;
import ru.gameengine.GamesAPI.Utils.FileManager;
import ru.gameengine.GamesAPI.Utils.Filters;
import ru.gameengine.GamesAPI.data.PackedLibData;
import ru.gameengine.GamesAPI.data.PlayerData;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class Game {

    public String storyId = "";
    public File storyFolder;

    public HashMap<String,Scene> scenes = new HashMap<String,Scene>();
    public HashMap<String, GameScript> scripts = new HashMap<String, GameScript>();
    public ArrayList<File> libraries = new ArrayList<File>();

    public HashMap<UUID,SceneInstance> activeScenes = new HashMap<UUID,SceneInstance>();

    public HashMap<UUID,PlayerData> playersData = new HashMap<UUID, PlayerData>();

    public boolean isBuilded = false;

    public Game(String id, File folder) {
        storyId = id;
        storyFolder = folder;
        readFolders();
    }

    public SceneInstance getActiveSceneForPlayer(UUID playerUuid) {
        return activeScenes.get(playerUuid);
    }

    public void tick() {
        MinecraftServer server = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer());
        List<ServerPlayerEntity> players = server.getPlayerList().getPlayers();
        players.forEach((p) -> {
            UUID playerUuid = p.getUUID();
            if(playersData.get(playerUuid) == null) playersData.put(playerUuid,readPlayerData(p));
            SceneInstance activeScene = getActiveSceneForPlayer(playerUuid);
            PlayerData data = playersData.get(playerUuid);
            if(activeScene == null) {
                if(data.queueTimer > 0) data.queueTimer-=1;
                else {
                    Scene scene = scenes.get(data.sceneQueued);
                    if(scene != null) {
                        startScene(scene, p);
                        FSC.sout(FSC.udcStar+"Starting scene "+scene.id.toUpperCase()+" for "+p.getName().getString());
                    }
                }
                playersData.put(playerUuid,data);
                if(data.queueTimer % 10 == 0 && data.queueTimer >= 10) writePlayerData(data, p);
            } else {
                activeScene.tick();
            }
        });
    }

    public void queueScene(JSResource entity, String scene, double secs) { queueScene( (PlayerEntity) entity.getNative(), scene, secs); }

    public void queueScene(PlayerEntity entity, String scene, double secs) { queueScene(entity, scene, (int) (secs*20));}
    public void queueScene(PlayerEntity player, String scene, int ticks) {
        PlayerData data = playersData.get(player.getUUID());
        data.queueTimer = ticks;
        data.sceneQueued = scene;
        playersData.put(player.getUUID(),data);
        writePlayerData(data, player);
    }


    public void startScene(Scene scene, ServerPlayerEntity playerEntity) {
        endScene(playerEntity);
        SceneInstance instance = new SceneInstance(scene, playerEntity);
        instance.startScene();
        activeScenes.put(playerEntity.getUUID(), instance);
    }

    public void endScene(ServerPlayerEntity playerEntity) {
        SceneInstance instance = activeScenes.get(playerEntity.getUUID());
        if(instance != null) instance.endScene();
        activeScenes.remove(playerEntity.getUUID());

    }

    public void readFolders() {
        readScenes(); readScripts(); readLibraries();
    }

    public void readScenes() {
        scenes.clear(); FileManager.listInDirAndDo(storyFolder, "scene", Filters.onlyJson, this::addScene); }

    public void readScripts() {
        scripts.clear(); FileManager.listInDirAndDo(storyFolder, "js", Filters.onlyJs, this::addScript); }

    public void readLibraries() {
        libraries.clear(); FileManager.listInDirAndDo(storyFolder, "lib", Filters.onlyJs, (f) -> {libraries.add(f);});}

    public void addScene(File f) {
        try {
            SceneJSON json = FileManager.jsonToJava(f, SceneJSON.class);
            Scene scene = new Scene(this,json);
            scenes.put(json.id,scene);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void addScene(SceneJSON json) {
        try {
            Scene scene = new Scene(this,json);
            scenes.put(json.id,scene);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void addScript(File f) {
        GameScript scr = new GameScript(f);
        String name = f.getName();
        scripts.put(
                name.substring(0,name.length()-3),
                scr
        );
    }

    public PackedLibData packLib(File lib) {
        PackedLibData data = new PackedLibData();
        data.id = lib.getName();
        try {
            List<String> lines = Files.readAllLines(lib.toPath(), StandardCharsets.UTF_8);
            lines.forEach((str) -> {
                data.content += str+"\n";
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public PlayerData readPlayerData(PlayerEntity player) {
        UUID playerUuid = player.getUUID();
        File dataFolder = new File(storyFolder,"data");
        dataFolder.mkdir();
        File playerDataFolder = new File(dataFolder,"player");
        playerDataFolder.mkdir();
        File playerDataFile = new File(playerDataFolder,playerUuid.toString()+".json");
        try {
            PlayerData playerData = FileManager.jsonToJava(playerDataFile, PlayerData.class);
            return playerData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PlayerData();
    }

    public void writePlayerData(PlayerData data, PlayerEntity player) {
        UUID playerUuid = player.getUUID();
        File dataFolder = new File(storyFolder,"data");
        dataFolder.mkdir();
        File playerDataFolder = new File(dataFolder,"player");
        playerDataFolder.mkdir();
        File playerDataFile = new File(playerDataFolder,playerUuid.toString()+".json");
        try {
            FileManager.javaToJson(playerDataFile, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
