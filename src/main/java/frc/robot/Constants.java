package frc.robot;

public final class Constants {

    // ================= TRAÇÃO =================
    public static final class TractionConstants {

        // ===== SPARK MAX - TRAÇÃO =====
        // Lado direito
        public static final int rightFrontMotorID = 5;
        public static final int rightBackMotorID  = 2;

        // Lado esquerdo
        public static final int leftFrontMotorID  = 3;
        public static final int leftBackMotorID   = 1;

        // ===== ENCODERS (PORTAS DIO DO roboRIO) =====
        // Ajuste se os fios estiverem em outras portas
        public static final int leftEncoderChannelA  = 0;
        public static final int leftEncoderChannelB  = 1;

        public static final int rightEncoderChannelA = 2;
        public static final int rightEncoderChannelB = 3;
    }

    // ================= CONTROLE =================
    public static final class ControlsXbox {
        public static final int leftMotors  = 5;
        public static final int rightMotors = 1;
    }

    // ================= INTAKE FLOOR =================
    public static final class IntakeFloor {
        public static final int intakeMarlonMotorID  = 8;
        public static final int intakeCleitaoMotorID = 10;

        public static final double IntakeIn  = 45.0;
        public static final double IntakeOut = 0.0;
    }
}