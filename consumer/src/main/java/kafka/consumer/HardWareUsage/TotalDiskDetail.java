package kafka.consumer.HardWareUsage;

public class TotalDiskDetail {
    /*
    ***** Important *****
    FOR USE JSON DAO MUST TAKE DEFAULT CONSTRUCTOR
    ***** Important *****
    */
    private float Read;
    private float Write;

    public TotalDiskDetail() {
    }

    @Override
    public String toString() {
        return "TotalDiskDetail{" +
                "Read='" + Read + '\'' +
                ", Write='" + Write + '\'' +
                '}';
    }

    public float getRead() {
        return Read;
    }

    public TotalDiskDetail setRead(float read) {
        Read = read;
        return this;
    }

    public float getWrite() {
        return Write;
    }

    public TotalDiskDetail setWrite(float write) {
        Write = write;
        return this;
    }

    public TotalDiskDetail(float read, float write) {
        Read = read;
        Write = write;
    }
}
