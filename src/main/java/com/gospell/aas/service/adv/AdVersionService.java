package com.gospell.aas.service.adv;

import com.google.common.collect.Lists;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.persistence.Page;
import com.gospell.aas.common.utils.IdGen;
import com.gospell.aas.common.utils.adv.AdChannelUtils;
import com.gospell.aas.common.utils.adv.AdComboUtils;
import com.gospell.aas.dto.adv.*;
import com.gospell.aas.entity.adv.*;
import com.gospell.aas.repository.hibernate.adv.AdChannelDao;
import com.gospell.aas.repository.hibernate.adv.AdVersionDao;
import com.gospell.aas.repository.hibernate.adv.AdvertiserDao;
import com.gospell.aas.repository.mybatis.adv.IAdComboDao;
import com.gospell.aas.repository.mybatis.adv.IChannelDao;
import com.gospell.aas.service.BaseService;
import com.gospell.aas.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AdVersionService extends BaseService {

	@Autowired
	private AdVersionDao thisDao;

	public void clear(){
		  thisDao.clear();
	}

	public AdVersion get(String id) {
		return thisDao.get(id);
	}

	public List<AdVersion> findAll() {
		return thisDao.findAll();
	}
}
