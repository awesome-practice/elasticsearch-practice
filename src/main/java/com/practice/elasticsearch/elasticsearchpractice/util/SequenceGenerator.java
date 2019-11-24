package com.practice.elasticsearch.elasticsearchpractice.util;

public abstract class SequenceGenerator {
    private static final ProtectedSequenceGenerator protectedSequenceGenerator = new ProtectedSequenceGenerator();

    public static long nextId() {
        return protectedSequenceGenerator.nextId();
    }
}
