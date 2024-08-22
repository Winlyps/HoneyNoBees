//1.File: HoneyNoBees.kt
package winlyps.honeyNoBees

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.type.Beehive
import org.bukkit.plugin.java.JavaPlugin
import winlyps.honeyNoBees.listener.HoneyListener

class HoneyNoBees : JavaPlugin() {

    override fun onEnable() {
        // Register listener
        val honeyListener = HoneyListener(this)
        server.pluginManager.registerEvents(honeyListener, this)

        // Scan for existing beehives
        scanForBeehives(honeyListener)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun scanForBeehives(honeyListener: HoneyListener) {
        Bukkit.getScheduler().runTaskLater(this, Runnable {
            for (world in Bukkit.getWorlds()) {
                for (chunk in world.loadedChunks) {
                    for (x in 0 until 16) {
                        for (z in 0 until 16) {
                            for (y in 0 until world.maxHeight) {
                                val block = chunk.getBlock(x, y, z)
                                if (block.type == Material.BEEHIVE) {
                                    honeyListener.addBeehiveToUpdate(block)
                                }
                            }
                        }
                    }
                }
            }
            honeyListener.scheduleHoneyIncreaseTask()
        }, 20L) // Delay the scan to ensure all chunks are loaded
    }
}
