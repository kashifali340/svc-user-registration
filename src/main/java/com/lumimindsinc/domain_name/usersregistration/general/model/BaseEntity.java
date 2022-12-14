package com.lumimindsinc.domain_name.usersregistration.general.model;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
/*import com.google.gson.Gson;
import com.google.gson.GsonBuilder;*/
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /*@Getter
    protected static Gson gson;

    private static GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializers.DateDeserializer())
            .registerTypeAdapter(Date.class, new DateSerializer()).setPrettyPrinting();

    protected static void initGson() {
        BaseEntity.gson = builder.create();
    }
*/
    @Column(name = "uuid", updatable = false, unique = true, nullable = false, length = 38)
    protected String uuid;

    protected BaseEntity() {
        this.uuid = UUID.randomUUID().toString();
    }

}
