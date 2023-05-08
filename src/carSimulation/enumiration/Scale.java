package carSimulation.enumiration;

import carSimulation.utilities.Colors;

public enum Scale {
    INCREASE(Colors.RED+"������ �������� ����� ���������� ��������"),
    DECREASE(Colors.RED+"������ �������� ����� ���������� ��������"),
    NO_CHANGE(Colors.GREEN_BOLD+"������ �������� �� ������ ��������");
    private String value;

    Scale(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
