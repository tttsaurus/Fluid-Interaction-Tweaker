package com.tttsaurus.fluidintetweaker.plugin.grs;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import com.cleanroommc.groovyscript.documentation.linkgenerator.LinkGeneratorHooks;
import com.tttsaurus.fluidintetweaker.Tags;
import org.jetbrains.annotations.NotNull;

public final class GrSFluidInteractionPlugin implements GroovyPlugin
{
    @GroovyBlacklist
    public static GrSFluidInteractionPropertyContainer propertyContainerInstance;

    @Override
    public @NotNull String getModId()
    {
        return Tags.MODID;
    }

    @Override
    public @NotNull String getContainerName()
    {
        return Tags.MODNAME;
    }

    @Override
    public GroovyPropertyContainer createGroovyPropertyContainer()
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
