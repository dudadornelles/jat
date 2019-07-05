package com.dudadornelles.jat;

import com.lesfurets.jenkins.unit.MethodCall;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class JATStep {
    private final MethodCall methodCall;
    private final List<MethodCall> jatMetadata;

    public JATStep(MethodCall methodCall, List<MethodCall> jatMetadata) {
        this.methodCall = methodCall;
        this.jatMetadata = jatMetadata;
    }

    public List<String> commandAndArgs() {
        return asList(methodCall.getMethodName(), methodCall.argsToString());
    }

    public Map env() {
        return this.jatMetadata.stream()
                .filter(e -> e.getMethodName().equals("withEnv"))
                .map(e -> ((List<String>) e.getArgs()[0]).stream().map(f -> f.split("=", 2)).collect(toList()))
                .collect(HashMap::new, (m, i) -> i.forEach(e -> m.put(e[0], e[1])), HashMap::putAll);
    }
}
