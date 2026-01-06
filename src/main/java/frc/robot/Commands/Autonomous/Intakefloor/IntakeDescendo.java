
  package frc.robot.Commands.Autonomous.Intakefloor;
  
  import edu.wpi.first.wpilibj2.command.Command;
  import frc.robot.Subsystem.IntakeFloor;
  
  public class IntakeDescendo extends Command {
  private final IntakeFloor intake;
  private final double velocidade = -0.3;
  
  public IntakeDescendo(IntakeFloor intake) {
  this.intake = intake;
  addRequirements(intake);
  }
  
  @Override
  public void initialize() {
  
  }
  
  @Override
  public void execute() {
  intake.setIntakeMarlonVelocidade(velocidade);
  }
  
  @Override
  public void end(boolean interrupted) {
  intake.intakeMarlonMotor.stopMotor();
  }
  
  @Override
  public boolean isFinished() {
  return intake.noLimiteInferior();
  }
  }
 