package candlecove.organicpaths.blocks

import java.util

import net.minecraft.init.Blocks
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.item.{ItemBlock, ItemStack}
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.event.terraingen.DecorateBiomeEvent
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraft.item.ItemBlock
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.registry.GameRegistry

import scala.collection.immutable.Vector

class AutoItemBlock(block: Block) extends ItemBlock(block) {
  setRegistryName( block.getRegistryName )
}




class CompressedDirt() extends Block(Material.GROUND)  {
  setCreativeTab(CreativeTabs.BUILDING_BLOCKS) // the block will appear on the Blocks tab in creative
  setRegistryName( s"compresseddirt" )

  // the block will render in the SOLID layer.  See http://greyminecraftcoder.blogspot.co.at/2014/12/block-rendering-18.html for more information.
  @SideOnly(Side.CLIENT)
  def getBlockLayer = BlockRenderLayer.SOLID

  // used by the renderer to control lighting and visibility of other blocks.
  // set to true because this block is opaque and occupies the entire 1x1x1 space
  // not strictly required because the default (super method) is true
  override def isOpaqueCube(iBlockState: IBlockState) = true

  // used by the renderer to control lighting and visibility of other blocks, also by
  // (eg) wall or fence to control whether the fence joins itself to this block
  // set to true because this block occupies the entire 1x1x1 space
  override def isFullCube(iBlockState: IBlockState) = true

  // render using a BakedModel (mbe01_block_simple.json --> mbe01_block_simple_model.json)
  // not strictly required because the default (super method) is MODEL.
  override def getRenderType(iBlockState: IBlockState) = EnumBlockRenderType.MODEL

  @SideOnly(Side.SERVER)
  override def onEntityWalk(worldIn: World, pos: BlockPos, entityIn: Entity): Unit = {

    println( s"STEPPED ON" )

  }
}




class CompressibleDirt() extends Block(Material.GROUND) {


  setCreativeTab(CreativeTabs.BUILDING_BLOCKS) // the block will appear on the Blocks tab in creative
  setRegistryName( Blocks.DIRT.getRegistryName )

  setDefaultState( Blocks.DIRT.getDefaultState )


  // the block will render in the SOLID layer.  See http://greyminecraftcoder.blogspot.co.at/2014/12/block-rendering-18.html for more information.
  @SideOnly(Side.CLIENT)
  def getBlockLayer = BlockRenderLayer.SOLID

  // used by the renderer to control lighting and visibility of other blocks.
  // set to true because this block is opaque and occupies the entire 1x1x1 space
  // not strictly required because the default (super method) is true
  override def isOpaqueCube(iBlockState: IBlockState) = true

  // used by the renderer to control lighting and visibility of other blocks, also by
  // (eg) wall or fence to control whether the fence joins itself to this block
  // set to true because this block occupies the entire 1x1x1 space
  override def isFullCube(iBlockState: IBlockState) = true

  // render using a BakedModel (mbe01_block_simple.json --> mbe01_block_simple_model.json)
  // not strictly required because the default (super method) is MODEL.
  override def getRenderType(iBlockState: IBlockState) = EnumBlockRenderType.MODEL

  override def onEntityWalk(worldIn: World, pos: BlockPos, entityIn: Entity): Unit = {

    println( s"STEPPED ON" )

  }
}



class CompressibleDirtItemBlock extends ItemBlock( new CompressibleDirt )  {

  import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate
  import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


  override def addInformation(stack: ItemStack, worldIn: World, tooltip: util.List[String], flagIn: ITooltipFlag): Unit = {
    super.addInformation(stack, worldIn, tooltip, flagIn)

    tooltip.add( s"Howdy" )
  }
}

object CompressibleDirtItemBlock
{
  /*
  @SubscribeEvent
  def plants(e: DecorateBiomeEvent.Decorate): Unit = {

    if ((e.getType eq Decorate.EventType.FLOWERS) && (e.getWorld.getBiome(e.getPlacementPos) eq WASTELAND))
    e.getWorld.setBlockState(e.getPlacementPos, Blocks.GOLD_BLOCK.getDefaultState)
  }
   */
}

