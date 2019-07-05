package com.dudadornelles.jat;

import com.lesfurets.jenkins.unit.BasePipelineTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;

public class JATTest extends BasePipelineTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void name() {
        runScript("src/test/resources/simple.groovy");

        List<JATStep> steps = JAT.digest(this.getHelper().getCallStack());

        assertEquals(1, steps.size());
        assertEquals(asList("sh", "./runscript"), steps.get(0).commandAndArgs());
    }
}