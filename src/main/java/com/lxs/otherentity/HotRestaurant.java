package com.lxs.otherentity;

import java.util.List;

public class HotRestaurant extends RestaurantName implements Comparable<HotRestaurant>{

    private int rSales;

//    private List<HotDish> hotDishes;

    public HotRestaurant(){
        this.rSales = 0;
    }

    public void setrSales(int rSales) { this.rSales = rSales; }

    public int getrSales() { return rSales; }

//    public void setHotDishes(List<HotDish> hotDishes) { this.hotDishes = hotDishes; }

//    public List<HotDish> getHotDishes() { return hotDishes; }

    @Override
    public int compareTo(HotRestaurant o) {
        if(this.rSales <= o.getrSales()){
            return 1;
        }else {
            return -1;
        }
    }
}
