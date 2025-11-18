@echo off
REM Simple script to run the Inventory Management System on Windows
REM Make sure sqlite-jdbc jar file is in the same directory

echo ========================================
echo  Inventory Management System
echo ========================================
echo.

REM Check if jar file exists
if not exist "sqlite-jdbc-3.51.0.0.jar" (
    echo ERROR: sqlite-jdbc-3.51.0.0.jar not found!
    echo Please make sure the SQLite JDBC driver is in this directory.
    pause
    exit /b 1
)

REM Compile if needed
if not exist "Implementation.class" (
    echo Compiling Java files...
    javac -cp "sqlite-jdbc-3.51.0.0.jar" *.java
    if errorlevel 1 (
        echo.
        echo Compilation failed!
        echo Please check for errors above.
        pause
        exit /b 1
    )
    echo Compilation successful!
    echo.
)

REM Run the application
echo Starting application...
echo.
java -cp ".;sqlite-jdbc-3.51.0.0.jar" Implementation

REM If we get here, the application has exited
echo.
echo Application closed.
pause

