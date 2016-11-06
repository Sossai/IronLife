package dev42.ironlife.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fernando on 05/11/2016.
 */

public class UsuarioLogadoBung {
    private String displayName;
    private String membershipId;
    private String groupId;
    private String groupName;
    private final Context context;

    public UsuarioLogadoBung(Context context){
        this.context = context;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setDadosShared(){
        SharedPreferences settings = context.getSharedPreferences("UsuarioLogado",0);
        SharedPreferences.Editor editor = settings.edit();
        //**  Salva no SharedPreferences  **
        editor.putString("membershipId", this.membershipId);
        editor.putString("displayName", this.displayName);
        editor.putString("groupId", this.groupId);
        editor.putString("groupName", this.groupName);

        editor.commit();
    }
    public void getDadosShared(){
        SharedPreferences settings = context.getSharedPreferences("UsuarioLogado",0);
        this.membershipId = settings.getString("membershipId","");
        this.displayName = settings.getString("displayName","");
        this.groupId = settings.getString("groupId","");
        this.groupName = settings.getString("groupName","");
    }

    public void deleteDadosShared(){
        SharedPreferences preferences = context.getSharedPreferences("UsuarioLogado", 0);
        preferences.edit().clear().commit();
    }
}
