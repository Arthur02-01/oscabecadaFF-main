
  package frc.robot.Commands.Autonomous.Intakefloor;
  
  import edu.wpi.first.wpilibj2.command.Command;
  import frc.robot.Subsystem.IntakeFloor;
  
  public class IntakeGirando extends Command {
  private final IntakeFloor intake;
  private final double velocidadeGirando;
  
  public IntakeGirando(IntakeFloor intake, double velocidadeGirando) {
  this.intake = intake;
  this.velocidadeGirando = velocidadeGirando;
  addRequirements(intake);
  }
  
  @Override
  public void execute() {
  intake.setRoller(velocidadeGirando);
  }
  
  @Override
  public void end(boolean interrupted) {
  intake.stopRoller();
  }
  
  @Override
  public boolean isFinished() {
  return false;
  }
  }
 