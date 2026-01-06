package frc.robot.Subsystem;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Traction extends SubsystemBase {
        public boolean turbo;

        SparkMax rightMotorFront = new SparkMax(Constants.TractionConstants.rightFrontMotorID, MotorType.kBrushed);
        SparkMax rightMotorback = new SparkMax(Constants.TractionConstants.rightBackMotorID, MotorType.kBrushed);
        SparkMax leftMotorback = new SparkMax(Constants.TractionConstants.leftFrontMotorID, MotorType.kBrushed);
        SparkMax leftMotorFront = new SparkMax(Constants.TractionConstants.leftBackMotorID, MotorType.kBrushed);

        SparkMaxConfig configSparkMotorEsquerda = new SparkMaxConfig();
        SparkMaxConfig configSparkMotorDireita = new SparkMaxConfig();

        @SuppressWarnings("removal")
        MotorControllerGroup leftMotorControllerGroup = new MotorControllerGroup(leftMotorFront, leftMotorback);
        @SuppressWarnings("removal")
        MotorControllerGroup rightMotorControllerGroup = new MotorControllerGroup(rightMotorFront, rightMotorback);

        DifferentialDrive differentialDrive = new DifferentialDrive(leftMotorControllerGroup,
                        rightMotorControllerGroup);

        public Traction() {
                /*
                 * inverter na questão do true or false
                 * original
                 * configSparkMotorDireita.inverted(false)
                 * alterado
                 * configSparkMotorDireita.inverted(true)
                 */
                configSparkMotorDireita.inverted(false).idleMode(IdleMode.kBrake);
                configSparkMotorDireita.smartCurrentLimit(60);

                rightMotorFront.configure(configSparkMotorDireita, ResetMode.kResetSafeParameters,
                                PersistMode.kPersistParameters);
                rightMotorback.configure(configSparkMotorDireita, ResetMode.kResetSafeParameters,
                                PersistMode.kPersistParameters);

                configSparkMotorEsquerda.inverted(false).idleMode(IdleMode.kBrake);
                configSparkMotorEsquerda.smartCurrentLimit(60);

                leftMotorFront.configure(configSparkMotorEsquerda, ResetMode.kResetSafeParameters,
                                PersistMode.kPersistParameters);
                leftMotorback.configure(configSparkMotorEsquerda, ResetMode.kResetSafeParameters,
                                PersistMode.kPersistParameters);

        }

        @Override
        public void periodic() {

        }

        public void arcadeMode(double drive, double turn) {
                differentialDrive.arcadeDrive(drive, +turn);
        }

        public void stop() {
                differentialDrive.stopMotor();
        }

        public void ativarTurbo(boolean turbo) {
                this.turbo = turbo;
        }
}