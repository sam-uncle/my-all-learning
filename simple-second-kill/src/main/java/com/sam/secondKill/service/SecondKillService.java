package com.sam.secondKill.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sam.secondKill.entity.SecondKillDetails;
import com.sam.secondKill.entity.SecondKillProduct;
import com.sam.secondKill.repository.SecondKillDetailRepository;
import com.sam.secondKill.repository.SecondKillProductRepository;
import com.sam.secondKill.utils.Utils;

@Service
public class SecondKillService {

	@Autowired
	SecondKillProductRepository secondKillProductRepository;

	@Autowired
	SecondKillDetailRepository secondKillDetailRepository;

	@Cacheable(value = "product", key = "#id")
	public SecondKillProduct findProductById(Integer id) {

		return secondKillProductRepository.findOne(id);
	}

	@CachePut(value = "product", key = "#entity.getId()")
	public int reduceProduct(SecondKillProduct entity, Integer oldVersion) {

		return secondKillProductRepository.updateNumberAndVersionById(entity.getId(), oldVersion);
	}

	@CachePut(value = "details")
	public SecondKillDetails saveDetail(SecondKillDetails details) {
		return secondKillDetailRepository.save(details);
	}

	/**
	 *减库存和计入秒杀详细信息要使用事务
	 */
	@Transactional
	public int execSecondKill(Integer productId) {
		SecondKillProduct productById = this.findProductById(productId);
//		System.out.println(productById.toString());

		if (productById.getNumber() > 0) {

			Integer oldVersion = productById.getVersion();
			//减库存
			int savedProduct = this.reduceProduct(productById, oldVersion);
			//如果减库存成功，则写入秒杀详细表
			if (savedProduct == 1) {
				SecondKillDetails details = new SecondKillDetails();
				details.setKillProductId(productById.getId());
				details.setStatus("0");
				this.saveDetail(details);
			} else {
				return Utils.SECOND_KILL_VERSION_OLD;
			}

		} else {
			return Utils.SECOND_KILL_SELL_OUT;

		}

		return Utils.SECOND_KILL_SUCCESS;
	}

}
