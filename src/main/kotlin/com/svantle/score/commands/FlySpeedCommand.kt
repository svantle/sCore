package com.svantle.score.commands

import com.svantle.score.SCore
import com.svantle.score.constants.*
import com.svantle.score.utils.MessageHelper
import com.svantle.score.utils.sendTo
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class FlySpeedCommand(core: SCore) : CommandExecutor {

	private val minSpeed = 1
	private val maxSpeed = 10

	init {
		core.getCommand("flyspeed")!!.setExecutor(this)
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
		if(args.isNullOrEmpty()) {
			MessageHelper.phrase(MSG_COMMAND_USAGE, "/flyspeed <speed> [player]").sendTo(sender)
		} else if (args.size == 1) {
			handleFlySpeedSelf(sender, args[0])
		} else {
			handleFlySpeedOthers(sender, args[0], args[1])
		}

		return true
	}

	private fun validateSpeed(speed: String): Boolean {
		return speed.toIntOrNull() != null && speed.toInt() in minSpeed..maxSpeed
	}

	private fun handleFlySpeedSelf(sender: CommandSender, speed: String) {
		if(sender !is Player) {
			MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
			return
		}

		if(sender.hasPermission(FLY_SPEED_COMMAND)) {
			if (validateSpeed(speed)) {
				val newSpeed = speed.toFloat() / 10.0f

				sender.flySpeed = newSpeed

				MessageHelper.phrase(MSG_FLY_SPEED_UPDATED, speed).sendTo(sender)
			} else {
				MessageHelper.phrase(MSG_INVALID_NUMBER, speed, minSpeed, maxSpeed).sendTo(sender)
			}
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun handleFlySpeedOthers(sender: CommandSender, speed: String, targetName: String) {
		val target = Bukkit.getPlayer(targetName)

		if (target == null) {
			MessageHelper.phrase(MSG_PLAYER_NOT_FOUND, targetName).sendTo(sender)
			return
		}

		if (sender.hasPermission(FLY_SPEED_COMMAND_OTHERS) || sender !is Player) {
			if (validateSpeed(speed)) {
				val newSpeed = speed.toFloat() / 10.0f

				target.flySpeed = newSpeed

				MessageHelper.phrase(MSG_FLY_SPEED_UPDATED, speed).sendTo(sender)
				MessageHelper.phrase(MSG_FLY_SPEED_UPDATED_OTHERS, target.name, speed).sendTo(target)
			} else {
				MessageHelper.phrase(MSG_INVALID_NUMBER, speed, minSpeed, maxSpeed).sendTo(sender)
			}
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}
}
