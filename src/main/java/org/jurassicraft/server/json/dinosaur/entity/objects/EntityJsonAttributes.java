package org.jurassicraft.server.json.dinosaur.entity.objects;

import com.google.gson.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.util.JsonUtils;
import org.jurassicraft.server.json.dinosaur.entity.EntityDinosaurJsonHandler;

import java.lang.reflect.Type;

public class EntityJsonAttributes {

    IAttribute attribute;
    double baseValue;

    public EntityJsonAttributes(IAttribute attribute, float baseValue) {
        super();
        this.attribute = attribute;
        this.baseValue = baseValue;
    }

    public void apply(EntityLivingBase entity) {
        entity.getEntityAttribute(this.attribute).setBaseValue(this.baseValue);
    }

    public static class Deserializer implements JsonDeserializer<EntityJsonAttributes> {

        @Override
        public EntityJsonAttributes deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = JsonUtils.getJsonObject(element, "attributes");
            String name = JsonUtils.getString(json, "name");
            IAttribute attribute = EntityDinosaurJsonHandler.ATTRIBUTE_MAP.get(name);
            if(attribute == null) {
                throw new IllegalArgumentException("Unable to find attribute with name " + name);
            }
            return new EntityJsonAttributes(attribute, JsonUtils.getFloat(json, "base_value"));
        }
    }

}