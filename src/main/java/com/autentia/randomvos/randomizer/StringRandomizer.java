package com.autentia.randomvos.randomizer;

public class StringRandomizer extends AbstractRandomizer<String> {

    private static final String ALFA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String OTHER = " 0123456789";
    private static final char[] CHARS = (ALFA + ALFA.toLowerCase() + OTHER).toCharArray();

    private final int minLength;
    private final int maxLength;

    public StringRandomizer(final int minLength, final int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public String nextRandomValue() {
        int length = minLength + getRandom().nextInt(maxLength - minLength + 1);
        if (length < 0) {
            return null;
        }

        char[] buffer = new char[length];
        for (int i = 0; i < length; i++) {
            buffer[i] = CHARS[getRandom().nextInt(CHARS.length)];
        }
        return new String(buffer);
    }
}
