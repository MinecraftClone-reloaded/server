package com.glowman554.block;

import com.glowman554.block.world.Chunk;
import com.glowman554.block.world.World;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ServerMain {

    public static World world;
    public static HashMap<String, String> players = new HashMap<>();
    private static String event = "";
    private static int port;

    public static void main(String[] args) {
        world = new World();

        if(args.length != 1) {
            port = 90;
        } else {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(String.format("Server is listening on port: %d", port));
            new ServerConsole().start();

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
                    world.setBlock(temp[1], Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), Integer.parseInt(temp[4]));
                }

                if(text.contains("lp")) {
                    SimpleDateFormat date = new SimpleDateFormat("HH-mm-ss");
                    String logindate = date.format(new Date());
                    String[] temp = text.split(" ");
                    players.put(temp[1], logindate);
                    System.out.println(temp[1] + " Joint the game!");
                }

                socket.close();
            }


        } catch (IOException e) {
            System.out.println("Server Exception: " + e.getMessage());
        }

    }
}
