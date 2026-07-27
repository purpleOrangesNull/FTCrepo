package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.localization.Pose;

public class farPoses {

    public static final Pose startPose = new Pose(14.72, 124.97, Math.toRadians(130));

    // MainChain
    public static final Pose mc1 = new Pose(14.72, 124.97);
    public static final Pose mc2 = new Pose(57.40, 83.21);

    // Chain2
    public static final Pose mc3 = new Pose(14.51, 82.49);

    // Chain3
    public static final Pose mc4 = new Pose(58.94, 82.61);

    // Chain4 (curve: mc4 -> ctrl4 -> mc5)
    public static final Pose ctrl4 = new Pose(50.71, 53.99);
    public static final Pose mc5 = new Pose(12.12, 58.02);

    // Chain5
    public static final Pose mc6 = new Pose(59.58, 82.13);

    // Chain6 (curve: mc6 -> ctrl6 -> mc7)
    public static final Pose ctrl6 = new Pose(58.44, 30.22);
    public static final Pose mc7 = new Pose(13.49, 34.07);

    // Chain7
    public static final Pose mc8 = new Pose(59.42, 82.88);

    // Chain8
    public static final Pose mc9 = new Pose(50.26, 53.71);
}