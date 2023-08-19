package com.svantle.score.commands

import com.svantle.score.SCore
import com.svantle.score.constants.*
import com.svantle.score.utils.MessageHelper
import com.svantle.score.utils.sendTo
import org.bukkit.Bukkit
import org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HealCommand(core: SCore) : CommandExecutor {

	init {
		core.getCommand("heal")!!.setExecutor(this)
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
		if(args.isNullOrEmpty()) {
			handleSelfHeal(sender)
		} else {
			handleHealOthers(sender, args[0])
		}

		return true
	}

	private fun handleSelfHeal(sender: CommandSender) {
		if(sender !is Player) {
			MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
			return
		}

		if(sender.hasPermission(HEAL_COMMAND)) {
			heal(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun handleHealOthers(sender: CommandSender, targetName: String) {
		if (sender.hasPermission(HEAL_COMMAND_OTHERS) || sender !is Player) {
			val target = Bukkit.getPlayer(targetName)

			if(target == null) {
				MessageHelper.phrase(MSG_PLAYER_NOT_FOUND, targetName).sendTo(sender)
				return
			}

			heal(target, sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun heal(player: Player, executor: CommandSender? = null) {
		player.health = player.getAttribute(GENERIC_MAX_HEALTH)!!.value

		if(executor != null) {
			MessageHelper.phrase(MSG_HEAL_SUCCESS_OTHERS, player.name).sendTo(executor)
		}

		MessageHelper.phrase(MSG_HEAL_SUCCESS).sendTo(player)
	}
}
