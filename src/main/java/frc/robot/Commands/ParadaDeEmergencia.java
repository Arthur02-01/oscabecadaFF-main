package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Subsystem.IntakeFloor;

public class ParadaDeEmergencia extends InstantCommand {
    public ParadaDeEmergencia (IntakeFloor intakeFloor){
        super(() ->{
            intakeFloor.Parar();
        }, intakeFloor);
    }
    
}
