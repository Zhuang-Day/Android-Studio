package com.example.myapplication;

public class Spn_Category implements Spn
{
    private String category;

    public Spn_Category(String category)
    {
        this.category = category;
    }
    public String get()
    {
        return category;
    }
}
