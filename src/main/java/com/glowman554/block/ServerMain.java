package com.glowman554.block;

import com.glowman554.block.command.CommandManager;
import com.glowman554.block.command.impl.ExitCommand;
import com.glowman554.block.command.impl.LoadCommand;
import com.glowman554.block.command.impl.SaveCommand;
import com.glowman554.block.plugin.PluginAPI;
import com.glowman554.block.plugin.PluginLoader;
import com.glowman554.block.utils.FileUtils;
import com.glowman554.block.world.Chunk;
import com.glowman554.block.world.World;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ServerMain {

    public static World world;
    private static PluginLoader pluginLoader;
    public static HashMap<String, String> players = new HashMap<>();
    private static String event = "";
    private static int port;

    public static CommandManager commandManager;

    public static void main(String[] args) {

        world = new World();
        try {
            pluginLoader = new PluginLoader();
        } catch (IOException e) {
            e.printStackTrace();
        }

        commandManager = new CommandManager();
        commandManager.registerCommand("exit", "Exit the server", new ExitCommand());
        commandManager.registerCommand("load", "Load the world", new LoadCommand());
        commandManager.registerCommand("save", "Save the world", new SaveCommand());

        if(args.length != 1) {
            port = 90;
        } else {
            port = Integer.parseInt(args[0]);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public synchronized void start() {
                try {
                    FileUtils.writeFile(ServerMain.world.save(), "world.msave");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Save done!");
                pluginLoader.disableAll();
            }
        });

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(String.format("Server is listening on port: %d", port));
            new ServerConsole().start();

            pluginLoader.enableAll();

            new Thread("Timer callback") {
                @Override
                public void run() {
                    while (true) {
                        PluginAPI.timerEvent();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            while (true) {
                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);

                String text = bufferedReader.readLine();


                switch (text) {
                    case "ge":
                        printWriter.println(event);
                        break;
                    case "gw":
                        printWriter.println(world.save());
                        break;
                    case "sw":
                        FileUtils.writeFile(ServerMain.world.save(), "world.msave");
                        System.out.println("Save done!");
                        break;
                    case "lw":
                        world.load(FileUtils.readFile("world.msave"));
                        System.out.println("Load done!");
                        break;
                    case "es":
                        System.exit(0);
                        break;
                    case "gp":
                        printWriter.println(players);
                        break;
                }

                if(text.contains("gc")) {
                    String[] temp = text.split(" ");
                    int x = Integer.parseInt(temp[1]);
                    int y = Integer.parseInt(temp[2]);

                    if(world.world[x][y] == null) {
                        world.world[x][y] = new Chunk(true, x, y);
                    }
                    printWriter.println(world.world[x][y].save());
                }

                if(text.contains("sb")) {
                    String[] temp = text.split(" ");
                    event = text;
                    world.setBlock(temp[1], Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), Integer.parseInt(temp[4]), Integer.parseInt(temp[5]), Integer.parseInt(temp[6]));
                    PluginAPI.blockPlaceEvent(temp[1], Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), Integer.parseInt(temp[4]), Integer.parseInt(temp[5]), Integer.parseInt(temp[6]));
                }

                if(text.contains("lp")) {
                    SimpleDateFormat date = new SimpleDateFormat("HH-mm-ss");
                    String logindate = date.format(new Date());
                    String[] temp = text.split(" ");
                    players.put(temp[1], logindate);
                    System.out.println(temp[1] + " Joint the game!");
                    PluginAPI.playerLoginEvent(temp[1]);
                }

                socket.close();
            }


        } catch (IOException e) {
            System.out.println("Server Exception: " + e.getMessage());
        }

    }
}
