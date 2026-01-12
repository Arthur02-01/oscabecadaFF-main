// Define o pacote principal do robô
package frc.robot;

// Importa a classe do controle Xbox
import edu.wpi.first.wpilibj.XboxController;

// Imports do sistema Command-based
import edu.wpi.first.wpilibj2.command.Command;    // Interface de comandos

// Imports para botões e gatilhos
import edu.wpi.first.wpilibj2.command.button.JoystickButton; // Botões físico       // Gatilhos baseados em condição

// Imports dos subsistemas
import frc.robot.Subsystem.Traction;     // Subsistema de tração
import frc.robot.Subsystem.IntakeFloor;  // Subsistema do intake

// Imports dos comandos
import frc.robot.Commands.AtivarTurbo;   // Comando que ativa/desativa o turbo
import frc.robot.Commands.Controller;    // Comando padrão de direção
import frc.robot.Commands.Autonomous.Intakefloor.IntakeDescendo;
import frc.robot.Commands.Autonomous.Intakefloor.IntakeGirando;
import frc.robot.Commands.Autonomous.Intakefloor.IntakeSubindo;
import frc.robot.Commands.Autonomous.Tracao.AndarEncoder;

// Classe responsável por:
// - Criar subsistemas
// - Criar controles
// - Fazer os bindings (botões → comandos)
public class RobotContainer {

  // ===== SUBSISTEMAS =====

  // Subsistema de tração do robô
  public final Traction traction = new Traction();

  // Subsistema do intake de chão
  public final IntakeFloor intakeFloor = new IntakeFloor();

  // ===== CONTROLES =====

  // Controle principal (driver)
  private final XboxController xbox1 = new XboxController(0);

  // Controle secundário (operador)
  private final XboxController xbox2 = new XboxController(1);

  // ===== BOTÕES =====

  // Botão A do controle xbox1 (ID 1)
  // Usado para ativar o turbo
  private final JoystickButton btnTurbo = new JoystickButton(xbox1, 1);

  // Botões do controle xbox2 para o intake
  private final JoystickButton btnIntakeIn2  = new JoystickButton(xbox2, 6);
  private final JoystickButton btnIntakeOut2 = new JoystickButton(xbox2, 5);

  // Construtor do RobotContainer
  public RobotContainer() {

    // Configura os botões e gatilhos
    configureBindings();

    // Define o comando padrão do subsistema de tração
    // Esse comando roda automaticamente enquanto nenhum outro usa o subsistema
    traction.setDefaultCommand(
        new Controller(traction, xbox1)
    );
  }

  // Método onde todos os botões e gatilhos são configurados
  private void configureBindings() {

    // Quando o botão A for pressionado, ativa o comando de turbo
    btnTurbo.onTrue(new AtivarTurbo(traction));

    // Enquanto o botão RB estiver pressionado, o intake gira para dentro
    btnIntakeIn2.whileTrue(
        new IntakeGirando(intakeFloor, 0.5)
    );

    // Enquanto o botão LB estiver pressionado, o intake gira para fora
    btnIntakeOut2.whileTrue(
        new IntakeGirando(intakeFloor, -0.9)
    );

    // Gatilho baseado no eixo Y do analógico esquerdo do xbox2
    // Se puxar para cima além de -0.2, o intake sobe
    new edu.wpi.first.wpilibj2.command.button.Trigger(() -> xbox2.getLeftY() < -0.2).whileTrue(new IntakeSubindo(intakeFloor));
    /*new Trigger(() -> xbox2.getLeftY() < -0.2)
        .whileTrue(new IntakeSubindo(intakeFloor));*/

    // Se empurrar o analógico para baixo além de 0.4, o intake desce
    new edu.wpi.first.wpilibj2.command.button.Trigger(() -> xbox2.getLeftY() > 0.4).whileTrue(new IntakeDescendo(intakeFloor));
    /*new Trigger(() -> xbox2.getLeftY() > 0.4)
        .whileTrue(new IntakeDescendo(intakeFloor));*/
  }
    // comando autonomo matheus
  public Command getAutonomousCommand() {
      return new AndarEncoder(traction, 0.5, 0.02);
  }  

}