package carSimulation;

import carSimulation.entity.Action;
import carSimulation.entity.City;
import carSimulation.entity.Park;
import carSimulation.entity.Register;
import carSimulation.stateOfCar.States;
import carSimulation.utilities.Colors;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static carSimulation.utilities.Rnd.rnd;

public class Simulating {
    private final City city;
    private Park park;
    private Date date;
    private List<Register> registerList;
    private Map<Date, Integer> countParkSize;
    private Action action;


    public Simulating() {
        city = new City();
        park = new Park();
        registerList = new ArrayList<>();
        countParkSize = new TreeMap<>();
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime end = ldt.plusMonths(1);
        System.out.println(city);
        for (LocalDateTime i = ldt; i.isBefore(end); i = i.plusMinutes(5)) {
            ZonedDateTime zdt = i.atZone(ZoneId.systemDefault());
            date = Date.from(zdt.toInstant());
            changeStateInPark();
            changeStateInStreet();
            countParkSize.put(date, park.getParkingList().size());
        }
        setDateAndTime();
        System.out.printf(Colors.GREEN + "��������� ������� ��������!����� ���� ��������� � ��������������� ������%n");
        System.out.println();
        getRegisterList();
        action = new Action(registerList, countParkSize);
        String continueAction = "y";
        while (continueAction.equals("y")) {
            printTab();
            System.out.print(Colors.YELLOW+"������� ����� ��������:");
            String option = in.nextLine().toLowerCase(Locale.ROOT);
            while (!option.equals("a") && !option.equals("b") && !option.equals("c") && !option.equals("e") && !option.equals("f") && !option.equals("g") && !option.equals("h")
                    && !option.equals("i") && !option.equals("j") && !option.equals("k") && !option.equals("d")) {
                System.out.println(Colors.RED+"����������� ����� ��������.������� ������!");
                option = in.nextLine().toLowerCase(Locale.ROOT);
            }
            performAction(option);

            System.out.println(Colors.PURPLE+"������� ����� y ���� ������ ���������� ".toUpperCase(Locale.ROOT));
            continueAction = in.nextLine().toLowerCase(Locale.ROOT);

        }
        System.out.printf(Colors.GREEN + "----------�����----------");

    }

    private int IsChangeState() {
        if (rnd(100) <= 2) {
            return 1;
        }
        return 0;
    }

    private void changeStateInPark() {
        if (!park.getParkingList().isEmpty()) {
            for (int i = 0; i < park.getParkingList().size(); i++) {
                if (IsChangeState() == 1) {
                    park.changeState(i);
                    city.getTotalAuto().add(park.getParkingList().get(i));
                    park.unLoadCar(i, date);
                }
            }
        }
    }

    private void changeStateInStreet() {
        for (int i = 0; i < city.getTotalAuto().size(); i++) {
            if (IsChangeState() == 1 && park.getLoadCapacity() > 0) {
                city.getTotalAuto().get(i).changeState();
                if (city.getTotalAuto().get(i).getState().equals(States.ON_PARK)) {
                    park.loadCar(city.getTotalAuto().get(i), date);
                    city.getTotalAuto().remove(i);
                }

            }
        }

    }

    private void setDateAndTime() {
        for (Map.Entry<String, List<Register>> entry : park.getMp().entrySet()) {
            List<Register> rList = entry.getValue();
            int index = rList.size() - 1;
            if (rList.get(index).geteDate() == null) {
                rList.get(index).setEDate(date);
            }
        }
    }

    private void getRegisterList() {
        var list = park.getMp().values().stream().toList();
        for (List<Register> registers : list) {
            registerList.addAll(registers);
        }
        registerList.forEach(Register::isCheckRegistered);
        registerList.forEach(Register::retrieveMoney);
    }

    private void printTab() {
        System.out.println(Colors.YELLOW + "----------��������----------");
        System.out.printf(Colors.BLUE + "a.%s%nb.%s%nc.%s%nd.%s%ne.%s%nf.%s%ng.%s%nh.%s%ni.%s%nj.%s%nk.%s%n", "�� ������ ����� ����� ����� ��������� �� ���� ���������� ������� ����",
                "�����������, ������� � ������������ ����� ��������� �� ������ ���������", "������ ����� �������� �� �������� ������ �����", "������� ����� � ���� � ����� ����� �� �������� ������ 30 �����",
                "������� ������� ������������� �������� (��������� ����� ����������� ��������� � ������� ����) � ����", "������� ������ ���� ����� ���������� �� �������� �� ������������ ���"
                , "������� ������ ���� ����� ���������� �� �������� �� ������������ ����", "�� ������������� ������ ������ ������� ��� ���, � ������� ��� ������ ���� �� ��������",
                "����������� ������� ���������� ������� ���� ������ ����, �� ���� ������ ���������", "����������� ���������� ������� ��������� �� ���� ������ ���������", "����������� ����� ���������� ����� �������������� ����� 30 ����� � ���� �� ���� ������ ���������"
        );

    }

    private void performAction(String option) {
        switch (option) {
            case "a" -> action.getTotalSumOfDay();
            case "b" -> action.getMinAndMax();
            case "c" -> action.getMaxDurationCar();
            case "d" -> action.getLess30minuteDuration();
            case "e" -> action.avgPercentOfCapacity();
            case "f" -> action.getCarByTime();
            case "g" -> action.getCarByDate();
            case "h" -> action.getDayByCarNumber();
            case "i" -> action.avg();
            case "j" -> action.getAvgSumPerDayHistogram();
            case "k" -> action.histogramOfLess30MinPark();
            default -> System.out.println("Sorting error!");
        }
    }
}