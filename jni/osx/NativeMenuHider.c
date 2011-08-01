#include <stdio.h>
#include <Carbon/Carbon.h>
#include "NativeMenuHider.h"

JNIEXPORT void JNICALL Java_NativeMenuHider_hideMenuJNI
  (JNIEnv * env, jobject o)
{
	SetSystemUIMode(kUIModeAllHidden, kUIOptionAutoShowMenuBar);
}

JNIEXPORT void JNICALL Java_NativeMenuHider_showMenuJNI
  (JNIEnv * env, jobject o)
{
	SetSystemUIMode(kUIModeNormal, 0);
}
