package kafka.producer.HardWareUsage;

public class TotalMemDetail {
    private String Used;
    private String Unused;

    @Override
    public String toString() {
        return "TotalMemDetail{" +
                "Used='" + Used + '\'' +
                ", Unused='" + Unused + '\'' +
                '}';
    }

    public TotalMemDetail(String used, String unused) {
        Used = used;
        Unused = unused;
    }

    public String getUsed() {
        return Used;
    }

    public TotalMemDetail setUsed(String used) {
        Used = used;
        return this;
    }

    public String getUnused() {
        return Unused;
    }

    public TotalMemDetail setUnused(String unused) {
        Unused = unused;
        return this;
    }
}
