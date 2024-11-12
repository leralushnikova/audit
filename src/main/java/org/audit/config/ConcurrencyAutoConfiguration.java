package org.audit.config;

import org.audit.aspect.AuditAspect;
import org.audit.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Класс авто-конфигураций
 */
@AutoConfiguration
public class ConcurrencyAutoConfiguration {

    private final DataSource dataSource;

    @Autowired
    public ConcurrencyAutoConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public AuditRepository auditRepository(){
        return new AuditRepository(dataSource);
    }
    @Bean
    public AuditAspect auditAspect() {
        return new AuditAspect(auditRepository());
    }
}
