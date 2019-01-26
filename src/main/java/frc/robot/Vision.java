package frc.robot;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

//TODO: fix NetworkTables
public class Vision{

    public NetworkTable table;
    public NetworkTableEntry tx, ty, ta, tv;

    public Vision() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        //NetworkTableEntry tlong = table.getEntry("tlong");
        //NetworkTableEntry tshort = table.getEntry("tshort");
    }

    //read values periodically

    
   // double x = tx.getDouble(0.0);
    //double y = ty.getDouble(0.0);
    //double area = ta.getDouble(0.0);
    //double longSide = tlong.getDouble(0.0);
    //double shortSide = tshort.getDouble(0.0);

    //public void visionTest(){
        //post to smart dashboard periodically
        //SmartDashboard.putNumber("LimelightX", x);
        //SmartDashboard.putNumber("LimelightY", y);
        //SmartDashboard.putNumber("LimelightArea", area);
        //SmartDashboard.putNumber("LimeloghtLongSide", longSide);
        //SmartDashboard.putNumber("LimelightShortSide", shortSide);
    //}
}

