package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Playlist extends AudioCollection {
    private String title;
    private int duration;
    private List<AudioFile> playlist = new ArrayList<>();

    public Playlist(String title) {
        super(title);
    }

    public String getTitle() {return this.title;}
    public int getDuration() {return this.duration;}
    public List<AudioFile> getPlaylist() {return this.playlist;}

    public void add(AudioFile file) {
        duration += file.getDuration();
        playlist.add(file);
    }

    public boolean remove(int id) {
        AudioFile toRemove = null;
        for (AudioFile file : this.playlist) {
            if (file.getId() == id) {
                toRemove = file;
                break;
            }
        }

        if (toRemove != null) {
            this.playlist.remove(toRemove);
            duration -= toRemove.getDuration();
            return true;
        }
        return false;
    }

    public void sortByTitle() {
        playlist.sort(new Comparator<AudioFile>() {
            @Override
            public int compare(AudioFile item1, AudioFile item2) {
                return item1.getTitle().compareToIgnoreCase(item1.getTitle());
            }
        });
    }

    @Override
    public String toString() {
        return title + " (" + playlist.size() + "items) " + duration + "s";
    }

    public String toFile(){
        StringBuilder sb = new StringBuilder("PLAYLIST|" + title + "\n");
        for (AudioFile i : playlist) sb.append(i.getId()).append("\n");
        return sb.toString();
    }
}
