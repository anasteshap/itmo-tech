package itmo.anasteshap.dao.mybatis.mappers;

import itmo.anasteshap.entities.Cat;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MyBatisCatMapper {
    @Insert("insert into cat(name, birth_date, breed, color, owner_id) values(#{name}, #{birthDate}, #{breed}, #{color}, #{owner.id}) returning *")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int save(Cat cat);

    @Delete("delete from cat where id = #{id}")
    void deleteById(long id);

    @Delete("delete from cat where id = #{id}")
        // во всех дао поменять на такое удаление просто по id
    void deleteByEntity(Cat cat);

    @Delete("delete from cat")
    void deleteAll();

    @Update("update cat " +
            "set name = #{name}, birth_date = #{birthDate}, breed = #{breed}, color = #{color}, owner_id = #{owner.id}" +
            "where id = #{id}")
        // ???
    Cat update(Cat cat);

    @Select("select * from cat where id = #{id}")
    Cat getById(long id);

    @Select("select * from cat")
    List<Cat> getAll();

    @Select("select * from cat where owner.id = #{id}")
    List<Cat> getAllByOwnerId(long id);
}


