package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Telem;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.intake;

import java.util.List;

public class Robot {
private DcMotor  frontLeft, frontRight, backLeft, backRight;
    private DcMotor intakeMotor;
    private List <LynxModule> hubs;
    public org.firstinspires.ftc.teamcode.subsystems.Drivetrain drivetrain;    public intake intake;
    private ElapsedTime loopTimer = new ElapsedTime();




   private double loopTime=0;











    public Robot(HardwareMap hm) {   frontLeft   = hm.get(DcMotor.class, "front_left");
        frontRight  = hm.get(DcMotor.class, "front_right");
        backLeft    = hm.get(DcMotor.class, "back_left");
        backRight   = hm.get(DcMotor.class, "back_right");
        intakeMotor = hm.get(DcMotor.class, "intake");

        drivetrain = new Drivetrain(frontLeft, frontRight, backLeft, backRight);
        intake     = new intake(intakeMotor);


        hubs = hm.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        loopTimer.reset();
    }





    public void update (boolean mode){
        loopTime= loopTimer.milliseconds();
        loopTimer.reset();
        double hz = 1000.0 / loopTime;
        for (LynxModule hub:hubs){
            hub.clearBulkCache();
        }
        Telem.addLine("=== LOOP ===");
        Telem.addData("Loop Time (ms)", String.format("%.1f", loopTime));
        drivetrain.telemetry();

        intake.telemetry();
        Telem.update();}

}

