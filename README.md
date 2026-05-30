# Chess-Short-Notation

## Purpose
The goal of this project is to recreate the classic game of chess using short form algebraic notation as the terminal input.

## Running
Compile and run with JDK 21:

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java | Where-Object { -not $_.PSIsContainer -and $_.FullName -notlike '*\src\test\*' }).FullName
& "C:\Program Files\Eclipse Adoptium\jdk-21.0.0.35-hotspot\bin\java.exe" -cp out ui.Main
```

Enter moves such as `e4`, `Nf3`, `exd5`, `O-O`, or `a8=Q`. Enter `quit` or `q` to stop.

## Testing
Compile and run the JUnit test suite with the bundled console runner:

```powershell
javac -cp lib\junit-platform-console-standalone-1.9.3.jar -d out_test (Get-ChildItem -Recurse src -Filter *.java | Where-Object { -not $_.PSIsContainer }).FullName
& "C:\Program Files\Eclipse Adoptium\jdk-21.0.0.35-hotspot\bin\java.exe" -jar lib\junit-platform-console-standalone-1.9.3.jar --class-path out_test --scan-class-path
```
