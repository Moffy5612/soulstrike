package com.moffy5612.soulstrike.event.modelbake;

import java.util.ArrayList;
import java.util.List;

import com.moffy5612.soulstrike.integration.tconstruct.event.ModelBakeCastingTable;

public class ModelBakeEvents {
    public static ModelBakeCastingTable TABLE_MODEL_BAKE;

    public static List<IModelBakeEvents> modelBakeEvents = new ArrayList<IModelBakeEvents>();
}
