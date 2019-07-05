package com.dudadornelles.jat;

import com.lesfurets.jenkins.unit.MethodCall;

import java.io.Serializable;
import java.util.List;

import static java.util.Arrays.asList;

public class JATStep {
    private final MethodCall methodCall;
    private final List<JATMetadata> jatMetadata;

    public JATStep(MethodCall methodCall, List<JATMetadata> jatMetadata) {
        this.methodCall = methodCall;
        this.jatMetadata = jatMetadata;
    }

    public static JATStep createFrom(MethodCall methodCall, List<JATMetadata> jatMetadata) {
        return new JATStep(methodCall, jatMetadata);
    }

    public List<String> commandAndArgs() {
        return asList(methodCall.getMethodName(), methodCall.argsToString());
    }

}
