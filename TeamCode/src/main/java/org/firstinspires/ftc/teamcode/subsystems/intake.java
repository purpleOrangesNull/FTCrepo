package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Telem;

public class intake {
    private DcMotor intakeMotor;
public intakeState state;

public enum intakeState {
    ON, OFF
}
public intake (DcMotor motor){
    intakeMotor = motor;
    intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    state = intakeState.OFF;
}
public void setState(intakeState state) {
    this.state = state;
    switch (state) {
        case ON:
            intakeMotor.setPower(1.0);
            break;
        case OFF:
            intakeMotor.setPower(0.0);
    break;
    }

}

    public void periodic(){
    setState(state);
}
    public intakeState getState(){
        return state;
    }
public void telemetry (){
    Telem.addLine("intake");
    Telem.addData("State", state);
}
}

