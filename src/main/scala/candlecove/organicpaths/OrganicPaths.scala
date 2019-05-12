package candlecove.organicpaths

import candlecove.organicpaths.blocks._
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.{Item, ItemBlock}
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.registries.IForgeRegistry

import scala.collection.{immutable => imm}


@Mod( modid = OrganicPaths.MODID, version = OrganicPaths.VERSION, modLanguage = "scala" )
@Mod.EventBusSubscriber
object OrganicPaths {
  final val MODID = "organicpaths"
  final val VERSION = "0.1"

  @EventHandler
  def postInit(event: FMLPreInitializationEvent): Unit = {

    println( s"**** OrganicPaths ${event.getModState}" )

    //Blocks.DIRT = new blocks.CompressibleGround

    //Blocks.



    // Dont know if this is valid.
    println(s"33333333333333 DIRT BLOCK >> ${Blocks.DIRT.getLocalizedName}")

  }

  var CompressedDirt = new CompressedDirt



  @SubscribeEvent
  def registerBlocks( event: RegistryEvent.Register[Block]): Unit = {

    println( s"Registering new blocks" )

    event.getRegistry.register( CompressedDirt )
    //event.getRegistry.register( new CompressibleDirt )

    var key = event.getRegistry.getKey( CompressedDirt )

  }

  @SubscribeEvent
  def registerItemBlocks( event: RegistryEvent.Register[Item]): Unit = {

    println( s"Registering new blocks" )

    event.getRegistry.register( new AutoItemBlock( new CompressedDirt ) )
    //event.getRegistry.register( new AutoItemBlock( new CompressibleDirt ) )
  }

  import net.minecraft.block.Block
  import net.minecraft.init.Blocks
  import net.minecraft.util.math.BlockPos
  import net.minecraft.world.chunk.Chunk
  import net.minecraftforge.event.world.ChunkEvent
  import net.minecraftforge.fml.common.eventhandler.EventPriority
  import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

  var replacements = imm.HashMap.empty[Block, Block]

  //replacements += (Blocks.GRASS -> CompressedDirt)
  //replacements += (Blocks.DIRT -> CompressedDirt)


  @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
  def onEvent(event: ChunkEvent.Load): Unit = {

    val theChunk = event.getChunk
    // replace all blocks of a type with another block type
    for (x <- 0 until 16)
    {
      for (z <- 0 until 16)
      {
        for (y <- 0 until 256)
        {
          import net.minecraft.util.math.BlockPos
          val blockPos = new BlockPos(x, y, z)
          val curBlock = theChunk.getBlockState( blockPos ).getBlock

          val newBlockOpt = replacements.get( curBlock )

          newBlockOpt.map( b => theChunk.setBlockState( blockPos, b.getDefaultState ))
        }
      }
    }
    theChunk.markDirty()
  }




}