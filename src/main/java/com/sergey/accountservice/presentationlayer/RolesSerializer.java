package com.sergey.accountservice.presentationlayer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sergey.accountservice.dto.signin.AuthUserResponseDto;

import java.io.IOException;

/**
 * Special serializer for roles lists. Makes them always wrapped as json arrays
 * Even list contains only one element
 */
public class RolesSerializer extends StdSerializer<AuthUserResponseDto> {

    public RolesSerializer() {
        this(null);
    }


    protected RolesSerializer(Class<AuthUserResponseDto> t) {
        super(t);
    }

    @Override
    public void serialize(AuthUserResponseDto value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("name", value.getName());
            gen.writeStringField("lastname", value.getLastname());
            gen.writeStringField("email", value.getEmail());
            gen.writeArrayFieldStart("roles");
        for (String role : value.getRoles()) {
            gen.writeString(role);
        }
            gen.writeEndArray();
            gen.writeEndObject();
        }

    }

