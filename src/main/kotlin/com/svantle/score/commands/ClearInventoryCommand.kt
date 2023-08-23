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

class ClearInventoryCommand(core: SCore) : CommandExecutor {

	init {
		core.getCommand("clearinventory")!!.setExecutor(this)
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
		if(args.isNullOrEmpty()) {
			handleClearSelf(sender)
		} else {
			handleClearOthers(sender, args[0])
		}

		return true
	}

	private fun handleClearSelf(sender: CommandSender) {
		if(sender !is Player) {
			MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
			return
		}

		if (sender.hasPermission(CLEAR_INVENTORY_COMMAND)) {
			sender.inventory.clear()
			MessageHelper.phrase(MSG_INVENTORY_CLEAR_SELF).sendTo(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun handleClearOthers(sender: CommandSender, targetName: String) {
		if (sender.hasPermission(CLEAR_INVENTORY_OTHERS_COMMAND) || sender !is Player) {
			val target = Bukkit.getPlayer(targetName)

			if (target == null) {
				MessageHelper.phrase(MSG_PLAYER_NOT_FOUND, targetName).sendTo(sender)
				return
			}

			target.inventory.clear()

			MessageHelper.phrase(MSG_INVENTORY_CLEAR_OTHERS, target.name).sendTo(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}
}
