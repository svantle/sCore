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

class GamemodeCommand(core: SCore) : CommandExecutor {

	init {
		core.getCommand("gamemode")!!.setExecutor(this)
	}

	private enum class GameModeType(val display: String, val mode: GameMode, val acceptedValues: Array<String>) {
		CREATIVE("Creative", GameMode.CREATIVE, arrayOf("creative", "c", "1")),
		SURVIVAL("Survival", GameMode.SURVIVAL, arrayOf("survival", "s", "0")),
		ADVENTURE("Adventure", GameMode.ADVENTURE, arrayOf("adventure", "a", "2")),
		SPECTATOR("Spectator", GameMode.SPECTATOR, arrayOf("spectator", "sp", "3"))
	}

	override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
		if(args.isNullOrEmpty()) {
			MessageHelper.phrase(MSG_COMMAND_USAGE, "/gamemode <mode> [target]").sendTo(sender)
		} else if (args.size == 1) {
			handleSelfGamemode(sender, args[0])
		} else {
			handleOthersGamemode(sender, args[0], args[1])
		}

		return true
	}

	private fun handleSelfGamemode(sender: CommandSender, mode: String) {
		if(sender !is Player) {
			MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
			return
		}

		val gamemode = parseGamemode(mode)

		if (gamemode == null) {
			MessageHelper.phrase(MSG_INVALID, "gamemode", mode).sendTo(sender)
			return
		} else if (sender.hasPermission(GAMEMODE_COMMAND)) {
			sender.gameMode = gamemode.mode
			MessageHelper.phrase(MSG_GAMEMODE_SELF, gamemode.display).sendTo(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun handleOthersGamemode(sender: CommandSender, mode: String, targetName: String) {
		if (sender.hasPermission(GAMEMODE_OTHERS_COMMAND) || sender !is Player) {
			val gamemode = parseGamemode(mode)
			val target = Bukkit.getPlayer(targetName)

			if (gamemode == null) {
				MessageHelper.phrase(MSG_INVALID, "gamemode", mode).sendTo(sender)
				return
			}

			if (target == null) {
				MessageHelper.phrase(MSG_PLAYER_NOT_FOUND, targetName).sendTo(sender)
				return
			}

			target.gameMode = gamemode.mode

			MessageHelper.phrase(MSG_GAMEMODE_SELF, gamemode.display).sendTo(target)
			MessageHelper.phrase(MSG_GAMEMODE_OTHERS, target.name, gamemode.display).sendTo(sender)
		} else {
			MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
		}
	}

	private fun parseGamemode(type: String): GameModeType? {
		return GameModeType.values().firstOrNull {
			it.acceptedValues.contains(type.lowercase())
		}
	}
}
