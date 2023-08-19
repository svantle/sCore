package com.svantle.score.handlers

import com.svantle.score.SCore
import org.bukkit.configuration.file.FileConfiguration

class ConfigHandler(plugin: SCore) {

	val fileConfig: FileConfiguration

	init {
		plugin.saveDefaultConfig()
		plugin.reloadConfig()

		fileConfig = plugin.getConfig()
	}

	inline fun <reified T : Any> get(string: String): T {
		return fileConfig.get(string) as T
	}
}