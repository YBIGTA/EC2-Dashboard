package kafka.consumer.AvgUsage;


import kafka.consumer.HardWareUsage.HardWareUsageDAO;

public class AvgUsage {
    public static float cnt =1;
    private static float TotalUserCPU=0;
    private static float TotalSysCPU=0;
    private static float TotalUsedMem=0;

    private static boolean ERROR = false;

    private String EC2_Name;
    private float AvgUserCpu;
    private float AvgSysCpu;
    private float MaxDiskRead;
    private float MaxDiskWrite;
    private float AvgUsedMem;

    private boolean error;

    public void parser(HardWareUsageDAO hardWareUsageDAO){
        System.out.println(hardWareUsageDAO.getCPU().getUser());
        TotalUserCPU += hardWareUsageDAO.getCPU().getUser();
        TotalSysCPU += hardWareUsageDAO.getCPU().getSystem();
        TotalUsedMem += hardWareUsageDAO.getMEM().getUsed();

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

    public static float getCnt() {
        return cnt;
    }

    public static void setCnt(float cnt) {
        AvgUsage.cnt = cnt;
    }

    public static float getTotalUserCPU() {
        return TotalUserCPU;
    }

    public static void setTotalUserCPU(float totalUserCPU) {
        TotalUserCPU = totalUserCPU;
    }

    public static float getTotalSysCPU() {
        return TotalSysCPU;
    }

    public static void setTotalSysCPU(float totalSysCPU) {
        TotalSysCPU = totalSysCPU;
    }

    public static float getTotalUsedMem() {
        return TotalUsedMem;
    }

    public static void setTotalUsedMem(float totalUsedMem) {
        TotalUsedMem = totalUsedMem;
    }

    public static boolean isERROR() {
        return ERROR;
    }

    public static void setERROR(boolean ERROR) {
        AvgUsage.ERROR = ERROR;
    }

    public String getEC2_Name() {
        return EC2_Name;
    }

    public AvgUsage setEC2_Name(String EC2_Name) {
        this.EC2_Name = EC2_Name;
        return this;
    }

    public float getAvgUserCpu() {
        return AvgUserCpu;
    }

    public AvgUsage setAvgUserCpu(float avgUserCpu) {
        AvgUserCpu = avgUserCpu;
        return this;
    }

    public float getAvgSysCpu() {
        return AvgSysCpu;
    }

    public AvgUsage setAvgSysCpu(float avgSysCpu) {
        AvgSysCpu = avgSysCpu;
        return this;
    }

    public float getMaxDiskRead() {
        return MaxDiskRead;
    }

    public AvgUsage setMaxDiskRead(float maxDiskRead) {
        MaxDiskRead = maxDiskRead;
        return this;
    }

    public float getMaxDiskWrite() {
        return MaxDiskWrite;
    }

    public AvgUsage setMaxDiskWrite(float maxDiskWrite) {
        MaxDiskWrite = maxDiskWrite;
        return this;
    }

    public float getAvgUsedMem() {
        return AvgUsedMem;
    }

    public AvgUsage setAvgUsedMem(float avgUsedMem) {
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
