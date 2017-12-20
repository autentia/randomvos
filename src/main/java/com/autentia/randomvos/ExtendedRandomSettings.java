package com.autentia.randomvos;

import com.autentia.randomvos.FieldDescriptor;
import java.util.Collections;
import java.util.List;

public class ExtendedRandomSettings {

    private int depth;
    private int minCollectionSize;
    private int maxCollectionSize;
    private int minStringLength;
    private int maxStringLength;
    private List<FieldDescriptor> excludedFields;
    private List<Class> excludedClasses;

    protected ExtendedRandomSettings() {
        depth = 2;
        minCollectionSize = -1;
        maxCollectionSize = 5;
        minStringLength = -1;
        maxStringLength = 10;
        excludedFields = Collections.emptyList();
        excludedClasses = Collections.emptyList();
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

    public List<FieldDescriptor> getExcludedFields() {
        return excludedFields;
    }

    protected void setExcludedFields(final List<FieldDescriptor> excludedFields) {
        this.excludedFields = excludedFields;
    }

    public List<Class> getExcludedClasses() {
        return excludedClasses;
    }

    protected void setExcludedClasses(final List<Class> excludedClasses) {
        this.excludedClasses = excludedClasses;
    }
}
