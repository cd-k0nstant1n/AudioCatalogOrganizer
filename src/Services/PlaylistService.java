package Services;

import Model.AudioFile;
import Model.Playlist;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService {
    private static List<Playlist> playlists = new ArrayList<>();

    public static List<Playlist> getPlaylist() { return playlists; }

    public static boolean add(Playlist playlist) {
        if (findByName(playlist.getTitle()) != null) {
            return false;
        }
        playlists.add(playlist);
        return true;
    }

    public static void remove(Playlist playlist) { playlists.remove(playlist); }

    public static Playlist findByName(String name) {
        String searchName = name.toLowerCase();
        for (Playlist item : playlists) {
            if(item.getTitle().toLowerCase().equals(searchName)){
                return item;
            }
        }
        return null;
    }

}
