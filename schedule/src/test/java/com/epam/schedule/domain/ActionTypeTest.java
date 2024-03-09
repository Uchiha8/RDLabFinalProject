package com.epam.schedule.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ActionTypeTest {

    @Test
    void testEnumValues() {
        // Arrange
        ActionType create = ActionType.CREATE;
        ActionType delete = ActionType.DELETE;

        // Act & Assert
        assertEquals("CREATE", create.name());
        assertEquals("DELETE", delete.name());
        assertEquals("CREATE", create.toString());
        assertEquals("DELETE", delete.toString());
        assertEquals(ActionType[] .class, ActionType.values().getClass());
        assertSame(create, ActionType.valueOf("CREATE"));
        assertSame(delete, ActionType.valueOf("DELETE"));
    }
}
