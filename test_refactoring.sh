#!/bin/bash

# Test script to verify both implementations produce same output

echo "======================================"
echo "CSE352 Assignment 5 - Verification Test"
echo "======================================"
echo ""

# Test original code
echo "1. Testing ORIGINAL code..."
cd src
javac DataProcessor.java ReportGenerator.java 2>&1
if [ $? -ne 0 ]; then
    echo "ERROR: Original code failed to compile"
    exit 1
fi

echo "   Compilation successful ✓"
echo ""
echo "   Running original code:"
echo "   ---------------------"
java DataProcessor > ../original_output.txt 2>&1
cat ../original_output.txt
echo ""

# Test refactored code
cd ../src-refactored
echo "2. Testing REFACTORED code..."
javac com/assignment5/model/*.java com/assignment5/formatter/*.java 2>&1
if [ $? -ne 0 ]; then
    echo "ERROR: Refactored code failed to compile"
    exit 1
fi

echo "   Compilation successful ✓"
echo ""
echo "   Running refactored code:"
echo "   -----------------------"
java com.assignment5.model.DataProcessor > ../refactored_output.txt 2>&1
cat ../refactored_output.txt
echo ""

# Compare outputs
cd ..
echo "3. Comparing outputs..."
echo "   ---------------------"

# Extract PDF sections
grep -A 8 "PDF Report:" original_output.txt > original_pdf.txt
grep -A 8 "PDF Report:" refactored_output.txt > refactored_pdf.txt

# Extract CSV sections  
grep -A 1 "CSV Report:" original_output.txt | tail -1 > original_csv.txt
grep -A 1 "CSV Report:" refactored_output.txt | tail -1 > refactored_csv.txt

# Compare
echo -n "   PDF output match: "
if diff -q original_pdf.txt refactored_pdf.txt > /dev/null 2>&1; then
    echo "✓ PASS"
else
    echo "✗ FAIL"
    exit 1
fi

echo -n "   CSV output match: "
if diff -q original_csv.txt refactored_csv.txt > /dev/null 2>&1; then
    echo "✓ PASS"
else
    echo "✗ FAIL"
    exit 1
fi

# Cleanup
rm -f original_output.txt refactored_output.txt
rm -f original_pdf.txt refactored_pdf.txt
rm -f original_csv.txt refactored_csv.txt

echo ""
echo "======================================"
echo "✓ All tests PASSED"
echo "✓ Behavior is preserved"
echo "✓ Refactoring is successful"
echo "======================================"
