/*package frc.robot.Commands.Autonomous;

import java.util.Objects;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystem.Traction;

public class AndarDistancia extends Command {

    private final Traction traction;
    private final double targetMeters;

    // PID 
    private static final double kP = 0.6;
    private static final double kI = 0.0;
    private static final double kD = 0.0001;

    private final PIDController pid;

    // Ajustes gerais
    private final double tolerance = 0.02; // 2 cm
    private final double maxSpeed = 0.3;
    private final double maxTime = 5.0;
    private final Timer timer = new Timer();

    public AndarDistancia(Traction traction, double targetMeters) {
        this.traction = Objects.requireNonNull(traction, "traction não pode ser null");
        this.targetMeters = targetMeters;

        this.pid = new PIDController(kP, kI, kD);
        this.pid.setTolerance(tolerance);
        this.pid.setIntegratorRange(-0.5, 0.5);

        addRequirements(traction);
    }

    @Override
    public void initialize() {
        traction.resetEncoders();
        pid.reset();
        pid.setSetpoint(targetMeters);

        timer.reset();   // importante resetar antes de iniciar
        timer.start();
    }

    @Override
    public void execute() {
        double current = traction.getAverageDistanceMeters();
        // calcular explicitamente com setpoint (se disponível)
        double output = pid.calculate(current, targetMeters);

        // Evitar que vá para trás — se quiser suportar back, remova este clamp
        if (output < 0.0) {
            output = 0.0;
        }

        // Limitar saída
        if (output > maxSpeed) {
            output = maxSpeed;
        }

        // prevenir NaN/inf
        if (Double.isFinite(output)) {
            traction.drive(output);
        } else {
            traction.stop();
        }
    }

    @Override
    public boolean isFinished() {
        double current = traction.getAverageDistanceMeters();

        // usar PID atSetpoint como condição mais robusta
        boolean atSetpoint = pid.atSetpoint();
        boolean passouAlvo = current >= targetMeters; // apenas para frente
        boolean timeout = timer.hasElapsed(maxTime);

        return atSetpoint || passouAlvo || timeout;
    }

    @Override
    public void end (boolean interrupted) {
        traction.stop();
        timer.stop();
        timer.reset();
        pid.reset();
    }
}*/
