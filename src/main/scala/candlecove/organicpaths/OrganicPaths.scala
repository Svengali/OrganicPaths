package candlecove.organicpaths

import net.minecraft.init.Blocks
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent


@Mod( modid = OrganicPaths.MODID, version = OrganicPaths.VERSION, modLanguage = "scala" )
object OrganicPaths {
  final val MODID = "organicpaths"
  final val VERSION = "0.1"

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = {

    //println(s"DIRT BLOCK >> ${Blocks.DIRT.getLocalizedName}")

    println( s"**** OrganicPaths ${event.getModState.getMarker}" )
  }

}