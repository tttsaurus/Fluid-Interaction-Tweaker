package com.tttsaurus.fluidintetweaker.wrapper.grs.impl;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import com.cleanroommc.groovyscript.documentation.linkgenerator.LinkGeneratorHooks;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GrSFluidInteractionPlugin implements GroovyPlugin
{
    @GroovyBlacklist
    public static GrSFluidInteractionPropertyContainer propertyContainerInstance;

    @Override
    public @NotNull String getModId()
    {
        return FluidInteractionTweaker.MODID;
    }

    @Override
    public @NotNull String getContainerName()
    {
        return FluidInteractionTweaker.NAME;
    }

    @Override
    public @Nullable GroovyPropertyContainer createGroovyPropertyContainer()
    {
        propertyContainerInstance = new GrSFluidInteractionPropertyContainer();
        return propertyContainerInstance;
    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> groovyContainer)
    {
        LinkGeneratorHooks.registerLinkGenerator(new FluidInteractionLinkGenerator());
    }
}
