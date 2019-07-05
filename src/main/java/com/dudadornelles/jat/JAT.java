package com.dudadornelles.jat;

import com.lesfurets.jenkins.unit.MethodCall;

import java.util.ArrayList;
import java.util.List;

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

    private static List<JATStep> digest(List<MethodCall> metadata, int index, List<MethodCall> callStack) {
        if (index >= callStack.size()) {
            return new ArrayList<>(); // its over, its oooover ♫♫
        }

        // every time we add a method, we need to check if the depth is lower than the previous method
        // if it is, we need to get rid of all metadata that was in the deeper levels of the stack
        MethodCall currentMethod = callStack.get(index);
        if (index > 0 && currentMethod.getStackDepth() < callStack.get(index - 1).getStackDepth()) {
            metadata = metadata.stream().filter(m -> m.getStackDepth() < currentMethod.getStackDepth()).collect(toList());
        }

        if (isMetadata(currentMethod)) {
            // if method is metadata just add it to the metadata list and keep going
            metadata.add(currentMethod);
            return digest(metadata, index + 1, callStack);

        } else {
            // method is not metadata, so it is an executable step
            final JATStep step = new JATStep(currentMethod, metadata);
            List<JATStep> jatSteps = new ArrayList<>() {{ add(step); }};

            // recursion continues...
            jatSteps.addAll(digest(metadata, index + 1, callStack));
            return jatSteps;
        }
    }


    private static boolean isMetadata(MethodCall methodCall) {
        return !EXECUTABLE_STEPS.contains(methodCall.getMethodName());
    }
}
