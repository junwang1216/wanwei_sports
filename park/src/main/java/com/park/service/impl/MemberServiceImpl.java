package com.park.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.common.bean.BalanceInputView;
import com.park.common.bean.InvoiceInputView;
import com.park.common.bean.MemberCardOpInputView;
import com.park.common.bean.MemberInputView;
import com.park.common.bean.PageBean;
import com.park.common.constant.IDBConstant;
import com.park.common.exception.MessageException;
import com.park.common.po.MemberCard;
import com.park.common.po.MemberCardType;
import com.park.common.po.OtherBalance;
import com.park.common.po.OtherInvoice;
import com.park.common.po.UserMember;
import com.park.common.util.DateUtil;
import com.park.common.util.SQLUtil;
import com.park.common.util.StrUtil;
import com.park.dao.IBaseDao;
import com.park.service.IDictService;
import com.park.service.IMemberService;

@Service
public class MemberServiceImpl extends BaseService implements IMemberService {
	
	@Autowired
	private IBaseDao baseDao;
	
	@Autowired
	private IDictService dictService;
	
	@Override
	public Integer saveMember(UserMember userMember) {
		Integer memberId = userMember.getMemberId();
		String nowDate = DateUtil.getNowDate();
		if(memberId == null){ //新增
			userMember.setCreateTime(nowDate);
			baseDao.save(userMember, null);
			return userMember.getMemberId();
		}else{ //编辑
			UserMember userMemberDB = getUserMember(memberId);
			if(userMemberDB == null) throw new MessageException("会员信息不存在！");
			userMemberDB.setMemberName(userMember.getMemberName());
			userMemberDB.setMemberMobile(userMember.getMemberMobile());
			userMemberDB.setMemberMobile2(userMember.getMemberMobile2());
			userMemberDB.setMemberIdcard(userMember.getMemberIdcard());
			userMemberDB.setMemberSex(userMember.getMemberSex());
			userMemberDB.setMemberBirthday(userMember.getMemberBirthday());
			userMemberDB.setMemberAddress(userMember.getMemberAddress());
			userMemberDB.setMemberRemark(userMember.getMemberRemark());
			userMemberDB.setSalesId(userMember.getSalesId());
			userMemberDB.setUpdateTime(nowDate);
			baseDao.save(userMemberDB, userMemberDB.getMemberId());
			return userMemberDB.getMemberId();
		}
	}
	
	@Override
	public void updateMemberName(UserMember userMember) {
		Integer memberId = userMember.getMemberId();
		String nowDate = DateUtil.getNowDate();
		if(memberId == null) throw new MessageException("会员id为null！");
		UserMember userMemberDB = baseDao.getToEvict(UserMember.class, memberId);
		if(userMemberDB == null) throw new MessageException("会员信息不存在！");
		userMemberDB.setMemberName(userMember.getMemberName());
		userMemberDB.setMemberMobile(userMember.getMemberMobile());
		userMemberDB.setUpdateTime(nowDate);
		baseDao.save(userMemberDB, userMemberDB.getMemberId());
	}
	
	@Override
	public Map<String, Object> saveMemberCar(MemberCard memberCard, OtherBalance otherBalance) {
		UserMember userMember = getUserMember(memberCard.getMemberId());
		if(userMember == null) throw new MessageException("会员信息不存在！");
		if(userMember.getCardId() != null && userMember.getCardId() != 0) throw new MessageException("该会员已绑定了会员卡！");
		MemberCardType memberCarType = getMemberCardType(memberCard.getCardTypeId());
		if(memberCarType == null) throw new MessageException("会员卡类型不存在！");
		memberCard.setCardDeadline(DateUtil.cardDeadline(memberCarType.getCardTypeMonth())); //计算会员卡截至日期(0:无限制)
		String nowDate = DateUtil.getNowDate();
		memberCard.setCardNo(getCardNo()); //会员卡编号	唯一的;
		memberCard.setCreateTime(nowDate);
		baseDao.save(memberCard, memberCard.getCardId());
		userMember.setCardId(memberCard.getCardId());
		baseDao.save(userMember, userMember.getMemberId());
		otherBalance.setBalanceServiceType(IDBConstant.BALANCE_SERVICE_TYPE_REG);
		otherBalance.setBalanceServiceId(memberCard.getCardId());
		otherBalance.setBalanceNo(getBalanceNo());
		otherBalance.setCreateTime(nowDate);
		otherBalance.setServiceDate(nowDate);
		otherBalance.setBalanceType(IDBConstant.BALANCE_TYPE_OTHER);
		otherBalance.setSalesId(memberCard.getSalesId());
		baseDao.save(otherBalance, null);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("cardId", memberCard.getCardId());
		data.put("balanceNo", otherBalance.getBalanceNo());
		data.put("balanceServiceType", otherBalance.getBalanceServiceType());
		data.put("balanceServiceTypeName", dictService.getDictValueByNameKey(IDBConstant.BALANCE_SERVICE_TYPE, otherBalance.getBalanceServiceType()));
		data.put("createTime", otherBalance.getCreateTime());
		data.put("balanceStatus", dictService.getDictValueByNameKey(IDBConstant.BALANCE_SERVICE_TYPE, otherBalance.getBalanceStatus()));
		return data;
	}
	
