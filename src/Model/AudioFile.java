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

    @Override
    public String toString() {
        return String.format("[%d] %s | Title: %s | Author: %s | Album: %s | %s | %d | %ds",
                id, format, title, author, album, genre, year, duration);
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
