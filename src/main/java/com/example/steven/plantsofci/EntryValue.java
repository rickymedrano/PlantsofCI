package com.example.steven.plantsofci;

/**
 * Object to store generic values for database entries
 */

public class EntryValue <T>{
    private T obj;

    EntryValue(T obj){ this.obj = obj; }

    public T getObj() {
        return this.obj;
    }

    public Boolean compareObj(T compare){
        Boolean check = false;

        if(obj.equals(compare)){
            check = true;
        }

        return check;
    }

}
