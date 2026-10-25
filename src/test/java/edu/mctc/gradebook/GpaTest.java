package edu.mctc.gradebook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class GpaTest {
    @Test
    void gpa_singleCourse_returnsThatCoursesPoints() {
        assertEquals(4.0, Grading.gpa(List.of("A"), List.of(3.0)), 0.001);
    }
    
    @Test
    void gpa_equalCredits_returnsSimpleAverage() {
        assertEquals(3.5, Grading.gpa(List.of("A", "B"), List.of(3.0, 3.0)));
    }

    @Test
    void gpa_moreCredits_weighsHeavier() {
        assertEquals(3.25, Grading.gpa(List.of("A", "B"), List.of(1.0, 3.0)));
    }

    @Test
    void gpa_mismatchedSizes_throws() {
        assertThrows(IllegalArgumentException.class, 
            () -> Grading.gpa(List.of("A", "B"), List.of(3.0)));
    }
}
