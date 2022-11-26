package kafka.producer.HardWareUsage;

public class TopProcessDetail {
    /*
    ***** Important *****
    FOR USE JSON DAO MUST TAKE DEFAULT CONSTRUCTOR
    ***** Important *****
    */
    private int PID;
    private String COMMAND;
    private float CPUusage;
    private String Time;
    private float Mem;
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

    public TopProcessDetail(int PID, String COMMAND, float CPUusage, String time, float mem, String state) {
        this.PID = PID;
        this.COMMAND = COMMAND;
        this.CPUusage = CPUusage;
        this.Time = time;
        this.Mem = mem;
        this.State = state;
    }

    public int getPID() {
        return PID;
    }

    public TopProcessDetail setPID(int PID) {
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

    public float getCPUusage() {
        return CPUusage;
    }

    public TopProcessDetail setCPUusage(float CPUusage) {
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

    public float getMem() {
        return Mem;
    }

    public TopProcessDetail setMem(float mem) {
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
