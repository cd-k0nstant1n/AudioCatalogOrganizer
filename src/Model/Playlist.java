package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Playlist extends AudioCollection {

    public Playlist(String title) {
        super(title);
    }

    @Override
    public void add(AudioFile file) {
        super.add(file);
    }

    @Override
    public List<AudioFile> getItems() {
        return items;
    }

    public boolean remove(int id) {
        AudioFile toRemove = null;
        for (AudioFile file : this.items) {
            if (file.getId() == id) {
                toRemove = file;
                break;
            }
        }

        if (toRemove != null) {
            this.items.remove(toRemove);
            duration -= toRemove.getDuration();
            return true;
        }
        return false;
    }

    public void sortByTitle() {
        items.sort(new Comparator<AudioFile>() {
            @Override
            public int compare(AudioFile item1, AudioFile item2) {
                return item1.getTitle().compareToIgnoreCase(item2.getTitle());
            }
        });
    }

    public List<AudioFile> findByTitleAndAuthor(String name, String author) {
        List<AudioFile> list = new ArrayList<>();
        String searchName = name.toLowerCase();
        String searchAuthor = author.toLowerCase();

        for (AudioFile item : items) {
            if(item.getTitle().toLowerCase().contains(searchName) || item.getAuthor().toLowerCase().contains(searchAuthor)){
                list.add(item);
            }
        }
        return list;
    }


    @Override
    public String toString() {
        return title + " (" + items.size() + " items) " + AudioFile.formatTime(duration);
    }

    public String toFile(){
        StringBuilder sb = new StringBuilder("PLAYLIST|" + title + "\n");
        for (AudioFile i : items) sb.append(i.getId()).append("\n");
        return sb.toString();
    }
}