package com.svantle.score.listeners

import com.svantle.score.SCore
import com.svantle.score.constants.FILE_PLAYER_DATA
import com.svantle.score.constants.MSG_ENABLED
import com.svantle.score.constants.MSG_FLIGHT_UPDATED
import com.svantle.score.utils.MessageHelper
import com.svantle.score.utils.sendTo
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File
import java.io.IOException

class PlayerDataListener(private val core: SCore): Listener {

	init {
		core.server.pluginManager.registerEvents(this, core)
	}

	@EventHandler
	fun onPlayerJoin(event: PlayerJoinEvent) {
		val player = event.player
		val dataFolder: File = core.dataFolder
		val playerFile = File(dataFolder, FILE_PLAYER_DATA)
		val playerData: FileConfiguration = YamlConfiguration.loadConfiguration(playerFile)
		val wasFlying = playerData.getBoolean(player.uniqueId.toString() + ".flight", false)

		player.allowFlight = wasFlying

		if(wasFlying) {
			player.isFlying = true

			val enabled = core.messages.getMessageString(MSG_ENABLED)
			MessageHelper.phrase(MSG_FLIGHT_UPDATED, enabled).sendTo(player)
		}
	}

	@EventHandler
	fun onPlayerQuit(event: PlayerQuitEvent) {
		val player = event.player
		val dataFolder: File = core.dataFolder
		val playerFile = File(dataFolder, FILE_PLAYER_DATA)
		val playerData: FileConfiguration = YamlConfiguration.loadConfiguration(playerFile)

		playerData[player.uniqueId.toString() + ".flight"] = player.allowFlight

		try {
			playerData.save(playerFile)
		} catch(e: IOException) {
			e.printStackTrace()
		}
	}
}