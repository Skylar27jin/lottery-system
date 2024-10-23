package zetian.bucssa.lottery_system.mapper;

import org.apache.ibatis.annotations.*;
import zetian.bucssa.lottery_system.domain.LotteryEntry;

import java.util.List;

@Mapper
public interface LotteryEntryMapper {

    @Insert("INSERT INTO lottery_entries(bu_id, lottery_id, entry_time) VALUES(#{buId}, #{lotteryId}, #{entryTime})")
    void insertEntry(LotteryEntry entry);


    @Select("SELECT * FROM lottery_entries WHERE lottery_id = #{lotteryId}")
    @Results(id = "entry", value = {
            @Result(property = "entryId", column = "entry_id"),
            @Result(property = "buId", column = "bu_id"),
            @Result(property = "lotteryId", column = "lottery_id"),
            @Result(property = "entryTime", column = "entry_time")
    })
    List<LotteryEntry> getEntriesByLotteryId(Long lotteryId);
}
