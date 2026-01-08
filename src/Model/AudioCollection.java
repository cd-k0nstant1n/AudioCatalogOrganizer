package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class AudioCollection {
    protected String title;
    protected int duration;
    protected List<AudioFile> items = new ArrayList<>();

    public AudioCollection(String title) {
        this.title = title;
        this.duration = 0;
    }

    public void add(AudioFile item) {
        items.add(item);
        duration += item.getDuration();
    }

    public void sortByName() {
        items.sort(new Comparator<AudioFile>() {
            @Override
            public int compare(AudioFile item1, AudioFile item2) {
                return item1.getTitle().compareToIgnoreCase(item2.getTitle());
            }
        });
    }

    public String getTitle() {return title;}
    public int getDuration() {return duration;}
    public List<AudioFile> getItems() {return items;}
}
