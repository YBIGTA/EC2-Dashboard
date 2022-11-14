package kafka.producer.HardWareUsage;

public class TopProcessDetail {
    /*
    ***** Important *****
    FOR USE JSON DAO MUST TAKE DEFAULT CONSTRUCTOR
    ***** Important *****
    */
    private String PID;
    private String COMMAND;
    private String CPUusage;
    private String Time;
    private String Mem;
    private String State;



    @Override
    public String toString() {
        return "TopProcessDetail{" +
                "PID='" + PID + '\'' +
                ", COMMAND='" + COMMAND + '\'' +
                ", CPUusage='" + CPUusage + '\'' +
                ", Time='" + Time + '\'' +
                ", Mem='" + Mem + '\'' +
                ", State='" + State + '\'' +
                '}';
    }

    public TopProcessDetail(String PID, String COMMAND, String CPUusage, String time, String mem, String state) {
        this.PID = PID;
        this.COMMAND = COMMAND;
        this.CPUusage = CPUusage;
        this.Time = time;
        this.Mem = mem;
        this.State = state;
    }

    public String getPID() {
        return PID;
    }

    public TopProcessDetail setPID(String PID) {
        this.PID = PID;
        return this;
    }

    public String getCOMMAND() {
        return COMMAND;
    }

    public TopProcessDetail setCOMMAND(String COMMAND) {
        this.COMMAND = COMMAND;
        return this;
    }

    public String getCPUusage() {
        return CPUusage;
    }

    public TopProcessDetail setCPUusage(String CPUusage) {
        this.CPUusage = CPUusage;
        return this;
    }

    public String getTime() {
        return Time;
    }

    public TopProcessDetail setTime(String time) {
        Time = time;
        return this;
    }

    public String getMem() {
        return Mem;
    }

    public TopProcessDetail setMem(String mem) {
        Mem = mem;
        return this;
    }

    public String getState() {
        return State;
    }

    public TopProcessDetail setState(String state) {
        State = state;
        return this;
    }
}
