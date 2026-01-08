package frc.robot.Commands.Autonomous.Tracao;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystem.Traction;

public class AndarEncoder extends Command {

    private final Traction traction;
    private final double velocidade;
    private final double distancia;

    public AndarEncoder(Traction traction, double velocidade, double distancia) {
        this.traction = traction;
        this.velocidade = velocidade;
        this.distancia = distancia;
        addRequirements(traction);
    }

    @Override
    public void initialize() {
        traction.resetEncoders();
    }

    @Override
    public void execute() {
        traction.arcadeMode(velocidade, 0);
    }

    @Override
    public void end(boolean interrupted) {
        traction.stop();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(traction.getAverageDistance()) >= distancia;
    }
}