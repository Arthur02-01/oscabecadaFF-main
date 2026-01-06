package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystem.Traction;

public class AtivarTurbo extends Command{
    public Traction traction;
    public AtivarTurbo(Traction traction) {
        this.traction = traction;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(traction);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if(traction.turbo == false){
        traction.ativarTurbo(true);
        } else {
            traction.ativarTurbo(false);
        }
        SmartDashboard.putBoolean("Turbo: ", traction.turbo);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute(){

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}

