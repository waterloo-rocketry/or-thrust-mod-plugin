package com.waterloorocketry.thrustmod;

import info.openrocket.core.aerodynamics.FlightConditions;
import info.openrocket.core.simulation.SimulationConditions;
import info.openrocket.core.simulation.SimulationStatus;
import info.openrocket.core.simulation.exception.SimulationException;
import info.openrocket.core.simulation.extension.AbstractSimulationExtension;
import info.openrocket.core.simulation.listeners.AbstractSimulationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Initialize the plugin.
 */
public class ThrustModPlugin extends AbstractSimulationExtension {

    @Override
    public String getName()
    {
        return "Thrust Modification";
    }

    @Override
    public String getDescription() {
        return "Plugin to modify thrust curves based on local atmospheric pressure";
    }

    /**
     * Initialize this extension before simulations by adding the simulation listener.
     * @param conditions
     * @throws SimulationException
     */
    @Override
    public void initialize(SimulationConditions conditions) throws SimulationException {
        conditions.getSimulationListenerList().add(new ThrustModListener());
    }

    public double getRefAtmPressure() {
        return config.getDouble("RefAtmPressure", 100000.0);
    }

    public void setRefAtmPressure(double refAtmPressure) {
        config.put("RefAtmPressure", refAtmPressure);
        fireChangeEvent();
    }

    public double getNozzleDiameter() {
        return config.getDouble("NozzleDiameter", 0.1524);
    }

    public void setNozzleDiameter(double nozzleDiameter) {
        config.put("NozzleDiameter", nozzleDiameter);
        fireChangeEvent();
    }

    private class ThrustModListener extends AbstractSimulationListener {

        private static final Logger log = LoggerFactory.getLogger(ThrustModPlugin.class);

        private double atm; // current atmospheric pressure
        private final double atmRef; // reference atmospheric pressure
        private final double Ae;

        private ThrustModListener() {
            this.atmRef = getRefAtmPressure();
            this.atm = atmRef;
            this.Ae = Math.pow(getNozzleDiameter() /2, 2) * Math.PI;
        }

        @Override
        public FlightConditions postFlightConditions(SimulationStatus status, FlightConditions flightConditions) throws SimulationException {
            this.atm = flightConditions.getAtmosphericConditions().getPressure();

            return flightConditions;
        }
        // Thrust calculated here
        // https://github.com/openrocket/openrocket/blob/bdf79b82144a9a406cff6c523d3cd550a1cd5c1c/core/src/main/java/info/openrocket/core/simulation/RK4SimulationStepper.java#L301
        @Override
        public double postSimpleThrustCalculation(SimulationStatus status, double thrust) throws SimulationException {

            if (thrust == 0 || Ae == 0) return thrust; // if rocket is not moving, do not modify

            double adjustmentFactor = (atmRef - atm) * Ae;

            System.out.println("Original thrust " + thrust);
            System.out.println("Thrust adjusted by " + adjustmentFactor);
            thrust += adjustmentFactor;

            return thrust;
        }
    }
}