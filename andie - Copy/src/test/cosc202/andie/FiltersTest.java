package cosc202.andie;

import cosc202.andie.filter.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FiltersTest {
  static final double sigma = 1.0 / 3.0;

  @BeforeAll
  static void setup() {}

  @Test
  void gaussianFunction() {
    BigDecimal zeroTest = new BigDecimal(GaussianFilter.Gaussian(0, 0, sigma));
    Assertions.assertEquals(
        1.43239450,
        zeroTest.setScale(8, RoundingMode.HALF_UP).doubleValue(),
        "Ensure validity of Gaussian function.");
    BigDecimal oneTest = new BigDecimal(GaussianFilter.Gaussian(1, 1, sigma));
    Assertions.assertEquals(
        0.00017677,
        oneTest.setScale(8, RoundingMode.HALF_UP).doubleValue(),
        "Ensure validity of Gaussian function.");
    BigDecimal negOneTest = new BigDecimal(GaussianFilter.Gaussian(-1, -1, sigma));
    Assertions.assertEquals(
        0.00017677,
        negOneTest.setScale(8, RoundingMode.HALF_UP).doubleValue(),
        "Ensure validity of Gaussian function.");
  }

  @Test
  void medianFinder() {
    Integer[] arr1 = {1, 2, 3, 4, 5, 6};
    Assertions.assertEquals(4, MedianFilter.median(arr1), "Ensure median function works correctly");
    Integer[] arr2 = {4, 1, 2, 6, 3, 7, 5};
    Assertions.assertEquals(4, MedianFilter.median(arr2), "Ensure median function works correctly");
  }
}
