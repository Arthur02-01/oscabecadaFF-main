package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger; 
import frc.robot.Subsystem.Traction;
import frc.robot.Commands.AtivarTurbo;
import frc.robot.Commands.Controller;
import frc.robot.Subsystem.IntakeFloor;

import frc.robot.Commands.Autonomous.Intakefloor.IntakeDescendo;
import frc.robot.Commands.Autonomous.Intakefloor.IntakeGirando;
import frc.robot.Commands.Autonomous.Intakefloor.IntakeSubindo;


public class RobotContainer {

  // Subsystems
  public final Traction traction = new Traction();
  public final IntakeFloor intakeFloor = new IntakeFloor();

  // Controls
  private final XboxController xbox1 = new XboxController(0);
  
    private final XboxController xbox2 = new XboxController(1);
   

  // Buttons (baseados no controle Xbox)
  private final JoystickButton btnTurbo = new JoystickButton(xbox1, 1);

  // button do sub

  
    private final JoystickButton btnIntakeIn2 = new JoystickButton(xbox2, 6);
    private final JoystickButton btnIntakeOut2 = new JoystickButton(xbox2, 5);
   

  public RobotContainer() {
    configureBindings();

    // Define que o drive padrão do robô será o comando DriveWithJoystick
    traction.setDefaultCommand(new Controller(traction, xbox1));
  }

  private void configureBindings() {

    // Botão A ativa/desativa modo turbo
    btnTurbo.onTrue(new AtivarTurbo(traction));
    
      btnIntakeIn2.whileTrue(new IntakeGirando(intakeFloor, 0.5));
      btnIntakeOut2.whileTrue(new IntakeGirando(intakeFloor, -0.9));
      
      new Trigger(() -> xbox2.getLeftY() < -0.2)
      .whileTrue(new IntakeSubindo(intakeFloor));
      new Trigger(() -> xbox2.getLeftY() > 0.4)
      .whileTrue(new IntakeDescendo(intakeFloor));
     
  }

  public Command getAutonomousCommand() {
    getAutonomousCommand();
    return Commands.print("No autonomous command configured");
  }
}