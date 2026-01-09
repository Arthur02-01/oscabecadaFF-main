package frc.robot.Commands.Autonomous.Tracao;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystem.Traction;

public class Andar extends Command {
    private final Traction traction;
    public final double velocidade;
    public final double duracao;
    public double comecarTimer;
    public double tempoAtual;

    public Andar(Traction traction, double velocidade, double duracao) {
        this.traction = traction;
        this.velocidade = velocidade;
        this.duracao = duracao;
        addRequirements(traction);
    }

    @Override
    public void initialize() {
        comecarTimer = System.currentTimeMillis() / 1000.0;
        traction.arcadeMode(velocidade, velocidade);
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Tempo que esta ativo: ", tempoAtual);
    }

    @Override
    public void end(boolean interromper) {
        traction.stop();
    }

    @Override
    public boolean isFinished() {
        tempoAtual = System.currentTimeMillis() / 1000.0;
        return (tempoAtual - comecarTimer) >= duracao;

    }

}
