package com.dudadornelles.jat;

import com.lesfurets.jenkins.unit.MethodCall;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class JAT {

    // executable steps are steps that actually do work in jenkins, e.g: 'sh', whereas non executable steps simply
    // modify the way an executable step performs. e.g. a 'node' changes where 'sh' runs, but it doesnt do anything on its own,
    // it needs an executable step after it to be of any use
    private static final List<String> EXECUTABLE_STEPS = asList("sh", "input");

    public static List<JATStep> digest(List<MethodCall> callStack) {
        return digest(new ArrayList<>(), 0, callStack);
    }

    private static List<JATStep> digest(List<JATMetadata> jatMetadata, int index, List<MethodCall> callStack) {
        if (index >= callStack.size()) {
            return new ArrayList<>();
        }

        MethodCall methodCall = callStack.get(index);

        if (isMetadata(methodCall)) {
            // if method is metadata just add it to the metadata list and keep going
            jatMetadata.add(JATMetadata.createFrom(methodCall));
            return digest(jatMetadata, index + 1, callStack);

        } else {
            // method is not metadata, so it is an executable step

            // create a list with the current step
            JATStep step = JATStep.createFrom(methodCall, jatMetadata);
            List<JATStep> jatSteps = new ArrayList<>() {{ add(step); }};


            // if next method is not the end of call stack, remove metadata based on next call stack depth
            List<JATMetadata> metadata = jatMetadata;
            if (callStack.size() - 1 != index) {
                MethodCall next = callStack.get(index + 1);
                metadata = jatMetadata.stream().filter(m -> m.stackDepth() < next.getStackDepth()).collect(toList());
            }

            // remove metadata steps if call stack depth of next command is lower
            jatSteps.addAll(digest(metadata, index + 1, callStack));
            return jatSteps;
        }
    }


    private static boolean isMetadata(MethodCall methodCall) {
        return !EXECUTABLE_STEPS.contains(methodCall.getMethodName());
    }
}
