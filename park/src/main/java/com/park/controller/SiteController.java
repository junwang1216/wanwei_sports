package com.park.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.common.bean.PageBean;
import com.park.common.bean.ResponseBean;
import com.park.common.bean.SiteInputView;
import com.park.common.constant.IDBConstant;
import com.park.common.exception.MessageException;
import com.park.common.po.SiteInfo;
import com.park.common.po.SiteSport;
import com.park.common.po.UserOperator;
import com.park.common.util.DateUtil;
import com.park.common.util.JsonUtils;
import com.park.common.util.StrUtil;
import com.park.service.IParkService;
import com.park.service.ISiteService;

@Controller
@RequestMapping("site")
public class SiteController extends BaseController {
	
	@Autowired
	private ISiteService siteService;
	
	@Autowired
	private IParkService parkService;
	
	@ResponseBody
	@RequestMapping(value = "saveSiteSport", method = RequestMethod.POST)
	public ResponseBean saveSiteSport(SiteSport siteSport) {
		try {
			siteSport.setSalesId(super.getUserInfo().getId());
			Integer sportId = siteService.saveSiteSport(siteSport);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("sportId", sportId);
			return new ResponseBean(data);
		} catch (MessageException e) {
			e.printStackTrace();
			return new ResponseBean(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "saveSiteInfo", method = RequestMethod.POST)
	public ResponseBean saveSiteInfo(SiteInfo siteInfo) {
		try {
			siteInfo.setSalesId(super.getUserInfo().getId());
			Integer siteId = siteService.saveSiteInfo(siteInfo);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("siteId", siteId);
			return new ResponseBean(data);
		} catch (MessageException e) {
			e.printStackTrace();
			return new ResponseBean(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "getSiteNames")
	public ResponseBean getSiteNames(SiteInputView siteInputView) {
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("siteNames", siteService.getSiteNames(siteInputView));
			return new ResponseBean(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "getSiteInfo")
	public ResponseBean getSiteInfo(Integer siteId) {
		try {
			return new ResponseBean(siteService.getSiteInfoMap(siteId));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "getSiteSport")
	public ResponseBean getSiteSport(Integer sportId) {
		try {
			SiteSport siteSport = siteService.getSiteSport(sportId);
			return new ResponseBean(JsonUtils.fromJsonDF(siteSport));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false);
		}
	}
	
	@RequestMapping("getSiteInfos")
	public String getSiteInfos(SiteInputView siteInputView, Model model) {
		try {
			model.addAllAttributes(JsonUtils.fromJsonDF(siteInputView));
			PageBean pageBean = siteService.getSiteInfos(siteInputView);
			super.setPageInfo(model, pageBean);
			model.addAttribute("siteSportNames", siteService.getSiteSportNames(siteInputView));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Reservation/ReservationsSitesSettings";
	}
	
	@RequestMapping("getSiteSports")
	public String getSiteSports(SiteInputView siteInputView, Model model) {
		try {
			model.addAllAttributes(JsonUtils.fromJsonDF(siteInputView));
			PageBean pageBean = siteService.getSiteSports(siteInputView);
			super.setPageInfo(model, pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Reservation/ReservationsSportsSettings";
	}
	
	//显示时间段，场地名
	@RequestMapping("getSiteReservationInfo")
	public String getSiteReservation(SiteInputView siteInputView, Model model) throws ParseException{
		siteInputView.setSportStatus(IDBConstant.LOGIC_STATUS_YES);
		List<Map<String, Object>> siteSports = siteService.getSiteSportNames(siteInputView);
		if(siteSports.size() > 0){
			Integer sportId = siteInputView.getSportId();
			if(sportId == null){ //未选择，默认第一个
				Map<String, Object> sportMap = siteSports.get(0);
				sportId = StrUtil.objToInt(sportMap.get("sportId"));
			}
			siteInputView.setSportId(sportId);
			siteInputView.setSiteStatus(IDBConstant.LOGIC_STATUS_YES);
			List<Map<String, Object>> sites = siteService.getSites(siteInputView);
			List<Map<String, Object>> timePeriod = parkService.getTimePeriod(parkService.getBusiness());
			model.addAttribute("siteSports", siteSports);
			model.addAttribute("sites", sites);
			model.addAttribute("timePeriod", timePeriod);
			model.addAttribute("curDate", DateUtil.dateToString(new Date(), null));
			model.addAttribute("curSportId", sportId);
			System.out.println(JsonUtils.toJson(siteSports));
			System.out.println(JsonUtils.toJson(sites));
			System.out.println(JsonUtils.toJson(timePeriod));
		}
		return "Reservation/ReservationsSequence";
		
	}
	
	//Ajax动态显示场地序列图
	@ResponseBody
	@RequestMapping("dynamicSiteReservation")
	public ResponseBean dynamicSiteReservation(SiteInputView siteInputView, Model model){
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			data.putAll(JsonUtils.fromJson(siteService.getSiteReservationInfo(siteInputView)));
			return new ResponseBean(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseBean(false);
	}
	
	//预定场地
	@ResponseBody
	@RequestMapping("saveReservationSite")
	public ResponseBean saveReservationSite(SiteInputView siteInputView){
		try {
			UserOperator userOperator = super.getUserInfo();
			siteInputView.setSalesId(userOperator.getId());
			siteService.saveReservationSite(siteInputView);
			return new ResponseBean(true);
		} catch (MessageException e) {
			e.printStackTrace();
			return new ResponseBean(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false);
		}
	}

	// 批量预订
	@RequestMapping("getSiteReservationBatch")
	public String getSiteReservationBatch(Model model){
		return "Reservation/ReservationsBatch";
	}

    // 热点
    @RequestMapping("getReservationsSequencePDA")
    public String getReservationsSequencePDA(Model model){
        return "Reservation/ReservationsSequencePDA";
    }
	
	//锁定场地
	@ResponseBody
	@RequestMapping("lockSite")
	public ResponseBean lockSite(SiteInputView siteInputView){
		try {
			UserOperator userOperator = super.getUserInfo();
			siteInputView.setSalesId(userOperator.getId());
			siteService.updateLockSite(siteInputView);
			return new ResponseBean(true);
		} catch (MessageException e) {
			e.printStackTrace();
			return new ResponseBean(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false);
		}
	}
	
	//计算预定场地价格
	@ResponseBody
	@RequestMapping("calculateSiteMoney")
	public ResponseBean calculateSiteMoney(SiteInputView siteInputView){
		try {
			siteService.calculateSiteMoney(siteInputView);
			return new ResponseBean(true);
		} catch (MessageException e) {
			e.printStackTrace();
			return new ResponseBean(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseBean(false);
		}
	}
	
}
