package Services;

import Model.Album;
import Model.AudioFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumService {
    private static List<Album> albums = new ArrayList<>();

    public static List<Album> getAlbums() { return albums; }

    public static void createAlbums() {
        albums.clear();

        List<AudioFile> catalog = CatalogService.getCatalog();
        Map<String, Album> map = new HashMap<>();
        for (AudioFile song : catalog) {
            String albumName = song.getAlbum();

            if (albumName == null) continue;

            String cleanName = albumName.trim();

            if (cleanName.equals("single")) {
                continue;
            }

            if (!map.containsKey(cleanName)) {
                Album newAlbum = new Album(cleanName, song.getAuthor());
                map.put(cleanName, newAlbum);
                albums.add(newAlbum);
            }

            map.get(cleanName).add(song);
        }
    }

}

