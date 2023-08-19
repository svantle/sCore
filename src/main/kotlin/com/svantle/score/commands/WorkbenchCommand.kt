package com.svantle.score.commands

import com.svantle.score.SCore
import com.svantle.score.constants.*
import com.svantle.score.utils.MessageHelper
import com.svantle.score.utils.sendTo
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WorkbenchCommand(core: SCore) : CommandExecutor {

	init {
		core.getCommand("workbench")!!.setExecutor(this)
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
		if (sender !is Player) {
			MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
		} else {
			if (sender.hasPermission(WORKBENCH_COMMAND)) {
				sender.openWorkbench(null, true)
				MessageHelper.phrase(MSG_WORKBENCH_OPENED).sendTo(sender)
			} else {
				MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
			}
		}

		return true
	}
}
