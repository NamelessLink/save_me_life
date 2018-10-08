package com.lxs.otherentity;

public class HotDish extends DishName implements Comparable<HotDish>{

    private int dishSales;

    public HotDish(){
        this.dishSales = 0;
    }

    public void setDishSales(int dishSales) { this.dishSales = dishSales; }

    public int getDishSales() { return dishSales; }

    @Override
    public int compareTo(HotDish o) {
        if(this.dishSales <= o.getDishSales()){
            return 1;
        }else {
            return -1;
        }
    }
}
