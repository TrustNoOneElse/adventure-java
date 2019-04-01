package de.flash.game.user.command;

import de.flash.game.charakter.player.Player;
import de.flash.game.dialog.DialogManager;
import de.flash.game.map.Map;
import de.flash.game.system.loot.LootHandler;
import de.flash.game.user.Manager;

import java.util.ArrayList;

public final class CommandManager extends Manager {

    private final ArrayList<String> commands = new ArrayList<>();

    public CommandManager() {
        commands.add("help");
        commands.add("die");
        commands.add("move <direction>");
        commands.add("rest");
    }

    public void handleCommand(final String command, final Map map, final Player player, final LootHandler lootHandler) {
        startCommand(command.toLowerCase(), map, player);
    }

    private void startCommand(final String command, final Map map, final Player player) {
        final String splittedCommand = command.split(" ")[0];
        switch (splittedCommand) {
            case "help":
                help();
                break;
            case "die":
                commitSuicide(player);
                break;
            case "move":
                move(command, map, player);
                break;
            case "rest":
                rest(player);
                break;
        }
    }

    private void commitSuicide(final Player player) {
        player.die();
    }

    private void rest(final Player player) {
        player.rest();
    }

    private void move(final String command, final Map map, final Player player) {
        final String[] splittedCommand = command.split(" ");
        if (splittedCommand.length > 1) {
            switch (splittedCommand[1]) {
                case "west":
                    player.moveLeft(map);
                    break;
                case "north":
                    player.moveForward(map);
                    break;
                case "north-west":
                    player.moveForwardLeft(map);
                    break;
                case "north-east":
                    player.moveForwardRight(map);
                    break;
                case "east":
                    player.moveRight(map);
                    break;
                case "south":
                    player.moveBackward(map);
                    break;
                case "south-west":
                    player.moveBackwardLeft(map);
                    break;
                case "south-east":
                    player.moveBackwardRight(map);
                    break;
                default:
                    DialogManager.printMessage("You look at the compass and dont found the direction");
                    break;
            }
        }
    }


    private void help() {
        DialogManager.printMessage("Possible commands: ");
        for (final String command : commands) {
            DialogManager.printMessage("-> " + command);
        }
    }

    public boolean isValidCommand(final String command) {
        final String splittedCommand = command.toLowerCase().split(" ")[0];
        return commands.stream().anyMatch(res -> res.split(" ")[0].equals(splittedCommand));
    }
}
