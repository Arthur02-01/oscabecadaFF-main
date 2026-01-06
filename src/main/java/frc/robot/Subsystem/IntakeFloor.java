
  package frc.robot.Subsystem;
  
  import com.revrobotics.spark.SparkMax;
  
  import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

  import com.revrobotics.spark.config.SparkMaxConfig;
  import com.revrobotics.spark.SparkLowLevel.MotorType;
  import com.revrobotics.RelativeEncoder;
  import com.revrobotics.spark.SparkBase.PersistMode;
  import com.revrobotics.spark.SparkBase.ResetMode;
  import com.revrobotics.spark.SparkMax;
  import com.revrobotics.spark.SparkClosedLoopController;
  import com.revrobotics.spark.SparkBase.ControlType;
  import com.revrobotics.spark.config.SparkMaxConfig;
  
  
  import edu.wpi.first.wpilibj2.command.SubsystemBase;
  import frc.robot.Constants;
  import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
  
  @SuppressWarnings("unused")
public class IntakeFloor extends SubsystemBase {
  public SparkMax intakeMarlonMotor = new
  SparkMax(Constants.IntakeFloor.intakeMarlonMotorID, MotorType.kBrushless);
  public SparkMax intakeCleitaoMotor = new
  SparkMax(Constants.IntakeFloor.intakeCleitaoMotorID, MotorType.kBrushless);
  public static final double LimiteSuperior = 300.0;
  public static final double LimiteInferior = 75.0;
  public static final double Reducao = 64.0;
  
  private RelativeEncoder intakeMarlonEncoder = intakeMarlonMotor.getEncoder();
  private final SparkClosedLoopController pid = intakeMarlonMotor.getClosedLoopController();
  
  public IntakeFloor() {
  intakeMarlonMotor.configure(
  new SparkMaxConfig()
  .idleMode(IdleMode.kBrake)
  .inverted(false)
  .smartCurrentLimit(50),
  ResetMode.kNoResetSafeParameters,
  PersistMode.kPersistParameters);
  
  intakeCleitaoMotor.configure(
  new SparkMaxConfig()
  .idleMode(IdleMode.kBrake)
  .smartCurrentLimit(50),
  ResetMode.kNoResetSafeParameters,
  PersistMode.kPersistParameters);

  SparkMaxConfig cfg = new SparkMaxConfig();
  
  cfg.closedLoop.p(0.02);
  cfg.closedLoop.i(0);
  cfg.closedLoop.d(0.0);
  cfg.closedLoop.outputRange(-1.0, 1.0);
  
  resetToStartAngle();
  }
  
  public double getAngulo() {
  return intakeMarlonEncoder.getPosition() * (360.0 / Reducao);
  }

  public double grausParaRotacao(double graus) {
  return (graus / 360.0) * Reducao;
  }
  
  public void resetToStartAngle() {
  intakeMarlonEncoder.setPosition(grausParaRotacao(LimiteSuperior));
  }
  public void moverParaAngulo(double alvoGraus){
  double alvoRot = grausParaRotacao(alvoGraus);
  
  pid.setReference(alvoRot,ControlType.kPosition);
  }
  
  public boolean noLimiteSuperior() {
  return getAngulo() >= LimiteSuperior;
  }
  
  public boolean noLimiteInferior() {
  return getAngulo() >= LimiteInferior;
  }
  
  public double getIntakeMarlonPosition() {
  return intakeMarlonEncoder.getPosition();
  }
  
  public void setIntakeMarlonVelocidade(double velocidade) {
  if (velocidade > 0 && noLimiteSuperior()) {
  intakeMarlonMotor.stopMotor();
  return;
  }
  if (velocidade < 0 && noLimiteInferior()) {
  intakeMarlonMotor.stopMotor();
  return;
  }
  intakeMarlonMotor.set(velocidade);
  }
  
  public void setRoller(double velocidade) {
  intakeCleitaoMotor.set(velocidade);
  }
  
  public void stopRoller() {
  intakeCleitaoMotor.stopMotor();
  }
  
  public void stopIntake() {
  intakeMarlonMotor.stopMotor();
  intakeCleitaoMotor.stopMotor();
  }
  
  @Override
  public void periodic() {
  SmartDashboard.putNumber("Intake Ângulo (°)", getAngulo());
  SmartDashboard.putNumber("Marlon Rotações",
  intakeMarlonEncoder.getPosition());
  SmartDashboard.putBoolean("Limite Superior 300", noLimiteInferior());
  SmartDashboard.putBoolean("Limite Inferior 75º", noLimiteSuperior());
  
  SmartDashboard.putNumber("Lift Output", intakeMarlonMotor.get());
  SmartDashboard.putNumber("Roller Output", intakeCleitaoMotor.get());
  }
  
}
