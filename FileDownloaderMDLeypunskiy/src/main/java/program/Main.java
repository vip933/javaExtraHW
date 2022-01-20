package program;

import program.downloader.Downloader;
import program.ui.UserInterface;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UserInterface.firstStartHello();
        UserInterface.help();
        String[] nextCommand;
        // Крутим пока не остановят.
        boolean toContinue = true;
        while (toContinue) {
            // Считываем команду и аргументы.
            nextCommand = scanner.nextLine().split(" ");
            // Выбираем что сделать.
            toContinue = UserInterface.switchCommands(nextCommand);
        }
        Downloader.shutDownExecutorService();
    }
}
