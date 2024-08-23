package assign01;

/**
 * This class represents a simple row or column vector of numbers. In a row
 * vector, the numbers are written horizontally (i.e., along the columns). In a
 * column vector, the numbers are written vertically (i.e., along the rows).
 * 
 * @author Erin Parker & Harry Kim
 * @version August 26, 2020
 */
public class MathVector {

	// 2D array to hold the numbers of the vector, either along the columns or rows
	private double[][] data;
	// set to true for a row vector and false for a column vector
	private boolean isRowVector;
	// count of elements in the vector
	private int vectorSize;

	/**
	 * Creates a new row or column vector. For a row vector, the input array is
	 * expected to have 1 row and a positive number of columns, and this number of
	 * columns represents the vector's length. For a column vector, the input array
	 * is expected to have 1 column and a positive number of rows, and this number
	 * of rows represents the vector's length.
	 * 
	 * @param data - a 2D array to hold the numbers of the vector
	 * @throws IllegalArgumentException if the numbers of rows and columns in the
	 *                                  input 2D array is not compatible with a row
	 *                                  or column vector
	 */
	public MathVector(double[][] data) {
		if (data.length == 0)
			throw new IllegalArgumentException("Number of rows must be positive.");
		if (data[0].length == 0)
			throw new IllegalArgumentException("Number of columns must be positive.");

		if (data.length == 1) {
			// This is a row vector with length = number of columns.
			this.isRowVector = true;
			this.vectorSize = data[0].length;
		} else if (data[0].length == 1) {
			// This is a column vector with length = number of rows.
			this.isRowVector = false;
			this.vectorSize = data.length;
		} else
			throw new IllegalArgumentException("Either the number of rows or the number of columns must be 1.");

		// Create the array and copy data over.
		if (this.isRowVector)
			this.data = new double[1][vectorSize];
		else
			this.data = new double[vectorSize][1];
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				this.data[i][j] = data[i][j];
			}
		}
	}

	/**
	 * Determines whether this vector is "equal to" another vector, where equality
	 * is defined as both vectors being row (or both being column), having the same
	 * vector length, and containing the same numbers in the same positions.
	 * 
	 * @param other - another vector to compare
	 */
	public boolean equals(Object other) {
		if (!(other instanceof MathVector))
			return false;

		MathVector otherVec = (MathVector) other;

		// If this is a row vector with length = number of columns.
		if (isRowVector && otherVec.isRowVector) {
			if (data[0].length != otherVec.vectorSize) {
				return false;
			}
			for (int i = 0; i < data[0].length; i++) {
				if (data[0][i] != otherVec.data[0][i]) {
					return false;
				}
			}
		}

		// If this is a column vector with length = number of rows.
		else if (!isRowVector && !otherVec.isRowVector) {
			if (data.length != otherVec.vectorSize) {
				return false;
			}
			for (int i = 0; i < data.length; i++) {
				if (data[i][0] != otherVec.data[i][0]) {
					return false;
				}
			}
		}

		// Returns false if the compared vectors are not the same type.
		else {
			return false;
		}
		return true;
	}

	/**
	 * Generates a returns a new vector that is the transposed version of this
	 * vector.
	 */
	public MathVector transpose() {
		MathVector transposedVector = new MathVector(new double[1][1]);

		// If this is a row vector with length = number of columns.
		if (isRowVector) {
			MathVector rowToColumnVector = new MathVector(new double[data[0].length][1]);
			for (int i = 0; i < data[0].length; i++) {
				rowToColumnVector.data[i][0] = data[0][i];
			}
			transposedVector = rowToColumnVector;
		}

		// If this is a column vector with length = number of rows.
		else if (!isRowVector) {
			MathVector columnToRowVector = new MathVector(new double[1][data.length]);
			for (int i = 0; i < data.length; i++) {
				columnToRowVector.data[0][i] = data[i][0];
			}
			transposedVector = columnToRowVector;
		}
		return transposedVector;
	}

	/**
	 * Generates and returns a new vector representing the sum of this vector and
	 * another vector.
	 * 
	 * @param other - another vector to be added to this vector
	 * @throws IllegalArgumentException if the other vector and this vector are not
	 *                                  both row vectors of the same length or
	 *                                  column vectors of the same length
	 */
	public MathVector add(MathVector other) {
		MathVector finalVector = new MathVector(new double[1][1]);

		// If this is a row vector with length = number of columns.
		if (isRowVector && other.isRowVector) {
			if (data[0].length == other.vectorSize) {
				// normalizedRowVector.data[0][i] = data[0][i] / magnitude;
				MathVector addingRowVector = new MathVector(new double[1][data[0].length]);
				for (int i = 0; i < data[0].length; i++) {
					addingRowVector.data[0][i] = data[0][i] + other.data[0][i];
				}
				finalVector = addingRowVector;
			} else {
				throw new IllegalArgumentException("other vector and this vector are not both row vectors of the same length or column vectors of the same length");
			}
		}

		// If this is a column vector with length = number of rows.
		else if (!isRowVector && !other.isRowVector) {
			if (data.length == other.vectorSize) {
				// normalizedRowVector.data[0][i] = data[0][i] / magnitude;
				MathVector addingColumnVector = new MathVector(new double[data.length][1]);
				for (int i = 0; i < data.length; i++) {
					addingColumnVector.data[i][0] = data[i][0] + other.data[i][0];
				}
				finalVector = addingColumnVector;
			} else {
				throw new IllegalArgumentException("other vector and this vector are not both row vectors of the same length or column vectors of the same length");
			}
		} else {
			throw new IllegalArgumentException("other vector and this vector are not both row vectors of the same length or column vectors of the same length");
		}
		return finalVector;
	}

	/**
	 * Computes and returns the dot product of this vector and another vector.
	 * 
	 * @param other - another vector to be combined with this vector to produce the
	 *              dot product
	 * @throws IllegalArgumentException if the other vector and this vector are not
	 *                                  both row vectors of the same length or
	 *                                  column vectors of the same length
	 */
	public double dotProduct(MathVector other) {
		// MathVector finalVector = new MathVector(new double[1][1]);
		double sumOfProducts = 0.0;

		// If this is a row vector with length = number of columns.
		if (isRowVector && other.isRowVector) {
			if (data[0].length == other.vectorSize) {
				// normalizedRowVector.data[0][i] = data[0][i] / magnitude;
				MathVector productRowVector = new MathVector(new double[1][data[0].length]);
				for (int i = 0; i < data[0].length; i++) {
					productRowVector.data[0][i] = data[0][i] * other.data[0][i];
					sumOfProducts = sumOfProducts + productRowVector.data[0][i];
				}
			} else {
				throw new IllegalArgumentException("other vector and this vector are not both row vectors of the same length or column vectors of the same length");
			}
		}

		// If this is a column vector with length = number of rows.
		else if (!isRowVector && !other.isRowVector) {
			if (data.length == other.vectorSize) {
				// normalizedRowVector.data[0][i] = data[0][i] / magnitude;
				MathVector productColumnVector = new MathVector(new double[data.length][1]);
				for (int i = 0; i < data.length; i++) {
					productColumnVector.data[i][0] = data[i][0] * other.data[i][0];
					sumOfProducts = sumOfProducts + productColumnVector.data[i][0];
				}
			} else {
				throw new IllegalArgumentException("other vector and this vector are not both row vectors of the same length or column vectors of the same length");
			}
		} else {
			throw new IllegalArgumentException("other vector and this vector are not both row vectors of the same length or column vectors of the same length");
		}
		return sumOfProducts;
	}

	/**
	 * Computes and returns this vector's magnitude (also known as a vector's
	 * length) .
	 */
	public double magnitude() {
		double finalMagnitude = 0;

		// If this is a row vector with length = number of columns.
		if (isRowVector) {
			for (int i = 0; i < data[0].length; i++) {
				finalMagnitude = finalMagnitude + (data[0][i] * data[0][i]);
			}
			finalMagnitude = Math.sqrt(finalMagnitude);
		}

		// If this is a column vector with length = number of rows.
		else if (!isRowVector) {
			for (int i = 0; i < data.length; i++) {
				finalMagnitude = finalMagnitude + (data[i][0] * data[i][0]);
			}
			finalMagnitude = Math.sqrt(finalMagnitude);
		}
		return finalMagnitude;
	}

	/**
	 * Generates and returns a normalized version of this vector.
	 */
	public MathVector normalize() {
		MathVector originalVector = new MathVector(data);
		MathVector normalizedVector = new MathVector(new double[1][1]);
		double magnitude = originalVector.magnitude();

		// If this is a row vector with length = number of columns.
		if (isRowVector) {
			MathVector normalizedRowVector = new MathVector(new double[1][data[0].length]);
			for (int i = 0; i < data[0].length; i++) {
				normalizedRowVector.data[0][i] = data[0][i] / magnitude;
			}
			normalizedVector = normalizedRowVector;
		}

		// If this is a column vector with length = number of rows.
		else if (!isRowVector) {
			MathVector columnToRowVector = new MathVector(new double[data.length][1]);
			for (int i = 0; i < data.length; i++) {
				columnToRowVector.data[i][0] = data[i][0] / magnitude;
			}
			normalizedVector = columnToRowVector;
		}
		return normalizedVector;
	}

	/**
	 * Generates and returns a textual representation of this vector. For example,
	 * "1.0 2.0 3.0 4.0 5.0" for a sample row vector of length 5 and "1.0 2.0 3.0
	 * 4.0 5.0" for a sample column vector of length 5. In both cases, notice the
	 * lack of a newline or space after the last number.
	 */
	public String toString() {
		String stringVector = "" + data[0][0];
		// If this is a row vector with length = number of columns.
		if (isRowVector) {
			for (int i = 1; i < data[0].length; i++) {
				double a = data[0][i];
				stringVector = stringVector + " " + a;
			}
		}
		// If this is a column vector with length = number of rows.
		else if (!isRowVector) {
			for (int i = 1; i < data.length; i++) {
				double a = data[i][0];
				stringVector = stringVector + "\n" + a;
			}
		}

		return stringVector;
	}
}