package itmo.anasteshap.dao.mybatis.mappers;

import itmo.anasteshap.entities.Owner;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MyBatisOwnerMapper {
    @Insert("insert into owner(name, birth_date) values(#{name}, #{birthDate}) returning *")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int save(Owner owner);

    @Delete("delete from owner where id = #{id}")
    void deleteById(long id);

    @Delete("delete from owner where id = #{id}")
        // во всех дао поменять на такое удаление просто по id
    void deleteByEntity(Owner owner);

    @Delete("delete from owner")
    void deleteAll();

    @Update("update owner " +
            "set name = #{name}, birth_date = #{birthDate} " +
            "where id = #{id}")
    int update(Owner owner);

    @Select("select * from owner where id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "birthDate", column = "birth_date"),
    })
    Owner getById(long id);

    @Select("select * from owner")
    List<Owner> getAll();
}
