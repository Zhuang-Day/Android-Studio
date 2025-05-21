package com.example.myapplication;

public class Spn_Remid implements Spn
{
    private String remid;
    public Spn_Remid(String remid)
    {
        this.remid = remid;
    }
    public String get()
    {
        return remid;
    }
}
