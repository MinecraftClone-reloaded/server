package com.glowman554.block.world;


public class World {
    private final static int world_size = 128;
    private final static int render_distance = 2;

    public Chunk world[][];

    public World() {
        this.world = new Chunk[world_size][world_size];
    }


    public String save() {

        String text = "";

        for (int i = 0; i < world_size; i++) {
            for (int k = 0; k < world_size; k++) {
                if(world[i][k] == null) {
                    text += "0<";
                } else {
                    text += world[i][k].save() + "<";
                }
            }
        }
        return text;
    }

    public void load(String text) {

        String[] world_load = text.split("<");
        int readsofar = 0;

        for (int i = 0; i < world_size; i++) {
            for (int k = 0; k < world_size; k++) {
                if(world_load[readsofar].equals("0")) {
                    world[i][k] = null;
                } else {
                    world[i][k] = new Chunk(false, i, k);
                    world[i][k].load(world_load[readsofar]);
                }
                readsofar++;
            }
        }
    }

    public void setBlock(String block, int x, int y, int z) {
        int chunkX = (int) (x / Chunk.chunk_size);
        int chunkY = (int) (z / Chunk.chunk_size);

        if(world[chunkX][chunkY] != null) {
            world[chunkX][chunkY].setBlock(block, x, y, z);
        }
    }
}
