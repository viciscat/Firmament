package moe.nea.firmament.keybindings

import java.util.BitSet
import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.input.KeyEvent

object FirmamentKeyboardState {

	private val pressedScancodes = BitSet()

	@Synchronized
	fun isScancodeDown(scancode: Int): Boolean {
		// TODO: maintain a record of keycodes that were pressed for this scanCode to check if they are still held
		return pressedScancodes.get(scancode)
	}

	@Synchronized
	fun maintainState(keyInput: KeyEvent, action: Int) {
		when (action) {
			InputConstants.PRESS -> pressedScancodes.set(keyInput.keycode)
			InputConstants.RELEASE -> pressedScancodes.clear(keyInput.keycode)
		}
	}
}
