package zetian.bucssa.lottery_system.mapper;

import org.apache.ibatis.annotations.*;
import zetian.bucssa.lottery_system.domain.Lottery;

import java.util.List;

@Mapper
public interface LotteryMapper {
    @Insert("INSERT INTO bucssa_lottery(name, description, start_time, end_time) " +
            "VALUES(#{name}, #{description}, #{startTime}, #{endTime})")
    void insertLottery(Lottery lottery);

    @Select("SELECT * FROM bucssa_lottery WHERE name = #{name}")
    @Results(id = "lottery", value={
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time")
    })
    Lottery getLotteryByName(String name);


    @Select("SELECT * FROM bucssa_lottery")
    @ResultMap("lottery")
    List<Lottery> getAllLotteries();
}
