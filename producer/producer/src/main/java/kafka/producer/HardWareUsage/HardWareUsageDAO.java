package kafka.producer.HardWareUsage;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class HardWareUsageDAO {
    /*
    TotalCpuDetail
        CPU HardWare Total Usage
        This is divided into [User, System]
    TotalMemDetail
        MEM HardWare Total Usage
        This is divided into [Used, Unused]
    TotalDiskDetail
        Disk is HardWare Total Usage
        This is divided into [Read, Write]
    TopProcessDetail
        This is COMMAND TOP result where ranked top 5
        This is consist of [PID, COMMAND, CPUusage, Time, Mem, State]
     */
    private TotalCpuDetail CPU;
    private TotalMemDetail MEM;
    private TotalDiskDetail DISK;
    private List<TopProcessDetail> TopRateProcess;

    public HardWareUsageDAO() {
        this.TopRateProcess = new ArrayList<TopProcessDetail>();
    }

    public HardWareUsageDAO(TotalCpuDetail CPU, TotalMemDetail MEM, TotalDiskDetail DISK, List<TopProcessDetail> topRateProcess) {
        this.CPU = CPU;
        this.MEM = MEM;
        this.DISK = DISK;
        this.TopRateProcess = new ArrayList<TopProcessDetail>();
    }

    @Override
    public String toString() {
        return "HardWareUsageDAO{" +
                "CPU=" + CPU +
                ", MEM=" + MEM +
                ", DISK=" + DISK +
                ", TopRateProcess=" + TopRateProcess +
                '}';
    }

    public TotalCpuDetail getCPU() {
        return CPU;
    }

    public HardWareUsageDAO setCPU(TotalCpuDetail CPU) {
        this.CPU = CPU;
        return this;
    }

    public TotalMemDetail getMEM() {
        return MEM;
    }

    public HardWareUsageDAO setMEM(TotalMemDetail MEM) {
        this.MEM = MEM;
        return this;
    }

    public TotalDiskDetail getDISK() {
        return DISK;
    }

    public HardWareUsageDAO setDISK(TotalDiskDetail DISK) {
        this.DISK = DISK;
        return this;
    }

    public List<TopProcessDetail> getTopRateProcess() {
        return TopRateProcess;
    }

    public HardWareUsageDAO setTopRateProcess(ArrayList<TopProcessDetail> topRateProcess) {
        TopRateProcess = topRateProcess;
        return this;
    }

    public void addTopRateProcess(TopProcessDetail tpd) {
        this.TopRateProcess.add(tpd);
    }
}
