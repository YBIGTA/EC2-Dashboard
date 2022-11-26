package kafka.consumer.HardWareUsage;

public class TotalCpuDetail {
    /*
    ***** Important *****
    FOR USE JSON DAO MUST TAKE DEFAULT CONSTRUCTOR
    ***** Important *****
    */
    private float User;

    private float System;

    @Override
    public String toString() {
        return "TotalCpuDetail{" +
                "User='" + User + '\'' +
                ", System='" + System + '\'' +
                '}';
    }

    public TotalCpuDetail() {
    }

    public TotalCpuDetail(float user, float system) {
        User = user;
        System = system;
    }

    public float getUser() {
        return User;
    }

    public TotalCpuDetail setUser(float user) {
        User = user;
        return this;
    }

    public float getSystem() {
        return System;
    }

    public TotalCpuDetail setSystem(float system) {
        System = system;
        return this;
    }
}
