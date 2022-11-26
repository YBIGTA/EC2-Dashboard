package kafka.consumer.HardWareUsage;

public class TotalMemDetail {
    /*
    ***** Important *****
    FOR USE JSON DAO MUST TAKE DEFAULT CONSTRUCTOR
    ***** Important *****
    */
    private float Used;
    private float Unused;

    public TotalMemDetail() {
    }

    @Override
    public String toString() {
        return "TotalMemDetail{" +
                "Used='" + Used + '\'' +
                ", Unused='" + Unused + '\'' +
                '}';
    }

    public TotalMemDetail(float used, float unused) {
        Used = used;
        Unused = unused;
    }

    public float getUsed() {
        return Used;
    }

    public TotalMemDetail setUsed(float used) {
        Used = used;
        return this;
    }

    public float getUnused() {
        return Unused;
    }

    public TotalMemDetail setUnused(float unused) {
        Unused = unused;
        return this;
    }
}
