package com.moffy5612.soulstrike.integration.tconstruct.trait;

import java.util.ArrayList;
import java.util.List;

import slimeknights.tconstruct.library.modifiers.Modifier;

public class Traits {
    public static final ModifierSoulConnection SOUL_CONNECTION = new ModifierSoulConnection();
    public static final ModifierSoulAround SOUL_AROUND = new ModifierSoulAround();

    public static List<Modifier> regArmorModifiers = new ArrayList<Modifier>();
}
