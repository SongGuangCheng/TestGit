package org.song.network.netty5demo.demo;

import java.io.Serializable;

public class BodyDTO implements Serializable {

    private static final long SerialVersionUID = 1L;

    private String id;
    private String name;
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
