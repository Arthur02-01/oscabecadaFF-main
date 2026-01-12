
  package frc.robot.Subsystem;
  
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
  
public class IntakeFloor extends SubsystemBase {
  /*Define a classe, e busca o import da wpilib SusbsystemBase */
  public SparkMax intakeMarlonMotor;
  public SparkMax intakeCleitaoMotor;
  /*Define dentro do Spark, os construtores dos motores, ou seja onde achar seu id e qual o tipo de motor*/
  public static final double LimiteSuperior = 345.0;
  public static final double LimiteInferior = 165.0;
  public static final double Reducao = 12.0;
  private static final double MAX_Velocidade = 0.15;
  public boolean usandoFF = false;
  /*Seta as funções ainda a serem utilizadas como limitações e reduções */
  private RelativeEncoder intakeMarlonEncoder;
  private SparkClosedLoopController pid;
  private ArmFeedforward ff;
  /*Seta o encoder do intakefloor(intakeMarlon) e tambem as funções PID atualmente chamadas de SparkClosedLoopController para definir o pid */
  public IntakeFloor() {
 /*Abre a classe IntakeFloor de define as suas funções, suas configurações são feitas pelo comando new SparkMaxConfig aonde configura, direção, maxima voltagem, modo parado, modo de como reiniciar e modo ligado */
  
  intakeMarlonMotor = new SparkMax(Constants.IntakeFloor.intakeMarlonMotorID, MotorType.kBrushless
  );
  intakeCleitaoMotor = new SparkMax(Constants.IntakeFloor.intakeCleitaoMotorID, MotorType.kBrushless
  );

  intakeMarlonEncoder = intakeMarlonMotor.getEncoder();
  pid = intakeMarlonMotor.getClosedLoopController();
  ff = new ArmFeedforward(
    0.2,
    0.6,
    0.1
  );

  intakeCleitaoMotor.configure(
  new SparkMaxConfig()
  .idleMode(IdleMode.kBrake)
  .smartCurrentLimit(60),
  ResetMode.kNoResetSafeParameters,
  PersistMode.kPersistParameters);

  SparkMaxConfig cfg = new SparkMaxConfig();
  /*Necessario para fazer a config do PID a qual atual precisa setar uma nova config de configuração para setar com o closedloop e a maxima distancia que ele fara */
    
    cfg
    .idleMode(IdleMode.kBrake)
    .inverted(false)
    .smartCurrentLimit(60);


    cfg.closedLoop
     .p(0.3)
     .i(0.0)
     .d(0.0)
     .outputRange(-1.0, 1.0);
    

  intakeMarlonMotor.configure(
  cfg,
  ResetMode.kNoResetSafeParameters,
  PersistMode.kPersistParameters
);
  
  resetToStartAngle();
  /*Absolutamente necessario pois zera a informação do angulo para não deixar começar no angulo a qual parou */
  }
  
  public double getAngulo() {
  return intakeMarlonEncoder.getPosition() * (360.0 / Reducao);
 /*Faz config para puxar a informação do angulo que está */
  }

  public double getAnguloRad(){
    return Math.toRadians(getAngulo());
  }

  public double grausParaRotacao(double graus) {
  return (graus / 360.0) * Reducao;
  /*Transforma o angulo para rotações */
  }
  
  public void resetToStartAngle() {
  intakeMarlonEncoder.setPosition(grausParaRotacao(LimiteSuperior));
  /*Reseta a posição do encoder */
  }
  public void moverParaAngulo(double alvoGraus){
    alvoGraus = MathUtil.clamp(alvoGraus, LimiteInferior, LimiteSuperior) ;
    usandoFF = true;

    double alvoCompensado = CompensarComFF(alvoGraus);
    pid.setReference(grausParaRotacao(alvoCompensado), ControlType.kPosition);
  /*FAz a função do autonomo moverParaAngulo, que diz para ir para posição do limite superior ou inferior, utilizando o PID */
  }  
  public boolean noLimiteSuperior() {
  return getAngulo() >= LimiteSuperior;
  /*Usa as funções limite superior e inferior para definir aonde o encoder deve parar */
  }
  
  public boolean noLimiteInferior() {
  return getAngulo() <= LimiteInferior;
  }
  
  public double getIntakeMarlonPosition() {
  return intakeMarlonEncoder.getPosition();
  /*Usa o encoder para achar a posição do intake */
  }
  
  public void setIntakeMarlonVelocidade(double velocidade) {
   double LimiteVelocidade = MathUtil.clamp(velocidade, MAX_Velocidade, MAX_Velocidade);
  if (LimiteVelocidade > 0 && noLimiteSuperior()) {
  intakeMarlonMotor.set(0);
  return;
  }
  if (LimiteVelocidade < 0 && noLimiteInferior()) {
  intakeMarlonMotor.set(0);
  return;
  }
  usandoFF = false;
  intakeMarlonMotor.set(LimiteVelocidade);
  /*Comando principal para setar a velocidade do robo, para se acertar as travas de segurança e o joyStick for solto */
  }
  private double CompensarComFF(double alvoGraus){
    double ffVolts = ff.calculate(Math.toRadians(alvoGraus), 0.0);
    double compensacao = MathUtil.clamp(ffVolts * 0.05, -5.0, 5.0);
    return alvoGraus + compensacao;
  }
  
  public void setRoller(double velocidade) {
  intakeCleitaoMotor.set(velocidade);
  /*Seta a velocidade dos rolos do intake, e tambem o para na função abaixo */
  }
  
  public void stopRoller() {
  intakeCleitaoMotor.set(0);
  }
  
  public void stopIntake() {
    /*Para o intake completamente, utilizando o comando stopMotor */
  intakeMarlonMotor.set(0);
  intakeCleitaoMotor.set(0);
  }

  public void Parar(){
    intakeMarlonMotor.set(0);
    intakeCleitaoMotor.set(0);
  }
  
  @Override
  public void periodic() {
  SmartDashboard.putNumber("Intake Ângulo (°)", getAngulo());
  SmartDashboard.putNumber("Marlon Rotações",
  intakeMarlonEncoder.getPosition());
  SmartDashboard.putBoolean("Limite Superior 360", noLimiteSuperior());
  SmartDashboard.putBoolean("Limite Inferior 0º", noLimiteInferior());
  
  SmartDashboard.putNumber("Lift Output", intakeMarlonMotor.get());
  SmartDashboard.putNumber("Roller Output", intakeCleitaoMotor.get());
  /*Manda as informação para visualização do driver com a função SmartDashBoard */
  }
  
}
