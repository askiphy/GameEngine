package ru.gameengine.GamesAPI;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import ru.gameengine.GamesAPI.data.PackedScriptData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;


public class GameScript {
    public File scriptFile;
    public String scriptId;

    public GameScript(File script) {
        scriptFile = script;
        scriptId = script.getName();
    }

    public void runWithEnv(Environment env) {
        try
        {

            Context ctx = Context.enter();
            ScriptableObject scope = ctx.initStandardObjects();
            PlotterEnvironment customEnv = (PlotterEnvironment) env;
            InputStreamReader reader = new InputStreamReader(new FileInputStream(scriptFile),StandardCharsets.UTF_8);

            applyEnvToScope(scope, customEnv);

            ctx.evaluateReader(scope, reader, scriptId,1,null);

        } catch (Exception e) {
            e.printStackTrace();
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.RED + e.getMessage()), Minecraft.getInstance().player.getUUID());
        }
    }

    public PackedScriptData toPacked() {
        PackedScriptData data = new PackedScriptData();
        data.id = scriptId;
        try {
            List<String> lines = Files.readAllLines(scriptFile.toPath(),StandardCharsets.UTF_8);
            lines.forEach((str) -> {
                data.content += str+"\n";
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void applyEnvToScope(ScriptableObject scope, Environment env) {
        Field[] envFields = env.getClass().getFields();
        for(Field envField:envFields) try
        {
            String fieldId = envField.getName();
            Object fieldVal = envField.get(env);
            ScriptableObject.putConstProperty(scope, fieldId, fieldVal);
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}
