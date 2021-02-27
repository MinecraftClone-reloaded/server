package com.glowman554.block.world;

import com.glowman554.block.block.*;

import java.util.Random;

public class Chunk {

    public final static int chunk_size = 8;
    public final static float field_size = 4;

    private int xOffset;
    private int yOffset;

    public int seed;

    private Block field[][][];
    public Chunk(boolean generate, int xOffset, int yOffset) {
        this.field = new Block[chunk_size][chunk_size][chunk_size];

        this.xOffset = xOffset;
        this.yOffset = yOffset;

        this.seed = (xOffset * new Random().nextInt()) % (yOffset + new Random().nextInt());

        System.out.println(String.format("Generating chunk: x: %d, y: %d, Seed: %d", this.xOffset, this.yOffset, this.seed));

        Random rand = new Random();
        rand.setSeed(this.seed);

        if(generate) {
            for (int i = 0; i < chunk_size; i++) {
                for (int k = 0; k < chunk_size; k++) {
                    this.field[i][0][k] = new GrassBlock();
                    int ran = rand.nextInt(100);
                    switch (ran) {
                        case 1:
                            Tree(i, 1, k);
                            break;
                        case 2:
                            Berry(i, 1, k);
                            break;
                    }
                }
            }
        }
    }



    public void Tree(int x, int y, int z) {
        try {
            field[x][y][z] = new WoodBlock();
            field[x][y + 1][z] = new WoodBlock();
            field[x][y + 2][z] = new WoodBlock();
            field[x][y + 3][z] = new WoodBlock();
            field[x][y + 4][z] = new LeavesBlock();
            field[x + 1][y + 2][z] = new LeavesBlock();
            field[x + 1][y + 3][z] = new LeavesBlock();
            field[x - 1][y + 2][z] = new LeavesBlock();
            field[x - 1][y + 3][z] = new LeavesBlock();
            field[x][y + 2][z + 1] = new LeavesBlock();
            field[x][y + 3][z + 1] = new LeavesBlock();
            field[x][y + 2][z - 1] = new LeavesBlock();
            field[x][y + 3][z - 1] = new LeavesBlock();
            System.out.println("TreeCreate " + x + "," + y + "," + z);
        } catch (Exception e) {
            System.out.println("TreeCreate: " + e);
            try {
                field[x][y][z] = null;
                field[x][y + 1][z] = null;
                field[x][y + 2][z] = null;
                field[x][y + 3][z] = null;
                field[x][y + 4][z] = null;
                field[x + 1][y + 2][z] = null;
                field[x + 1][y + 3][z] = null;
                field[x - 1][y + 2][z] = null;
                field[x - 1][y + 3][z] = null;
                field[x][y + 2][z + 1] = null;
                field[x][y + 3][z + 1] = null;
                field[x][y + 2][z - 1] = null;
                field[x][y + 3][z - 1] = null;
            } catch (Exception e2) {
                System.out.println("TreeCreate: " + e2);
            }
        }
    }

    public void Berry(int x, int y, int z) {
        try {
            field[x][y][z] = new BerryBlock();
            System.out.println("BerryCreate " + x + "," + y + "," + z);
        } catch (Exception e) {
            System.out.println("BerryCreate: " + e);
        }
    }

    public String save() {
        String text = "";
        for (int i = 0; i < chunk_size; i++) {
            for (int j = 0; j < chunk_size; j++) {
                for (int k = 0; k < chunk_size; k++) {
                    if(this.field[i][j][k] != null) {
                        text += this.field[i][j][k].type + ",";
                    } else {
                        text += "0,";
                    }
                }
            }
        }
        return text;
    }

    public void load(String text) {

        String[] world_load = text.split(",");
        int readsofar = 0;

        for (int i = 0; i < chunk_size; i++) {
            for (int j = 0; j < chunk_size; j++) {
                for (int k = 0; k < chunk_size; k++) {
                    if(world_load[readsofar].equals("0")) {

                    } else {
                        switch(Enum.valueOf(Block.Type.class, world_load[readsofar])) {
                            case DirtBlock:
                                field[i][j][k] = new DirtBlock();
                                break;
                            case WoodBlock:
                                field[i][j][k] = new WoodBlock();
                                break;
                            case BerryBlock:
                                field[i][j][k] = new BerryBlock();
                                break;
                            case GrassBlock:
                                field[i][j][k] = new GrassBlock();
                                break;
                            case LeavesBlock:
                                field[i][j][k] = new LeavesBlock();
                                break;
                            case StoneBlock:
                                field[i][j][k] = new StoneBlock();
                                break;
                            case GlassBlock:
                                field[i][j][k] = new GlassBlock();
                                break;
                            case TestBlock:
                                field[i][j][k] = new TestBlock();
                                break;
                        }
                    }
                    readsofar++;
                }
            }
        }
    }



    public void setBlock(String block, int x, int y, int z) {
        switch (Enum.valueOf(Block.Type.class, block)) {
            case LeavesBlock:
                field[x][y][z] = new LeavesBlock();
                break;
            case GrassBlock:
                field[x][y][z] = new GrassBlock();
                break;
            case BerryBlock:
                field[x][y][z] = new BerryBlock();
                break;
            case WoodBlock:
                field[x][y][z] = new WoodBlock();
                break;
            case DirtBlock:
                field[x][y][z] = new DirtBlock();
                break;
            case TestBlock:
                field[x][y][z] = new TestBlock();
                break;
            case GlassBlock:
                field[x][y][z] = new GlassBlock();
                break;
            case StoneBlock:
                field[x][y][z] = new StoneBlock();
                break;

        }
    }
}
