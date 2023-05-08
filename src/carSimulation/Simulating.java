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
        System.out.printf(Colors.GREEN + "СИМУЛЯЦИЯ УСПЕШНО ОКОНЧЕНА!Даные были добавлены в регистрационный журнал%n");
        System.out.println();
        getRegisterList();
        action = new Action(registerList, countParkSize);
        String continueAction = "y";
        while (continueAction.equals("y")) {
            printTab();
            System.out.print(Colors.YELLOW+"Введите букву действия:");
            String option = in.nextLine().toLowerCase(Locale.ROOT);
            while (!option.equals("a") && !option.equals("b") && !option.equals("c") && !option.equals("e") && !option.equals("f") && !option.equals("g") && !option.equals("h")
                    && !option.equals("i") && !option.equals("j") && !option.equals("k") && !option.equals("d")) {
                System.out.println(Colors.RED+"Неправильно ввели значение.Введите заново!");
                option = in.nextLine().toLowerCase(Locale.ROOT);
            }
            performAction(option);

            System.out.println(Colors.PURPLE+"Введите букву y если хотите продолжить ".toUpperCase(Locale.ROOT));
            continueAction = in.nextLine().toLowerCase(Locale.ROOT);

        }
        System.out.printf(Colors.GREEN + "----------КОНЕЦ----------");

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
        System.out.println(Colors.YELLOW + "----------Действия----------");
        System.out.printf(Colors.BLUE + "a.%s%nb.%s%nc.%s%nd.%s%ne.%s%nf.%s%ng.%s%nh.%s%ni.%s%nj.%s%nk.%s%n", "Вы хотите знать общую сумму заработка за один конкретный рабочий день",
                "Минимальную, среднюю и максимальную сумма заработка за период симуляции", "Десять машин стоявших на парковке дольше всего", "Сколько машин в день в общем стоит на парковке меньше 30 минут",
                "Средний процент загруженности парковки (отношение между количеством свободных и занятых мест) в день", "Вывести список всех машин побывавших на парковке за определенный час"
                , "Вывести список всех машин побывавших на парковке за определенный день", "По определенному номеру машины вывести все дни, в которые эта машина была на парковке",
                "ГИСТОГРАММА Среднее количество занятых мест каждый день, за весь период симуляции", "ГИСТОГРАММА Ежедневный средний заработок за весь период симуляции", "ГИСТОГРАММА Общее количество машин припаркованных менее 30 минут в день за весь период симуляции"
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