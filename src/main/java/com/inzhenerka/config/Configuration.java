package com.inzhenerka.config;

import java.util.List;
import java.util.Objects;

public class Configuration {
    private String src;
    private String dst;
    private List<String> repositories;
    private List<Dependency> dependencies;

    public Configuration() {
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

    public List<String> getRepositories() {
        return repositories;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Configuration that)) return false;
        return Objects.equals(getSrc(), that.getSrc()) && Objects.equals(getDst(), that.getDst()) && Objects.equals(getRepositories(), that.getRepositories()) && Objects.equals(getDependencies(), that.getDependencies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSrc(), getDst(), getRepositories(), getDependencies());
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "src='" + src + '\'' +
                ", dst='" + dst + '\'' +
                ", repositories=" + repositories +
                ", dependencies=" + dependencies +
                '}';
    }
}
