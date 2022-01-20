package program.ui;

import program.downloader.Buffer;
import program.downloader.Downloader;

import java.net.MalformedURLException;
import java.util.Arrays;

public class UserInterface {

    /**
     * Приватный конструктор, тк подразумевается, что класс - static.
     */
    private UserInterface() {}

    /**
     * Обработка команд.
     * @param command Команда и аргументы.
     * @return Истина, если ждем следующего ввода команд. Ложь, если программа должна завершиться.
     */
    public static boolean switchCommands(String[] command) {
        // Проверка корректности ввода.
        if (command == null) {
            System.out.println("Fatal error while reading command!");
            return true;
        }
        // Проверка корректности ввода.
        if (command.length == 0) {
            System.out.println("Wrong file names!");
            return true;
        }
        // Обработка команды загрузки.
        if ("/load".equalsIgnoreCase(command[0])) {
            // Проверка корректности ввода.
            if (command.length < 2) {
                System.out.println("Wrong number of files!");
                return true;
            }
            // Скачивание файлов в многопоточном режиме.
            try {
                Downloader.multithreadingDownloading(Arrays.copyOfRange(command, 1, command.length));
            } catch (MalformedURLException ex) {
                System.out.println("Error while reading url: " + ex.getMessage());
            }
            return true;
            // Обработка команды показа скаченных файлов.
        } else if ("/show".equalsIgnoreCase(command[0])) {
            Buffer.printBuffer();
            return true;
            // Выход из программы.
        } else if ("/exit".equalsIgnoreCase(command[0])) {
            return false;
            // Вывод справки команд.
        } else if ("/help".equalsIgnoreCase(command[0])) {
            help();
            return true;
            // Смена пути к папке загрузок.
        } else if ("/dest".equalsIgnoreCase(command[0])) {
            Downloader.switchDestination(command[1]);
            return true;
        }
        // Вывод сообщения об ошибке.
        System.out.println("Wrong command!");
        return true;
    }

    /**
     * Вывод приветственного сообщения.
     */
    public static void firstStartHello() {
        System.out.println("Hello.");
    }

    /**
     * Вывод справки команд.
     */
    public static void help() {
        System.out.println("/load url - скачать файл с данным url.");
        System.out.println("/load url1 url2 ... - скачать файлы с данными url1, url2 и тд.");
        System.out.println("/show - показать список скаченных файлов.");
        System.out.println("/exit - выход из программы.");
        System.out.println("/help - показать справку команд.");
        System.out.println("/dist PATH - указать путь к папке загрузки.");
    }
}
