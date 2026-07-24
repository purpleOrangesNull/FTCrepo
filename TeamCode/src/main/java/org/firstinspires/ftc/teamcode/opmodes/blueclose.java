package org.firstinspires.ftc.teamcode.OpModes;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.DashboardPoseTracker;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.MyTelemetry;
@Autonomous
public class CloseBlueAuto extends LinearOpMode {
    Robot robot;
    private DashboardPoseTracker dashboardPoseTracker;
    private int pathState;
    private ElapsedTime waitTimer = new ElapsedTime();
    public PathChain Chain1;
    public PathChain Chain2;
    public PathChain Chain3;
    public PathChain Chain4;
    public PathChain MainChain;
    @Override
    public void runOpMode() throws InterruptedException {
        MyTelemetry.init(telemetry);
        robot = new Robot(hardwareMap);
        pathState = 0;
        Robot.follower.setStartingPose(Poses.startPose);
        Paths();
        waitForStart();
        while (opModeIsActive()) {
            robot.update();
            switch (pathState) {
                case 0:
                    Robot.follower.followPath(Chain1, true);
                    pathState = 1;
                    waitTimer.reset();
                    break;
                case 1:
                    if (!Robot.follower.isBusy() && waitTimer.milliseconds() >= 1000) {
                        Robot.follower.followPath(Chain2, true);
                        pathState = 2;
                        waitTimer.reset();
                    }
                    break;
                case 2:
                    if (waitTimer.milliseconds() >= 1000 && !Robot.follower.isBusy()) {
                        Robot.follower.followPath(Chain3, true);
                        pathState = 3;
                        waitTimer.reset();
                    }
                    break;
                case 3:
                    if (!Robot.follower.isBusy() && waitTimer.milliseconds() >= 1000) {
                        Robot.follower.followPath(Chain4, true);
                        pathState = 4;
                    }
                    break;
                case 4:
                    if (!Robot.follower.isBusy()) {
                        pathState = -1;
                    }
                    break;
            }
            robot.update();
        }
    }
    public void Paths() {
        Chain1 = Robot.follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(25, 125),
                                new Point(55, 100)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(140))
                .build();
        Chain2 = Robot.follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(55.000, 100.000),
                                new Point(60.000, 80.000),
                                new Point(35, 83.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(35, 83.000),
                                new Point(55.000, 100.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                .build();
        Chain3 = Robot.follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(55.000, 100.000),
                                new Point(60.000, 56.000),
                                new Point(35, 59.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(35, 59.000),
                                new Point(55.000, 100.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                .build();
        Chain4 = Robot.follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(55.000, 100.000),
                                new Point(60.000, 32.000),
                                new Point(35, 35.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(35, 35.000),
                                new Point(55.000, 100.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                .build();
        MainChain = Robot.follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(56.000, 8.000),
                                new Point(56.914, 82.862)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                .addPath(
                        new BezierCurve(
                                new Point(56.914, 82.862),
                                new Point(45.422, 82.343),
                                new Point(12.099, 82.452)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(12.099, 82.452),
                                new Point(57.159, 82.424)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                .addPath(
                        new BezierLine(
                                new Point(57.159, 82.424),
                                new Point(34.936, 58.311)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(34.936, 58.311),
                                new Point(12.585, 58.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(12.585, 58.000),
                                new Point(56.584, 82.500)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                .addPath(
                        new BezierLine(
                                new Point(56.584, 82.500),
                                new Point(37.957, 34.996)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(37.957, 34.996),
                                new Point(13.715, 35.102)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .addPath(
                        new BezierLine(
                                new Point(13.715, 35.102),
                                new Point(37.749, 35.085)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                .addPath(
                        new BezierLine(
                                new Point(37.749, 35.085),
                                new Point(57.151, 82.640)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(135))
                .addPath(
                        new BezierLine(
                                new Point(57.151, 82.640),
                                new Point(10.121, 61.152)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                .build();
    }
}