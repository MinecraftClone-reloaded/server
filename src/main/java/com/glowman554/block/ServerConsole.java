package com.glowman554.block;

import com.glowman554.block.utils.FileUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerConsole extends Thread {
    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String command = bufferedReader.readLine();

                switch (command) {
                    case "exit":
                        System.exit(0);
                        break;
                    case "save":
                        FileUtils.writeFile(ServerMain.world.save(), "world.msave");
                        System.out.println("Save done!");
                        break;
                    case "load":
                        ServerMain.world.load(FileUtils.readFile("world.msave"));
                        System.out.println("Load done!");
                        break;
                    case "players":
                        System.out.println(ServerMain.players);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Console Exception: " + e.getMessage());
        }
    }
}
