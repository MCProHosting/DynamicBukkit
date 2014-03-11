package com.mcprohosting.plugins.dynamicbukkit;

public abstract class DynamicPlugin {

    private final String NAME = this.getClass().getSimpleName();

    public abstract void onLoad();

    public String getName() {
        return NAME;
    }

}