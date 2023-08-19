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

class FlyCommand(private val core: SCore) : CommandExecutor {

	init {
		core.getCommand("fly")!!.setExecutor(this)
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
		if(args.isNullOrEmpty()) {
			handleSelfFly(sender)
		} else {
			handleFlyOthers(sender, args[0])
		}

		return true
	}

	private fun handleSelfFly(sender: CommandSender) {
		if(sender !is Player) {
			MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
			return
		}

		if(sender.hasPermission(FLY_COMMAND)) {
			toggleFly(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun handleFlyOthers(sender: CommandSender, targetName: String) {
		if (sender.hasPermission(FLY_COMMAND_OTHERS) || sender !is Player) {
			val target = Bukkit.getPlayer(targetName)

			if(target == null) {
				MessageHelper.phrase(MSG_PLAYER_NOT_FOUND, targetName).sendTo(sender)
				return
			}

			toggleFly(target, sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun toggleFly(player: Player, executor: CommandSender? = null) {
		if(player.allowFlight) {
			val disabled = core.messages.getMessageString(MSG_DISABLED)

			player.allowFlight = false
			player.isFlying = false

			if(executor != null) {
				MessageHelper.phrase(MSG_FLIGHT_UPDATED_OTHERS, disabled, player.name).sendTo(executor)
			}

			MessageHelper.phrase(MSG_FLIGHT_UPDATED, disabled).sendTo(player)
		} else {
			val enabled = core.messages.getMessageString(MSG_ENABLED)

			player.allowFlight = true

			if(executor != null) {
				MessageHelper.phrase(MSG_FLIGHT_UPDATED_OTHERS, enabled, player.name).sendTo(executor)
			}

			MessageHelper.phrase(MSG_FLIGHT_UPDATED, enabled).sendTo(player)
		}
	}
}
