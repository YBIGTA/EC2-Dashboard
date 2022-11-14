package kafka.consumer.HardWareUsage;

public class TotalDiskDetail {
    /*
    ***** Important *****
    FOR USE JSON DAO MUST TAKE DEFAULT CONSTRUCTOR
    ***** Important *****
    */
    private String Read;
    private String Write;

    @Override
    public String toString() {
        return "TotalDiskDetail{" +
                "Read='" + Read + '\'' +
                ", Write='" + Write + '\'' +
                '}';
    }

    public TotalDiskDetail() {
    }

    public String getRead() {
        return Read;
    }

    public TotalDiskDetail setRead(String read) {
        Read = read;
        return this;
    }

    public String getWrite() {
        return Write;
    }

    public TotalDiskDetail setWrite(String write) {
        Write = write;
        return this;
    }

    public TotalDiskDetail(String read, String write) {
        Read = read;
        Write = write;
    }
}
