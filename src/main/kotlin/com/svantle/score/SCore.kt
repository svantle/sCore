package com.svantle.score

import com.svantle.score.commands.*
import com.svantle.score.handlers.ConfigHandler
import com.svantle.score.handlers.MessageHandler
import com.svantle.score.listeners.PlayerDataListener
import com.svantle.score.listeners.PlayerJoinListener
import com.svantle.score.listeners.PlayerLeaveListener
import org.bukkit.plugin.java.JavaPlugin

class SCore: JavaPlugin() {
	lateinit var messages: MessageHandler; private set
	lateinit var config: ConfigHandler; private set

	override fun onEnable() {
		core = this

		config = ConfigHandler(this)
		messages = MessageHandler(this)

		// Register listeners
		PlayerDataListener(this)
		PlayerJoinListener(this)
		PlayerLeaveListener(this)

		// Register commands
		FeedCommand(this)
		FlyCommand(this)
		FlySpeedCommand(this)
		HealCommand(this)
		WorkbenchCommand(this)
		TeleportCommand(this)
		GamemodeCommand(this)
		TimeCommand(this)
		ClearInventoryCommand(this)
	}

	companion object {
		private lateinit var core: SCore

		fun get(): SCore = core
	}
}