	@Override
	public List<Map<String, Object>> getMemberCarTypeNames() {
		return baseDao.queryBySql("SELECT cardTypeId, cardTypeName FROM member_card_type WHERE cardTypeStatus = ?", IDBConstant.LOGIC_STATUS_YES);
	}
	
	@Override
	public PageBean getMemberCarTypes(MemberInputView memberInputView) {
		String cardTypeStatus = memberInputView.getCardTypeStatus();
		StringBuilder headSql = new StringBuilder("SELECT cardTypeId, cardTypeName, cardTypeStatus, cardTypeMonth, cardTypeMoney, cardTypeDiscount, cardTypeWeek, cardTypeTimeStart, cardTypeTimeEnd, salesId, DATE_FORMAT(createTime,'%Y-%m-%d') createTime");
		StringBuilder bodySql = new StringBuilder(" FROM member_card_type");
		StringBuilder whereSql = new StringBuilder(" WHERE 1=1");
		if(StrUtil.isNotBlank(cardTypeStatus)){
			whereSql.append(" AND cardTypeStatus = :cardTypeStatus");
		}
		return super.getPageBean(headSql, bodySql, whereSql, memberInputView);
	}
	
	@Override
	public MemberCardType getMemberCardType(int cardTypeId) {
		return baseDao.queryByHqlFirst("FROM MemberCardType WHERE cardTypeId = ?", cardTypeId);
	}
	
	@Override
	public Map<String, Object> getMemberCardTypeMap(int cardTypeId) {
		Map<String, Object> memberCardType = baseDao.queryBySqlFirst("SELECT cardTypeId, cardTypeName, cardTypeMonth, cardTypeMoney, cardTypeDiscount, cardTypeWeek, cardTypeTimeStart, cardTypeTimeEnd FROM member_card_type WHERE cardTypeId = ?", cardTypeId);
		memberCardType.put("cardDeadline", DateUtil.getAddMonth(StrUtil.objToStr(memberCardType.get("cardTypeMonth"))));
		return memberCardType;
	}
	
	@Override
	public UserMember getUserMember(int memberId) {
		return baseDao.getToEvict(UserMember.class, memberId);
	}
	
	@Override
	public MemberCard getMemberCard(int cardId) {
		return baseDao.getToEvict(MemberCard.class, cardId);
	}
	
	@Override
	public Integer saveInvoice(OtherInvoice invoice) {
		if(getInvoice(invoice.getInvoiceServiceType(), invoice.getInvoiceServiceId()) != null) throw new MessageException("已打印！");
		invoice.setInvoiceState(IDBConstant.LOGIC_STATUS_NO); //打印发票，默认未领取
		invoice.setCreateTime(DateUtil.getNowDate());
		invoice.setPrintTime(DateUtil.getNowDate());
		baseDao.save(invoice, null);
		return invoice.getInvoiceId();
	}
	
	@Override
	public OtherInvoice getInvoice(String serviceType, int serviceId) {
		return baseDao.queryByHqlFirst("FROM OtherInvoice WHERE invoiceServiceType=? AND invoiceServiceId=?", serviceType, serviceId);
	}
	
