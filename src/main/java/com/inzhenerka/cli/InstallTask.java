package com.inzhenerka.cli;

import com.inzhenerka.config.Configuration;
import com.inzhenerka.config.ConfigurationProvider;
import com.inzhenerka.dependencies.DependencyManager;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "install", description = "Installing deps")
public class InstallTask implements Callable<Integer> {

    @Override
    public Integer call() throws IOException {
        Configuration configuration = ConfigurationProvider.load();
        System.out.printf("Выгружаем список зависимостей:%d", configuration.getDependencies().size());
        DependencyManager.load(configuration);

        return 0;
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new InstallTask()).execute(args);
        System.exit(exitCode);
    }
}
