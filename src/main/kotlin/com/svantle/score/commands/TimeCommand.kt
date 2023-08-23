package com.svantle.score.commands

import com.svantle.score.SCore
import com.svantle.score.constants.*
import com.svantle.score.utils.MessageHelper
import com.svantle.score.utils.sendTo
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TimeCommand(core: SCore) : CommandExecutor {

	init {
		core.getCommand("time")!!.setExecutor(this)
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
		if (args.isNullOrEmpty()) {
			MessageHelper.phrase(MSG_COMMAND_USAGE, "/time <action> [args]").sendTo(sender)
		} else {
			val action = args[0].lowercase()

			if (sender !is Player) {
				MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
				return false
			}

			if (!sender.hasPermission(TIME_COMMAND)) {
				MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
				return false
			}

			if (action in arrayOf("get", "set", "add")) {
				when(action) {
					"get" -> handleGet(sender)
					"set" -> handleSet(sender, args[1])
					"add" -> handleAdd(sender, args[1])
				}
			} else {
				MessageHelper.phrase(MSG_INVALID, "time action", action).sendTo(sender)
				return false
			}
		}

		return true
	}

	private fun handleSet(sender: Player, time: String) {
		val parsed = parseStringTime(time)

		sender.world.time = parsed
		MessageHelper.phrase(MSG_TIME_SET, parsed).sendTo(sender)
	}

	private fun handleAdd(sender: Player, amount: String) {
		val time = amount.toLongOrNull()

		if (time == null) {
			MessageHelper.phrase(MSG_INVALID, "time value", amount).sendTo(sender)
			return
		}

		sender.world.time += time
		MessageHelper.phrase(MSG_TIME_ADD, time).sendTo(sender)
	}

	private fun handleGet(sender: Player) {
		MessageHelper.phrase(MSG_TIME_GET, sender.world.time).sendTo(sender)
	}

	fun parseStringTime(arg: String): Long {
		return when(arg) {
			"day" -> 6000
			"night" -> 14500
			"morning" -> 23500
			"evening" -> 13000
			else -> arg.toLongOrNull() ?: 0
		}
	}
}
