package com.njmetro.evaluation.mapper;

import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.domain.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njmetro.evaluation.vo.SignVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
public interface StudentMapper extends BaseMapper<Student> {

    @SelectProvider(type = getHaveSighCompanyList.class,method = "getHaveSighCompanyList")
    List<String> getHaveSighCompanyList();

    @Select("SELECT  id FROM student")
    List<Integer> getStudentIdList();

    class  getHaveSighCompanyList{
        public String getHaveSighCompanyList(){
            return "select distinct company_name  from student where sign_state = '1'";
        }
    }

    @Select("select company_name,code,name,id_card,seat_id from student,seat_draw where seat_draw.student_id=student.id and student.test_day_state = 2 and game_round = 1")
   List<SignVO> getSignVOList();
}
