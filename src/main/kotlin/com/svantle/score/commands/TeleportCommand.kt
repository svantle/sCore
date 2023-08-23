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

class TeleportCommand(core: SCore) : CommandExecutor {

	init {
		core.getCommand("teleport")!!.setExecutor(this)
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
		if(args.isNullOrEmpty()) {
			MessageHelper.phrase(MSG_COMMAND_USAGE, "/teleport <player> [target]").sendTo(sender)
		} else if (args.size == 1) {
			handleTeleportSelf(sender, args[0])
		} else {
			handleTeleportOthers(sender, args[0], args[1])
		}

		return true
	}

	private fun handleTeleportSelf(sender: CommandSender, targetName: String) {
		if(sender !is Player) {
			MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
			return
		}

		val player = Bukkit.getPlayer(targetName)

		if (player == null) {
			MessageHelper.phrase(MSG_PLAYER_NOT_FOUND, targetName).sendTo(sender)
			return
		} else if (sender.hasPermission(TELEPORT_COMMAND)) {
			sender.teleport(player)
			MessageHelper.phrase(MSG_TELEPORT_TO_PLAYER, player.name).sendTo(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun handleTeleportOthers(sender: CommandSender, playerName: String, targetName: String) {
		if (sender.hasPermission(TELEPORT_OTHERS_COMMAND) || sender !is Player) {
			val player = Bukkit.getPlayer(playerName)
			val target = Bukkit.getPlayer(targetName)

			if (player == null) {
				MessageHelper.phrase(MSG_PLAYER_NOT_FOUND, playerName).sendTo(sender)
				return
			}

			if (target == null) {
				MessageHelper.phrase(MSG_PLAYER_NOT_FOUND, targetName).sendTo(sender)
				return
			}

			player.teleport(target)
			MessageHelper.phrase(MSG_TELEPORT_PLAYER_TO_PLAYER, player.name, target.name).sendTo(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}
}
