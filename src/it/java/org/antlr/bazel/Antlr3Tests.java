package org.antlr.bazel;

import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;


/**
 * ANTLR 3 tests.
 *
 * @author  Marco Hunsicker
 */
public class Antlr3Tests
{
    @Test
    public void basic() throws Exception
    {
        try (TestProject project = TestProject.create("examples/antlr3/SimpleC"))
        {
            AntlrRules.create(project.root())
                .srcjar(project.srcjar().toString())
                .version("3")
                .classpath(project.antlr3())
                .outputDirectory(project.outputDirectory().toString())
                .grammars(project.grammars())
                .args(project.args())
                .generate();

            assertTrue(Files.exists(project.srcjar()));
        }
    }


    @Test
    public void inheritFromLibFolder() throws Exception
    {
        try (TestProject project = TestProject.create("examples/antlr3/InheritLibFolder"))
        {
            AntlrRules.create(project.root())
                .srcjar(project.srcjar().toString())
                .version("3")
                .classpath(project.antlr3())
                .outputDirectory(project.outputDirectory().toString())
                .grammars(project.grammars())
                .args(project.args("-lib", "src/main/antlr3/lib"))
                .generate();

            assertTrue(Files.exists(project.srcjar()));
        }
    }


    @Test
    public void inheritFromSameFolder() throws Exception
    {
        try (TestProject project = TestProject.create("examples/antlr3/InheritSameFolder"))
        {
            AntlrRules.create(project.root())
                .srcjar(project.srcjar().toString())
                .version("3")
                .classpath(project.antlr3())
                .outputDirectory(project.outputDirectory().toString())
                .grammars(project.grammars())
                .args(project.args())
                .generate();

            assertTrue(Files.exists(project.srcjar()));
        }
    }


    @Test
    public void severalErrors() throws Exception
    {
        try (TestProject project = TestProject.create(
                "src/it/resources/antlr3/SeveralErrors"))
        {
            AntlrRules.create(project.root())
                .srcjar(project.srcjar().toString())
                .version("3")
                .classpath(project.antlr3())
                .outputDirectory(project.outputDirectory().toString())
                .grammars(project.grammars())
                .args(project.args())
                .generate();

            assertTrue(Files.exists(project.srcjar()));

            fail("Expected IllegalStateException");
        }
        catch (IllegalStateException ex)
        {
            assertEquals("ANTLR terminated with 7 errors", ex.getMessage());
        }
    }


    @Test
    public void singleError() throws Exception
    {
        try (TestProject project = TestProject.create(
                "src/it/resources/antlr3/SingleError"))
        {
            AntlrRules.create(project.root())
                .srcjar(project.srcjar().toString())
                .version("3")
                .classpath(project.antlr3())
                .outputDirectory(project.outputDirectory().toString())
                .grammars(project.grammars())
                .args(project.args())
                .generate();

            assertTrue(Files.exists(project.srcjar()));

            fail("Expected IllegalStateException");
        }
        catch (IllegalStateException ex)
        {
            assertEquals("ANTLR terminated with 1 error", ex.getMessage());
        }
    }
}