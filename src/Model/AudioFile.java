package Model;

public class AudioFile {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int duration;
    private int year;
    private String album;
    private AudioFormat format;

    public AudioFile(int id, String title, String author, String genre, int duration, int year, String album, AudioFormat format) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.duration = duration;
        this.year = year;
        if(album == null) album = "single";
        else{
            album = album.trim();
            if (album.isEmpty()||album.equalsIgnoreCase("none")) {
                album = "single";
            }
        }
        this.album = album;
        this.format = format;
    }

    public int getId() { return id; }
    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public String getGenre() {return genre;}
    public int getDuration() {return duration;}
    public int getYear() {return year;}
    public String getAlbum() {return album;}
    public AudioFormat getFormat() {return format;}

    public static String formatTime(int totalSeconds) {
        if (totalSeconds <= 0) return "00:00";

        long days = totalSeconds / 86400;
        long remaining = totalSeconds % 86400;
        long hours = remaining / 3600;
        remaining %= 3600;
        long minutes = remaining / 60;
        long seconds = remaining % 60;

        if (days > 0) {
            return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
        }
        else if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | Title: %s | Author: %s | Album: %s | %s | %d | %s",
                id, format, title, author, album, genre, year, formatTime(duration));
    }

    public String toFile() {
        return id + "|" + format + "|" + title + "|" + author + "|" + album + "|" +
                duration + "|" + genre + "|" + year;
    }

    public static AudioFile fromFile(String line) {
        String[] p = line.split("\\|");
        return new AudioFile(
                Integer.parseInt(p[0]),
                p[2],
                p[3],
                p[6],
                Integer.parseInt(p[5]),
                Integer.parseInt(p[7]),
                p[4],
                AudioFormat.valueOf(p[1])
        );
    }
}
