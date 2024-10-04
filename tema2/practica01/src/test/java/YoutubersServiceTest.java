import main.Youtuber;
import main.YoutuberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class YoutuberServiceTest {

    private YoutuberService service;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        // Crear un archivo temporal con los datos
        tempFile = Files.createTempFile("youtubers", ".csv");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write("Name,StartDate,NumVideos,NumFollowers\n");
            writer.write("Youtuber1,2012-06-12,500,100000\n");
            writer.write("Youtuber2,2013-04-18,1000,150000\n");
            writer.write("Youtuber3,2013-08-25,300,80000\n");
            writer.write("Youtuber4,2014-09-01,750,50000\n");
        }

        service = new YoutuberService();
        service.loadData(tempFile.toString());
    }

    @Test
    void testGetYoutuberWithTheMostFollowers() {
        Youtuber youtuber = service.getYoutuberWithTheMostFollowers();
        assertNotNull(youtuber);
        assertEquals("Youtuber2", youtuber.getName());
    }

    @Test
    void testGetAverageNumVideos() {
        double averageVideos = service.getAverageNumVideos();
        assertEquals(637.5, averageVideos, 0.01);
    }

    @Test
    void testGetYoutubersOf2013() {
        List<Youtuber> youtubersOf2013 = service.getYoutubersOf2013();
        assertEquals(2, youtubersOf2013.size());
        List<String> names = youtubersOf2013.stream().map(Youtuber::getName).toList();
        assertTrue(names.containsAll(Arrays.asList("Youtuber2", "Youtuber3")));
    }

    @Test
    void testGetTop3YoutubersWithTheHighestIncome() {
        List<Youtuber> top3 = service.getTop3YoutubersWithTheHighestIncome();
        assertEquals(3, top3.size());
        assertEquals("Youtuber2", top3.get(0).getName());
    }

    @Test
    void testCalculateTotalIncome() {
        List<Youtuber> top3 = service.getTop3YoutubersWithTheHighestIncome();
        double totalIncome = service.calculateTotalIncome(top3);
        assertTrue(totalIncome > 0);
    }

    @Test
    void testGroupYoutubersByYear() {
        Map<String, List<Youtuber>> groupedByYear = service.groupYoutubersByYear();
        assertEquals(3, groupedByYear.size());  // Tres años: 2012, 2013, 2014
        assertEquals(2, groupedByYear.get("2013").size());  // Dos youtubers en 2013
    }

}
