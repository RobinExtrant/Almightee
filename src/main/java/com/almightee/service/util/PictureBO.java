package com.almightee.service.util;

import java.nio.file.Path;
import java.util.Arrays;

public class PictureBO {
    private String name;
    private byte[] content;

    public PictureBO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
