package com.waterloorocketry.thrustmod;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import info.openrocket.core.document.Simulation;
import info.openrocket.swing.gui.SpinnerEditor;
import info.openrocket.swing.gui.adaptors.DoubleModel;
import info.openrocket.core.plugin.Plugin;
import info.openrocket.swing.simulation.extension.AbstractSwingSimulationExtensionConfigurator;
import info.openrocket.core.unit.UnitGroup;


@Plugin

public class ThrustModPluginConfigurator extends AbstractSwingSimulationExtensionConfigurator<ThrustModPlugin> {


    public ThrustModPluginConfigurator() {
        super(ThrustModPlugin.class);
    }

    @Override
    protected JComponent getConfigurationComponent(ThrustModPlugin extension, Simulation simulation, JPanel panel) {

        panel.add(new JLabel("Reference Atmospheric Pressure:"));


        DoubleModel m = new DoubleModel(extension, "RefAtmPressure", UnitGroup.UNITS_DISTANCE, 0);

        JSpinner spin = new JSpinner(m.getSpinnerModel());
        spin.setEditor(new SpinnerEditor(spin));
        panel.add(spin, "w 65lp!");

        return panel;

    }

}