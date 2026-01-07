// Define o pacote onde este comando está localizado
package frc.robot.Commands;

// Importa o subsistema de tração
import frc.robot.Subsystem.Traction;

// Importa o controle Xbox
import edu.wpi.first.wpilibj.XboxController;

// Importa a classe base de comandos da WPILib
import edu.wpi.first.wpilibj2.command.Command;

// Comando responsável por dirigir o robô usando o controle Xbox
// Este é o comando padrão do subsistema de tração
public class Controller extends Command {

    // Referência ao subsistema de tração
    // O warning é suprimido porque o campo é usado indiretamente
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Traction traction;

    // Referência ao controle Xbox
    private final XboxController xbox;

    // Variáveis de controle do movimento
    public double drive;       // Movimento frente / trás
    public double turn;        // Rotação esquerda / direita
    public double velocidade;  // Fator de velocidade (normal ou turbo)

    // Variável estática que indica se o turbo está ativo
    // OBS: no código atual ela NÃO está sendo usada
    public static boolean turboAtivo = false;

    // Construtor do comando
    public Controller(Traction traction, XboxController xbox) {
        this.traction = traction;
        this.xbox = xbox;

        // Declara que este comando utiliza o subsistema de tração
        // Impede que outros comandos o usem ao mesmo tempo
        addRequirements(traction);
    }

    // Alterna o estado do turbo (true <-> false)
    // Método estático: pertence à classe, não à instância
    public static void toggleTurbo() {
        turboAtivo = !turboAtivo;
    }

    // Executado uma única vez quando o comando inicia
    @Override
    public void initialize() {
        drive = 0;
        turn = 0;
    }

    // Executado continuamente enquanto o comando estiver ativo
    @Override
    public void execute() {

        // Define a velocidade com base no modo turbo do subsistema
        if (traction.turbo) {
            velocidade = 0.9;   // Modo turbo
        } else {
            velocidade = 0.6;   // Modo normal
        }

        /*
         * Aqui você já percebeu corretamente uma possível inversão:
         * 
         * Normalmente:
         *  - LeftY  -> frente / trás (drive)
         *  - RightX -> rotação (turn)
         * 
         * Dependendo da orientação física:
         *  - pode ser necessário inverter sinais
         *  - ou trocar os eixos
         */

        // Leitura dos eixos do controle com aplicação da velocidade
        turn  = xbox.getLeftY()  * velocidade;
        drive = xbox.getRightX() * velocidade;

        // Normalização para evitar que a soma ultrapasse 1.0
        double max = Math.abs(drive) + Math.abs(turn);
        if (max > 1.0) {
            drive /= max;
            turn  /= max;
        }

        // Envia os valores para o subsistema de tração
        traction.arcadeMode(drive, +turn);
    }

    // Executado quando o comando termina ou é interrompido
    @Override
    public void end(boolean interrupted) {
        traction.stop();
    }

    // Retorna false para indicar que o comando nunca termina sozinho
    // Por isso ele funciona como comando padrão
    @Override
    public boolean isFinished() {
        return false;
    }
}
