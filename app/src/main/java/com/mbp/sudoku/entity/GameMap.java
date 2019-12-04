package com.mbp.sudoku.entity;

/**
 * 游戏地图类
 */
public class GameMap {
    /** 关卡编号 **/
    private Integer id;
    /** 原始地图 **/
    private String gameMap;
    /** 游戏地图 **/
    private String mapStatus;
    /** 最佳通关时间 **/
    private String goodTime;
    /** 关卡状态 **/
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGameMap() {
        return gameMap;
    }

    public void setGameMap(String gameMap) {
        this.gameMap = gameMap;
    }

    public String getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(String mapStatus) {
        this.mapStatus = mapStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodTime() {
        return goodTime;
    }

    public void setGoodTime(String goodTime) {
        this.goodTime = goodTime;
    }

}
