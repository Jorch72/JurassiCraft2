package org.jurassicraft.server.block.machine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.block.BlockHandler;
import org.jurassicraft.server.block.OrientedBlock;
import org.jurassicraft.server.creativetab.TabHandler;
import org.jurassicraft.server.tileentity.CleaningStationTile;

import java.util.Random;

public class CleaningStationBlock extends OrientedBlock
{
    public CleaningStationBlock()
    {
        super(Material.iron);
        this.setUnlocalizedName("cleaning_station");
        this.setHardness(2.0F);
        this.setLightOpacity(0);
        this.setStepSound(Block.soundTypeMetal);
        this.setCreativeTab(TabHandler.INSTANCE.blocks);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof CleaningStationTile)
            {
                ((CleaningStationTile) tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof CleaningStationTile)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (CleaningStationTile) tileentity);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlockHandler.INSTANCE.cleaning_station);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return Item.getItemFromBlock(BlockHandler.INSTANCE.cleaning_station);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return true;
        }
        else if (!player.isSneaking())
        {
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity instanceof CleaningStationTile)
            {
                CleaningStationTile cleaningStation = (CleaningStationTile) tileEntity;

                if (cleaningStation.isUseableByPlayer(player))
                {
                    player.openGui(JurassiCraft.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new CleaningStationTile();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return true;
    }
}
