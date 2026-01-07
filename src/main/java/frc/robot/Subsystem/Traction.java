// Define o pacote onde esse subsistema está localizado
package frc.robot.Subsystem;

// Imports das classes da REV (Spark MAX)
import com.revrobotics.spark.SparkBase.PersistMode;      // Define se a configuração será salva no controlador
import com.revrobotics.spark.SparkLowLevel.MotorType;    // Define o tipo do motor (brushed ou brushless)
import com.revrobotics.spark.SparkMax;                   // Classe do controlador Spark MAX
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode; // Modo de parada do motor (Brake ou Coast)
import com.revrobotics.spark.config.SparkMaxConfig;      // Classe para configurar o Spark MAX
import com.revrobotics.spark.SparkBase.ResetMode;        // Define como o Spark deve resetar parâmetros

// Imports da WPILib
import edu.wpi.first.wpilibj.drive.DifferentialDrive;    // Classe para controle de tração diferencial
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; // Agrupa motores
import edu.wpi.first.wpilibj2.command.SubsystemBase;     // Classe base de subsistemas no Command-based
import frc.robot.Constants;                              // Classe de constantes do robô

// Declara o subsistema de tração
public class Traction extends SubsystemBase {

        // Variável que indica se o modo turbo está ativo
        public boolean turbo;

        // Instancia os motores Spark MAX do lado direito e esquerdo
        // Os IDs vêm da classe Constants
        // MotorType.kBrushed indica que são motores DC escovados
        SparkMax rightMotorFront = new SparkMax(
                Constants.TractionConstants.rightFrontMotorID, MotorType.kBrushed);

        SparkMax rightMotorback = new SparkMax(
                Constants.TractionConstants.rightBackMotorID, MotorType.kBrushed);

        SparkMax leftMotorback = new SparkMax(
                Constants.TractionConstants.leftFrontMotorID, MotorType.kBrushed);

        SparkMax leftMotorFront = new SparkMax(
                Constants.TractionConstants.leftBackMotorID, MotorType.kBrushed);

        // Objetos de configuração para os motores
        SparkMaxConfig configSparkMotorEsquerda = new SparkMaxConfig();
        SparkMaxConfig configSparkMotorDireita = new SparkMaxConfig();

        // Agrupa os motores do lado esquerdo
        // @SuppressWarnings evita aviso de API antiga
        @SuppressWarnings("removal")
        MotorControllerGroup leftMotorControllerGroup =
                new MotorControllerGroup(leftMotorFront, leftMotorback);

        // Agrupa os motores do lado direito
        @SuppressWarnings("removal")
        MotorControllerGroup rightMotorControllerGroup =
                new MotorControllerGroup(rightMotorFront, rightMotorback);

        // Cria o sistema de tração diferencial
        // Esse objeto cuida do cálculo de velocidade dos motores
        DifferentialDrive differentialDrive =
                new DifferentialDrive(leftMotorControllerGroup, rightMotorControllerGroup);

        // Construtor do subsistema (executa uma vez quando o robô inicia)
        public Traction() {

                /*
                 * Aqui você define se os motores da direita precisam ser invertidos
                 * Isso depende da montagem física do robô
                 *
                 * inverted(true)  -> motor gira ao contrário
                 * inverted(false) -> motor gira normal
                 */

                // Configuração dos motores da direita
                configSparkMotorDireita
                        .inverted(false)                 // Define inversão do motor
                        .idleMode(IdleMode.kBrake);      // Motor freia ao parar

                configSparkMotorDireita.smartCurrentLimit(60); // Limite de corrente (proteção elétrica)

                // Aplica a configuração nos motores da direita
                rightMotorFront.configure(
                        configSparkMotorDireita,
                        ResetMode.kResetSafeParameters,   // Reseta apenas parâmetros seguros
                        PersistMode.kPersistParameters); // Salva no Spark MAX

                rightMotorback.configure(
                        configSparkMotorDireita,
                        ResetMode.kResetSafeParameters,
                        PersistMode.kPersistParameters);

                // Configuração dos motores da esquerda
                configSparkMotorEsquerda
                        .inverted(false)
                        .idleMode(IdleMode.kBrake);

                configSparkMotorEsquerda.smartCurrentLimit(60);

                // Aplica a configuração nos motores da esquerda
                leftMotorFront.configure(
                        configSparkMotorEsquerda,
                        ResetMode.kResetSafeParameters,
                        PersistMode.kPersistParameters);

                leftMotorback.configure(
                        configSparkMotorEsquerda,
                        ResetMode.kResetSafeParameters,
                        PersistMode.kPersistParameters);
        }

        // Método chamado automaticamente pelo WPILib a cada ciclo do robô
        @Override
        public void periodic() {
                // Pode ser usado para telemetria, debug ou lógica contínua
        }

        // Método para dirigir o robô no modo arcade
        // drive = frente/trás
        // turn  = rotação esquerda/direita
        public void arcadeMode(double drive, double turn) {
                differentialDrive.arcadeDrive(drive, +turn);
        }

        // Para completamente os motores
        public void stop() {
                differentialDrive.stopMotor();
        }

        // Ativa ou desativa o modo turbo
        public void ativarTurbo(boolean turbo) {
                this.turbo = turbo;
        }
}