	@Override
	public PageBean getUserMembers(MemberInputView memberInputView) {
		String memberMobile = memberInputView.getMemberMobile();
		String memberIdcard = memberInputView.getMemberIdcard();
		String cardNo = memberInputView.getCardNo();
		String cardTypeId = memberInputView.getCardTypeId();
		StringBuilder headSql = new StringBuilder("SELECT um.memberId, mc.cardId, um.memberName, um.memberMobile, um.memberIdcard, mc.cardNo, mc.cardTypeId, mc.cardDeadline, mc.cardBalance, mc.cardStatus, mc.salesId, DATE_FORMAT(mc.createTime,'%Y-%m-%d') createTime");
		StringBuilder bodySql = new StringBuilder(" FROM user_member um, member_card mc");
		StringBuilder whereSql = new StringBuilder(" WHERE um.cardId = mc.cardId");
		if(StrUtil.isNotBlank(memberMobile)){
			whereSql.append(" AND um.memberMobile = :memberMobile");
		}
		if(StrUtil.isNotBlank(memberIdcard)){
			whereSql.append(" AND um.memberIdcard = :memberIdcard");
		}
		if(StrUtil.isNotBlank(cardNo)){
			whereSql.append(" AND mc.cardNo = :cardNo");
		}
		if(StrUtil.isNotBlank(cardTypeId)){
			whereSql.append(" AND mc.cardTypeId = :cardTypeId");
		}
		PageBean pageBean = super.getPageBean(headSql, bodySql, whereSql, memberInputView);
		List<Map<String, Object>> list = pageBean.getList();
		for(Map<String, Object> map : list){
			map.put("cardTypeName", baseDao.getUniqueObjectResult("SELECT cardTypeName FROM member_card_type WHERE cardTypeId = ?", map.get("cardTypeId")));
			getType(map);
		}
		return pageBean;
	}

	@Override
	public Map<String, Object> getUserMemberAndCard(int memberId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT um.memberId, mc.cardId, um.memberName, um.memberSex, um.memberBirthday, um.memberAddress, um.memberRemark, um.memberMobile, um.memberMobile2, um.memberIdcard, mc.cardNo, mc.cardTypeId, mc.cardDeadline, mc.cardBalance, mc.cardStatus, mc.salesId, DATE_FORMAT(mc.createTime,'%Y-%m-%d') createTime, mct.cardTypeDiscount, mct.cardTypeWeek, mct.cardTypeTimeStart, mct.cardTypeTimeEnd, mct.cardTypeName");
		sql.append(" FROM user_member um, member_card mc, member_card_type mct WHERE um.cardId = mc.cardId AND mc.cardTypeId = mct.cardTypeId AND um.memberId = ?");
		Map<String, Object> memberMap = baseDao.queryBySqlFirst(sql.toString(), memberId);
		if(memberMap == null) throw new MessageException("会员信息不存在！");
		return getType(memberMap);
	}
	
	@Override
	public Integer saveMemberCardType(MemberCardType memberCardType) {
		String[] cardTypeTimes = memberCardType.getCardTypeTime().split("-");
		if(memberCardType.getCardTypeId() == null){
			if(baseDao.getUniqueObjectResult("SELECT 1 FROM member_card_type WHERE cardTypeName = ?", memberCardType.getCardTypeName()) != null) throw new MessageException("会员卡类型名称已存在！");
		}
		memberCardType.setCardTypeTimeStart(cardTypeTimes[0]);
		memberCardType.setCardTypeTimeEnd(cardTypeTimes[1]);
		memberCardType.setCreateTime(DateUtil.getNowDate());
		baseDao.save(memberCardType, memberCardType.getCardTypeId());
		return memberCardType.getCardTypeId();
	}
	
	@Override
	public Integer updateMemberCardUpLevel(MemberCardOpInputView memberCardOpInputView) {
		String cardId = memberCardOpInputView.getCardId();
		String cardTypeId = memberCardOpInputView.getCardTypeId();
		String balanceStyle = memberCardOpInputView.getBalanceStyle();
		String upLevelMoney = memberCardOpInputView.getUpLevelMoney();
		String subMoney = memberCardOpInputView.getSubMoney();
		String remark = memberCardOpInputView.getRemark();
		int salesId = StrUtil.objToInt(memberCardOpInputView.getSalesId());
		MemberCard memberCard = getMemberCard(StrUtil.objToInt(cardId));
		if(memberCard == null) throw new MessageException("会员卡信息不存在！");
		MemberCardType memberCardType = getMemberCardType(StrUtil.objToInt(cardTypeId));
		if(memberCardType == null) throw new MessageException("会员卡类型不存在！");
		if(cardTypeId.equals(StrUtil.objToStr(memberCard.getCardTypeId()))) throw new MessageException("当前会员卡已经是此类型！");
		String nowDate = DateUtil.getNowDate();
		memberCard.setCardDeadline(DateUtil.cardDeadline(memberCardType.getCardTypeMonth())); //会员卡升级，根据新会员卡类型重新计算会员卡截至日期
		memberCard.setCardTypeId(memberCardType.getCardTypeId());
		memberCard.setCardStatus(IDBConstant.LOGIC_STATUS_YES); //升级会员卡后，默认会员卡为可用状态
		memberCard.setSalesId(salesId); //升级会员卡后，操作员覆盖为当前的
		memberCard.setUpdateTime(nowDate);
		baseDao.save(memberCard, memberCard.getCardId());
		OtherBalance balance = new OtherBalance();
		balance.setBalanceNo(getBalanceNo());
		balance.setBalanceServiceId(memberCard.getCardId());
		balance.setBalanceServiceType(IDBConstant.BALANCE_SERVICE_TYPE_CARD_UP);
		balance.setBalanceStyle(balanceStyle);
		balance.setOldAmount(StrUtil.objToDouble(upLevelMoney));
		balance.setSubAmount(StrUtil.objToDouble(subMoney));
		balance.setRealAmount(balance.getOldAmount()-balance.getSubAmount());
		balance.setBalanceType(IDBConstant.BALANCE_TYPE_OTHER);
		balance.setServiceDate(nowDate);
		balance.setCreateTime(nowDate);
		balance.setRemark(remark);
		balance.setSalesId(salesId);
		baseDao.save(balance, null);
		return memberCard.getCardId();
	}
	
