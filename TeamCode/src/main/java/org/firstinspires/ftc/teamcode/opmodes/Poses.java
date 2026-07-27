package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.localization.Pose;

public class Poses {

    public static final Pose startPose = new Pose(56.00, 8.00, Math.toRadians(90));

    // MainChain
    public static final Pose mc1 = new Pose(56.00, 8.00);
    public static final Pose mc2 = new Pose(56.23, 9.84);

    // Chain2 (curve: mc2 -> ctrl2 -> mc3)
    public static final Pose ctrl2 = new Pose(51.00, 33.00);
    public static final Pose mc3 = new Pose(17.62, 36.01);

    // Chain3
    public static final Pose mc4 = new Pose(55.80, 8.56);

    // Chain4
    public static final Pose mc5 = new Pose(5.98, 7.98);

    // Chain5
    public static final Pose mc6 = new Pose(56.37, 8.37);

    // Chain6
    public static final Pose mc7 = new Pose(54.00, 39.00);
}