package program.downloader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DownloaderTest {

    @Test
    void switchDestination() {
        String path = "hello";
        Downloader.switchDestination(path);
        assertEquals(path ,Downloader.getPath().get());
    }

    @Test
    void switchDestinationShouldNotChangePathIfItIsIncorrect() {
        String path = null;
        Downloader.switchDestination(path);
        assertTrue(Downloader.getPath().isEmpty());

        String path2 = "";
        Downloader.switchDestination(path2);
        assertTrue(Downloader.getPath().isEmpty());
    }
}