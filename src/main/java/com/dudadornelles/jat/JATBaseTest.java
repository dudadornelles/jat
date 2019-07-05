package com.dudadornelles.jat;

import com.lesfurets.jenkins.unit.BasePipelineTest;
import groovy.lang.Closure;

import java.util.List;

import static java.util.Arrays.asList;

public class JATBaseTest extends BasePipelineTest {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.getHelper().registerAllowedMethod("withEnv", asList(List.class, Closure.class), null);
    }
}

