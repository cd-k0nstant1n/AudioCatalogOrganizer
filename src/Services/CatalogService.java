package Services;

import Model.AudioFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CatalogService {
    private List<AudioFile> catalog = new ArrayList<>();

    public List<AudioFile> getCatalog() {return catalog;}

    public void add(AudioFile file) {catalog.add(file);}

    public boolean remove(int id) {
        return catalog.removeIf( i -> i.getId() == id);
    }

    public int getNextInt(){
        int max = 0;
        for (AudioFile file : catalog) {
            if (file.getYear() > max) {
                max = file.getYear();
            }
        }
        return max+1;
    }

    public AudioFile getById(int id) {
        for (AudioFile file : catalog) {
            if (file.getId() == id) {
                return file;
            }
        }
        return null;
    }

    public List<AudioFile> getByName(String name) {
        List<AudioFile> list = new ArrayList<>();
        String searchName = name.toLowerCase();
        for (AudioFile item : catalog) {
            if(item.getTitle().toLowerCase().contains(searchName)){
                list.add(item);
            }
        }
        return list;
    }

    public List<AudioFile> getByAuthor(String author) {
        List<AudioFile> list = new ArrayList<>();
        String searchAuthor = author.toLowerCase();
        for (AudioFile item : catalog) {
            if(item.getAuthor().toLowerCase().contains(searchAuthor)){
                list.add(item);
            }
        }
        return list;
    }

    public List<AudioFile> getByGenre(String genre) {
        List<AudioFile> list = new ArrayList<>();
        String searchGenre = genre.toLowerCase();
        for (AudioFile item : catalog) {
            if(item.getGenre().toLowerCase().contains(searchGenre)){
                list.add(item);
            }
        }
        return list;
    }

    public List<AudioFile> getByYear(int year) {
        List<AudioFile> list = new ArrayList<>();
        for (AudioFile item : catalog) {
            if(item.getYear() == year){
                list.add(item);
            }
        }
        return list;
    }

    public List<AudioFile> getByNameAndAuthor(String name, String author) {
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

    public void sortByName() {
        Collections.sort(catalog, new Comparator<AudioFile>(){
        @Override
        public int compare(AudioFile item1, AudioFile item2) {
            return item1.getTitle().compareToIgnoreCase(item2.getTitle());
            }
        });
    }

}
