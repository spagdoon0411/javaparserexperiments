package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaticMethodVisitor extends VoidVisitorAdapter<List<StaticCallInfo>> {

    /***
     * Parses the file at the provided path for static calls and collects results
     * as StaticCallInfo objects. Prints the names of methods for which calling
     * classes could not be resolved (rather than throwing an exception).
     *
     * @param path The absolute path of the file to search.
     * @return A list of StaticCallInfo objects
     */
    public List<StaticCallInfo> getStaticCalls(String path) throws IOException {
        this.parserSetup();

        JavaParser parser = this.parserSetup();

        Optional<CompilationUnit> cuOpt = parser.parse(Files.newInputStream(Paths.get(path))).getResult();

        if(cuOpt.isPresent()) {
            CompilationUnit cu = cuOpt.get();
            ArrayList<StaticCallInfo> calls = new ArrayList<>();
            this.visit(cu, calls);
            return calls;
        }
        else {
            System.err.println("Failed to parse file at " + path);
            return null;
        }
    }

    /***
     * Produces a JavaParser with a desired configuration.
     */
    private JavaParser parserSetup() {
        JavaParser jp = new JavaParser();

        CombinedTypeSolver cts = new CombinedTypeSolver();

        cts.add(new ReflectionTypeSolver());

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(cts);

        jp.getParserConfiguration().setSymbolResolver(symbolSolver);

        return jp;
    }

    /***
     * Visit method overridden from JavaParser's VoidVisitorAdapter. Not intended
     * for direct use (i.e., call getStaticCalls instead).
     *
     * @param mc See JavaParser documentation.
     * @param infoList See JavaParser documentation.
     */
    @Override
    public void visit(MethodCallExpr mc, List<StaticCallInfo> infoList) {
        super.visit(mc, infoList);

        try {
            ResolvedMethodDeclaration rmd = mc.resolve();
            if(rmd.isStatic())
                infoList.add(new StaticCallInfo(mc.getName().asString(), rmd.getClassName()));
        } catch (Exception e)
        {
            System.err.println("Unable to resolve calling class for " + mc.getName().asString());
        }
    }
}
