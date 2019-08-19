package com.springboot.action.saas.modules.user.repository;

import com.springboot.action.saas.modules.user.domain.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/*
 * JpaRepository继承了接口PagingAndSortingRepository和QueryByExampleExecutor。
 * PagingAndSortingRepository又继承CrudRepository。
 * JpaRepository接口同时拥有基本CRUD功能以及分页功能。
 * 官方的给的定义
 * public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T>
 * T 实例类
 * ID id的数据类型
 * */
@Repository
public interface UserMemberRepository extends JpaRepository<UserMember, Long> {
    //通过name查找用户
    UserMember findByName(String name);

    //获取全部用户，带有分页参数
    //调用findAll 给分页参数即可

    //修改密码
    @Modifying
    @Query(value = "update user set password = ?2 where username = ?1", nativeQuery = true)
    void updatePass(String username, String pass);
}
