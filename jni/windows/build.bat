cd ..\..\src
echo Compiling Java...
javac -d ../bin/ NativeMenuHider.java
echo Generating jni .h...
cd ..\bin
javah -d ../jni/win32/ -jni NativeMenuHider
cd ..\jni\win32\

call "C:\Program Files (x86)\Microsoft Visual Studio 8\VC\bin\vcvars32.bat"


cl -I"C:\Program Files (x86)\Java\jdk1.6.0_07\include\win32" -I"C:\Program Files (x86)\Java\jdk1.6.0_07\include" /nologo /MT /LD NativeMenuHider.c -FeNativeMenuHider.dll /link user32.lib

move NativeMenuHider.dll ..\..\bin\NativeMenuHider.dll
del NativeMenuHider.lib NativeMenuHider.obj NativeMenuHider.exp NativeMenuHider.dll.manifest

call "C:\Program Files (x86)\Microsoft Visual Studio 8\VC\vcvarsall.bat" amd64

cl -I"C:\Program Files (x86)\Java\jdk1.6.0_07\include\win32" -I"C:\Program Files (x86)\Java\jdk1.6.0_07\include" /nologo /MT /LD NativeMenuHider.c -FeNativeMenuHider.dll /link user32.lib

move NativeMenuHider.dll ..\..\bin\NativeMenuHider64.dll
del NativeMenuHider.lib NativeMenuHider.obj NativeMenuHider.exp NativeMenuHider.dll.manifest

