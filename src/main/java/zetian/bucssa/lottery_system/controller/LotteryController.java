package zetian.bucssa.lottery_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zetian.bucssa.lottery_system.domain.Lottery;
import zetian.bucssa.lottery_system.domain.LotteryEntry;
import zetian.bucssa.lottery_system.domain.User;
import zetian.bucssa.lottery_system.service.LotteryEntryService;
import zetian.bucssa.lottery_system.service.LotteryService;
import zetian.bucssa.lottery_system.service.UserService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private LotteryEntryService lotteryEntryService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createLottery(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {

        Lottery lottery = new Lottery();
        lottery.setName(name);
        lottery.setDescription(description);
        lottery.setStartTime(Timestamp.valueOf(startTime));
        lottery.setEndTime(Timestamp.valueOf(endTime));

        lotteryService.addLottery(lottery);
        return "Lottery created successfully!";
    }

    @GetMapping("/all")
    public List<Lottery> getAllLotteries() {
        return lotteryService.getAllLotteries();
    }

    @GetMapping("/{name}")
    public Lottery getLotteryByName(@PathVariable String name) {
        return lotteryService.getLotteryByName(name);
    }

    @PostMapping("/enter")
    public String enterLottery(
            @RequestParam("buId") String buId,
            @RequestParam("lotteryId") Long lotteryId) {

        lotteryEntryService.insertLotteryEntry(buId, lotteryId);
        return "User entered into the lottery successfully!";
    }

    @GetMapping("/entries/{lotteryId}")
    public List<LotteryEntry> getLotteryEntries(@PathVariable Long lotteryId) {
        return lotteryEntryService.getEntriesForLottery(lotteryId);
    }

    @PostMapping("/draw")
    public ResponseEntity<String> drawWinner(@RequestParam("lotteryId") Long lotteryId) {
        LotteryEntry winner = lotteryService.drawWinner(lotteryId);

        if (winner == null) {
            return new ResponseEntity("No participants found for the lottery.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(userService.getUserByBuId(winner.getBuId()), HttpStatus.OK);
    }

}
