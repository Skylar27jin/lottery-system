package zetian.bucssa.lottery_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zetian.bucssa.lottery_system.domain.LotteryEntry;
import zetian.bucssa.lottery_system.mapper.LotteryEntryMapper;

import java.util.List;

@Service
public class LotteryEntryService {
    @Autowired
    private LotteryEntryMapper lotteryEntryMapper;

    public void insertLotteryEntry(String buId, Long lotteryId) {
        LotteryEntry entry = new LotteryEntry();
        entry.setBuId(buId);
        entry.setLotteryId(lotteryId);
        entry.setEntryTime(new java.sql.Timestamp(System.currentTimeMillis()));

        lotteryEntryMapper.insertEntry(entry);
    }

    public List<LotteryEntry> getEntriesForLottery(Long lotteryId) {
        return lotteryEntryMapper.getEntriesByLotteryId(lotteryId);
    }
}
