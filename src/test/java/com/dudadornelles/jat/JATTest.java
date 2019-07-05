package com.dudadornelles.jat;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;

public class JATTest extends JATBaseTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void test_pipeline_groovy() {
        runScript("src/test/resources/pipeline.groovy");

        List<JATStep> steps = JAT.digest(this.getHelper().getCallStack());

        assertEquals(3, steps.size());
        assertEquals(asList("sh", "./runscript"), steps.get(0).commandAndArgs());

        assertEquals(asList("sh", "./runanotherscript"), steps.get(1).commandAndArgs());
        assertEquals("1", steps.get(1).env().get("LOCAL_VAR"));
        assertEquals("true", steps.get(1).env().get("GLOBAL_ENV_VAR"));

        assertEquals(asList("sh", "./yetanotherone"), steps.get(2).commandAndArgs());
        assertEquals("2", steps.get(2).env().get("LOCAL_VAR"));
        assertEquals("true", steps.get(1).env().get("GLOBAL_ENV_VAR"));
    }
}