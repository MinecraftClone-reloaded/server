package com.glowman554.block.command.impl;

import com.glowman554.block.ServerMain;
import com.glowman554.block.command.Command;
import com.glowman554.block.command.CommandEvent;
import com.glowman554.block.utils.FileUtils;

import java.io.IOException;

public class LoadCommand implements Command {
    @Override
    public void execute(CommandEvent event) {
        if(event.args.length != 0) {
            event.CommandFail();
            return;
        }
        try {
            ServerMain.world.load(FileUtils.readFile("world.msave"));
            System.out.println("Load done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) {
        System.out.println("What do i need to say about load");
    }
}
