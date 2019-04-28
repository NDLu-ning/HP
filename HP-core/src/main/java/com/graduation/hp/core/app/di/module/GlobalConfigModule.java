package com.graduation.hp.core.app.di.module;

import com.graduation.hp.core.app.di.qualifier.DatabaseInfo;
import com.graduation.hp.core.repository.http.interceptor.DebugInterceptor;
import com.graduation.hp.core.repository.http.log.HttpLogger;
import com.graduation.hp.core.repository.http.params.ParamsAdder;
import com.graduation.hp.core.repository.http.url.UrlManager;
import com.graduation.hp.core.repository.http.validator.DataValidator;
import com.graduation.hp.core.utils.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by Ning on 2018/11/19.
 */

@Module
public class GlobalConfigModule {

    private boolean validate;
    private boolean debug;
    private boolean runUrlManager;
    private HttpUrl httpUrl;
    private HttpLogger httpLogger;
    private DebugInterceptor.Level level;
    private List<Interceptor> interceptors;
    private List<DataValidator> dataValidators;
    private List<ParamsAdder> paramsAdders;
    private ClientModule.RetrofitConfiguration retrofitConfiguration;
    private ClientModule.OkHttpConfiguration okHttpConfiguration;
    private String dbName;


    private GlobalConfigModule(Builder builder) {
        this.debug = builder.debug;
        this.validate = builder.validate;
        this.httpUrl = builder.httpUrl;
        this.httpLogger = Preconditions.getValue(builder.httpLogger, HttpLogger.DEFAULT);
        this.level = Preconditions.getValue(builder.level, DebugInterceptor.Level.BASIC);
        this.interceptors = builder.interceptors == null || builder.interceptors.size() == 0 ? new ArrayList<Interceptor>() : builder.interceptors;
        this.dataValidators = builder.dataValidators == null || builder.dataValidators.size() == 0 ? Arrays.asList(DataValidator.DEFAULT) : builder.dataValidators;
        this.paramsAdders = builder.paramsAdders == null || builder.paramsAdders.size() == 0 ? Arrays.asList(ParamsAdder.DEFAULT) : builder.paramsAdders;
        this.okHttpConfiguration = builder.okHttpConfiguration;
        this.retrofitConfiguration = builder.retrofitConfiguration;
        this.runUrlManager = builder.runUrlManager;
        this.dbName = builder.dbName;
    }

    public static GlobalConfigModule.Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private boolean debug;
        private boolean validate;
        private boolean runUrlManager;
        private String preName;
        private String dbName;
        private HttpUrl httpUrl;
        private DebugInterceptor.Level level;
        private HttpLogger httpLogger;
        private List<Interceptor> interceptors;
        private ClientModule.RetrofitConfiguration retrofitConfiguration;
        private ClientModule.OkHttpConfiguration okHttpConfiguration;
        private List<DataValidator> dataValidators;
        private List<ParamsAdder> paramsAdders;

        public Builder retrofitConfiguration(ClientModule.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okHttpConfiguration(ClientModule.OkHttpConfiguration okHttpConfiguration) {
            this.okHttpConfiguration = okHttpConfiguration;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptors == null) {
                interceptors = new ArrayList<>();
            }
            interceptors.add(interceptor);
            return this;
        }

        public Builder addDataValidator(DataValidator validator) {
            if (dataValidators == null) {
                dataValidators = new ArrayList<>();
            }
            dataValidators.add(validator);
            return this;
        }

        public Builder addParamsAdder(ParamsAdder paramsAdder) {
            if (paramsAdders == null) {
                paramsAdders = new ArrayList<>();
            }
            paramsAdders.add(paramsAdder);
            return this;
        }

        public Builder debug(boolean debug) {
            this.debug = debug;
            UrlManager.getInstance().setDebug(debug);
            return this;
        }

        public Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        public Builder runUrlManager(boolean run) {
            this.runUrlManager = run;
            UrlManager.getInstance().setRun(run);
            return this;
        }

        public Builder level(DebugInterceptor.Level level) {
            this.level = level;
            return this;
        }

        public Builder httpLogger(HttpLogger httpLogger) {
            this.httpLogger = httpLogger;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            UrlManager.getInstance().setGlobalDomain(baseUrl);
            this.httpUrl = HttpUrl.parse(Preconditions.checkNotNull(baseUrl, "baseUrl can not be null."));
            return this;
        }

        public Builder baseUrl(HttpUrl baseUrl) {
            this.httpUrl = Preconditions.checkNotNull(baseUrl, HttpUrl.class.getCanonicalName() + "can not be null.");
            UrlManager.getInstance().setGlobalDomain(baseUrl.url().toString());
            return this;
        }

        public Builder startAdvancedUrl(String url) {
            this.httpUrl = HttpUrl.parse(Preconditions.checkNotNull(url, "baseUrl can not be null."));
            UrlManager.getInstance().startAdvancedModel(url);
            return this;
        }

        public Builder multiDomain(String key, String domain) {
            Preconditions.checkNotNull(key, "Domain-Key can not be null.");
            Preconditions.checkNotNull(domain, "Domain-Value can not be null.");
            UrlManager.getInstance().putDomain(key, domain);
            return this;
        }

        public Builder domainMap(Map<String, String> map) {
            Preconditions.checkNotNull(map, "domainMap can not be null.");
            Set<Map.Entry<String, String>> entries = map.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                UrlManager.getInstance().putDomain(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }

    @Provides
    @Singleton
    public ClientModule.OkHttpConfiguration provideOkHttpConfiguration() {
        return okHttpConfiguration;
    }

    @Provides
    @Singleton
    public ClientModule.RetrofitConfiguration provideRetrofitConfiguration() {
        return retrofitConfiguration;
    }

    @Provides
    @Singleton
    public List<Interceptor> provideInterceptors() {
        return interceptors;
    }

    @Provides
    @Singleton
    public HttpUrl provideHttpUrl() {
        return httpUrl;
    }

    @Provides
    @Singleton
    public HttpLogger provideHttpLogger() {
        return httpLogger;
    }

    @Provides
    @Singleton
    @Named("debug")
    public boolean provideDebug() {
        return debug;
    }

    @Provides
    @Singleton
    @Named("runUrlManager")
    public boolean provideRunUrlManager() {
        return runUrlManager;
    }


    @Provides
    @Singleton
    @Named("validate")
    public boolean provideValidate() {
        return validate;
    }

    @Provides
    @Singleton
    public List<DataValidator> provideDataValidators() {
        return dataValidators;
    }

    @Provides
    @Singleton
    public List<ParamsAdder> provideParamsAdders() {
        return paramsAdders;
    }

    @Provides
    @DatabaseInfo
    public String provideDbName() {
        return dbName;
    }

    @Provides
    @Singleton
    public DebugInterceptor.Level provideRequestsInterceptorLevel() {
        return level;
    }
}
