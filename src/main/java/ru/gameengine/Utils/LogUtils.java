package ru.gameengine.Utils;

public class LogUtils {
    public static String BuildVersion = "0.3-BuildsForDev";
    public static String udcTick = "✔";
    public static String udcCross = "✖";
    public static String udcRFaceArrow = "⋙";
    public static String udcStar = "✦";
    public static String udcRombus = "◆";
/*
Метод sendInformationMessage выполняет сборку сообщения о инициализированной версии GameEngine
*/
    public static void sendInformationMessage() {
        String strInfo = "GameEngine "+BuildVersion;
        System.out.println(strInfo);
    }
/* -???- */
    public static String join(String[] array, String reg) {
        String joined = "";
        for(String el : array) {
            joined+=el+reg;
        }
        return joined;
    }
/*
sendLog позволяет отправить сообщение в логи, такое сообщение будет постороено по шаблону : "[СООБЩЕНИЕ] |GELogger"
*/
    public static void sendLog(String msg) {
        System.out.println(msg+" | GE Logger");
    }

    public static enum FSError {
        ROOT("RootError"),
        STORY("StoryError"),
        SCENE("SceneError"),
        JS("SceneError"),
        GENERAL("UnexpectedError"),
        NPC("NpcError"),
        JSON("JsonError"),
        PLOTTER("PlotterError")
        ;

        public final String errorName;
        FSError(String errorName) {
            this.errorName = errorName;
        }

        public String createMessage(FSError type, String msg) {
            return type.errorName+": "+msg+" | GELogger";
        }
    }
}
