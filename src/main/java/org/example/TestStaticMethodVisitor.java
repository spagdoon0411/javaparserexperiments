package org.example;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestStaticMethodVisitor {

    // Tests the parsing of a simple file with static methods from Java standard libraries and self
    @Test
    public void testSuccessfulInfos() throws IOException {
        String path = "src/main/test-repo/Bird.java";

        List<StaticCallInfo> expectedCalls = Arrays.asList(
                new StaticCallInfo("confirmConspiracy", "Bird"),
                new StaticCallInfo("asList", "Arrays"),
                new StaticCallInfo("of", "List"),
                new StaticCallInfo("formatGovernmentId", "Bird") // nested static method call
        );

        StaticMethodVisitor smv = new StaticMethodVisitor();
        List<StaticCallInfo> receivedCalls = smv.getStaticCalls(path);
        assert(receivedCalls.containsAll(expectedCalls));
        assert(!receivedCalls.contains(new StaticCallInfo("peck", "Bird")));
        assert(!receivedCalls.contains(new StaticCallInfo("println", "PrintStream")));
        assert(!receivedCalls.contains(new StaticCallInfo("hop", "Bird")));
    }

    // Ensures exception is handled when encountering a non-text file (e.g., director)
    @Test
    public void testGracefulNonFileError() throws IOException {
        String path = "src/main/test-repo";

        StaticMethodVisitor smv = new StaticMethodVisitor();
        List<StaticCallInfo> receivedCalls = smv.getStaticCalls(path);
        assert(receivedCalls.isEmpty());
    }
}
