package com.glowman554.block.plugin;

import com.glowman554.block.ServerMain;
import com.glowman554.block.utils.FileUtils;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PluginAPI {

    private static List<ScriptObjectMirror> login_events = new ArrayList<>();
    private static List<ScriptObjectMirror> block_place_events = new ArrayList<>();
    private static List<ScriptObjectMirror> timer_events = new ArrayList<>();

    public static void addCommand(String command, String help, String help_long, ScriptObjectMirror command_executor) {
        ServerMain.commandManager.registerCommand(command, help_long, new PluginCommand(help, command_executor));
    }

    public static void addCommand(String command, String help, ScriptObjectMirror command_executor) {
        ServerMain.commandManager.registerCommand(command, "Not specified!", new PluginCommand(help, command_executor));
    }

    public static void save() {
        try {
            FileUtils.writeFile(ServerMain.world.save(), "world.msave");
            System.out.println("Save done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(String what) {
        try {
            FileUtils.writeFile(ServerMain.world.save(), what);
            System.out.println(String.format("Save as %s done!", what));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            ServerMain.world.load(FileUtils.readFile("world.msave"));
            System.out.println("Load done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(String what) {
        try {
            ServerMain.world.load(FileUtils.readFile(what));
            System.out.println(String.format("Load as %s done!", what));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registerEvent(String what, ScriptObjectMirror callback) {
        switch (what) {
            case "PlayerLogin":
                System.out.println("Registering event " + what);
                login_events.add(callback);
                break;

            case "SetBlock":
                System.out.println("Registering event " + what);
                block_place_events.add(callback);
                break;

            case "Timer":
                System.out.println("Registering event " + what);
                timer_events.add(callback);
                break;


            default:
                System.out.println(String.format("Oh no the event %s is not implemented!", what));
                break;
        }
    }

    public static void playerLoginEvent(String who) {
        login_events.forEach(event -> {
            event.call(event, who);
        });
    }

    public static void blockPlaceEvent(String block, int x, int y, int z, int chunkX, int chunkY) {
        block_place_events.forEach(event -> {
            event.call(event, block, x, y, z, chunkX, chunkY);
        });
    }

    public static void timerEvent() {
        timer_events.forEach(event -> {
            event.call(event);
        });
    }
}


