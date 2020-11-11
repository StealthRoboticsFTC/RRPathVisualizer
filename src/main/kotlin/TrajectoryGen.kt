import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumConstraints
import com.acmerobotics.roadrunner.util.Angle
import java.util.*

object TrajectoryGen {
    // Remember to set these constraints to the same values as your DriveConstants.java file in the quickstart
    private val driveConstraints = DriveConstraints(40.0, 30.0, 0.0, 270.0.toRadians, 270.0.toRadians, 0.0)

    // Remember to set your track width to an estimate of your actual bot to get accurate trajectory profile duration!
    private const val trackWidth = 16.0

    private val combinedConstraints = MecanumConstraints(driveConstraints, trackWidth)

    private val startPose = Pose2d(-60.0, 19.0, 0.0)

    fun createTrajectory(): List<Trajectory> {
        val list = ArrayList<Trajectory>()

        val builder1 = TrajectoryBuilder(startPose, startPose.heading, combinedConstraints)
        builder1.splineTo(Vector2d(22.0, 40.0), 90.0.toRadians)
        list.add(builder1.build())
        val builder2 = TrajectoryBuilder(list[list.size - 1].end(), true, combinedConstraints)
        builder2.splineTo(Vector2d(-2.0, 36.0), 180.0.toRadians)
        list.add(builder2.build())
        val builder3 = TrajectoryBuilder(list[list.size - 1].end(), 90.0.toRadians, combinedConstraints)
        builder3.splineToSplineHeading(Pose2d(-14.0, 54.0, 170.0.toRadians), 170.0.toRadians)
        builder3.splineTo(Vector2d(-36.0, 57.0), 180.0.toRadians)
        list.add(builder3.build())
        val builder4 = TrajectoryBuilder(list[list.size - 1].end(), 0.0.toRadians, combinedConstraints)
        builder4.splineToSplineHeading(Pose2d(-26.0, 57.0, 0.0.toRadians), 0.0.toRadians)
        builder4.splineTo(Vector2d(-6.0, 57.0), 0.0.toRadians)
        list.add(builder4.build())
        val builder5 = TrajectoryBuilder(list[list.size - 1].end(), 270.0.toRadians, combinedConstraints)
        builder5.splineToConstantHeading(Vector2d(8.0, 36.0), 0.0.toRadians)
        list.add(builder5.build())

        return list
    }

    fun drawOffbounds() {
//        GraphicsUtil.fillRect(Vector2d(0.0, -63.0), 18.0, 18.0) // robot against the wall
    }
}

val Double.toRadians get() = (Math.toRadians(this))
