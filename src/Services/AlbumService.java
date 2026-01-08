package Services;

import Model.Album;
import Model.AudioFile;
import Services.CatalogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumService {
    private static List<AudioFile> albums = new ArrayList<>();

    public static List<AudioFile> getAlbums() { return albums; }

    public static void createAlbums() {
        albums.clear();

        List<AudioFile> catalog = CatalogService.getCatalog();
        Map<String, Album> map = new HashMap<>();
    }

}

