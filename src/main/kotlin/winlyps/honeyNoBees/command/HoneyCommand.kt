//2.File: HoneyCommand.kt
package winlyps.honeyNoBees.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import winlyps.honeyNoBees.HoneyNoBees

class HoneyCommand(private val plugin: HoneyNoBees) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("honey", ignoreCase = true)) {
            sender.sendMessage("HoneyNoBees plugin is active!")
            return true
        }
        return false
    }
}