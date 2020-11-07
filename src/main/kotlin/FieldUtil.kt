import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.util.Angle

fun Pose2d.mirror() = Pose2d(x, -y, Angle.norm(-heading))
fun Vector2d.mirror() = Vector2d(x, -y)

object FieldUtil {
    data class AutoConfig(val color: Color, val side: Side, val stackHeight: StackHeight)

    enum class Color {
        RED,
        BLUE
    }

    enum class Side {
        CENTER,
        FAR
    }

    enum class StackHeight {
        ZERO,
        ONE,
        FOUR
    }

    fun getStartPose(autoConfig: AutoConfig): Pose2d {
        val basePose = Pose2d(-60.0, if (autoConfig.side == Side.FAR) 48.0 else 24.0, 0.0)
        return if (autoConfig.color == Color.RED) basePose.mirror() else basePose
    }

    fun getDropoffMidPose(autoConfig: AutoConfig): Pose2d {
        val basePose = if (autoConfig.side == Side.CENTER) Pose2d(-24.0, 18.0, 0.0)
                else Pose2d(-24.0, 54.0, 0.0)
        return if (autoConfig.color == Color.RED) basePose.mirror() else basePose
    }

    fun getDropoffPose(autoConfig: AutoConfig): Pose2d {
        val x = -6.0 + when(autoConfig.stackHeight) {
            StackHeight.ZERO -> 0.0
            StackHeight.ONE -> 24.0
            StackHeight.FOUR -> 48.0
        }
        val basePose = if (autoConfig.stackHeight == StackHeight.ZERO && autoConfig.side == Side.CENTER)
            Pose2d(12.0, 42.0, 90.0.toRadians)
            else Pose2d(x, 60.0 - (if (autoConfig.stackHeight == StackHeight.ONE) 24.0 else 0.0), 0.0)
        return if (autoConfig.color == Color.RED) basePose.mirror() else basePose
    }

}