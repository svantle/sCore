package com.svantle.score.commands

import com.svantle.score.SCore
import com.svantle.score.constants.*
import com.svantle.score.utils.MessageHelper
import com.svantle.score.utils.sendTo
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EnderChestCommand(core: SCore) : CommandExecutor {

    init {
        core.getCommand("enderchest")!!.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
        if (sender !is Player) {
            MessageHelper.phrase(MSG_ONLY_PLAYERS).sendTo(sender)
        } else {
            if (sender.hasPermission(ENDER_CHEST_COMMAND)) {
                sender.openInventory(sender.enderChest);
                MessageHelper.phrase(MSG_ENDER_CHEST_OPENED).sendTo(sender)
            } else {
                MessageHelper.phrase(MSG_NO_PERMISSION).sendTo(sender)
            }
        }

        // TODO: Add the ability to view other players' ender chests
        // The following constants have been added to aid in a faster implementation
        // MSG_ENDER_CHEST_OPENED_OTHERS
        // ENDER_CHEST_OTHERS_COMMAND

        return true
    }
}
