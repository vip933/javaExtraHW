package program.downloader;

import program.utils.Tuple;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс скачиваемого объекта.
 */
public class Downloader implements Runnable {

    private final java.net.URL URL;
    private static Optional<String> path = Optional.empty();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public static Optional<String> getPath() {
        return path;
    }

    public Downloader(String URL) throws MalformedURLException {
        this.URL = new URL(URL);
    }

    /**
     * Завершение работы executorService.
     */
    public static void shutDownExecutorService() {
        executorService.shutdown();
    }

    /**
     * Создание и запуск потоков.
     * @param names Имена скачиваемых файлов.
     * @throws MalformedURLException Ошибка.
     */
    public static void multithreadingDownloading(String[] names) throws MalformedURLException {
        for (String name : names) {
            executorService.submit(new Downloader(name));
        }
    }

    /**
     * Смена директории загрузки скаченных файлов.
     * @param newDestination Новый путь.
     */
    public static void switchDestination(String newDestination) {
        if (newDestination == null || newDestination.isEmpty()) {
            System.out.println("Incorrect path!");
            return;
        }
        path = Optional.of(newDestination);
        System.out.println("Successfully changed directory!");
    }

    /**
     *
     * @param path Путь к директории загрузок.
     * @param name Имя файла.
     * @return Полный путь к файлу.
     * @throws IllegalArgumentException Неверно заданный путь.
     */
    private static Path getPathOfFile(String path, String name) throws IllegalArgumentException {
        // Создание папки для загрузок.
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Создание нового файла.
        File file = new File(path + "/" + name);
        // Проверка на то, что такой файл уже может быть существует.
        if (file.exists()) {
            throw new IllegalArgumentException("Файл с таким названием уже существует: " + name);
        }
        // Отдаем путь к созданному файлу.
        return file.toPath();
    }

    @Override
    public void run() {
        // Открытие потока скачки.
        try (InputStream inputStream = URL.openStream()) {

             // Debug.
             System.out.println("hello from " + Thread.currentThread().getName());

            // Получение имени файла (имени файла, а не url).
            String name = URL.getPath().replace("/", "");

            // Проверка, что пользователь выбрал папку, если не выбрал, скачиваем в стандартную.
            if (path.isEmpty()) {
                path = Optional.of("./downloads");
            }

            try {
                // Запись скаченного файла в наш локальный файл.
                Files.copy(inputStream, getPathOfFile(path.get(), name));
                // Запись в список скаченных файлов.
                Buffer.getContainer().add(new Tuple<>(name, new Date()));
                System.out.println(name + " - successfully downloaded!");
            } catch (IllegalArgumentException ex) {
                System.out.println("error: " + ex.getMessage());
            }
        } catch (IOException exception) {
            System.out.println("Error while working with io: " + exception.getMessage());
        }
    }
}
