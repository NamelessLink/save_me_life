package com.lxs.otherentity;

import com.lxs.entity.Dish;

import java.util.List;

public class RestaruantDetail {

    private String r_id;

    private String r_name;

    private List<DishName> Dishes;

    public String getR_id() { return r_id; }

    public void setR_id(String r_id) { this.r_id = r_id; }

    public String getR_name() { return r_name; }

    public void setR_name(String r_name) { this.r_name = r_name; }

    public List<DishName> getDishes() { return Dishes; }

    public void setDishes(List<DishName> dishes) { Dishes = dishes; }
}
