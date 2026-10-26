package edu.mctc.gradebook;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Example tests to show the structure. Lab 9 asks for at least 15 test cases:
 * normal inputs, boundary values and error conditions. Add yours below.
 *
 * Run from lab09/starter:   mvn test
 * Coverage report:          target/site/jacoco/index.html
 */
class GradingTest {

    @Test
    void letterGrade_normalScore_returnsB() {
        assertEquals("B", Grading.letterGrade(85));
    }

    @Test
    void letterGrade_above100_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Grading.letterGrade(100.5));
    }

    @Test
    void letterGrade_exactly93_returnsA() {
        assertEquals("A", Grading.letterGrade(93));
    }

    @Test
    void letterGrade_justBelow93_returnsAMinus() {
        assertEquals("A-", Grading.letterGrade( 92.99));
    }

    @Test
    void letterGrade_exactly90_returnsAMinus() {
        assertEquals("A-", Grading.letterGrade( 90));
    }

    @Test
    void letterGrade_perfectScore_returnsA() {
        assertEquals("A", Grading.letterGrade( 100));
    }

    @Test
    void letterGrade_exactly60_returnsD() {
        assertEquals("D", Grading.letterGrade( 60));
    }

    @Test
    void letterGrade_justBelow60_returnsF() {
        assertEquals("F", Grading.letterGrade(59.99));
    }

    @Test
    void letterGrade_zero_returnsF() {
        assertEquals("F", Grading.letterGrade( 0));
    }

    @Test
    void letterGrade_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.letterGrade( -1));
    }

    @Test
    void letterGrade_nan_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.letterGrade(Double.NaN));
    }

    //gradePoints

    @Test
    void gradePoints_bPlus_returns333() {
        assertEquals(3.33, Grading.gradePoints("B+"), 0.001);
    }

    @Test
    void gradePoints_a_returns4() {
        assertEquals(4.0, Grading.gradePoints("A"), 0.001);
    }

    @Test
    void gradePoints_c_returns2() {
        assertEquals(2.0, Grading.gradePoints("C"), 0.001);
    }

    @Test 
    void gradePoints_f_returns0() {
        assertEquals(0.0, Grading.gradePoints("F"), 0.001);
    }

    @Test
    void gradePoints_lowercase_returnsSamePoints() {
        assertEquals( 3.0, Grading.gradePoints("b"), 0.001);
    }

    @Test
    void gradePoints_spacesIgnored_returnsSamePoints() {
        assertEquals(3.0, Grading.gradePoints(" B "), 0.001);
    }

    @Test
    void gradePoints_unknownLetter_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.gradePoints("E"));
    }

    @Test
    void gradePoints_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () -> Grading.gradePoints(null));
    }

    //dropLowest

    @Test
    void dropLowest_oneLowest_removed() {
        assertEquals(List.of(80.0, 90.0), Grading.dropLowest(List.of(70.0, 80.0, 90.0), 1));
    }

    @Test
    void dropLowest_zero_returnsSameScores() {
        assertEquals(List.of(70.0, 80.0, 90.0), Grading.dropLowest(List.of(70.0, 80.0, 90.0), 0));
    }

    @Test
    void dropLowest_keepsOriginalOrder() {
        assertEquals(List.of(90.0, 80.0), Grading.dropLowest(List.of(90.0, 70.0, 80.0), 1));
    }

    @Test
    void dropLowest_negativeN_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.dropLowest(List.of(70.0, 80.0, 90.0), -1));
    }

    @Test
    void dropLowest_dropEveryScore_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.dropLowest(List.of(70.0, 80.0, 90.0), 3));
    }

    @Test
    void dropLowest_scoreOutOfRange_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.dropLowest(List.of(101.0, 80.0, 90.0), 1));
    }

    //weightedAverage

    @Test
    void weightedAverage_normalCase_returns837() {
        assertEquals(83.7, Grading.weightedAverage(
            Map.of("labs", List.of(90.0, 85.0), "exams", List.of(78.0)),
            Map.of("labs", 0.6, "exams", 0.4)), 0.001);
    }

    @Test
    void weightedAverage_weightsNotOne_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.weightedAverage(
            Map.of("labs", List.of(90.0)), Map.of("labs", 0.9)));
    }

    @Test
    void weightedAverage_negativeWeight_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.weightedAverage(
            Map.of("labs", List.of(90.0), "exams", List.of(80.0)), 
            Map.of("labs", -0.1, "exams", 1.1)));
    }

    @Test
    void weightedAverage_categoryWithoutWeight_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.weightedAverage(
            Map.of("labs", List.of(90.0), "quizzes", List.of(80.0)),
            Map.of("labs", 1.0)));
    }

    @Test
    void weightedAverage_categoryWithNoScores_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.weightedAverage(
            Map.of("labs", List.of(90.0), "exams", List.of()),
            Map.of("labs", 0.6, "exams", 0.4)));
    }

    @Test
    void weightedAverage_emptyWeights_throws() {
        assertThrows(IllegalArgumentException.class, () -> Grading.weightedAverage(
            Map.of("labs", List.of(90.0)),
            Map.of()));
    }
    // TODO: boundary values (exactly 93? 92.99? 0? 100?), dropLowest, gradePoints,
    //       weightedAverage, RosterLoader, StudentRepository, GradebookService ...
}
