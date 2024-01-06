package ru.gameengine.GamesAPI;

import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import ru.gameengine.Annotations.MethodJSON;
import ru.gameengine.GamesAPI.Instances.SceneInstance;
import ru.gameengine.GamesAPI.Utils.FileManager;

import java.lang.reflect.Method;
import java.util.HashMap;

public class PlotterJSON {
    public static Method getActionById(String id) {
        Method[] methods = PlotterJSON.class.getMethods();
        for(Method m : methods) {
            boolean hasAnn = m.isAnnotationPresent(MethodJSON.class);
            if(!hasAnn) continue;
            String actionId = m.getAnnotation(MethodJSON.class).id();
            if(id.equals(actionId)) return m;
        }
        return null;
    }

    public static void talk(SceneInstance scene, HashMap<String,Object> data) {
        String author = (String) FileManager.getSafely(data,"author","NPC");
        String text = (String) FileManager.getSafely(data,"text","Hello World!");
        String color = (String) FileManager.getSafely(data, "color", "f");
        String message = "\u00A7"+color+"["+author+"]\u00A7r "+text;
        StringTextComponent playerMessage = new StringTextComponent(message);
        scene.getPlayer().sendMessage(playerMessage, Util.NIL_UUID);
    }

 
    public static void doNothing(SceneInstance scene, HashMap<String,Object> data) {

    }
}
