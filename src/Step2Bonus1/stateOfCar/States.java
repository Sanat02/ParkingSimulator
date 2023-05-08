package Step2Bonus1.stateOfCar;

import Step2Bonus1.entity.Auto;


public enum States implements State {
    ON_WAY("¬ œ”“»") {
        @Override
        public void changeState(Auto auto) {
            auto.setState(ON_PARK);
        }
    }, ON_PARK("Õ¿ œ¿– Œ¬ ≈") {
        @Override
        public void changeState(Auto auto) {
            auto.setState(ON_WAY);
        }
    };
    private String value;

    States(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
