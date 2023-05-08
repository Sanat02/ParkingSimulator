package carSimulation.stateOfCar;

import carSimulation.entity.Auto;


public interface State {

    void changeState(Auto auto);
}
