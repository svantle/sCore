package com.svantle.score.listeners

import com.svantle.score.SCore
import com.svantle.score.constants.CONFIG_JOIN_SOUND
import com.svantle.score.constants.CONFIG_LEAVE_SOUND
import com.svantle.score.constants.MSG_PLAYER_JOIN
import com.svantle.score.constants.MSG_PLAYER_LEAVE
import com.svantle.score.utils.MessageHelper
import com.svantle.score.utils.playTo
import com.svantle.score.utils.sendTo
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerLeaveListener(private val core: SCore): Listener {

	init {
		core.server.pluginManager.registerEvents(this, core)
	}

	@EventHandler
	fun onQuit(event: PlayerQuitEvent) {
		event.quitMessage(null)

		Sound.valueOf(core.config.get(CONFIG_LEAVE_SOUND)).playTo(event.player)
		MessageHelper.phrase(MSG_PLAYER_LEAVE).sendTo(event.player)
	}
}