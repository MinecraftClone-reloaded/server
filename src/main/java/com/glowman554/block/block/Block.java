package com.glowman554.block.block;


public class Block {

    public Type type;

    public Block(Type type) {
        this.type = type;
    }



    public enum Type {
        DirtBlock, WoodBlock, LeavesBlock, BerryBlock, GrassBlock, GlassBlock, StoneBlock, TestBlock;
    }
}
