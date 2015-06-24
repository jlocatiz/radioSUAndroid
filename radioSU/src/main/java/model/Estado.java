package model;

/**
 * Created by guilherme on 23/06/15.
 */
public class Estado {

    String uf;

    String name;

    public Estado(String uf, String name) {
        this.uf = uf;
        this.name = name;
    }

    public Estado() {
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
