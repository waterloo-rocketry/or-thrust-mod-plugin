package com.waterloorocketry.thrustmod;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import info.openrocket.core.document.Simulation;
import info.openrocket.swing.gui.SpinnerEditor;
import info.openrocket.swing.gui.adaptors.DoubleModel;
import info.openrocket.core.plugin.Plugin;
import info.openrocket.swing.gui.components.BasicSlider;
import info.openrocket.swing.gui.components.UnitSelector;
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


        DoubleModel refAtmPressureModel = new DoubleModel(extension, "RefAtmPressure", UnitGroup.UNITS_PRESSURE, 0);

        JSpinner refAtmPressureSpinner = new JSpinner(refAtmPressureModel.getSpinnerModel());
        refAtmPressureSpinner.setEditor(new SpinnerEditor(refAtmPressureSpinner));
        panel.add(refAtmPressureSpinner, "w 80lp!");

        UnitSelector refAtmPressureUnit = new UnitSelector(refAtmPressureModel);
        panel.add(refAtmPressureUnit, "w 25");

        BasicSlider refAtmPressureSlider = new BasicSlider(refAtmPressureModel.getSliderModel(0, 500000));
        panel.add(refAtmPressureSlider, "w 75lp, wrap");

        UnitGroup.UNITS_LENGTH.setDefaultUnit("in");
        DoubleModel NozzleDiameterModel = new DoubleModel(extension, "NozzleDiameter", UnitGroup.UNITS_LENGTH, 0);

        JSpinner nozzleDiameterSpin = new JSpinner(NozzleDiameterModel.getSpinnerModel());
        nozzleDiameterSpin.setEditor(new SpinnerEditor(nozzleDiameterSpin));
        panel.add(nozzleDiameterSpin, "w 80lp!");

        UnitSelector nozzleDiameterUnit = new UnitSelector(NozzleDiameterModel);
        panel.add(nozzleDiameterUnit, "w 25");

        BasicSlider nozzleDiameterSlider = new BasicSlider(NozzleDiameterModel.getSliderModel(0, 100));
        panel.add(nozzleDiameterSlider, "w 75lp, wrap");

        return panel;

    }

}