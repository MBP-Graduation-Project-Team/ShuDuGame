package com.mbp.sudoku.entity;

/**
 * @author 邓宁
 * @deprecated  Created in 17:25 2019/11/21
 */
public class GameMapEntity {
    private Integer id;
    private String gameMap;
    private String mapStatus;
    private String goodTime;
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

    @Override
    public String toString() {
        return "GameMapEntity{" +
                "id=" + id +
                ", gameMap='" + gameMap + '\'' +
                ", mapStatus='" + mapStatus + '\'' +
                ", goodTime='" + goodTime + '\'' +
                ", status=" + status +
                '}';
    }
}
