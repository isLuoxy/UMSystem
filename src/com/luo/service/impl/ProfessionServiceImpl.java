package com.luo.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.luo.base.list.SortedCirDoublyList;
import com.luo.dao.ProfessionDao;
import com.luo.dao.ProfessionItemDao;
import com.luo.dao.impl.ProfessionDaoImpl;
import com.luo.dao.impl.ProfessionItemDaoImpl;
import com.luo.entity.Profession;
import com.luo.service.ProfessionService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class ProfessionServiceImpl implements ProfessionService {

	public ProfessionDao professionDao;

	public ProfessionServiceImpl() {
		this.professionDao = new ProfessionDaoImpl();
	}

	@Override
	public void insertProfession(Profession profession) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getProfessionList(String course) {
		// 获取全部专业链表

		SortedCirDoublyList<Profession> professionList = professionDao.getProfessionList();
		
		// 存放经过筛选后的专业，如果没有筛选，则为全部专业
		List<Profession> professionTemp = new ArrayList<Profession>();

		for (int i = 0; i < professionList.size(); i++) {
			professionTemp.add(professionList.get(i));
		}

		if (!(course == null || "".equals(course.trim()))) {
			// 如果传进来的课程不为空，说明要返回的有专业的id、名称、和对应的课程
			ProfessionItemDao professionItemDao = new ProfessionItemDaoImpl();
			Iterator<Profession> it = professionTemp.iterator();
			while (it.hasNext()) {
				Profession p = it.next();
				p.setCourseList(professionItemDao.findCourseListByProfessionId(p.getId()));
			}
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { "majorRequired" ,"optional","required"});
			String result = JSONArray.fromObject(professionTemp,config).toString();
			return result;
		} else {
			// 说明返回的只有专业的id和名称，不用返回对应的课程
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[] { "couseList" });
			String result = JSONArray.fromObject(professionTemp, config).toString();
			return result;
		}
	}

	@Override
	public void alertProfession(Profession profession) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProfession(Profession profession) {
		// TODO Auto-generated method stub

	}
}
