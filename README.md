## Overview
- This is a (1.12.2 Minecraft Forge) CraftTweaker/GroovyScript addon that enables you to add more fluid interactions using the `zenscript`/`groovyscript`.
- This mod doesn't change anything about the vanilla water and lava interaction. Instead, the mod creates a new general system for fluid interactions.
- It can also act as a library mod for fluid interactions.

## Features
### Fluid Interaction
- Fluid turns into another block when it comes into contact with another fluid.
- Fluid turns into another block when it comes into contact with a block.
- A block turns into another block when it comes into contact with fluid. 
- Any of the above reactions can trigger a series of events. The available events are as follows:
  - Placing a block
  - Explosion
  - Spawning an entity
  - Spawning an item
### Fluid Behavior
- Fluids can perform a series of actions. The available actions are as follows:
  - Applying potion effects
  - Entity conversion
  - Instant forced fire extinguishing
  - Igniting nearby blocks
  - Covering nearby blocks with snow
  - Teleporting entities
  - Breaking blocks (supports OreDict)

## Todo List
- Expose `JEFIPlugin.addRecipeWrapper` to both crt and groovy
- Update groovy support
- Finish `bop` jei compat (maybe also fixing BoP's buggy fluid interactions)
- Finish `thermalfoundation` jei compat
- Add `abyssalcraft` jei compat
- Add more conditions for interaction and behavior
- Add `abyssalcraft`-like limited fluid spread mechanism
- Add jei compat for fluid behavior

## Credit
This mod is created using [GregTechCEu's Buildscripts](https://github.com/GregTechCEu/Buildscripts)

## Dependencies
- Optional: crafttweaker
- Optional: groovyscript
- Optional: zenutils (for crt reloading purposes)
- Optional: jei (for recipes to be displayed)
- Optional: modularui (makes jei recipes look better; no longer needed since v1.4.0)