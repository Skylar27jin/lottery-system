package zetian.bucssa.lottery_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zetian.bucssa.lottery_system.domain.Lottery;
import zetian.bucssa.lottery_system.domain.LotteryEntry;
import zetian.bucssa.lottery_system.mapper.LotteryMapper;
import zetian.bucssa.lottery_system.mapper.UserMapper;

import java.util.List;
import java.util.Random;

@Service
public class LotteryService {

    @Autowired
    LotteryMapper lotteryMapper;
    @Autowired
    private LotteryEntryService lotteryEntryService;

    public void addLottery(Lottery lottery) {
        lotteryMapper.insertLottery(lottery);
    }

    public Lottery getLotteryByName(String name) {
        return lotteryMapper.getLotteryByName(name);
    }

    public List<Lottery> getAllLotteries() {
        return lotteryMapper.getAllLotteries();
    }

    public LotteryEntry drawWinner(Long lotteryId) {
        // 获取该抽奖活动的所有参与者
        List<LotteryEntry> entries = lotteryEntryService.getEntriesForLottery(lotteryId);

        if (entries.isEmpty()) {
            return null;  // 没有参与者
        }

        // 随机选择一个参与者作为中奖者
        Random random = new Random();
        int winnerIndex = random.nextInt(entries.size());

        return entries.get(winnerIndex);
    }

}
