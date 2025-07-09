#!/bin/bash

echo "Building Digital Bank Application..."
echo

# Set paths
SRC_DIR="src/main/java"
OUT_DIR="build"
MAIN_CLASS="com.wipro.bank.BankApplication"
JAR_NAME="BankApplication.jar"
MYSQL_JAR="mysql-connector-j-8.0.33.jar"

# Create build directory
mkdir -p "$OUT_DIR"

# Clean previous build
rm -rf "$OUT_DIR"/*.class
rm -rf "$OUT_DIR"/com

echo "Compiling Java source files..."
javac -cp "$MYSQL_JAR" -d "$OUT_DIR" -sourcepath "$SRC_DIR" \
    "$SRC_DIR"/com/wipro/bank/*.java \
    "$SRC_DIR"/com/wipro/bank/model/*.java \
    "$SRC_DIR"/com/wipro/bank/dao/*.java \
    "$SRC_DIR"/com/wipro/bank/service/*.java \
    "$SRC_DIR"/com/wipro/bank/util/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo "Compilation successful!"

# Create manifest file
echo "Main-Class: $MAIN_CLASS" > "$OUT_DIR/MANIFEST.MF"
echo "Class-Path: $MYSQL_JAR" >> "$OUT_DIR/MANIFEST.MF"
echo "" >> "$OUT_DIR/MANIFEST.MF"

echo "Creating JAR file..."
cd "$OUT_DIR"
jar cfm "$JAR_NAME" MANIFEST.MF com/

if [ $? -ne 0 ]; then
    echo "JAR creation failed!"
    cd ..
    exit 1
fi

cd ..
echo "JAR file created successfully: $OUT_DIR/$JAR_NAME"

echo
echo "To run the application:"
echo "1. Ensure MySQL is running"
echo "2. Place $MYSQL_JAR in the same directory as the JAR"
echo "3. Run: java -jar $OUT_DIR/$JAR_NAME"
echo
echo "Build completed successfully!"
