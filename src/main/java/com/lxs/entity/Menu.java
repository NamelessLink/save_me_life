package com.lxs.entity;

public class Menu extends MenuKey implements Comparable<Menu>{
    private int sales;

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @Override
    public int compareTo(Menu o) {
        if(this.sales <= o.getSales()){
            return 1;
        }else {
            return -1;
        }
    }
}