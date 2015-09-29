package com.offerme.server.database.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.offerme.server.database.model.Offer;

public interface OfferDao extends BaseDao{
	
	public int insertOffer(Offer offer) throws InvocationTargetException;
    
    public void updateOffer(Offer offer) throws InvocationTargetException;
    
    public void deleteOffer(int offerId) throws InvocationTargetException;
    
    public Offer getOffer(int offerId) throws InvocationTargetException;
    /**
     * 根据城市,公司模糊查询
     * @param city 城市
     * @param comp 公司
     * @return
     * @throws Exception
     */
    public List<Offer> getOfferByDemand(String city, String keyword) throws InvocationTargetException;
    /**
     * 随机生成Offer
     * @param count
     * @return
     * @throws Exception
     */
    public List<Offer> getOfferByRandom(int count) throws InvocationTargetException;
    /**
     * 根据UserId获取Offer列表(收藏)
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Offer> getOfferListByUserId(int userId) throws InvocationTargetException;
    
    /**
     * 获取user所发布的所有offer
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Offer> getPublishOfferListByUserId(int userId) throws InvocationTargetException;

}
