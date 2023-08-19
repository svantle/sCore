package com.svantle.score.utils

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun Component.sendTo(sender: CommandSender) {
	sender.sendMessage(this)
}

fun Component.sendTo(player: Player) {
	player.sendMessage(this)
}

fun String.toComponent(): Component {
	return Component.text(this)
}