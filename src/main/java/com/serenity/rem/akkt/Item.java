package com.serenity.rem.akkt;

public class Item {


    protected String name;
    protected Player Player;
    protected int amount;
    protected Enemy enemy;

    public Item(Player p, String name, int amount){
        this.Player = p;
        this.name = name;
        this.amount = amount;
    }
    public Item(Enemy e, String name, int amount){
        this.enemy = e;
        this.name = name;
        this.amount = amount;
    }

    public void buy(){
        System.out.println("BOUGHT A " + name);
        Player.addItem(this);

    }

    public void addAmount(int add){
        amount += add;
    }

    public void minusAmount(int minus){
        amount -= minus;
    }


    //protected void init(int amount, String name){
      //  this.name = name;
        //this.amount = amount;
    //}
}
