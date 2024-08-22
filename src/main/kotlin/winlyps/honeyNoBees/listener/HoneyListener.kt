//3.File: HoneyListener.kt
package winlyps.honeyNoBees.listener

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.type.Beehive
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import winlyps.honeyNoBees.HoneyNoBees

class HoneyListener(private val plugin: HoneyNoBees) : Listener {

    private val beehivesToUpdate = mutableSetOf<Block>()

    @EventHandler
    fun onBeehivePlace(event: BlockPlaceEvent) {
        val block: Block = event.block
        if (block.type == Material.BEEHIVE) {
            addBeehiveToUpdate(block)
            scheduleHoneyIncreaseTask()
        }
    }

    fun addBeehiveToUpdate(block: Block) {
        beehivesToUpdate.add(block)
    }

    fun scheduleHoneyIncreaseTask() {
        if (beehivesToUpdate.isNotEmpty()) {
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                for (block in beehivesToUpdate) {
                    val beehiveData = block.blockData as? Beehive
                    if (beehiveData != null) {
                        beehiveData.honeyLevel = beehiveData.maximumHoneyLevel
                        block.blockData = beehiveData
                        block.state.update(true, false)
                    }
                }
                schedulePeriodicHoneyCheck()
            }, 100L) // 100 ticks = 5 seconds
        }
    }

    private fun schedulePeriodicHoneyCheck() {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            val iterator = beehivesToUpdate.iterator()
            while (iterator.hasNext()) {
                val block = iterator.next()
                val beehiveData = block.blockData as? Beehive
                if (beehiveData != null) {
                    if (beehiveData.honeyLevel == 0) {
                        beehiveData.honeyLevel = beehiveData.maximumHoneyLevel
                        block.blockData = beehiveData
                        block.state.update(true, false)
                    }
                } else {
                    iterator.remove()
                }
            }
        }, 200L, 200L) // 200 ticks = 10 seconds, check every 10 seconds
    }
}