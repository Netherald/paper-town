package net.netherald.papertown

import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.projecttl.pbalance.api.Economy
import org.bukkit.plugin.java.JavaPlugin

class PaperTown : JavaPlugin() {

    override fun onEnable() {
    }

    fun registerCommand() {
        kommand {
            register("마을") {
                then("생성") {
                    then("마을 이름" to string()) {
                        executes { ctx ->
                            val townName : String = ctx["마을 이름"]
                            val sender = ctx.source
                            if (!sender.isPlayer) {
                                sender.sender.sendMessage(Component.text("플레이어만 사용할 수 있는 명령어 입니다.").color(NamedTextColor.RED))
                                return@executes
                            }
                            val playerEconomy = Economy(sender.player)
                            if (playerEconomy.money < 20000) {
                                sender.player.sendMessage(Component.text("마을을 생성할 돈이 부족합니다 (${playerEconomy.money}/10000).").color(NamedTextColor.GOLD))
                                return@executes
                            }
                            playerEconomy.removeMoney(10000)
                            InitSQLDriver(this@PaperTown).createTown(townName,sender.player)
                        }
                    }
                }
            }
        }
    }
}