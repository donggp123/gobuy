package com.cndinuo.service.impl;

import com.cndinuo.base.AbstractService;
import com.cndinuo.dao.MrhtGoodsClassDao;
import com.cndinuo.domain.MrhtGoodsClass;
import com.cndinuo.service.MrhtGoodsClassService;
import com.cndinuo.vo.GoodsClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;


/**
* @date 2017-09-01
* @author dgb
* 
*/
@Service("MrhtGoodsClassService")
public class MrhtGoodsClassServiceImpl extends AbstractService<MrhtGoodsClass, Integer> implements MrhtGoodsClassService {

	@Autowired
	private MrhtGoodsClassDao mrhtGoodsClassDao;

	@PostConstruct
	public void setBaseDao() {
		super.setBaseDao(mrhtGoodsClassDao);
	}


    @Override
    public List<GoodsClassVO> getSelectData() {
		List<GoodsClassVO> clazzs = mrhtGoodsClassDao.getSelectData();

		List<GoodsClassVO> roots = clazzs.stream()
				.filter(g -> "0".equals(g.getPId()))
				.collect(Collectors.toList());

		return recursion(roots, clazzs);
    }

    private List<GoodsClassVO> recursion(List<GoodsClassVO> roots,List<GoodsClassVO> clazzs){
		if (roots != null && roots.size() > 0) {
			roots.forEach(clazz ->{
				List<GoodsClassVO> son = clazzs.stream()
						.filter(g -> clazz.getId().equals(g.getPId()))
						.collect(Collectors.toList());
				clazz.setSon(son);
				if (son != null && son.size() > 0) {
					recursion(son, clazzs);
				}
			});
		}
		return roots;
	}
}