package com.waither.mapping;

import java.sql.Time;

public interface UserAlarmMapping {
    Character getMon();
    Character getTue();
    Character getWed();
    Character getThu();
    Character getFri();
    Character getSat();
    Character getSun();

    Time getOutTime();
    Character getOutAlarm();
    Character getClimateAlarm();
    Character getCustomAlarm();
    Character getRainAlarm();
}
