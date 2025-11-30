package com.soulforged.duel.model;

public class CardTemplate {

    private String code;
    private String name;
    private int attack;
    private int health;
    private int manaCost;

    public CardTemplate() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getManaCost() { return manaCost; }
    public void setManaCost(int manaCost) { this.manaCost = manaCost; }
}
