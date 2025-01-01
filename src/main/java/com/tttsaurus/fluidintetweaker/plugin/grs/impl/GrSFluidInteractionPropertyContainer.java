package com.tttsaurus.fluidintetweaker.plugin.grs.impl;

import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;

public class GrSFluidInteractionPropertyContainer extends GroovyPropertyContainer
{
    public final FITweaker tweaker = new FITweaker();

    public GrSFluidInteractionPropertyContainer()
    {
        addProperty(tweaker);
    }
}
