package moe.nea.firmament.compat.jade

import snownee.jade.api.Accessor
import snownee.jade.api.ui.BoxElement
import snownee.jade.api.ui.BoxProgress
import snownee.jade.api.ui.IBoxProgressProvider
import snownee.jade.api.ui.MessageType
import moe.nea.firmament.util.MC

enum class FirmamentProgressProvider : IBoxProgressProvider {
	INSTANCE;
	override fun getProgress(
		p0: BoxElement,
		p1: Accessor<*>,
		p2: Float
	): BoxProgress? {
		val speed = CustomMiningHardnessProvider.replaceBlockBreakSpeed(-1f)
		val progress = CustomMiningHardnessProvider.replaceBreakProgress(-1f)
		if (speed < 0 || progress < 0) return null;
		return BoxProgress(Math.clamp(progress + MC.instance.deltaTracker.getGameTimeDeltaPartialTick(false) * speed, 0f, 1f), MessageType.TITLE)
	}
}
