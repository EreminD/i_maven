package com.inzhenerka.cli;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "test", mixinStandardHelpOptions = true,
        description = "Prints the checksum (SHA-256 by default) of a file to STDOUT.")
public class PrintTest implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "The file whose checksum to calculate.")
    private String text;

    @CommandLine.Option(names = {"-a", "--algorithm"}, description = "MD5, SHA-1, SHA-256, ...")
    private String moreText = "Hello world!";

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        System.out.println("Test!");
        System.out.println(text);
        System.out.println(moreText);
        return 0;
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new PrintTest()).execute(args);
        System.exit(exitCode);
    }
}
