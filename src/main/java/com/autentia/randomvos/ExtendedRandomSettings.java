package com.autentia.randomvos;

public class ExtendedRandomSettings {

    private int depth;
    private int minCollectionSize;
    private int maxCollectionSize;
    private int minStringLength;
    private int maxStringLength;

    protected ExtendedRandomSettings() {
        depth = 2;
        minCollectionSize = -1;
        maxCollectionSize = 5;
        minStringLength = -1;
        maxStringLength = 10;
    }

    public int getDepth() {
        return depth;
    }

    protected void setDepth(final int depth) {
        this.depth = depth;
    }

    public int getMinCollectionSize() {
        return minCollectionSize;
    }

    protected void setMinCollectionSize(final int minCollectionSize) {
        this.minCollectionSize = minCollectionSize;
    }

    public int getMaxCollectionSize() {
        return maxCollectionSize;
    }

    protected void setMaxCollectionSize(final int maxCollectionSize) {
        this.maxCollectionSize = maxCollectionSize;
    }

    public int getMinStringLength() {
        return minStringLength;
    }

    protected void setMinStringLength(final int minStringLength) {
        this.minStringLength = minStringLength;
    }

    public int getMaxStringLength() {
        return maxStringLength;
    }

    protected void setMaxStringLength(final int maxStringLength) {
        this.maxStringLength = maxStringLength;
    }
}
