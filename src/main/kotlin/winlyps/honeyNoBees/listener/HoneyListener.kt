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
import java.util.concurrent.ConcurrentHashMap

class HoneyListener(private val plugin: HoneyNoBees) : Listener {

    private val beehives = ConcurrentHashMap<Block, Beehive>()

    init {
        startHoneyIncreaseTask(plugin)
    }

    @EventHandler
    fun onBeehivePlace(event: BlockPlaceEvent) {
        val block: Block = event.block
        if (block.type == Material.BEEHIVE) {
            val beehiveData = block.blockData as? Beehive
            if (beehiveData != null) {
                beehives[block] = beehiveData
            }
        }
    }

    private fun startHoneyIncreaseTask(plugin: Plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            val iterator = beehives.entries.iterator()
            while (iterator.hasNext()) {
                val (block, beehiveData) = iterator.next()
                if (beehiveData.honeyLevel < beehiveData.maximumHoneyLevel) {
                    beehiveData.honeyLevel++
                    block.blockData = beehiveData
                    block.state.update()
                } else {
                    iterator.remove()
                }
            }
        }, 0L, 100L).taskId // 100 ticks = 5 seconds
    }
}