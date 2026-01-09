// Define o pacote onde esse subsistema está localizado
package frc.robot.Subsystem;

// Imports das classes da REV (Spark MAX)
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;

// Imports da WPILib
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Encoder;

import frc.robot.Constants;

// Declara o subsistema de tração
public class Traction extends SubsystemBase {

    // VARIÁVEIS 

    public boolean turbo;

    // Motores
    private SparkMax rightMotorFront =
            new SparkMax(Constants.TractionConstants.rightFrontMotorID, MotorType.kBrushed);

    private SparkMax rightMotorBack =
            new SparkMax(Constants.TractionConstants.rightBackMotorID, MotorType.kBrushed);

    private SparkMax leftMotorFront =
            new SparkMax(Constants.TractionConstants.leftFrontMotorID, MotorType.kBrushed);

    private SparkMax leftMotorBack =
            new SparkMax(Constants.TractionConstants.leftBackMotorID, MotorType.kBrushed);

    // Configurações
    private SparkMaxConfig configSparkMotorEsquerda = new SparkMaxConfig();
    private SparkMaxConfig configSparkMotorDireita = new SparkMaxConfig();

    // Grupos de motores
    @SuppressWarnings("removal")
    private MotorControllerGroup leftMotorControllerGroup =
            new MotorControllerGroup(leftMotorFront, leftMotorBack);

    @SuppressWarnings("removal")
    private MotorControllerGroup rightMotorControllerGroup =
            new MotorControllerGroup(rightMotorFront, rightMotorBack);

    // Drive
    private DifferentialDrive differentialDrive =
            new DifferentialDrive(leftMotorControllerGroup, rightMotorControllerGroup);

    // ENCODERS 

    private Encoder leftEncoder =
            new Encoder(
                    Constants.TractionConstants.leftEncoderChannelA,
                    Constants.TractionConstants.leftEncoderChannelB);

    private Encoder rightEncoder =
            new Encoder(
                    Constants.TractionConstants.rightEncoderChannelA,
                    Constants.TractionConstants.rightEncoderChannelB);

    // CONSTRUTOR

    public Traction() {

        // Configuração motores da direita
        configSparkMotorDireita
                .inverted(false)
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(60);

        rightMotorFront.configure(
                configSparkMotorDireita,
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        rightMotorBack.configure(
                configSparkMotorDireita,
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        // Configuração motores da esquerda
        configSparkMotorEsquerda
                .inverted(false)
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(60);

        leftMotorFront.configure(
                configSparkMotorEsquerda,
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        leftMotorBack.configure(
                configSparkMotorEsquerda,
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        // ===== CONFIGURAÇÃO DOS ENCODERS
        double wheelDiameterMeters = 0.1524; // 6 polegadas
        int pulsesPerRevolution = 1024;      // AJUSTE se necessário

        double distancePerPulse =
                (Math.PI * wheelDiameterMeters) / pulsesPerRevolution;

        leftEncoder.setDistancePerPulse(distancePerPulse);
        rightEncoder.setDistancePerPulse(distancePerPulse);

        // Inverta se necessário
        rightEncoder.setReverseDirection(true);
    }

    // MÉTODOS

    @Override
    public void periodic() {
        // opcional: telemetria
    }

    public void arcadeMode(double drive, double turn) {
        differentialDrive.arcadeDrive(drive, turn);
    }

    public void stop() {
        differentialDrive.stopMotor();
    }

    public void ativarTurbo(boolean turbo) {
        this.turbo = turbo;
    }

    // ENCODER API 
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public double getAverageDistance() {
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
    }
}