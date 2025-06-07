package com.tttsaurus.fluidintetweaker.plugin.grs.impl;

import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;
import com.tttsaurus.fluidintetweaker.Tags;

public class FluidInteractionLinkGenerator extends BasicLinkGenerator
{
    @Override
    public String id()
    {
        return Tags.MODID;
    }
    @Override
    protected String domain()
    {
        return "https://github.com/tttsaurus/Fluid-Interaction-Tweaker/";
    }
}