	@Override
	public Integer updateMemberCardCZ(MemberCardOpInputView memberCardOpInputView) {
		String cardId = memberCardOpInputView.getCardId();
		String balanceStyle = memberCardOpInputView.getBalanceStyle();
		String czMoney = memberCardOpInputView.getCzMoney();
		String subMoney = memberCardOpInputView.getSubMoney();
		String remark = memberCardOpInputView.getRemark();
		int salesId = StrUtil.objToInt(memberCardOpInputView.getSalesId());
		MemberCard memberCard = getMemberCard(StrUtil.objToInt(cardId));
		if(memberCard == null) throw new MessageException("会员卡信息不存在！");
		
		String nowDate = DateUtil.getNowDate();
		
		memberCard.setCardBalance(memberCard.getCardBalance()+StrUtil.objToDouble(czMoney));
		memberCard.setUpdateTime(nowDate);
		baseDao.save(memberCard, memberCard.getCardId());
		
		OtherBalance balance = new OtherBalance();
		balance.setBalanceNo(getBalanceNo());
		balance.setBalanceServiceId(memberCard.getCardId());
		balance.setBalanceServiceType(IDBConstant.BALANCE_SERVICE_TYPE_CARD_CZ);
		balance.setBalanceStyle(balanceStyle);
		balance.setOldAmount(StrUtil.objToDouble(czMoney));
		balance.setSubAmount(StrUtil.objToDouble(subMoney));
		balance.setRealAmount(balance.getOldAmount()-balance.getSubAmount());
		balance.setBalanceType(IDBConstant.BALANCE_TYPE_CZ);
		balance.setServiceDate(nowDate);
		balance.setCreateTime(nowDate);
		balance.setRemark(remark);
		balance.setSalesId(salesId);
		baseDao.save(balance, null);
		return memberCard.getCardId();
	}
	
	@Override
	public Integer updateMemberCardBuBan(MemberCardOpInputView memberCardOpInputView) {
		String cardId = memberCardOpInputView.getCardId();
		String balanceStyle = memberCardOpInputView.getBalanceStyle();
		String buBanMoney = memberCardOpInputView.getBuBanMoney();
		String subMoney = memberCardOpInputView.getSubMoney();
		String remark = memberCardOpInputView.getRemark();
		int salesId = StrUtil.objToInt(memberCardOpInputView.getSalesId());
		MemberCard memberCard = getMemberCard(StrUtil.objToInt(cardId));
		if(memberCard == null) throw new MessageException("会员卡信息不存在！");
		
		String nowDate = DateUtil.getNowDate();
		
		memberCard.setCardStatus(IDBConstant.LOGIC_STATUS_YES); //补办会员卡后，默认会员卡为可用状态
		memberCard.setSalesId(salesId); //补办会员卡后，操作员覆盖为当前的
		memberCard.setUpdateTime(nowDate);
		baseDao.save(memberCard, memberCard.getCardId());
		
		OtherBalance balance = new OtherBalance();
		balance.setBalanceNo(getBalanceNo());
		balance.setBalanceServiceId(memberCard.getCardId());
		balance.setBalanceServiceType(IDBConstant.BALANCE_SERVICE_TYPE_CARD_BUBAN);
		balance.setBalanceStyle(balanceStyle);
		balance.setOldAmount(StrUtil.objToDouble(buBanMoney));
		balance.setSubAmount(StrUtil.objToDouble(subMoney));
		balance.setRealAmount(balance.getOldAmount()-balance.getSubAmount());
		balance.setBalanceType(IDBConstant.BALANCE_TYPE_OTHER);
		balance.setServiceDate(nowDate);
		balance.setCreateTime(nowDate);
		balance.setRemark(remark);
		balance.setSalesId(salesId);
		baseDao.save(balance, null);
		return memberCard.getCardId();
	}
	
