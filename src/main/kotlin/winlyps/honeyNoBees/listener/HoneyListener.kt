//3.File: HoneyListener.kt
package winlyps.honeyNoBees.listener

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.type.Beehive
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.plugin.Plugin
import winlyps.honeyNoBees.HoneyNoBees

class HoneyListener(private val plugin: HoneyNoBees) : Listener {

    @EventHandler
    fun onBeehivePlace(event: BlockPlaceEvent) {
        val block: Block = event.block
        if (block.type == Material.BEEHIVE) {
            startHoneyIncreaseTask(block, plugin)
        }
    }

    private fun startHoneyIncreaseTask(block: Block, plugin: Plugin) {
        val taskId = Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            val beehiveData = block.blockData as? Beehive ?: return@Runnable
            if (beehiveData.honeyLevel < beehiveData.maximumHoneyLevel) {
                beehiveData.honeyLevel++
                block.blockData = beehiveData
                block.state.update()
            }
        }, 0L, 100L).taskId // 100 ticks = 5 seconds
    }
}