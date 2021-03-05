package com.glowman554.block;

import com.glowman554.block.command.CommandEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerConsole extends Thread {
    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String command = bufferedReader.readLine();

                ServerMain.commandManager.onCommand(new CommandEvent(command, command.split(" ")[0], CommandEvent.getArguments(command.split(" "))));
            }
        } catch (Exception e) {
            System.out.println("Console Exception: " + e.getMessage());
        }
    }
}
