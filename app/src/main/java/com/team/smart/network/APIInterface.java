package com.team.smart.network;

import com.team.smart.vo.FoodDetailVO;
import com.team.smart.vo.FoodOrderVO;
import com.team.smart.vo.FoodStoreVO;
import com.team.smart.vo.ParkingBDVO;
import com.team.smart.vo.Foods;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface
{
    //protocall 약속
    //@GET("/smart/api/food/getList")
    //Call<Foods> DishesList(@Query("page") HashMap<String,String> param);
    //주차관리 은지 시작
    //주차장 건물 전체리스트 정보
    @GET("/api/parking/ParkingInfo")
    Call<ParkingBDVO> getParkingInfo();

    //주차장 건물 정보 상세
    @GET("/api/parking/ParkingbuidingInfo")
    Call<ParkingBDVO> getParkingBDInfo(@Query("b_code") String b_code);
    //주차관리 은지 종료

    //지혜 시작
    @GET("/api/food/getStoreList")
    Call<FoodStoreVO> DishesList(@Query("f_category") String f_category, @Query("comp_seq") String comp_seq);
    @GET("/api/food/getStoreList")
    Call<FoodStoreVO> foodStore(@Query("comp_seq") String comp_seq);
    @GET("/api/food/getFoodMenuList")
    Call<FoodDetailVO> foodMenuList(@Query("comp_seq") String comp_seq);
    @POST("/api/food/payTest")
    Call<HashMap> kakaoPayTest(@Body FoodOrderVO foodOrderVO);
    @POST
    Call<HashMap> kakaoPaySuccess(@Url String reUrl, @Query("f_ocode") String f_ocode);
    Call<FoodOrderVO> getOrderDerailInfo(@Query("comp_seq") String comp_seq, @Query("id") String id); //주문정보 1건 조회
    //지혜 종료

}
