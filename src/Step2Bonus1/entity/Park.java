package Step2Bonus1.entity;

import java.util.*;

import static Step2Bonus1.utilities.Colors.CYAN;


public class Park {
    private List<Auto> parkingList;
    private int loadCapacity;
    private Map<String, List<Register>> mp;
    private Register register;


    public Park() {
        loadCapacity = 20;
        parkingList = new ArrayList<>();
        mp = new HashMap<>();
    }

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public List<Auto> getParkingList() {
        return parkingList;
    }

    public void loadCar(Auto auto, Date date) {
        parkingList.add(auto);
        register = new Register(auto.getCarNumber(), date);
        if (!mp.isEmpty() && mp.containsKey(auto.getCarNumber())) {
            List<Register> l = new ArrayList<>(mp.get(auto.getCarNumber()));
            l.add(register);
            mp.put(auto.getCarNumber(), l);
        } else {
            mp.put(auto.getCarNumber(), List.of(register));
        }
        loadCapacity--;
    }

    public void unLoadCar(int index, Date date) {
        if (mp.containsKey(parkingList.get(index).getCarNumber())) {
            int size = mp.get(parkingList.get(index).getCarNumber()).size() - 1;
            mp.get(parkingList.get(index).getCarNumber()).get(size).setEDate(date);
        }
        parkingList.remove(index);
        loadCapacity++;
    }

    public void changeState(int index) {
        parkingList.get(index).changeState();
    }


    @Override
    public String toString() {
        return String.format(CYAN + "Ìàøèíû íà ÏÀĞÊÎÂÊÅ:%n%s%n", parkingList);
    }

    public Map<String, List<Register>> getMp() {
        return mp;
    }
}
