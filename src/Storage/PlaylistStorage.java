package Storage;

import Model.Playlist;
import Services.CatalogService;
import Model.AudioFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistStorage {
    public static void save(List<Playlist> playlists, String file) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Playlist p : playlists) {
                pw.print(p.toFile());
            }
        }
    }

    public static List<Playlist> load(String file) throws IOException {
        List<Playlist> list = new ArrayList<>();
        File f = new File(file);
        if (!f.exists()) {
            f.createNewFile();
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Playlist current = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("PLAYLIST")) {

                    String[] parts = line.split("\\|");
                    if (parts.length > 1) {
                        current = new Playlist(parts[1]);
                        list.add(current);
                    }
                } else {
                    try {
                        int id = Integer.parseInt(line);
                        AudioFile item = CatalogService.findById(id);
                        if (item != null && current != null) {
                            current.add(item);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Skipped corrupted line: " + line);
                    }
                }
            }
        }
        return list;
    }
}
