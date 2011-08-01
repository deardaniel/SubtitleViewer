cd ../../src
echo Compiling Java...
javac -d ../bin NativeMenuHider.java
echo Generating jni .h...
cd ../bin
javah -d ../jni/osx/ -jni NativeMenuHider
cd ../jni/osx/

echo Compiling C...
gcc -arch i386 -arch ppc -I/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Headers/ -c NativeMenuHider.c -o NativeMenuHider.o
gcc -m64 -arch i386 -arch ppc -I/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Headers/ -c NativeMenuHider.c -o NativeMenuHider64.o

echo Linking C...
libtool -dynamic -framework Carbon -lSystem -o ../../bin/libNativeMenuHider.jnilib NativeMenuHider.o NativeMenuHider64.o

lipo -info ../../bin/libNativeMenuHider.jnilib

echo Cleaning up...
rm NativeMenuHider.o
rm NativeMenuHider64.o

echo Done!
