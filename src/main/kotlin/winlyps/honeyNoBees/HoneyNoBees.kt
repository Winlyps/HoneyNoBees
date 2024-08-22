//1.File: HoneyNoBees.kt
package winlyps.honeyNoBees

import org.bukkit.plugin.java.JavaPlugin
import winlyps.honeyNoBees.listener.HoneyListener

class HoneyNoBees : JavaPlugin() {

    override fun onEnable() {
        // Register listener
        server.pluginManager.registerEvents(HoneyListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
