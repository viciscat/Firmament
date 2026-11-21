package moe.nea.firmament.events

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.gui.screens.Screen
import moe.nea.firmament.keybindings.GenericInputAction
import moe.nea.firmament.keybindings.InputModifiers
import moe.nea.firmament.keybindings.SavedKeyBinding

sealed interface HandledScreenInputEvent {
	val screen: Screen
	val input: GenericInputAction
	val modifiers: InputModifiers
}

data class HandledScreenKeyPressedEvent(
	override val screen: Screen,
	override val input: GenericInputAction,
	override val modifiers: InputModifiers,
) : FirmamentEvent.Cancellable(), HandledScreenInputEvent {
	val isRepeat: Boolean = internalPollAction() == InputConstants.REPEAT
	fun matches(keyBinding: SavedKeyBinding, atLeast: Boolean = false): Boolean {
		return keyBinding.matches(input, modifiers, atLeast)
	}

	fun isLeftClick() = input == GenericInputAction.mouse(InputConstants.MOUSE_BUTTON_LEFT)

	companion object : FirmamentEventBus<HandledScreenKeyPressedEvent>() {
		private var lastAction = -1

		/**
		 * save the exact action last passed to [net.minecraft.client.KeyboardHandler.keyPress]. can be restored using
		 * [internalPollAction]
		 */
		fun internalPushAction(action: Int) {
			lastAction = action
		}

		fun internalPollAction(): Int {
			return lastAction
		}
	}
}

data class HandledScreenKeyReleasedEvent(
	override val screen: Screen,
	override val input: GenericInputAction,
	override val modifiers: InputModifiers,
) : FirmamentEvent.Cancellable(), HandledScreenInputEvent {
	fun matches(keyBinding: SavedKeyBinding, atLeast: Boolean = false): Boolean {
		return keyBinding.matches(input, modifiers, atLeast)
	}

	companion object : FirmamentEventBus<HandledScreenKeyReleasedEvent>()
}
