package com.springboot.action.saas.modules.repository;

import com.springboot.action.saas.common.logging.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface RoleRepository extends JpaRepository<Log,Long> {
}
