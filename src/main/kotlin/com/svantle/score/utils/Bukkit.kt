package com.svantle.score.utils

import org.bukkit.Sound
import org.bukkit.entity.Player

fun Sound.playTo(player: Player, volume: Float = 1.0f, pitch: Float = 1.0f) {
	player.playSound(player.location, this, volume, pitch)
}