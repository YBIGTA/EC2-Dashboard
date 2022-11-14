package kafka.consumer.HardWareUsage;

public class TotalCpuDetail {
    /*
    ***** Important *****
    FOR USE JSON DAO MUST TAKE DEFAULT CONSTRUCTOR
    ***** Important *****
    */
    private String User;

    private String System;

    @Override
    public String toString() {
        return "TotalCpuDetail{" +
                "User='" + User + '\'' +
                ", System='" + System + '\'' +
                '}';
    }

    public TotalCpuDetail() {
    }

    public TotalCpuDetail(String user, String system) {
        User = user;
        System = system;
    }

    public String getUser() {
        return User;
    }

    public TotalCpuDetail setUser(String user) {
        User = user;
        return this;
    }

    public String getSystem() {
        return System;
    }

    public TotalCpuDetail setSystem(String system) {
        System = system;
        return this;
    }
}
