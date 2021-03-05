package com.glowman554.block.command;

import java.util.HashMap;

public class CommandManager {
    public HashMap<String, Command> commands = new HashMap<>();
    public HashMap<String, String> helps = new HashMap<>();


    public void onCommand(CommandEvent event) {

        if(event.command.equals("help")) {
            switch(event.args.length) {
                case 0:

                    final String[] help_msg = {""};

                    helps.forEach((key, value) -> {
                        help_msg[0] += key + " -> " + value + "\n";
                    });

                    System.out.println(help_msg[0]);

                    break;

                case 1:
                    if(commands.get(event.args[0]) != null) {
                        commands.get(event.args[0]).on_help(event);
                    } else {
                        System.out.println("Command not found");
                    }
                    break;

                default:
                    System.out.println("Please use help");
                    break;
            }
        }

        if(commands.get(event.command) != null) {
            commands.get(event.command).execute(event);
        }
    }

    public void registerCommand(String what, String help, Command c) {
        c.on_register();

        commands.put(what, c);
        helps.put(what, help);
        System.out.printf("[%s] Command register complete\n", what);
    }
}
