import Model.*;
import Services.*;
import Storage.*;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String CATALOG_FILE = "catalog.txt";
    private static final String PLAYLIST_FILE = "playlists.txt";

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // --- LOADING DATA ---
        System.out.println("Starting Audio Manager...");
        try {
            List<AudioFile> loadedData = CatalogStorage.load(CATALOG_FILE);
            for (AudioFile item : loadedData) {
                CatalogService.add(item);
            }
            System.out.println(" [OK] Catalog loaded: " + CatalogService.getCatalog().size() + " items");
        } catch (IOException e) {
            System.out.println("Could not load catalog: " + e.getMessage());
        }

        try {
            List<Playlist> loadedPlaylists = PlaylistStorage.load(PLAYLIST_FILE);
            PlaylistService.getPlaylist().addAll(loadedPlaylists);
            System.out.println(" [OK] Playlists loaded: " + loadedPlaylists.size());
        } catch (IOException e) {
            System.out.println("Could not load playlists: " + e.getMessage());
        }

        AlbumService.createAlbums();

        // --- MAIN LOOP ---
        while (true) {
            System.out.println("\n==================================");
            System.out.println("       AUDIO LIBRARY MANAGER      ");
            System.out.println("==================================");

            System.out.println(" ---- BROWSE ----");
            System.out.println("  1. Show All Media");
            System.out.println("  2. Show Albums");
            System.out.println("  3. Search Library");
            System.out.println("  4. Playlists Menu");

            System.out.println(" ---- MANAGE ----");
            System.out.println("  5. Add New Media");
            System.out.println("  6. Remove Media");
            System.out.println("  7. Sorting Options");

            System.out.println(" ----------------");
            System.out.println("  0. Save & Exit");
            System.out.print("\n > Choose option[0-7]: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> showCatalog();
                case "2" -> showAlbums();
                case "3" -> searchMenu();
                case "4" -> playlistMenu();
                case "5" -> addNewItem();
                case "6" -> removeMedia();
                case "7" -> sortCatalog();
                case "0" -> {
                    saveAndExit();
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void showCatalog() {
        System.out.println("\n--- CURRENT CATALOG ---");
        List<AudioFile> list = CatalogService.getCatalog();
        if (list.isEmpty()) {
            System.out.println(" (Catalog is empty)");
        } else {
            for (AudioFile item : list) {
                System.out.println(item);
            }
            System.out.println("-----------------------");
            System.out.println("Total items: " + list.size());
        }
    }

    private static void showAlbums() {
        System.out.println("\n--- ALBUM COLLECTION ---");
        List<Album> albums = AlbumService.getAlbums();

        if (albums.isEmpty()) {
            System.out.println(" (No albums found)");
        } else {
            for (Album a : albums) {
                System.out.println(a);
            }
        }
    }

    private static void addNewItem() {
        System.out.println("\n--- ADD NEW MEDIA ---");

        String title;
        String author;
        String genre;

        while (true) {
            System.out.print(" Title: ");
            title = sc.nextLine();
            if (title.trim().isEmpty()) {
                System.out.println("Invalid title");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print(" Author: ");
            author = sc.nextLine();
            if (author.trim().isEmpty()) {
                System.out.println("Invalid author");
                continue;
            }
            break;
        }
            System.out.print(" Album (press Enter for 'single'): ");
            String album = sc.nextLine();

        while (true) {
            System.out.print(" Genre: ");
            genre = sc.nextLine();
            if (genre.trim().isEmpty()) {
                System.out.println("Invalid genre");
                continue;
            }
            break;
        }

        int year = 0;
        while (true) {
            System.out.print(" Year (1900 - 2026): ");
            String input = sc.nextLine();
            try {
                year = Integer.parseInt(input);
                if (year >= 1900 && year <= 2026) break;
                else System.out.println("  Year must be between 1900 and 2026.");
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }

        int duration = 0;
        while (true) {
            System.out.print(" Duration (seconds): ");
            String input = sc.nextLine();
            try {
                duration = Integer.parseInt(input);
                if (duration > 0 && duration <= 86400) break;
                else System.out.println("  Duration must be between 1s and 86400s.");
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }

        System.out.print(" Type (SONG, AUDIOBOOK, PODCAST): ");
        String typeStr = sc.nextLine().toUpperCase();
        AudioFormat format;
        try {
            format = AudioFormat.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            format = AudioFormat.SONG;
            System.out.println("  Invalid type. Defaulting to SONG.");
        }

        CatalogService.createAndAdd(title, genre, duration, format, author, year, album);
        AlbumService.createAlbums();
        System.out.println("Item added to catalog.");
    }

    private static void removeMedia() {
        System.out.println("\n--- REMOVE MEDIA ---");
        System.out.print(" Enter Title or Author to find item: ");
        String input = sc.nextLine();

        List<AudioFile> matches = CatalogService.findByTitleAndAuthor(input, input);
        int idToRemove = -1;

        if (matches.isEmpty()) {
            System.out.println("No media found matching '" + input + "'");
            return;
        }
        else if (matches.size() == 1) {
            AudioFile item = matches.get(0);
            System.out.println(" Found: " + item);
            System.out.print(" >> Delete this item? (Yes/No): ");
            if (sc.nextLine().equalsIgnoreCase("yes")) {
                idToRemove = item.getId();
            } else {
                System.out.println(" Operation cancelled.");
                return;
            }
        }
        else {
            System.out.println(" Multiple matches found:");
            for (AudioFile m : matches) System.out.println(m);

            System.out.print(" >> Enter ID to delete (or 0 to cancel): ");
            try {
                int inputId = Integer.parseInt(sc.nextLine());
                if (inputId == 0) return;

                for(AudioFile m : matches) {
                    if(m.getId() == inputId) {
                        idToRemove = inputId;
                        break;
                    }
                }
                if (idToRemove == -1) {
                    System.out.println("ID selected is not in the search results.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
                return;
            }
        }

        if (idToRemove != -1) {
            if (CatalogService.remove(idToRemove)) {
                for (Playlist p : PlaylistService.getPlaylist()) {
                    p.remove(idToRemove);
                }
                AlbumService.createAlbums(); // Refresh albums
                System.out.println("Item removed completely.");
            } else {
                System.out.println("Could not remove item.");
            }
        }
    }

    private static void searchMenu() {
        System.out.println("\n--- SEARCH LIBRARY ---");
        System.out.println(" 1. Smart Search (Title/Author)");
        System.out.println(" 2. Filter by Genre");
        System.out.println(" 3. Filter by Year");
        System.out.print(" > Select: ");
        String ch = sc.nextLine();

        List<AudioFile> results = new ArrayList<>();

        switch (ch) {
            case "1" -> {
                System.out.print(" Search query: ");
                String query = sc.nextLine();
                results = CatalogService.findByTitleAndAuthor(query, query);
            }
            case "2" -> {
                System.out.print(" Enter Genre: ");
                String genre = sc.nextLine();
                results = CatalogService.findByGenre(genre);
            }
            case "3" -> {
                System.out.print(" Enter Year: ");
                try {
                    int year = Integer.parseInt(sc.nextLine());
                    results = CatalogService.findByYear(year);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid year.");
                }
            }
            default -> System.out.println("Invalid option.");
        }

        if (results != null && !results.isEmpty()) {
            System.out.println("\n Found " + results.size() + " items:");
            for (AudioFile m : results) System.out.println(m);
        } else {
            System.out.println("No results found.");
        }
    }

    private static void sortCatalog(){
        System.out.println("\n--- SORTING OPTIONS ---");
        System.out.println(" 1. Sort By Title (A-Z)");
        System.out.println(" 2. Sort By Newest (Added)");
        System.out.println(" 3. Sort By Oldest (Added)");
        System.out.println(" 0. Back");
        System.out.print(" > Choose: ");

        String choice = sc.nextLine();
        if(choice.equals("0")) return;

        switch (choice){
            case "1" -> CatalogService.sortByName();
            case "2" -> CatalogService.sortByNewest();
            case "3" -> CatalogService.sortByOldest();
            default ->  System.out.println("Invalid choice.");
        }
        System.out.println("Catalog sorted.");
        showCatalog();
    }

    private static void playlistMenu() {
        while (true) {
            System.out.println("\n--- PLAYLIST MENU ---");
            System.out.println(" 1. Show All Playlists");
            System.out.println(" 2. Create New Playlist");
            System.out.println(" 3. Manage a Playlist (Add/Remove songs)");
            System.out.println(" 0. Back to Main Menu");
            System.out.print(" > Choice: ");
            String ch = sc.nextLine();

            if (ch.equals("0")) break;

            switch (ch) {
                case "1" -> {
                    List<Playlist> all = PlaylistService.getPlaylist();
                    if(all.isEmpty()) System.out.println(" (No playlists created yet)");
                    else for (Playlist p : all) System.out.println(p);
                }
                case "2" -> {
                    System.out.print(" Enter new Playlist Name: ");
                    String name = sc.nextLine();
                    if (PlaylistService.add(new Playlist(name))) {
                        System.out.println("Playlist '" + name + "' created.");
                    } else {
                        System.out.println("A playlist with this name already exists.");
                    }
                }
                case "3" -> managePlaylist();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void managePlaylist() {
        System.out.print(" Enter Playlist Name to open: ");
        String name = sc.nextLine();
        Playlist p = PlaylistService.findByName(name);

        if (p == null) {
            System.out.println(" Playlist not found.");
            return;
        }

        boolean playlistExists = true;
        while (playlistExists) {
            System.out.println("\n >> Managing: [" + p.getTitle() + "]");
            System.out.println(" 1. Show items");
            System.out.println(" 2. Add item (Search)");
            System.out.println(" 3. Remove item");
            System.out.println(" 4. Sort playlist by Title");
            System.out.println(" 5. Search inside Playlist");
            System.out.println(" 6. DELETE Playlist");
            System.out.println(" 0. Back");
            System.out.print(" > Choice: ");
            String ch = sc.nextLine();

            if (ch.equals("0")) break;

            switch (ch) {
                case "1" -> {
                    if (p.getItems().isEmpty()){
                        System.out.println(" (Playlist is empty)");
                    } else {
                        for (AudioFile m : p.getItems()) {
                            System.out.println(m);
                        }
                    }
                }
                case "2" -> {
                    System.out.print(" Enter Title/Author to add: ");
                    String input = sc.nextLine();
                    List<AudioFile> matches = CatalogService.findByTitleAndAuthor(input, input);

                    if (matches.isEmpty()) {
                        System.out.println("No matches found in Catalog.");
                    }
                    else if (matches.size() == 1) {
                        AudioFile item = matches.get(0);
                        System.out.println(" Found: " + item.getTitle() + " by " + item.getAuthor());
                        System.out.print(" Add this? (Yes/No): ");
                        if(sc.nextLine().equalsIgnoreCase("yes")){
                            p.add(item);
                            System.out.println("Added.");
                        }
                    }
                    else {
                        System.out.println(" Multiple matches:");
                        for (AudioFile m : matches) System.out.println(m);
                        System.out.print(" Enter ID to add: ");
                        try {
                            int id = Integer.parseInt(sc.nextLine());
                            AudioFile selected = CatalogService.findById(id);

                            if(selected != null) {
                                p.add(selected);
                                System.out.println("Added: " + selected.getTitle());
                            } else {
                                System.out.println("ID not found.");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input.");
                        }
                    }
                }
                case "3" -> {
                    System.out.print(" Enter Title/Author to remove: ");
                    String input = sc.nextLine();
                    List<AudioFile> matches = p.findByTitleAndAuthor(input, input);
                    if (matches.isEmpty()) System.out.println("Not found in playlist.");
                    else {
                        for (AudioFile m : matches) System.out.println(m);
                        System.out.print(" Enter ID to remove: ");
                        try {
                            int id = Integer.parseInt(sc.nextLine());
                            if (p.remove(id)) System.out.println("Removed.");
                            else System.out.println("ID not found in playlist.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID.");
                        }
                    }
                }
                case "4" -> {
                    p.sortByTitle();
                    System.out.println("Playlist sorted.");
                }
                case "5" -> {
                    System.out.print(" Search query: ");
                    String query = sc.nextLine();
                    List<AudioFile> results = p.findByTitleAndAuthor(query, query);
                    if (results.isEmpty()) System.out.println("No matches.");
                    else for (AudioFile m : results) System.out.println(m);
                }
                case "6" -> {
                    System.out.println(" Are you sure you want to delete this playlist? (Yes/No):");
                    if(sc.nextLine().equalsIgnoreCase("yes")){
                        PlaylistService.remove(p);
                        System.out.println("Playlist deleted.");
                        playlistExists = false;
                    }
                }
            }
        }
    }

    private static void saveAndExit() {
        System.out.println("\nSaving data...");
        try {
            CatalogStorage.save(CatalogService.getCatalog(), CATALOG_FILE);
            PlaylistStorage.save(PlaylistService.getPlaylist(), PLAYLIST_FILE);
            System.out.println("Data saved. Goodbye!");
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }
}