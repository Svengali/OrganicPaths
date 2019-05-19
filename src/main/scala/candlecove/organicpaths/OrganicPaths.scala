package candlecove.organicpaths

import candlecove.organicpaths.blocks._
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.{Item, ItemBlock}
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{SoundCategory, SoundEvent}
import net.minecraft.world.{IWorldEventListener, World}
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent
import net.minecraftforge.registries.IForgeRegistry

import scala.collection.{immutable => imm}
import scala.util.Random


@Mod( modid = OrganicPaths.MODID, version = OrganicPaths.VERSION, modLanguage = "scala" )
@Mod.EventBusSubscriber
object OrganicPaths  {
	final val MODID = "organicpaths"
	final val VERSION = "0.1"

	val s_rand = new Random()



	@EventHandler
	def postInit(event: FMLPreInitializationEvent): Unit = {

		println( s"**** OrganicPaths ${event.getModState}" )

		//Blocks.DIRT = new blocks.CompressibleGround

		//Blocks.



		// Dont know if this is valid.
		println(s"33333333333333 DIRT BLOCK >> ${Blocks.DIRT.getLocalizedName}")

	}

	//var CompressedDirt = new CompressedDirt



	@SubscribeEvent
	def registerBlocks( event: RegistryEvent.Register[Block]): Unit = {

		println( s"Registering new blocks" )

		event.getRegistry.register( PathBlocks.CompressedDirt_01 )
		event.getRegistry.register( PathBlocks.CompressedDirt_02 )
		event.getRegistry.register( PathBlocks.CompressedDirt_03 )
		event.getRegistry.register( PathBlocks.CompressedDirt_04 )

	}

	@SubscribeEvent
	def registerItemBlocks( event: RegistryEvent.Register[Item]): Unit = {

		println( s"Registering new blocks" )

		event.getRegistry.register( new AutoItemBlock( new CompressedDirt_01 ) )
		event.getRegistry.register( new AutoItemBlock( new CompressedDirt_02 ) )
		event.getRegistry.register( new AutoItemBlock( new CompressedDirt_03 ) )
		event.getRegistry.register( new AutoItemBlock( new CompressedDirt_04 ) )
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


	/*
	WorldTickEvent

	ClientChatEvent

	EntityConstructing

	LivingUpdateEvent

	 */

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	def onEvent( event: WorldTickEvent ): Unit ={

		//println( s"WorldTickEvent: $event" )

	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	def onEvent( event: LivingUpdateEvent ): Unit ={

		val ent = event.getEntityLiving

		val blockPath = Blocks.GRASS_PATH

		//val props = blockPath.getBlockState.getProperties

		val dx = Math.abs( ent.posX - ent.prevPosX )
		val dz = Math.abs( ent.posZ - ent.prevPosZ )

		if( ent.onGround && ( dx > 0.01 || dz > 0.01 ) ) {

			if( s_rand.nextFloat > 0.1f )	return

			val blockPos = ent.getPosition.down

			val block = ent.world.getBlockState( blockPos )

			val name = block.getBlock.getRegistryName

			if( name equals Blocks.GRASS.getRegistryName ) {
				ent.world.setBlockState( blockPos, PathBlocks.CompressedDirt_02.getDefaultState )
			}
		}

	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	def onEvent( event: EntityConstructing ): Unit ={

		//println( s"EntityConstructing: $event" )

	}




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


object WorldEntityListener extends IWorldEventListener {

	override def notifyBlockUpdate(worldIn: World, pos: BlockPos, oldState: IBlockState, newState: IBlockState, flags: Int): Unit = ???

	override def notifyLightSet(pos: BlockPos): Unit = ???

	override def markBlockRangeForRenderUpdate(x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int): Unit = ???

	override def playSoundToAllNearExcept(player: EntityPlayer, soundIn: SoundEvent, category: SoundCategory, x: Double, y: Double, z: Double, volume: Float, pitch: Float): Unit = ???

	override def playRecord(soundIn: SoundEvent, pos: BlockPos): Unit = ???

	override def spawnParticle(particleID: Int, ignoreRange: Boolean, xCoord: Double, yCoord: Double, zCoord: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double, parameters: Int*): Unit = ???

	override def spawnParticle(id: Int, ignoreRange: Boolean, minimiseParticleLevel: Boolean, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double, parameters: Int*): Unit = ???

	override def onEntityAdded(entityIn: Entity): Unit = ???

	override def onEntityRemoved(entityIn: Entity): Unit = ???

	override def broadcastSound(soundID: Int, pos: BlockPos, data: Int): Unit = ???

	override def playEvent(player: EntityPlayer, `type`: Int, blockPosIn: BlockPos, data: Int): Unit = ???

	override def sendBlockBreakProgress(breakerId: Int, pos: BlockPos, progress: Int): Unit = ???
}