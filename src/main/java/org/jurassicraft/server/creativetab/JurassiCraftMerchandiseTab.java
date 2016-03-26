package org.jurassicraft.server.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.base.EntityHandler;
import org.jurassicraft.server.item.ItemHandler;

public class JurassiCraftMerchandiseTab extends CreativeTabs
{
    private int[] metas;

    public JurassiCraftMerchandiseTab(String label)
    {
        super(label);
        this.metas = new int[EntityHandler.INSTANCE.getRegisteredDinosaurs().size()];

        int i = 0;

        for (Dinosaur dino : EntityHandler.INSTANCE.getDinosaurs())
        {
            if (dino.shouldRegister())
            {
                metas[i] = EntityHandler.INSTANCE.getDinosaurId(dino);

                i++;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
        return new ItemStack(getTabIconItem(), 1, metas[((int) ((JurassiCraft.timerTicks / 20) % metas.length))]);
    }

    @Override
    public Item getTabIconItem()
    {
        return ItemHandler.INSTANCE.action_figure;
    }
}
