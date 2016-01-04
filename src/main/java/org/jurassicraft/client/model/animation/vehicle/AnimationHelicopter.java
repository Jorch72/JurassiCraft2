package org.jurassicraft.client.model.animation.vehicle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.timeless.animationapi.client.model.json.IModelAnimator;
import net.timeless.animationapi.client.model.json.ModelJson;
import net.timeless.animationapi.client.model.tools.MowzieModelRenderer;
import org.jurassicraft.common.vehicles.helicopter.EntityHelicopterBase;

@SideOnly(Side.CLIENT)
public class AnimationHelicopter implements IModelAnimator
{
    private float partialTicks = 0;

    @Override
    public void setRotationAngles(ModelJson model, float f, float f1, float rotation, float rotationYaw, float rotationPitch, Entity entity)
    {
        EntityHelicopterBase helicopter = (EntityHelicopterBase) entity;

        MowzieModelRenderer rotor = model.getCube("rotorbase_rotatehere");
        MowzieModelRenderer tailrotor = model.getCube("tailrotor_rotatehere");
        // rotor.rotateAngleY += helicopter.getEnginePower()*partialTicks;
        float time = helicopter.getEnginePower() / EntityHelicopterBase.MAX_POWER;
        rotor.rotateAngleY = easeInCubic(time, rotor.rotateAngleY, helicopter.getEnginePower() * partialTicks, 1f);
        rotor.rotateAngleY %= 360f;

        tailrotor.rotateAngleX = easeInCubic(time, tailrotor.rotateAngleX, helicopter.getEnginePower() * partialTicks, 1f);
        tailrotor.rotateAngleX %= 360f;
        // Be sure that this works only with the lastest version of Unilib!
    }

    @Override
    public void preRenderCallback(EntityLivingBase entity, float partialTicks)
    {
        this.partialTicks = partialTicks;
    }

    private float easeInCubic(float time, float startValue, float change, float duration)
    {
        time /= duration;
        return change * time * time * time + startValue;
    }
}
