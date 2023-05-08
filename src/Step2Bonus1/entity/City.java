package Step2Bonus1.entity;

import Step2Bonus1.utilities.Colors;

import java.util.ArrayList;
import java.util.List;

import static Step2Bonus1.utilities.Rnd.rnd;


public class City {
    private final String name;
    private List<Auto> totalAuto;

    public City() {
        String[] citiesName = {"������", "����� � ������", "���������", "���������", "����������"};
        this.name = citiesName[rnd(citiesName.length)];
        totalAuto = new ArrayList<>();
        int num = 1;
        for (int i = 0; i < 200; i++) {
            totalAuto.add(new Auto(num));
            num++;
        }
    }

    @Override
    public String toString() {
        return String.format(Colors.YELLOW+ "�����: %s ,������� ���� ������������� 200 �����!%n", name);
    }

    public List<Auto> getTotalAuto() {
        return totalAuto;
    }
}
