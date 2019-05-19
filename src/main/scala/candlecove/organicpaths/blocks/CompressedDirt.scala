package candlecove.organicpaths.blocks

import java.util

import net.minecraft.init.Blocks
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.{BlockFaceShape, IBlockState}
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.{Entity, EntityLiving}
import net.minecraft.item.{ItemBlock, ItemStack}
import net.minecraft.util.{BlockRenderLayer, EnumBlockRenderType, EnumFacing, ResourceLocation}
import net.minecraft.util.math.{BlockPos, Vec3d}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.event.terraingen.DecorateBiomeEvent
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraft.item.ItemBlock
import net.minecraft.pathfinding.PathNodeType
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.registry.GameRegistry

import scala.collection.immutable.Vector
import scala.util.Random

class AutoItemBlock(block: Block) extends ItemBlock(block) {
  setRegistryName( block.getRegistryName )
}


object PathBlocks {

  val CompressedDirt_01 = new CompressedDirt_01
  val CompressedDirt_02 = new CompressedDirt_02
  val CompressedDirt_03 = new CompressedDirt_03
  val CompressedDirt_04 = new CompressedDirt_04

}


class PathBlock( val offset: Vec3d ) extends Block(Material.GROUND) {
  setCreativeTab(CreativeTabs.BUILDING_BLOCKS) // the block will appear on the Blocks tab in creative
  setTickRandomly( true )

  val s_rand = new Random()

  override def getOffset( state: IBlockState, worldIn: IBlockAccess, pos: BlockPos ): Vec3d = {
    offset
  }


  override def randomTick( worldIn: World, pos: BlockPos, state: IBlockState, random: util.Random ): Unit = {

    super.randomTick( worldIn, pos, state, random )

    if( s_rand.nextFloat < 1.00f ) {
      worldIn.setBlockState( pos, getPrevBlockType.getDefaultState )

      //println( s"**** randomTick" )
    }

  }

  // the block will render in the SOLID layer.  See http://greyminecraftcoder.blogspot.co.at/2014/12/block-rendering-18.html for more information.
  @SideOnly(Side.CLIENT)
  def getBlockLayer = BlockRenderLayer.SOLID

  // used by the renderer to control lighting and visibility of other blocks.
  // set to true because this block is opaque and occupies the entire 1x1x1 space
  // not strictly required because the default (super method) is true
  override def isOpaqueCube(iBlockState: IBlockState) = false

  // used by the renderer to control lighting and visibility of other blocks, also by
  // (eg) wall or fence to control whether the fence joins itself to this block
  // set to true because this block occupies the entire 1x1x1 space
  override def isFullCube(iBlockState: IBlockState) = true

  // render using a BakedModel (mbe01_block_simple.json --> mbe01_block_simple_model.json)
  // not strictly required because the default (super method) is MODEL.
  override def getRenderType(iBlockState: IBlockState) = EnumBlockRenderType.MODEL

  def getPrevBlockType(): Block = Blocks.GRASS
  def getNextBlockType(): Block = PathBlocks.CompressedDirt_02

  override def onEntityWalk(worldIn: World, pos: BlockPos, ent: Entity): Unit = {

    val dx = Math.abs( ent.posX - ent.prevPosX )
    val dz = Math.abs( ent.posZ - ent.prevPosZ )

    if( !ent.onGround || ( dx < 0.05 && dz < 0.05 ) ) return


    if( s_rand.nextFloat < 0.1f ) {
      worldIn.setBlockState( pos, getNextBlockType.getDefaultState )
    }

  }
}


class CompressedDirt_01() extends PathBlock( new Vec3d( 0, -(0.5f / 16.0f), 0 ) )  {
  setRegistryName( s"compresseddirt_01" )

  override def getPrevBlockType(): Block = Blocks.GRASS
  override def getNextBlockType(): Block = PathBlocks.CompressedDirt_02

}


class CompressedDirt_02() extends PathBlock( new Vec3d( 0, -(1.0f / 16.0f), 0 ) )  {
  setRegistryName( s"compresseddirt_02" )

  override def getPrevBlockType(): Block = PathBlocks.CompressedDirt_01
  override def getNextBlockType(): Block = PathBlocks.CompressedDirt_03

}


class CompressedDirt_03() extends PathBlock( new Vec3d( 0, -(1.5f / 16.0f), 0 ) )  {
  setRegistryName( s"compresseddirt_03" )

  override def getPrevBlockType(): Block = PathBlocks.CompressedDirt_02
  override def getNextBlockType(): Block = PathBlocks.CompressedDirt_04

}


class CompressedDirt_04() extends PathBlock( new Vec3d( 0, -(2.0f / 16.0f), 0 ) )  {
  setRegistryName( s"compresseddirt_04" )

  override def getPrevBlockType(): Block = PathBlocks.CompressedDirt_03
  override def getNextBlockType(): Block = Blocks.COBBLESTONE

}

