package net.timeless.jurassicraft.client.render.renderdef;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.timeless.jurassicraft.client.model.animation.AnimationAnkylosaurus;
import net.timeless.jurassicraft.common.entity.base.JCEntityRegistry;
import net.timeless.unilib.client.model.json.IModelAnimator;
import net.timeless.unilib.client.model.json.ModelJson;

@SideOnly(Side.CLIENT)
public class RenderDefAnkylosaurus extends RenderDinosaurDefinition
{
    private IModelAnimator animator;
    private ModelJson model;

    public RenderDefAnkylosaurus()
    {
        super(JCEntityRegistry.anklyosaurus);

        this.animator = new AnimationAnkylosaurus();

        try
        {
            this.model = getDefaultTabulaModel();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ModelBase getModel(int geneticVariant)
    {
        return model;
    }

    @Override
    public float getAdultScaleAdjustment()
    {
        return 2.6F;
    }

    @Override
    public float getBabyScaleAdjustment()
    {
        return 0.55F;
    }

    @Override
    public float getShadowSize()
    {
        return 0.65F;
    }

    @Override
    public IModelAnimator getModelAnimator(int geneticVariant)
    {
        return animator;
    }
}