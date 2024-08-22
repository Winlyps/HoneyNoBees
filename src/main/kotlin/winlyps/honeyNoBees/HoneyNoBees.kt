//1.File: HoneyNoBees.kt
package winlyps.honeyNoBees

import org.bukkit.plugin.java.JavaPlugin
import winlyps.honeyNoBees.command.HoneyCommand
import winlyps.honeyNoBees.listener.HoneyListener

class HoneyNoBees : JavaPlugin() {

    override fun onEnable() {
        // Register command and listener
        getCommand("honey")?.setExecutor(HoneyCommand(this))
        server.pluginManager.registerEvents(HoneyListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
