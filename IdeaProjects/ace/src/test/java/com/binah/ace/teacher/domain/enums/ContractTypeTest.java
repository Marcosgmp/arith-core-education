package com.binah.ace.teacher.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTypeTest {

    @Test
    void testDefaultWorkload() {
        assertEquals(40, ContractType.FULL_TIME.getDefaultWorkload());
        assertEquals(20, ContractType.PART_TIME.getDefaultWorkload());
        assertEquals(0, ContractType.HOURLY.getDefaultWorkload());
        assertEquals(0, ContractType.SUBSTITUTE.getDefaultWorkload());
    }
}
