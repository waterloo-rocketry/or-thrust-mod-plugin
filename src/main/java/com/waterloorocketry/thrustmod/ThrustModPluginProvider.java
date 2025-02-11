package com.waterloorocketry.thrustmod;

import info.openrocket.core.plugin.Plugin;

import info.openrocket.core.simulation.extension.AbstractSimulationExtensionProvider;


@Plugin
public class ThrustModPluginProvider extends AbstractSimulationExtensionProvider {

    public ThrustModPluginProvider() {

        super(ThrustModPlugin.class, "Waterloo Rocketry", "Thrust Modification");

    }

}