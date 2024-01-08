package ru.gameengine.GamesAPI;

import com.google.gson.internal.LinkedTreeMap;
import ru.gameengine.GamesAPI.Utils.CustomCasters;
import ru.gameengine.GamesAPI.Utils.FileManager;
import ru.gameengine.GamesAPI.data.Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Scene {
    public String id = "";
    public String scriptId = "";

    public ArrayList<Action> actions = new ArrayList<Action>();

    public final Game story;

    public final SceneJSON json;

    public Scene(Game story, SceneJSON json) {
        this.id = json.id;
        this.story = story;
        if(json.lang.equals("js")) this.scriptId = json.script;
        else if(json.lang.equals("json")) loadActionsFromJSON(json);
        this.json = json;
    };

    public void loadActionsFromJSON(SceneJSON json) { loadActionsFromJSON(json.actions); }

    @SuppressWarnings("all")
    public void loadActionsFromJSON(ArrayList<HashMap<String,Object>> acts) {
        acts.forEach((act) -> {
            String id = (String) FileManager.getSafely(act,"id","placeholder");
            Method actionMethod = PlotterJSON.getActionById(id);
            ArrayList<LinkedTreeMap<String,Object>> actsMulti = (ArrayList<LinkedTreeMap<String,Object>>) act.get("multi");
            if(actsMulti == null) actsMulti = new ArrayList<LinkedTreeMap<String,Object>>();

            final ArrayList<LinkedTreeMap<String,Object>> mul = actsMulti;

            Action action = new Action((packet) -> {
                try {
                    actionMethod.invoke(null, packet.scene, act);
                    mul.forEach((e) -> {
                        String mId = (String) FileManager.getSafely(e,"id","placeholder");
                        Method mActionMethod = PlotterJSON.getActionById(mId);
                        HashMap<String,Object> actE = CustomCasters.setToHashMap(e.entrySet());
                        try { mActionMethod.invoke(null, packet.scene, ( actE ));
                        } catch (Exception e1) { e1.printStackTrace(); }
                    });
                }
                catch (Exception e) { e.printStackTrace(); }
            },ActionEvent.DEF());
            actions.add(action);
        });
    }
}
