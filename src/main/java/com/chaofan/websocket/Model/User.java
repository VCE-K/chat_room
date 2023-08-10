package com.chaofan.websocket.Model;

import com.chaofan.websocket.Web.MyWebSocket;

import java.util.Objects;

/**
 * Created by Chaofan at 2018/7/6 10:08
 * email:chaofan2685@qq.com
 **/
public class User {

    public String id;
    public String nickname;
    public MyWebSocket webSocket;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public User(String id, String nickname, MyWebSocket webSocket) {
        this.id = id;
        this.nickname = nickname;
        this.webSocket = webSocket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public MyWebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(MyWebSocket webSocket) {
        this.webSocket = webSocket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
