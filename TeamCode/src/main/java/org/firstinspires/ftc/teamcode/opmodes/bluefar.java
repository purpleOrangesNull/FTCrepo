package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants.Constants;
import org.firstinspires.ftc.teamcode.Constants.farPoses;
import org.firstinspires.ftc.teamcode.Telem;

@Autonomous(name = "Far Blue Auto", group = "Autonomous")
public class bluefar extends OpMode {
    private ElapsedTime timer;
    private ElapsedTime waitTimer;
    private Follower follower;
    private Paths paths;
    private int pathState;

    @Override
    public void init() {
        timer = new ElapsedTime();
        waitTimer = new ElapsedTime();
        Telem.init(telemetry);
        follower = Constants.createFollower(hardwareMap);
        paths = new Paths(follower);
        pathState = 0;
    }

    @Override
    public void start() {
        timer.reset();

        follower.followPath(paths.MainChain);
    }

    @Override
    public void loop() {
        follower.update();
        pathState = autonomousPathUpdate();

        Telem.addData("POSE", follower.getPose());
        Telem.addData("TIMER", timer.seconds());
        Telem.addData("Path State", pathState);
        Telem.update();
    }

    public int autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                // MainChain: ends facing 110, start the pause once it finishes
                if (!follower.isBusy()) {
                    waitTimer.reset();
                    return 1;
                }
                return 0;
            case 1:
                // Pause 2s at heading 110 before Chain2
                if (waitTimer.seconds() >= 2.0) {
                    follower.followPath(paths.Chain2);
                    return 2;
                }
                return 1;
            case 2:
                // Chain2: ends facing 180, no pause needed
                if (!follower.isBusy()) {
                    follower.followPath(paths.Chain3);
                    return 3;
                }
                return 2;
            case 3:
                // Chain3: ends facing 110, start the pause once it finishes
                if (!follower.isBusy()) {
                    waitTimer.reset();
                    return 4;
                }
                return 3;
            case 4:
                // Pause 2s at heading 110 before Chain4
                if (waitTimer.seconds() >= 2.0) {
                    follower.followPath(paths.Chain4);
                    return 5;
                }
                return 4;
            case 5:
                // Chain4: ends facing 180, no pause needed
                if (!follower.isBusy()) {
                    follower.followPath(paths.Chain6);
                    return 6;
                }
                return 5;
            case 6:
                // Chain6: ends facing 110, start the pause once it finishes
                if (!follower.isBusy()) {
                    waitTimer.reset();
                    return 7;
                }
                return 6;
            case 7:
                // Pause 2s at heading 110, then done
                if (waitTimer.seconds() >= 2.0) {
                    return -1;
                }
                return 7;
            default:
                return pathState;
        }
    }

    public static class Paths {
        public PathChain MainChain;
        public PathChain Chain2;
        public PathChain Chain3;
        public PathChain Chain4;
        public PathChain Chain6;

        public Paths(Follower follower) {
            follower.setStartingPose(farPoses.startPose);

            MainChain = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc1, farPoses.mc2))
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(110))
                    .build();

            Chain2 = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc2, farPoses.mc3))
                    .setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(180))
                    .build();

            Chain3 = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc3, farPoses.mc4))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(110))
                    .build();

            Chain4 = follower.pathBuilder()
                    .addPath(new BezierCurve(farPoses.mc4, farPoses.ctrl4, farPoses.mc5))
                    .setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(180))
                    .build();

            Chain6 = follower.pathBuilder()
                    .addPath(new BezierLine(farPoses.mc5, farPoses.mc6))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(110))
                    .build();
        }
    }
}