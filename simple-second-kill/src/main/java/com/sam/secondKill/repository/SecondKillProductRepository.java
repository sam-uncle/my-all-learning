package com.sam.secondKill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sam.secondKill.entity.SecondKillProduct;

public interface SecondKillProductRepository extends JpaRepository<SecondKillProduct, Integer>{
	/**
	 *防止超卖，采用乐观锁
	 *加入version字段，每卖出一件，version+1
	 *这里采用了重试次数的方式，重试3次
	 */
	@Modifying
	@Query("update SecondKillProduct set number = number -1, version = version + 1 where version =:oldVersion and id =:id")
    public int updateNumberAndVersionById(@Param("id") Integer id, @Param("oldVersion") Integer oldVersion);
	
}
