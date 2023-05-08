package Step2Bonus1.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Register {
    private final String carNumber;
    private final Date sDate;
    private Date eDate;
    private long money;
    private long duration;
    private String check;

    public Register(String carNumber, Date sDate) {
        this.carNumber = carNumber;
        this.sDate = sDate;
        money = 0;
    }

    public String getsDate() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(sDate);
    }


    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(sDate);
    }


    public String getCarNumber() {
        return carNumber;
    }

    public void setEDate(Date eDate) {
        this.eDate = eDate;
    }

    public Date geteDate() {
        return eDate;
    }

    @Override
    public String toString() {
        isCheckRegistered();
        retrieveMoney();
        return String.format("[номер машины:%s, дата и время заезда:%s ,дата и время выезда:%s, чек оплаты:%s,деньги:%s]", carNumber, sDate, eDate, check, money);
    }

    public void isCheckRegistered() {
        long diff = eDate.getTime() - sDate.getTime();
        duration = TimeUnit.MILLISECONDS.toMinutes(diff);
        check = duration >= 30 ? "REGISTERED" : "UNREGISTERED";
    }

    public long getDuration() {
        return duration;
    }

    public long getMoney() {
        return money;
    }

    public void retrieveMoney() {
        int dayMoney=2;
        int nightMoney=4;
        if (duration >= 30) {
            WorkTimeCalculator calculator = new WorkTimeCalculator(sDate, eDate);
            calculator.setWorkingTime("09:00", "21:00");//Для дневной оплаты
            int workingMinutes = calculator.getMinutes();
            money+=workingMinutes*dayMoney;
            calculator.setWorkingTime("21:00", "23:59"); //Для ночной оплаты
            workingMinutes=calculator.getMinutes();
            money+=workingMinutes*nightMoney;
        }

    }

}
