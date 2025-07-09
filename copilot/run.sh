#!/bin/bash

echo "Starting Digital Bank Application..."
echo

# Check if MySQL connector exists
if [ ! -f "mysql-connector-j-8.0.33.jar" ]; then
    echo "Error: MySQL connector JAR not found!"
    echo "Please download mysql-connector-j-8.0.33.jar and place it in this directory."
    echo "Download from: https://dev.mysql.com/downloads/connector/j/"
    exit 1
fi

# Check if build directory exists
if [ ! -d "build" ]; then
    echo "Build directory not found. Running build first..."
    ./build.sh
    if [ $? -ne 0 ]; then
        echo "Build failed!"
        exit 1
    fi
fi

# Run the application
echo "Starting application..."
java -cp "mysql-connector-j-8.0.33.jar:build" com.wipro.bank.BankApplication

if [ $? -ne 0 ]; then
    echo "Application terminated with error code $?"
fi

echo
echo "Application finished."
