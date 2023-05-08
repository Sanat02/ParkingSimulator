package Step2Bonus1.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * A utility class to calculate working minutes/hours/days between two given dates unlike the simple difference
 * between two dates.
 * @since 0.0.1
 * @auhor Shashank Agrawal
 */
public class WorkTimeCalculator {

    private Date startDate, endDate, dayStartTime, dayEndTime;

    private Calendar startCal = Calendar.getInstance();
    private Calendar endCal = Calendar.getInstance();

    private DateFormat timeParser = new SimpleDateFormat("HH:mm");
    private int minutes, workingMinutes;

    public WorkTimeCalculator(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;

        startCal.setTime(startDate);
        endCal.setTime(endDate);

        setWorkingTime("09:00", "21:00");
    }

   // private void log(Object data) {
   //     System.out.println(data);
   // }

    private Boolean isDuringWorkingHour() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(dayStartTime);
        calendar2.setTime(dayEndTime);

        List<Integer> fields = Arrays.asList(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);

        for (int field : fields) {
            calendar1.set(field, startCal.get(field));
            calendar2.set(field, startCal.get(field));
        }

        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();
        Date date = startCal.getTime();

        if (date1.before(startDate)) {
            date1 = startDate;
        }

        if (date2.after(endDate)) {
            date2 = endDate;
        }

        Boolean status = (date.equals(date1) || date.after(date1)) && date.before(date2);
        return status;
    }

    public Integer getMinutes() {
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            int day = startCal.get(Calendar.DAY_OF_WEEK);

            if (!isDuringWorkingHour()) {
                startCal.add(Calendar.MINUTE, 1);
                continue;
            }

            minutes++;
            startCal.add(Calendar.MINUTE, 1);
        }

        return minutes;
    }

    public void setWorkingTime(String startTime, String endTime) {
        try {
            this.dayStartTime = timeParser.parse(startTime);
            this.dayEndTime = timeParser.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }

        this.workingMinutes = (int) ((this.dayEndTime.getTime() - this.dayStartTime.getTime()) / (1000 * 60));
    }
}