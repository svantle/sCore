package com.svantle.score.utils

import com.svantle.score.SCore
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor

class MessageHelper {
	companion object {
		fun colorize(message: String): String {
			return ChatColor.translateAlternateColorCodes('&', message)
		}

		fun appendPrefix(message: Component): Component {
			val core = SCore.get()

			return core.messages.prefix.append(message)
		}

		fun phrase(phrase: String, vararg params: Any): Component {
			val core = SCore.get()

			return core.messages.getPhrase(phrase, *params)
		}
	}
}