package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
@ClauseDefinition(clause = 'a', def = "t1arr[0] < 0")
@ClauseDefinition(clause = 'b', def = "t1arr[0] + t1arr[1] < t1arr[2]")
@ClauseDefinition(clause = 'c', def = "t1arr[0] != t2arr[0]")
@ClauseDefinition(clause = 'd', def = "t1arr[1] != t2arr[1]")
@ClauseDefinition(clause = 'e', def = " t1arr[2] != t2arr[2]")
class TriCongruenceTest {
	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	@Test
	public void sampleTest() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	public boolean line_14_predicate(double[] t1arr, double[] t2arr){
		return (t1arr[0] != t2arr[0] || t1arr[1] != t2arr[1] || t1arr[2] != t2arr[2]);
	}

	public boolean line_15_predicate(double[] t1arr){
		return (t1arr[0] < 0 || t1arr[0] + t1arr[1] < t1arr[2]);
	}

	@ClauseCoverage(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
		}
	)
	@Test
	public void line_15_predicate_covering_both_clauses_true_value_clause_coverage_test(){
		double[] testInput = {-1, 2, 3};
		assertTrue(line_15_predicate(testInput));
	}

	@ClauseCoverage(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
		}
	)
	@Test
	public void line_15_predicate_covering_both_clauses_false_value_clause_coverage_test(){
		double[] testInput = {1, 2, 3};
		assertFalse(line_15_predicate(testInput));
	}

	@CACC(
		predicate = "a + b",
		majorClause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = true
	)
	@Test
	public void line_15_predicate_major_clause_a_evaluating_to_true_CACC_coverage(){
		double[] testInput = {-1, 5, 3};
		assertTrue(line_15_predicate(testInput));
	}

	@CACC(
		predicate = "a + b",
		majorClause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void line_15_predicate_test_major_clause_a_evaluating_to_false_CACC_coverage(){
		double[] testInput = {1, 2, 3};
		assertFalse(line_15_predicate(testInput));
	}

	@CACC(
		predicate = "a + b",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true)
		},
		predicateValue = true
	)
	@Test
	public void line_15_predicate_major_clause_b_evaluating_to_true_CACC_coverage(){
		double[] testInput = {1, 2, 4};
		assertTrue(line_15_predicate(testInput));
	}

	@CACC(
		predicate = "a + b",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void line_15_predicate_test_major_clause_b_evaluating_to_false_CACC_coverage(){
		double[] testInput = {1, 2, 3};
		assertFalse(line_15_predicate(testInput));
	}

	@UniqueTruePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'c', valuation = true),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void line_14_predicate_test_unique_true_point_on_implicant_c_CUTPNFP_coverage(){
		double[] testInput1 = {1, 3, 4};
		double[] testInput2 = {2, 3, 4};
		assertTrue(line_14_predicate(testInput1, testInput2));
	}

	@UniqueTruePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "d",
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void line_14_predicate_test_unique_true_point_on_implicant_d_CUTPNFP_coverage(){
		double[] testInput1 = {1, 2, 4};
		double[] testInput2 = {1, 3, 4};
		assertTrue(line_14_predicate(testInput1, testInput2));
	}

	@UniqueTruePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "e",
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true)
		}
	)
	@Test
	public void line_14_predicate_test_unique_true_point_on_implicant_e_CUTPNFP_coverage(){
		double[] testInput1 = {1, 2, 3};
		double[] testInput2 = {1, 2, 4};
		assertTrue(line_14_predicate(testInput1, testInput2));
	}

	@NearFalsePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "c",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void line_14_predicate_test_near_false_point_on_implicant_and_clause_c_CUTPNFP_coverage(){
		double[] testInput1 = {1, 2, 3};
		double[] testInput2 = {1, 2, 3};
		assertFalse(line_14_predicate(testInput1, testInput2));
	}

	@NearFalsePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "d",
		clause = 'd',
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void line_14_predicate_test_near_false_point_on_implicant_and_clause_d_CUTPNFP_coverage(){
		double[] testInput1 = {1, 2, 3};
		double[] testInput2 = {1, 2, 3};
		assertFalse(line_14_predicate(testInput1, testInput2));
	}

	@NearFalsePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "e",
		clause = 'e',
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void line_14_predicate_test_near_false_point_on_implicant_and_clause_e_CUTPNFP_coverage(){
		double[] testInput1 = {1, 2, 3};
		double[] testInput2 = {1, 2, 3};
		assertFalse(line_14_predicate(testInput1, testInput2));
	}


	/*

	*/
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = (a & b) | (c & d) | (d & e);
		return predicate;
	}

	@UniqueTruePoint(
		predicate = "~(ab + cd + de)",
		dnf = "~a~d + ~b~d + ~a~c~e + ~b~c~e",
		implicant = "~a~d",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = true),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true)
		}
	)
	@Test
	public void unique_true_point_UTPC(){
		assertFalse(questionTwo(false, true, true, false, true));
	}


}
