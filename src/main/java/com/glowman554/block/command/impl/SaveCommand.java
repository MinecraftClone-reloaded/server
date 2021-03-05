package com.glowman554.block.command.impl;

import com.glowman554.block.ServerMain;
import com.glowman554.block.command.Command;
import com.glowman554.block.command.CommandEvent;
import com.glowman554.block.utils.FileUtils;

import java.io.IOException;

public class SaveCommand implements Command {
    @Override
    public void execute(CommandEvent event) {
        if(event.args.length != 0) {
            event.CommandFail();
            return;
        }
        try {
            FileUtils.writeFile(ServerMain.world.save(), "world.msave");
            System.out.println("Save done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void on_register() {

    }

    @Override
    public void on_help(CommandEvent event) {
        System.out.println("What do i need to say about save");
    }
}
