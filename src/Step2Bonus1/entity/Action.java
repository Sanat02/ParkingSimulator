package Step2Bonus1.entity;

import Step2Bonus1.Histogram;
import Step2Bonus1.enumiration.Scale;
import Step2Bonus1.utilities.Colors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Action {
    private List<Register> registerList;
    private Map<Date, Integer> countParkSize;

    public Action(List<Register> registerList, Map<Date, Integer> countParkSize) {
        this.registerList = registerList;
        this.countParkSize = countParkSize;
    }

    public void getCarByDate() {
        System.out.println(Colors.YELLOW+ "Cписок всех машин побывавших на парковке в определенный день");
        var mp = registerList.stream().collect(Collectors.groupingBy(Register::getsDate, Collectors.mapping(Register::getCarNumber, Collectors.toList())));
        Map sortedMap=new TreeMap<>(mp);
        sortedMap.forEach((k, v) -> System.out.printf(Colors.BLUE+"%s - %s%n", k, v));

    }

    public void getCarByTime() {
        System.out.println(Colors.YELLOW + "Cписок всех машин побывавших на парковке в определенный час");
        var mp = registerList.stream().collect(Collectors.groupingBy(Register::getTime, Collectors.mapping(Register::getCarNumber, Collectors.toList())));
        Map sortedMap=new TreeMap<>(mp);
        sortedMap.forEach((k, v) -> System.out.printf(Colors.BLUE+"%s - %s%n", k, v));
    }


    public void getTotalSumOfDay() {
        System.out.println(Colors.YELLOW + "ќбща€ сумма заработка за определенный день");
        var mp = registerList.stream().collect(Collectors.groupingBy(Register::getsDate, Collectors.summingLong(Register::getMoney)));
        mp.forEach((k, v) -> System.out.printf(Colors.BLUE+"%s - %s%n", k, v));

    }

    public void getMinAndMax() {
        var min = registerList.stream().mapToLong(Register::getMoney).min().getAsLong();
        var max = registerList.stream().mapToLong(Register::getMoney).max().getAsLong();
        var sum = (Long) registerList.stream().mapToLong(Register::getMoney).sum();
        var avg = sum / registerList.size();
        System.out.printf(Colors.BLUE + "ћаксимальное количество денег:%s%nћинимальное количество денег:%s%n—реднее количество денег:%s%n", max, min, avg);
    }

    public void getMaxDurationCar() {
        System.out.print(Colors.YELLOW + "ƒес€ть машин сто€вших на парковке дольше всего.:");
        var maxDurationList = registerList.stream().sorted(Comparator.comparing(Register::getDuration).reversed()).limit(10).collect(Collectors.toList());
        System.out.println(Colors.BLUE);
        maxDurationList.forEach(System.out::println);
    }

    public ArrayList<Double> getLess30minuteDuration() {
        System.out.println(Colors.YELLOW+ " оличество машин в день которые  сто€т на парковке меньше 30 минут.");
        var mp = registerList.stream().collect(Collectors.groupingBy(Register::getsDate, Collectors.filtering(e -> e.getDuration() < 30, Collectors.counting())));
        TreeMap<String, Long> sortedMap = new TreeMap<>(mp);
        sortedMap.forEach((k, v) -> System.out.printf(Colors.BLUE+"%s - %s%n", k, v));
        ArrayList<Double> valuesList = sortedMap.values().stream().map(Double::valueOf).collect(Collectors.toCollection(ArrayList::new));
        return valuesList;
    }

    public void getDayByCarNumber() {
        System.out.println(Colors.YELLOW+"Ќомера машины и врем€ их парковки");
        var mp = registerList.stream().collect(Collectors.groupingBy(Register::getCarNumber, Collectors.mapping(Register::getsDate, Collectors.toList())));
        mp.forEach((k, v) -> System.out.printf(Colors.BLUE+"%s - %s%n", k, v));

    }

    public void histogramOfLess30MinPark() {
        String text = "ѕо определенному номеру машины вывести все дни, в которые эта машина была на парковке.";
        List<Double> list = getLess30minuteDuration();
        System.out.println();
        Histogram.printHistogram(list, Scale.NO_CHANGE, text);
    }


    public void getAvgSumPerDayHistogram() {
        String text = "≈жедневный средний заработок в день";
        var mp = registerList.stream().collect(Collectors.groupingBy(Register::getsDate, Collectors.averagingLong(Register::getMoney)));
        TreeMap<String, Double> sortedMap = new TreeMap<>(mp);
        List<Double> list = new ArrayList<>(sortedMap.values());
        Histogram.printHistogram(changeScale(Scale.DECREASE, 10, list), Scale.DECREASE, text);

    }


    private List<Double> changeScale(Scale scale, int value, List<Double> list) {
        switch (scale) {
            case INCREASE -> list = list.stream().map(e -> e * value).collect(Collectors.toList());
            case DECREASE -> list = list.stream().map(e -> e / value).collect(Collectors.toList());
        }
        return list;
    }

    public void avg() {
        String text = "—реднее количество зан€тых мест каждый день, за весь период симул€ции:";
        Map<String, List<Integer>> eachDayCount = getParkQuantityADay();
        List<Double> listofAvg = new ArrayList<>();
        for (var entry : eachDayCount.entrySet()) {
            var value = entry.getValue().stream().mapToInt(a -> a).average().getAsDouble();
            listofAvg.add(value);
        }
        Histogram.printHistogram(changeScale(Scale.DECREASE, 5, listofAvg), Scale.DECREASE, text);
    }

    private Map<String, List<Integer>> getParkQuantityADay() {
        Map<String, List<Integer>> eachDayCount = new TreeMap<>();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<Date, Integer> entry : countParkSize.entrySet()) {
            Date date = entry.getKey();
            if (!eachDayCount.isEmpty() && eachDayCount.containsKey(sdf.format(date))) {
                List<Integer> listCount = new ArrayList<>(eachDayCount.get(sdf.format(date)));
                listCount.add(entry.getValue());
                eachDayCount.put(sdf.format(date), listCount);

            } else {
                eachDayCount.put(sdf.format(date), List.of(entry.getValue()));
            }
        }
        return eachDayCount;
    }

    public void avgPercentOfCapacity() {
        System.out.println(Colors.YELLOW+"—редний процент загруженности парковки  в день.");
        Map<String, List<Integer>> eachDayCount = getParkQuantityADay();
        Map<String, Double> avgPercent = new TreeMap<>();
        for (var entry : eachDayCount.entrySet()) {
            var sum = entry.getValue().stream().mapToInt(a -> a).sum();
            var avg = (double) ((entry.getValue().size() * 20) - sum) / entry.getValue().size() * 20; //свободные места дел€тс€ на зан€тые места
            avgPercent.put(entry.getKey(), avg);
        }
        avgPercent.forEach((k, v) -> System.out.printf(Colors.BLUE+"%s - %s %% %n", k, v));
    }

}
