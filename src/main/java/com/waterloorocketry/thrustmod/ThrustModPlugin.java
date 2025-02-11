package com.waterloorocketry.thrustmod;

import info.openrocket.core.aerodynamics.FlightConditions;
import info.openrocket.core.simulation.SimulationConditions;
import info.openrocket.core.simulation.SimulationStatus;
import info.openrocket.core.simulation.exception.SimulationException;
import info.openrocket.core.simulation.extension.AbstractSimulationExtension;
import info.openrocket.core.simulation.listeners.AbstractSimulationListener;

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
        return config.getDouble("RefAtmPressure", 1.0); // TODO: Default?
    }

    private class ThrustModListener extends AbstractSimulationListener {

        private double Ae; // nozzle exit area TODO: How to get? Multiple boosters?
        private double atm; // current atmospheric pressure
        private final double atmRef; // reference atmospheric pressure

        private ThrustModListener() {
            this.atmRef = getRefAtmPressure();
            this.atm = atmRef;
        }

        public void startSimulation(SimulationStatus status) throws SimulationException {

        }

        @Override
        public FlightConditions postFlightConditions(SimulationStatus status, FlightConditions flightConditions) throws SimulationException {
            this.atm = flightConditions.getAtmosphericConditions().getPressure();

            return flightConditions;
        }

        @Override
        public double postSimpleThrustCalculation(SimulationStatus status, double thrust) throws SimulationException {
            thrust += (atmRef - atm) * Ae;

            return thrust;
        }
    }
}