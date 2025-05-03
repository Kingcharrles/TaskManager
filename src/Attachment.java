public class Attachment {
    // add files, screen caps, videos
    private String filename;
    private byte[] data;

    public Attachment(String filename, byte[] data) {
        this.filename = filename;
        this.data = data;
    }
}
