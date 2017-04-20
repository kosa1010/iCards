package com.example.kosa1010.icards.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by kosa1010 on 15.03.17.
 */
@DatabaseTable
public class Card {

    @DatabaseField(generatedId = true)
    long id;

    @DatabaseField
    String name;

    @DatabaseField
    String type;

    @DatabaseField
    String code;

    @DatabaseField
    String login;

    @DatabaseField
    String pass;

    public Card() {
    }

    public Card(String name, String type, String code, String login, String pass) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.login = login;
        this.pass = pass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
