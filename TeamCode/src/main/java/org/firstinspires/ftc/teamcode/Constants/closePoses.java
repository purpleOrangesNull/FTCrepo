package org.firstinspires.ftc.teamcode.Constants;

import com.pedropathing.localization.Pose;

public class closePoses {

    public static final Pose startPose = new Pose(56.00, 8.00, Math.toRadians(90));

    // MainChain
    public static final Pose mc1 = new Pose(56.00, 8.00);
    public static final Pose mc2 = new Pose(55.54, 10.86);

    // Chain2
    public static final Pose mc3 = new Pose(9.60, 10.29);

    // Chain3
    public static final Pose mc4 = new Pose(56.61, 11.65);

    // Chain4 (curve: mc4 -> ctrl4 -> mc5)
    public static final Pose ctrl4 = new Pose(51.57, 40.79);
    public static final Pose mc5 = new Pose(13.62, 35.58);

    // Chain6
    public static final Pose mc6 = new Pose(60.49, 11.39);
}