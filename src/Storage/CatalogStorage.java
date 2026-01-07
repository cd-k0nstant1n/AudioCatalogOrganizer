package Storage;

import Model.AudioFile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogStorage {

    public static void save(List<AudioFile> catalog, String fileName) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            for (AudioFile item : catalog) {
                writer.println(item.toFile());
            }
        }
    }

    public static List<AudioFile> load(String fileName) throws IOException {
        List<AudioFile> list = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            file.createNewFile();
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    try {
                        AudioFile item = AudioFile.fromFile(line);
                        list.add(item);
                    } catch (Exception e) {
                        System.out.println("Пропуснат повреден ред: " + line);
                    }
                }
            }
        }

        return list;
    }
}