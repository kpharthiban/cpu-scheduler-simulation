@echo off
set OUTPUT_DIR=bin

:: Check if the output directory exists and delete old .class files
if exist %OUTPUT_DIR% (
    echo Cleaning old .class files...
    rmdir /s /q %OUTPUT_DIR%
)

:: Create a new output directory
mkdir %OUTPUT_DIR%

:: Compile all Java files and store .class files in OUTPUT_DIR
echo Compiling Java files...
javac -d %OUTPUT_DIR% *.java

:: Check if compilation was successful
if %ERRORLEVEL% == 0 (
    echo Compilation successful!
    echo All .class files are stored in the "%OUTPUT_DIR%" directory.
) else (
    echo Compilation failed. Please check your code for errors.
    exit /b 1
)

:: Ask user if they want to run the program
set /p RUN_PROGRAM=Do you want to run the program? (y/n) 
if /I "%RUN_PROGRAM%"=="y" (
    echo Running Main...
    java -cp %OUTPUT_DIR% Main
)
