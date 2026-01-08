package Services;

import Model.AudioFile;
import Model.AudioFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CatalogService {
    private static List<AudioFile> catalog = new ArrayList<>();

    public static List<AudioFile> getCatalog() {return catalog;}

    public static void add(AudioFile file) {catalog.add(file);}

    public static boolean isDublicate(String title, String author, String album) {
        if (album == null || album.trim().isEmpty() || album.equalsIgnoreCase("none")) {
            album = "none";
        } else {
            album = album.trim();
        }

        for (AudioFile item : catalog) {
            if (item == null) {
                continue;
            }
            if (item.getTitle() == null || item.getAuthor() == null) {
                continue;
            }
            boolean titleMatch = title.equalsIgnoreCase(item.getTitle());
            boolean authorMatch = author.equalsIgnoreCase(item.getAuthor());
            boolean albumMatch = album.equalsIgnoreCase(item.getAlbum());

            if (titleMatch && authorMatch && albumMatch) {
                return true;
            }
        }
        return false;
    }


    public static void createAndAdd(String title, String genre, int duration, AudioFormat format, String author, int year, String album) {
        if (isDublicate(title,author, album)){
            System.out.println("Item already exists");
            System.out.println("    | " + title + " by " + author + " in " + album + " |");
            return;
        }
        int newId = getNextInt();
        AudioFile newItem = new AudioFile(newId, title, author, genre, duration, year, album, format);
        catalog.add(newItem);
    }

    public static boolean remove(int id) {
        return catalog.removeIf( i -> i.getId() == id);
    }

    public static int getNextInt(){
        int max = 0;
        for (AudioFile file : catalog) {
            if (file.getId() > max) {
                max = file.getId();
            }
        }
        return max+1;
    }

    public static AudioFile findById(int id) {
        for (AudioFile file : catalog) {
            if (file.getId() == id) {
                return file;
            }
        }
        return null;
    }

    public static List<AudioFile> findByName(String name) {
        List<AudioFile> list = new ArrayList<>();
        String searchName = name.toLowerCase();
        for (AudioFile item : catalog) {
            if(item.getTitle().toLowerCase().contains(searchName)){
                list.add(item);
            }
        }
        return list;
    }


    public static List<AudioFile> findByAuthor(String author) {
        List<AudioFile> list = new ArrayList<>();
        String searchAuthor = author.toLowerCase();
        for (AudioFile item : catalog) {
            if(item.getAuthor().toLowerCase().contains(searchAuthor)){
                list.add(item);
            }
        }
        return list;
    }

    public static List<AudioFile> findByGenre(String genre) {
        List<AudioFile> list = new ArrayList<>();
        String searchGenre = genre.toLowerCase();
        for (AudioFile item : catalog) {
            if(item.getGenre().toLowerCase().contains(searchGenre)){
                list.add(item);
            }
        }
        return list;
    }

    public static List<AudioFile> findByYear(int year) {
        List<AudioFile> list = new ArrayList<>();
        for (AudioFile item : catalog) {
            if(item.getYear() == year){
                list.add(item);
            }
        }
        return list;
    }

    public static List<AudioFile> findByTitleAndAuthor(String name, String author) {
        List<AudioFile> list = new ArrayList<>();
        String searchName = name.toLowerCase();
        String searchAuthor = author.toLowerCase();
        for (AudioFile item : catalog) {
            if(item.getTitle().toLowerCase().contains(searchName) || item.getAuthor().toLowerCase().contains(searchAuthor)){
                list.add(item);
            }
        }
        return list;
    }

    public static void sortByNewest() {
        catalog.sort(new Comparator<AudioFile>() {
            @Override
            public int compare(AudioFile item1, AudioFile item2) {
                return Integer.compare(item2.getId(), item1.getId());
            }
        });
    }

    public static void sortByOldest() {
        catalog.sort(new Comparator<AudioFile>() {
            @Override
            public int compare(AudioFile item1, AudioFile item2) {
                return Integer.compare(item1.getId(), item2.getId());
            }
        });
    }

    public static void sortByName() {
        Collections.sort(catalog, new Comparator<AudioFile>(){
        @Override
        public int compare(AudioFile item1, AudioFile item2) {
            return item1.getTitle().compareToIgnoreCase(item2.getTitle());
            }
        });
    }

}
