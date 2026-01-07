package Services;

import Model.AudioFile;
import Model.Playlist;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService {
    private List<Playlist> playlists = new ArrayList<>();

    public List<Playlist> getPlaylists() { return playlists; }

    public boolean add(Playlist playlist) {
        if (getByName(playlist.getTitle()) != null) {
            return false;
        }
        playlists.add(playlist);
        return true;
    }

    public void remove(Playlist playlist) { playlists.remove(playlist); }

    public Playlist getByName(String name) {
        String searchName = name.toLowerCase();
        for (Playlist item : playlists) {
            if(item.getTitle().toLowerCase().contains(searchName)){
                return item;
            }
        }
        return null;
    }

}
