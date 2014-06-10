package com.lilleswing.scunt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lilleswing.scunt.assets.AssetsBundleConfiguration;
import com.lilleswing.scunt.assets.AssetsConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ScuntConfiguration extends Configuration implements AssetsBundleConfiguration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = new AssetsConfiguration();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }
}
