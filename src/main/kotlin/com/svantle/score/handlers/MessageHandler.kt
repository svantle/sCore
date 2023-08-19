package com.svantle.score.handlers

import com.svantle.score.*
import com.svantle.score.constants.MSG_PREFIX
import com.svantle.score.constants.FILE_MESSAGE_CONFIG
import com.svantle.score.utils.toComponent
import com.svantle.score.utils.MessageHelper
import com.svantle.score.utils.MessageHelper.Companion.colorize
import net.kyori.adventure.text.Component
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class MessageHandler(core: SCore) {
	private val config: FileConfiguration
	val prefix: Component

	private val baseFile = File(core.dataFolder, FILE_MESSAGE_CONFIG)

	init {
		if (!baseFile.exists()) {
			core.saveResource(FILE_MESSAGE_CONFIG, false)
		}

		config = YamlConfiguration.loadConfiguration(baseFile)
		prefix = Component.text(colorize(config.getString(MSG_PREFIX, "") ?: ""))
	}

	/**
	 * Get the raw string with the given path
	 * from the messages.yml file as a [String]
	 *
	 * @param path The message path in the configuration file
	 */
	fun getString(path: String): String {
		return config.getString(path, "") ?: throw NullPointerException("Message at path $path does not exist!")
	}

	/**
	 * Get a colorized message without a prefix
	 * from the messages.yml file as a [String]
	 *
	 * @param path The message path in the configuration file
	 */
	fun getMessageString(path: String): String {
		return colorize(getString(path))
	}

	/**
	 * Get a colorized message without a prefix
	 * from the messages.yml file as a [Component]
	 *
	 * @param path The message path in the configuration file
	 */
	fun getMessageComponent(path: String): Component {
		return getMessageString(path).toComponent()
	}

	/**
	 * Get a colorized message with a prefix
	 * from the messages.yml file as a [String]
	 *
	 * @param path The message path in the configuration file
	 */
	fun getPrefixedMessageComponent(path: String): Component {
		return MessageHelper.appendPrefix(colorize(getString(path)).toComponent())
	}

	/**
	 * Get a certain phrase from the messages.yml
	 * file as a [Component]
	 */
	fun getPhrase(phrase: String, vararg params: Any): Component {
		val result =  if (params.isNotEmpty()) {
			String.format(getMessageString(phrase), *params).toComponent()
		} else {
			getMessageString(phrase).toComponent()
		}

		return MessageHelper.appendPrefix(result)
	}
}