	@Override
	public PageBean getBalances(BalanceInputView balanceInputView) {
		String balanceType = balanceInputView.getBalanceType();
		String createTimeStart = balanceInputView.getCreateTimeStart();
		String createTimeEnd = balanceInputView.getCreateTimeEnd();
		String memberMobile = balanceInputView.getMemberMobile();
		String cardId = balanceInputView.getCardId();
		
		StringBuilder headSql = new StringBuilder("SELECT balanceId, balanceNo, memberName, balanceServiceType, balanceStyle, balanceServiceName, realAmount, balanceStatus, ob.salesId, DATE_FORMAT(ob.createTime,'%Y-%m-%d') createTime");
		StringBuilder bodySql = new StringBuilder(" FROM other_balance ob, member_card mc, user_member um");
		StringBuilder whereSql = new StringBuilder(" WHERE ob.balanceServiceId = mc.cardId AND mc.cardId = um.cardId");
		if(StrUtil.isNotBlank(balanceType)){
			whereSql.append(" AND ob.balanceType IN(:balanceTypeArr)");
		}
		if(StrUtil.isNotBlank(createTimeStart)){
			whereSql.append(" AND ob.createTime >= :createTimeStart");
		}
		if(StrUtil.isNotBlank(createTimeEnd)){
			whereSql.append(" AND ob.createTime <= :createTimeEnd");
		}
		if(StrUtil.isNotBlank(memberMobile)){
			whereSql.append(" AND um.memberMobile = :memberMobile");
		}
		if(StrUtil.isNotBlank(cardId)){
			whereSql.append(" AND mc.cardId = :cardId");
		}
		PageBean pageBean = super.getPageBean(headSql, bodySql, whereSql, balanceInputView, SQLUtil.getInToSQL("balanceTypeArr", balanceType));
		List<Map<String, Object>> list = pageBean.getList();
		for(Map<String, Object> map : list){ 
			map.put("balanceServiceTypeName", dictService.getDictValueByNameKey(IDBConstant.BALANCE_SERVICE_TYPE, StrUtil.objToStr(map.get("balanceServiceType"))));
			map.put("balanceStyleName", dictService.getDictValueByNameKey(IDBConstant.BALANCE_STYLE, StrUtil.objToStr(map.get("balanceStyle"))));
		}
		return pageBean;
	}
	
	@Override
	public PageBean getInvoices(InvoiceInputView invoiceInputView){
		String status = invoiceInputView.getStatus();
		
		StringBuilder headSql = new StringBuilder("SELECT *");
		StringBuilder bodySql = new StringBuilder(" FROM other_invoice");
		StringBuilder whereSql = new StringBuilder(" WHERE 1=1");
		
		if(StrUtil.isNotBlank(status)){
			whereSql.append(" AND invoiceState = :status");
		}
		return super.getPageBean(headSql, bodySql, whereSql, invoiceInputView);
	}
	
	@Override
	public void updateGetInvoices(String invoiceIds) {
		String[] invoiceIdArr = invoiceIds.split(",");
		if(ArrayUtils.isEmpty(invoiceIdArr)) throw new MessageException("请选择发票进行操作！");
		Map params = SQLUtil.getInToSQL("invoiceIdArr", invoiceIdArr);
		params.put("invoiceState", IDBConstant.LOGIC_STATUS_YES);
		baseDao.updateBySql("UPDATE other_invoice o SET invoiceState=:invoiceState WHERE o.invoiceId IN(:invoiceIdArr)", params);
	}
	
	private Map<String, Object> getType(Map<String, Object> map) {
		map.put("cardStatusName", dictService.getDictValueByNameKey(IDBConstant.STATUS, StrUtil.objToStr(map.get("cardStatus"))));
		return map;
	}
	
	private String getCardNo() {
		do {
			StringBuffer no = new StringBuffer();
			for(int i = 0; i < 6; i++){
				no.append((int)(Math.random()*10));
			}
			if(baseDao.getUniqueResult("SELECT 1 FROM member_card WHERE cardNo = ?", no) == null) return no.toString(); 
		} while (true);
	}
	
	private String getBalanceNo(){
		return DateUtil.dateToString(new Date(), DateUtil.YYYYMMDD_HMS);
	}

}



