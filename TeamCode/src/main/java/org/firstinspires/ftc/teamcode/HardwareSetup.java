package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
//import com.qualcomm.hardware.goblilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/**
 * Hardware Setup Class for FTC Robot
 * This class initializes all hardware components and can be used by both
 * Autonomous and TeleOp programs to ensure consistent setup.
 *
 * Features:
 * - Drive motors (Mecanum)
 * - Pedro Pathing Follower
 * - Pinpoint IMU for odometry
 * - Additional motors and servos (customize as needed)
 */
public class HardwareSetup {

    // Drive Motors
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    // Pedro Pathing Follower
    public Follower follower;

    // Pinpoint IMU for odometry
   // public GoBildaPinpointDriver pinpoint;

    // Control Hub IMU (optional, for additional sensing)
    public IMU imu;

    // Additional motors (customize based on your robot)
    // Example: public DcMotorEx intakeMotor;
    // Example: public DcMotorEx armMotor;

    // Servos (customize based on your robot)
    // Example: public Servo clawServo;
    // Example: public Servo wristServo;

    // Hardware Map and Telemetry references
    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    // Initialization status
    private boolean isInitialized = false;

    /**
     * Constructor
     * @param hardwareMap The hardware map from the OpMode
     * @param telemetry The telemetry object for status updates
     */
    public HardwareSetup(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    /**
     * Initialize all hardware components
     * Call this method in the init() section of your OpMode
     */
    public void init() {
        telemetry.addData("Status", "Initializing Hardware...");
        telemetry.update();

        // Initialize drive motors
        initDriveMotors();

        // Initialize Pedro Pathing
        initPedroPathing();

        // Initialize Pinpoint IMU
        initPinpointIMU();

        // Initialize Control Hub IMU (optional)
        initControlHubIMU();

        // Initialize additional motors
        initAdditionalMotors();

        // Initialize servos
        initServos();

        isInitialized = true;

        telemetry.addData("Status", "Hardware Initialized!");
        telemetry.addData("Drive Motors", "Ready");
        telemetry.addData("Pedro Pathing", "Ready");
        //telemetry.addData("Pinpoint IMU", pinpoint != null ? "Ready" : "Not Found");
        telemetry.update();
    }

    /**
     * Initialize drive motors
     * Motor names should match your robot configuration
     */
    private void initDriveMotors() {
        try {
            // Get motors from hardware map (names match Constants.java)
            frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
            frontRight = hardwareMap.get(DcMotorEx.class, "fr");
            backLeft = hardwareMap.get(DcMotorEx.class, "bl");
            backRight = hardwareMap.get(DcMotorEx.class, "br");

            // Set motor directions (already configured in Constants.java for Pedro)
            // These are for direct control in TeleOp
            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            frontRight.setDirection(DcMotor.Direction.FORWARD);
            backRight.setDirection(DcMotor.Direction.FORWARD);

            // Set zero power behavior
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // Reset encoders
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Set to run using encoders
            frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            telemetry.addData("Drive Motors", "Initialized");
        } catch (Exception e) {
            telemetry.addData("Drive Motors Error", e.getMessage());
        }
    }

    /**
     * Initialize Pedro Pathing Follower
     */
    private void initPedroPathing() {
        try {
            follower = Constants.createFollower(hardwareMap);
            telemetry.addData("Pedro Pathing", "Initialized");
        } catch (Exception e) {
            telemetry.addData("Pedro Pathing Error", e.getMessage());
        }
    }

    /**
     * Initialize Pinpoint IMU for odometry
     * Configure based on your robot's specifications
     */
    private void initPinpointIMU() {
        try {
            // Get Pinpoint IMU from hardware map
           // pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

            // Set odometry pod positions (in mm from robot center)
            // CUSTOMIZE THESE VALUES FOR YOUR ROBOT
            //pinpoint.setOffsets(-84.0, -168.0); // X and Y offsets

            // Set odometry pod orientations
            // CUSTOMIZE THESE VALUES FOR YOUR ROBOT
            //pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
            //pinpoint.setEncoderDirections(
                //GoBildaPinpointDriver.EncoderDirection.FORWARD,
                //GoBildaPinpointDriver.EncoderDirection.FORWARD
            //);

            // Reset position and recalibrate
            //pinpoint.resetPosAndIMU();

            telemetry.addData("Pinpoint IMU", "Initialized");
        } catch (Exception e) {
            telemetry.addData("Pinpoint IMU Error", e.getMessage());
           // pinpoint = null; // Set to null if initialization fails
        }
    }

    /**
     * Initialize Control Hub IMU (optional, for additional sensing)
     */
    private void initControlHubIMU() {
        try {
            imu = hardwareMap.get(IMU.class, "imu");

            // Define hub orientation
            // CUSTOMIZE THESE VALUES FOR YOUR ROBOT
            IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
            ));

            imu.initialize(parameters);
            telemetry.addData("Control Hub IMU", "Initialized");
        } catch (Exception e) {
            telemetry.addData("Control Hub IMU", "Not configured");
            imu = null; // Set to null if not needed or initialization fails
        }
    }

    /**
     * Initialize additional motors
     * CUSTOMIZE THIS METHOD FOR YOUR ROBOT
     */
    private void initAdditionalMotors() {
        try {
            // Example: Initialize intake motor
            // intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
            // intakeMotor.setDirection(DcMotor.Direction.FORWARD);
            // intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            // intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // Example: Initialize arm motor
            // armMotor = hardwareMap.get(DcMotorEx.class, "arm");
            // armMotor.setDirection(DcMotor.Direction.FORWARD);
            // armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            // armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            telemetry.addData("Additional Motors", "Ready");
        } catch (Exception e) {
            telemetry.addData("Additional Motors", "Not configured");
        }
    }

    /**
     * Initialize servos
     * CUSTOMIZE THIS METHOD FOR YOUR ROBOT
     */
    private void initServos() {
        try {
            // Example: Initialize claw servo
            // clawServo = hardwareMap.get(Servo.class, "claw");
            // clawServo.setPosition(0.5); // Set to initial position

            // Example: Initialize wrist servo
            // wristServo = hardwareMap.get(Servo.class, "wrist");
            // wristServo.setPosition(0.5); // Set to initial position

            telemetry.addData("Servos", "Ready");
        } catch (Exception e) {
            telemetry.addData("Servos", "Not configured");
        }
    }

    /**
     * Update Pinpoint IMU - call this in your loop
     */
    //public void updatePinpoint() {
        //if (pinpoint != null) {
        //    pinpoint.update();
       // }
    //}

    /**
     * Update Pedro Pathing - call this in your autonomous loop
     */
    public void updatePedroPathing() {
        if (follower != null) {
            follower.update();
        }
    }

    /**
     * Stop all motors
     */
    public void stopAllMotors() {
        if (frontLeft != null) frontLeft.setPower(0);
        if (frontRight != null) frontRight.setPower(0);
        if (backLeft != null) backLeft.setPower(0);
        if (backRight != null) backRight.setPower(0);

        // Stop additional motors
        // if (intakeMotor != null) intakeMotor.setPower(0);
        // if (armMotor != null) armMotor.setPower(0);
    }

    /**
     * Check if hardware is initialized
     */
    public boolean isInitialized() {
        return isInitialized;
    }
}

