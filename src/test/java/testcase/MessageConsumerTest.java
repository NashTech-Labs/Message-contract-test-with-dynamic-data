package testcase;


import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit.MessagePactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import au.com.dius.pact.core.model.messaging.MessagePact;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Tag("annotations")
@Tag("junit5")
@PactFolder("Message-Pact")

public class MessageConsumerTest {

    @Rule
    public MessagePactProviderRule mockProvider = new MessagePactProviderRule(this);

    private static Map<String, Object> jsonMap = new HashMap<>();
    String file = "EmployeeCreatedEvent_2bb56706-fcdd-420e-a707-66507f5f3bd9.json";

    @Pact(provider = "producer", consumer = "consumer")
    public MessagePact userCreatedMessagePact(MessagePactBuilder builder) throws IOException {
        PactDslJsonBody body = new PactDslJsonBody();
        ObjectMapper objectMapper = new ObjectMapper();
        jsonMap = objectMapper.readValue(new File(file),
                new TypeReference<Map<String, Object>>() {
                });
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            body.stringValue(entry.getKey(), entry.getValue().toString());
        }

        MessagePact a_user_created_message = builder
                .expectsToReceive("a user created message")
                .withContent(body)
                .toPact();

        return a_user_created_message;
    }

    @Test
    @PactVerification("userCreatedMessagePact")
    public void verifyCreatedPact() {
        assertEquals("432cc207-40d4-469f-b062-97841dfc7197", jsonMap.get("id"));
    }
}
