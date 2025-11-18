#!/bin/bash
# Shell script to compile and run the Inventory Management System
# Make sure sqlite-jdbc.jar is in the same directory as this script

echo "Compiling Java files..."
javac -cp "sqlite-jdbc-*.jar:." *.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "Running Inventory Management System..."
    echo ""
    java -cp "sqlite-jdbc-*.jar:." Implementation
else
    echo "Compilation failed!"
    echo "Make sure you have:"
    echo "1. Java JDK installed"
    echo "2. sqlite-jdbc-X.X.X.jar file in this directory"
fi
