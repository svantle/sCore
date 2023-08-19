package com.svantle.score.commands

import com.svantle.score.SCore
import com.svantle.score.constants.*
import com.svantle.score.utils.sendTo
import com.svantle.score.utils.MessageHelper
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class FeedCommand(core: SCore) : CommandExecutor {

	init {
		core.getCommand("feed")!!.setExecutor(this)
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
		if (args.isNullOrEmpty()) {
			handleSelfFeed(sender)
		} else {
			handleFeedOthers(sender, args[0])
		}

		return true
	}

	private fun handleSelfFeed(sender: CommandSender) {
		if (sender !is Player) {
			MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
			return
		}

		if (sender.hasPermission(FEED_COMMAND)) {
			sender.foodLevel = 20
			MessageHelper.phrase(MSG_FEED_SUCCESS).sendTo(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun handleFeedOthers(sender: CommandSender, targetName: String) {
		if (sender.hasPermission(FEED_COMMAND_OTHERS) || sender !is Player) {
			val target = Bukkit.getPlayer(targetName)

			if(target == null) {
				MessageHelper.phrase(MSG_PLAYER_NOT_FOUND, targetName).sendTo(sender)
				return
			}

			target.foodLevel = 20

			MessageHelper.phrase(MSG_FEED_SUCCESS).sendTo(target)
			MessageHelper.phrase(MSG_FEED_SUCCESS_OTHERS, target.name).sendTo(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}
}