package net.netherald.papertown

import io.github.monun.kommand.kommand
import org.bukkit.plugin.java.JavaPlugin

class PaperTown : JavaPlugin() {

    override fun onEnable() {
        SQLManager(this).init()
    }

    fun registerCommand() {
        kommand {
            register("마을") {
                then("생성") {
                    then("마을 이름" to string()) {
                        executes { ctx ->
                            val townName : String = ctx["마을 이름"]

                        }
                    }
                }
            }
        }
    }
}