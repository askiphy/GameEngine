package ru.bananus.gameengine.GamesAPI;

import net.minecraft.entity.player.ServerPlayerEntity;
import ru.bananus.gameengine.GamesAPI.Instances.SceneInstance;
import ru.bananus.gameengine.GamesAPI.JS.ApiJS;
import ru.bananus.gameengine.GamesAPI.JS.Event.EventManager;
import ru.bananus.gameengine.GamesAPI.JS.PlayerJS;
import ru.bananus.gameengine.GamesAPI.JS.SceneJS;
import ru.bananus.gameengine.GamesAPI.JS.WorldJS;

public class PlotterEnvironment extends Environment {
    public static String envId = "plotter";
    public static String version = "3";
    public Environment env = this;

    public PlayerJS player;

    public Root root;
    public Game story;
    public SceneJS scene;
    public WorldJS world;

    public ActionEvent e = ActionEvent.DEF();
    public ApiJS.CameraMode cam = ApiJS.CameraMode.NIL();
    public EventManager evManager;

    public ApiJS api;

    public PlotterEnvironment(ServerPlayerEntity player, SceneInstance inst) {
        this.player = new PlayerJS(player);
        this.scene = new SceneJS(inst);
        this.story = inst.getScene().story;
        this.evManager = inst.eventManager;
        this.root = new Root();
        this.api = new ApiJS();
        this.world = new WorldJS(player.level);
    }
}
