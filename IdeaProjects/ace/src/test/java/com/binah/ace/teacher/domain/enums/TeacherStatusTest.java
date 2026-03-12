package com.binah.ace.teacher.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TeacherStatusTest {
    @Test
    void testIsActive() {
        assertTrue(TeacherStatus.ACTIVE.isActive());
        assertFalse(TeacherStatus.INACTIVE.isActive());
        assertFalse(TeacherStatus.ON_LEAVE.isActive());
        assertFalse(TeacherStatus.TERMINATED.isActive());
    }

    @Test
    void testCanTeach() {
        assertTrue(TeacherStatus.ACTIVE.canTeach());
        assertFalse(TeacherStatus.INACTIVE.canTeach());
    }
}