package ru.bananus.gameengine.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import ru.bananus.gameengine.GamesAPI.Root;

public class MainCommand {
    @SuppressWarnings("all")
    private static final SimpleCommandExceptionType UNKNOWN = new SimpleCommandExceptionType(new StringTextComponent("Command not found!"));
    private static final SimpleCommandExceptionType STORY_NOT_FOUND = new SimpleCommandExceptionType(new StringTextComponent("Игра не найдена!"));

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("gameengine").requires(s -> s.hasPermission(1)
                )
                .then(Commands.literal("set_game")
                        .then(Commands.argument("game", StringArgumentType.string())
                                .executes(MainCommand::setStory))
                ).then(Commands.literal("refresh")
                        .executes(MainCommand::refresh)
                ));
    }

    private static int setStory(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        String story = StringArgumentType.getString(context, "story");
        if(Root.storiesList.containsKey(story)) {
            Root.setActiveStory(story);
            Root.reloadStories();
            source.sendSuccess(new StringTextComponent("Loaded successfully!"), false);
            return 0;
        } else {
            throw STORY_NOT_FOUND.create();
        }
    }

    private static int refresh(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        Root.reloadStories();
        source.sendSuccess(new StringTextComponent("Reloaded!"), false);
        return 0;
    }
}
