package com.dudadornelles.jat;

import com.lesfurets.jenkins.unit.MethodCall;

public class JATMetadata {
    private MethodCall methodCall;

    public JATMetadata(MethodCall methodCall) {
        this.methodCall = methodCall;
    }

    public static JATMetadata createFrom(MethodCall methodCall) {
        return new JATMetadata(methodCall);
    }

    public int stackDepth() {
        return this.methodCall.getStackDepth();
    }
}
