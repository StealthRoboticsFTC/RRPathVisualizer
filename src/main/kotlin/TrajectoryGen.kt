import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumConstraints
import java.util.*

object TrajectoryGen {
    // Remember to set these constraints to the same values as your DriveConstants.java file in the quickstart
    private val driveConstraints = DriveConstraints(60.0, 30.0, 0.0, 180.0.toRadians, 180.0.toRadians, 0.0)

    // Remember to set your track width to an estimate of your actual bot to get accurate trajectory profile duration!
    private const val trackWidth = 16.0

    private val combinedConstraints = MecanumConstraints(driveConstraints, trackWidth)

    private val autoConfig = FieldUtil.AutoConfig(FieldUtil.Color.RED, FieldUtil.Side.CENTER, FieldUtil.StackHeight.ZERO)
    private val startPose = FieldUtil.getStartPose(autoConfig)

    fun createTrajectory(): List<Trajectory> {
        val list = ArrayList<Trajectory>()

        val builder1 = TrajectoryBuilder(startPose, startPose.heading, combinedConstraints)

        val dropoffMidPose = FieldUtil.getDropoffMidPose(autoConfig)
        val dropoffPose = FieldUtil.getDropoffPose(autoConfig)
        builder1
            .splineTo(dropoffMidPose.vec(), dropoffMidPose.heading)
            .splineTo(dropoffPose.vec(), dropoffPose.heading)
        list.add(builder1.build())
        return list
    }

    fun drawOffbounds() {
        GraphicsUtil.fillRect(Vector2d(0.0, -63.0), 18.0, 18.0) // robot against the wall
    }
}

val Double.toRadians get() = (Math.toRadians(this))
