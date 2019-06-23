import java.io.*;

public class IdFiles {
    public static void main(String[] arguments) {
        try {
            File folder = new File(".");
            File[] contents = folder.listFiles();
            for (int i = 0; i < contents.length; i++) {
                String name = contents[i].getName();
                if (name.indexOf(".mp3") == -1) {
                    continue;
                }
                FileInputStream file = new FileInputStream(contents[i]);
                int size = (int)contents[i].length();
                file.skip(size - 128);
                byte[] last128 = new byte[128];
                file.read(last128);
                String id3 = new String(last128);
                String tag = id3.substring(0, 3);
                if (tag.equals("TAG")) {
                    // Retrieve ID3 information from MP3 file
                    String title = id3.substring(3, 32);
                    title = title.trim();
                    String artist = id3.substring(33, 62);
                    artist = artist.trim();
                    String album = id3.substring(63, 91);
                    album = album.trim();
                    file.close();
                    // Create new filename to use
                    StringBuffer newName = new StringBuffer();
                    newName.append(artist);
                    if (title.length() > 0) {
                        newName.append(" - " + title);
                    }
                    if (album.length() > 0) {
                        newName.append(" (" + album + ")");
                    }
                    File newFile = new File(newName.toString() + ".mp3");
                    // Rename file and display its new name
                    contents[i].renameTo(newFile);
                    System.out.println(newFile.getName());
                } else {
                    file.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Error -- " + e.toString());
        }
    }
}
