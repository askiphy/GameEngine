package ru.gameengine;

public class FSC {
    public static String[] logoAscii = new String[] {
            "┏━━━┓╋╋╋╋╋╋╋╋╋╋┏━━━┓┏┓",
            "┃┏━━┛╋╋╋╋╋╋╋╋╋╋┃┏━┓┣┛┗┓",
            "┃┗━━┳━━┳━┳━━┳━━┫┗━━╋┓┏╋━━┳━┳┓╋┏┓",
            "┃┏━━┫┏┓┃┏┫┏┓┃┃━╋━━┓┃┃┃┃┏┓┃┏┫┃╋┃┃",
            "┃┃╋╋┃┗┛┃┃┃┗┛┃┃━┫┗━┛┃┃┗┫┗┛┃┃┃┗━┛┃",
            "┗┛╋╋┗━━┻┛┗━┓┣━━┻━━━┛┗━┻━━┻┛┗━┓┏┛",
            "╋╋╋╋╋╋╋╋╋┏━┛┃╋╋╋╋╋╋╋╋╋╋╋╋╋╋┏━┛┃",
            "╋╋╋╋╋╋╋╋╋┗━━┛╋╋╋╋╋╋╋╋╋╋╋╋╋╋┗━━┛"
    };

    public static String fsVersion = "0.1-ALPHA";

    public static String udcTick = "✔";
    public static String udcCross = "✖";
    public static String udcRFaceArrow = "⋙";
    public static String udcStar = "✦";
    public static String udcRombus = "◆";

    public static void sendInformationMessage() {
        String logo = join(logoAscii, "\n");
        String fsInfo = "GameEngine "+fsVersion;
        String headfooter = "------------------------------------";

        System.out.println(
                "\n"+headfooter
                        +"\n"+logo
                        +"\n"+fsInfo
                        +"\n"+headfooter
        );
    }

    public static String join(String[] array, String reg) {
        String joined = "";
        for(String el : array) {
            joined+=el+reg;
        }
        return joined;
    }

    public static String createMessage(String msg) {
        return msg+" | geLogger";
    }

    public static void sout(String x) {
        System.out.println(createMessage(x));
    }

    public static  enum FSError {
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
            return type.errorName+": "+msg+" | geLogger";
        }
    }
}
