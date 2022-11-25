package kafka.consumer.AvgUsage;


import kafka.consumer.HardWareUsage.HardWareUsageDAO;
import kafka.consumer.HardWareUsage.TotalCpuDetail;
import kafka.consumer.HardWareUsage.TotalDiskDetail;
import kafka.consumer.HardWareUsage.TotalMemDetail;

import java.sql.SQLOutput;


public class AvgUsage {
    public static double cnt =1;
    private static double TotalUserCPU=0;
    private static double TotalSysCPU=0;
    private static double TotalUsedMem=0;

    private static boolean ERROR = false;

    private String EC2_Name;
    private double AvgUserCpu;
    private double AvgSysCpu;
    private String MaxDiskRead;
    private String MaxDiskWrite;
    private double AvgUsedMem;

    private boolean error;

    public void parser(HardWareUsageDAO hardWareUsageDAO){
        System.out.println(hardWareUsageDAO.getCPU().getUser());
        TotalUserCPU += Double.parseDouble(hardWareUsageDAO.getCPU().getUser());
        TotalSysCPU += Double.parseDouble(hardWareUsageDAO.getCPU().getSystem());
        TotalUsedMem += Double.parseDouble(hardWareUsageDAO.getMEM().getUsed());

        this.setAvgSysCpu(TotalSysCPU/cnt);
        this.setAvgUserCpu(TotalUserCPU/cnt);
        this.setAvgUsedMem(TotalUsedMem/cnt);
        this.setMaxDiskRead(hardWareUsageDAO.getDISK().getRead());
        this.setMaxDiskWrite(hardWareUsageDAO.getDISK().getWrite());
        this.setEC2_Name(hardWareUsageDAO.getEC2Number());
        if(this.getAvgUserCpu()>2.5){
            this.setError(true);
        }
        else{
            this.setError(false);
        }

        System.out.println(" ______________________________________");
        System.out.println(hardWareUsageDAO.toString());
        System.out.println("TotalUserCPU " + (TotalUserCPU));
        System.out.println("TotalSysCPU " + (TotalSysCPU));
        System.out.println("MaxDiskRead " + (this.MaxDiskRead));
        System.out.println("MaxDiskWrite " + (this.MaxDiskWrite));
        System.out.println("ERROR" + this.error);
        cnt++;

    }

    @Override
    public String toString() {
        return "AvgUsage{" +
                "AvgUserCpu=" + AvgUserCpu +
                ", AvgSysCpu=" + AvgSysCpu +
                ", MaxDiskRead='" + MaxDiskRead + '\'' +
                ", MaxDiskWrite='" + MaxDiskWrite + '\'' +
                ", AvgUsedMem=" + AvgUsedMem +
                '}';
    }

    public String getEC2_Name() {
        return EC2_Name;
    }

    public AvgUsage setEC2_Name(String EC2_Name) {
        this.EC2_Name = EC2_Name;
        return this;
    }

    public double getAvgUserCpu() {
        return AvgUserCpu;
    }

    public AvgUsage setAvgUserCpu(double avgUserCpu) {
        AvgUserCpu = avgUserCpu;
        return this;
    }

    public double getAvgSysCpu() {
        return AvgSysCpu;
    }

    public AvgUsage setAvgSysCpu(double avgSysCpu) {
        AvgSysCpu = avgSysCpu;
        return this;
    }

    public String getMaxDiskRead() {
        return MaxDiskRead;
    }

    public AvgUsage setMaxDiskRead(String maxDiskRead) {
        MaxDiskRead = maxDiskRead;
        return this;
    }

    public String getMaxDiskWrite() {
        return MaxDiskWrite;
    }

    public AvgUsage setMaxDiskWrite(String maxDiskWrite) {
        MaxDiskWrite = maxDiskWrite;
        return this;
    }

    public double getAvgUsedMem() {
        return AvgUsedMem;
    }

    public AvgUsage setAvgUsedMem(double avgUsedMem) {
        AvgUsedMem = avgUsedMem;
        return this;
    }

    public boolean isError() {
        return error;
    }

    public AvgUsage setError(boolean error) {
        this.error = error;
        return this;
    }
}
