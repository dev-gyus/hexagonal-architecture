package com.example.hexagonal.config;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.TypeAliasAccessor;
import org.springframework.data.convert.TypeInformationMapper;
import org.springframework.data.mapping.Alias;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.UnaryOperator;

@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(uri);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        DefaultDbRefResolver resolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MongoMappingContext context = new MongoMappingContext();
        MappingMongoConverter converter = new MappingMongoConverter(resolver, context);
        converter.setTypeMapper(new CustomMongoTypeMapper("_cla222ss2222"));
        converter.afterPropertiesSet();
        return new MongoTemplate(mongoDatabaseFactory);
    }


    public static class CustomMongoTypeMapper extends DefaultMongoTypeMapper {
        private final TypeAliasAccessor<Bson> accessor;

        public CustomMongoTypeMapper() {
            accessor = new DocumentTypeAliasAccessor("_class");
        }



        public CustomMongoTypeMapper(String typeKey) {
            super(typeKey);
            this.accessor = new DocumentTypeAliasAccessor(typeKey);
        }

        public CustomMongoTypeMapper(String typeKey, MappingContext<? extends PersistentEntity<?, ?>, ?> mappingContext, TypeAliasAccessor<Bson> accessor) {
            super(typeKey, mappingContext);
            this.accessor = accessor;
        }

        public CustomMongoTypeMapper(String typeKey, MappingContext<? extends PersistentEntity<?, ?>, ?> mappingContext, UnaryOperator<Class<?>> writeTarget, TypeAliasAccessor<Bson> accessor) {
            super(typeKey, mappingContext, writeTarget);
            this.accessor = accessor;
        }

        public CustomMongoTypeMapper(String typeKey, List<? extends TypeInformationMapper> mappers, TypeAliasAccessor<Bson> accessor) {
            super(typeKey, mappers);
            this.accessor = accessor;
        }

        @Override
        public TypeInformation<?> readType(Bson source) {
            return null;
        }

        @Override
        public <T> TypeInformation<? extends T> readType(Bson source, TypeInformation<T> basicType) {
            return null;
        }

        @Override
        public void writeType(Class<?> type, Bson dbObject) {
            writeType(ClassTypeInformation.from(type), dbObject);
        }

        @Override
        public void writeType(TypeInformation<?> type, Bson sink) {
            Assert.notNull(type, "TypeInformation must not be null");

            Alias alias = getAliasFor(type);
            if (alias.isPresent()) {
                accessor.writeTypeTo(sink, alias.getValue());
            }
        }
    }

}
