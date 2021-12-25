package program.downloader;

import program.utils.Tuple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Buffer {

    // Список скаченных файлов и времени скачивания.
    private static final List<Tuple<String, Date>> container = new ArrayList<>();

    private Buffer() {}

    public static List<Tuple<String, Date>> getContainer() {
        return container;
    }

    /**
     * Вывод списка скаченных файлов.
     */
    public static void printBuffer() {
        for (var item : container) {
            System.out.println("Name: " + item.getFirst() + ", downloaded: " + item.getSecond());
        }
    }
}
