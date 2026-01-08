package Model;

public class Album extends AudioCollection {
    private String artist;

    public Album(String title, String artist) {
        super(title);
        this.artist = artist;
    }

    public String getArtist() {return artist;}

    @Override
    public String toString() {
        String timeString = AudioFile.formatTime(duration);

        StringBuilder sb = new StringBuilder("Album: " + title + " | " + artist + " | - Total: " + timeString + "\n");
        for (AudioFile s : items) {
            sb.append("  ").append(s).append("\n");
        }
        return sb.toString();
    }
}
