package com.tttsaurus.fluidintetweaker.wrapper.grs.impl;

import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;
import com.tttsaurus.fluidintetweaker.FluidInteractionTweaker;

public class FluidInteractionLinkGenerator extends BasicLinkGenerator
{
    @Override
    public String id()
    {
        return FluidInteractionTweaker.MODID;
    }
    @Override
    protected String domain()
    {
        return "https://github.com/tttsaurus/Fluid-Interaction-Tweaker/";
    }
}
