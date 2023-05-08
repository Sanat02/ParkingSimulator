package carSimulation.entity;

import carSimulation.stateOfCar.States;
import carSimulation.utilities.Colors;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Auto {
    private States state;
    private final String carNumber;

    public Auto(int num) {
        state = States.ON_WAY;
        carNumber = makeCode(state.getValue() + num);
    }

    public void setState(States state) {
        this.state = state;
    }

    public void changeState() {
        state.changeState(this);
    }


    public States getState() {
        return state;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String makeCode(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return convertToString(md.digest(input.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String convertToString(byte[] array) {
        return IntStream.range(0, array.length / 4)
                .map(i -> array[i])
                .map(i -> (i < 0) ? i + 127 : i)
                .mapToObj(Integer::toHexString)
                .collect(Collectors.joining());
    }

    @Override
    public String toString() {
        return String.format(Colors.BLUE+"{состояние=%s,  номер машины=%s}", state.getValue(), carNumber);
    }

}
