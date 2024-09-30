import org.json.JSONObject;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class hackathonVsCode{

    public static void main(String[] args) {
        try {
            // Read the JSON file
            JSONObject testCase1 = readJsonFile("testcase1.json");
            JSONObject testCase2 = readJsonFile("testcase2.json");

            // Solve the first test case
            System.out.println("Test Case 1:");
            findSecretAndWrongPoints(testCase1);

            // Solve the second test case
            System.out.println("Test Case 2:");
            findSecretAndWrongPoints(testCase2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to read JSON file
    public static JSONObject readJsonFile(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        StringBuilder jsonBuilder = new StringBuilder();
        int i;
        while ((i = reader.read()) != -1) {
            jsonBuilder.append((char) i);
        }
        reader.close();
        return new JSONObject(jsonBuilder.toString());
    }

    // Function to decode y values and find the constant term
    public static void findSecretAndWrongPoints(JSONObject jsonObject) {
        int n = jsonObject.getJSONObject("keys").getInt("n");
        int k = jsonObject.getJSONObject("keys").getInt("k");

        ArrayList<BigInteger> xValues = new ArrayList<>();
        ArrayList<BigInteger> yValues = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (jsonObject.has(String.valueOf(i))) {
                int x = i;
                String base = jsonObject.getJSONObject(String.valueOf(i)).getString("base");
                String value = jsonObject.getJSONObject(String.valueOf(i)).getString("value");

                // Decode the value from the given base
                BigInteger decodedY = new BigInteger(value, Integer.parseInt(base));

                xValues.add(BigInteger.valueOf(x));
                yValues.add(decodedY);
            }
        }

        // Solve for the constant term 'c' using Lagrange interpolation
        BigInteger secret = lagrangeInterpolation(BigInteger.ZERO, xValues, yValues, k);

        System.out.println("Secret (constant term) of polynomial: " + secret);

        // For the second test case, detect wrong points
        if (n > k) {
            ArrayList<BigInteger> wrongPoints = findWrongPoints(xValues, yValues, k);
            if (wrongPoints.size() > 0) {
                System.out.println("Wrong points detected: " + wrongPoints);
            } else {
                System.out.println("No wrong points detected.");
            }
        }
    }

    // Function for Lagrange interpolation
    public static BigInteger lagrangeInterpolation(BigInteger x, ArrayList<BigInteger> xValues, ArrayList<BigInteger> yValues, int k) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < k; i++) {
            BigInteger term = yValues.get(i);

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    term = term.multiply(x.subtract(xValues.get(j)));
                    term = term.divide(xValues.get(i).subtract(xValues.get(j)));
                }
            }

            result = result.add(term);
        }

        return result;
    }

    // Function to find wrong points on the curve
    public static ArrayList<BigInteger> findWrongPoints(ArrayList<BigInteger> xValues, ArrayList<BigInteger> yValues, int k) {
        ArrayList<BigInteger> wrongPoints = new ArrayList<>();
        
        // Iterate over all points and check if they satisfy the polynomial equation
        for (int i = k; i < xValues.size(); i++) {
            BigInteger xi = xValues.get(i);
            BigInteger yi = yValues.get(i);

            // Calculate the expected y value using Lagrange interpolation
            BigInteger expectedY = lagrangeInterpolation(xi, xValues, yValues, k);

            // If the actual y doesn't match the expected y, mark this point as wrong
            if (!yi.equals(expectedY)) {
                wrongPoints.add(xi);
            }
        }

        return wrongPoints;
    }
}