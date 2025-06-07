[![Versions](https://img.shields.io/curseforge/game-versions/1094214?logo=curseforge&label=Game%20Version)](https://www.curseforge.com/minecraft/mc-mods/fluid-interaction-tweaker)
[![Downloads](https://img.shields.io/curseforge/dt/1094214?logo=curseforge&label=Downloads)](https://www.curseforge.com/minecraft/mc-mods/fluid-interaction-tweaker)
[![Downloads](https://img.shields.io/modrinth/dt/fluid-interaction-tweaker?logo=modrinth&label=Downloads)](https://modrinth.com/mod/fluid-interaction-tweaker)

If you like this project, don't forget to give it a star⭐!

## Overview
- This is a CraftTweaker/GroovyScript addon that enables you to add more fluid interactions using the `zenscript`/`groovyscript`.
- This mod doesn't extend anything on the vanilla water and lava interaction system. Instead, the mod creates a new general system for fluid interactions.
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
  - Placing a fluid (experimental)
### Fluid Behavior
- Fluids can perform a series of actions. The available actions are as follows:
  - Applying potion effects
  - Entity conversion
  - Instant forced fire extinguishing
  - Igniting nearby blocks
  - Covering nearby blocks with snow
  - Teleporting entities
  - Breaking blocks (experimental; only supports OreDict for now)
### JEI Compatibilities
- Thermal Foundation

## Todo List
- Update groovy support
- Finish `bop`-jei compat (maybe also fixing `bop`'s buggy fluid interactions)
- Add `abyssalcraft`-jei compat
- Add more conditions for interaction and behavior
- Add `abyssalcraft`-like limited fluid spread mechanism
- Add configurable boolean expressions for conditions

Suggestions/PRs are welcome!

## Wiki
Here's the [Wiki Link](https://github.com/tttsaurus/Fluid-Interaction-Tweaker/wiki)

## Issues
Here's the [Issues Link](https://github.com/tttsaurus/Fluid-Interaction-Tweaker/issues)

## Credits
This mod is created using [GregTechCEu's Buildscripts](https://github.com/GregTechCEu/Buildscripts)

## Dependencies
- Optional: crafttweaker
- Optional: groovyscript
- Optional: zenutils (for crt reloading purposes)
- Optional: jei (for recipes to be displayed)
- Optional: modularui (makes jei recipes look better; no longer needed since v1.4.0)
