package zetian.bucssa.lottery_system.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LotteryEntry {
    private Long entryId;
    private String buId;
    private Long lotteryId;
    private Timestamp entryTime;
}
