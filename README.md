This is a (1.12.2 Minecraft Forge) CraftTweaker addon that enables you to tweak the fluid interaction using the zenscripts.
This mod doesn't change anything about the vanilla water and lava interaction. Instead, the mod creates a new general system for fluid interaction.

Example Script (lava & water like interaction)
```
#reloadable

import mods.fluidintetweaker.FITweaker;

//addRecipe(ILiquidStack liquidInitiator, boolean isSourceA, ILiquidStack liquidSurrounding, IBlock outputBlock)

FITweaker.addRecipe(<liquid:cobalt>, false, <liquid:water>, <item:minecraft:stone>.asBlock());
FITweaker.addRecipe(<liquid:cobalt>, true, <liquid:water>, <item:minecraft:obsidian>.asBlock());
```
