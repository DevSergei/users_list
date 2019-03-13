package me.bkkn.users.overflow;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public final class UsersResponse {
    private ArrayList<UserOverFlow> items;

    public ArrayList<UserOverFlow> getItems() {
        return items;
    }

    public UsersResponse(ArrayList<UserOverFlow> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "UsersResponse{" +
                "items=" + items +
                '}';
    }
}
