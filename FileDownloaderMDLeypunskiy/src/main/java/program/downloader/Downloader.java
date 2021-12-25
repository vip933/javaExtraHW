package program.downloader;

import program.utils.Tuple;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Date;

/**
 * Класс скачиваемого объекта.
 */
public class Downloader implements Runnable {

    private final java.net.URL URL;

    public Downloader(String URL) throws MalformedURLException {
        this.URL = new URL(URL);
    }

    /**
     * Создание и запуск потоков.
     * @param names Имена скачиваемых файлов.
     * @throws MalformedURLException Ошибка.
     */
    public static void multithreadingDownloading(String[] names) throws MalformedURLException {
        Thread[] threads = new Thread[names.length];
        for (int i = 0; i < names.length; ++i) {
            // Создание нового потока на основе скачиваемого файла.
            threads[i] = new Thread(new Downloader(names[i]));
            threads[i].start();
        }
    }

    @Override
    public void run() {
        // Открытие потока скачки.
        try (InputStream inputStream = URL.openStream()) {

            // Debug.
            // System.out.println("hello from " + Thread.currentThread().getName());

            // Получение имени файла (имени файла, а не url).
            String name = URL.getPath().replace("/", "");
            // Создание папки для загрузок.
            File directory = new File("./downloads");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // Создание нового файла.
            File file = new File("./downloads/" + name);
            // Проверка на то, что такой файл уже может быть существует.
            if (file.exists()) {
                System.out.println("Файл с таким названием уже существует: " + name);
                return;
            }
            // Запись скаченного файла в наш локальный файл.
            Files.copy(inputStream, file.toPath());

            // Запись в список скаченных файлов.
            Buffer.getContainer().add(new Tuple<>(name, new Date()));

            System.out.println(name + " - successfully downloaded!");
        } catch (IOException exception) {
            System.out.println("Error while working with io: " + exception.getMessage());
        }
    }
}
