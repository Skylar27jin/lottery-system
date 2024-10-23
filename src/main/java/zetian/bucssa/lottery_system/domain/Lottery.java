package zetian.bucssa.lottery_system.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Lottery {
    Long lotteryId;
    String name;
    String description;
    Timestamp startTime;
    Timestamp endTime;
}
