package Step2Bonus1.enumiration;

import Step2Bonus1.utilities.Colors;

public enum Scale {
    INCREASE(Colors.RED+"Данные значения после увеличения масштаба"),
    DECREASE(Colors.RED+"Данные значения после уменьшения масштаба"),
    NO_CHANGE(Colors.GREEN_BOLD+"Данные значения не меняли масштаба");
    private String value;

    Scale(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
